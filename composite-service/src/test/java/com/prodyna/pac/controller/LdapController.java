package com.prodyna.pac.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.naming.Name;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.prodyna.pac.rest.exception.LdapPersonNotFound;
import com.prodyna.pac.service.ldap.LdapService;
import com.prodyna.pac.service.ldap.impl.HtmlRowLdapTreeVisitor;
import com.prodyna.pac.service.ldap.impl.LdapPerson;
import com.prodyna.pac.service.ldap.impl.LdapServiceImpl;
import com.prodyna.pac.service.ldap.impl.LdapTree;
import com.prodyna.pac.service.ldap.impl.LdapTreeBuilder;

/**
 * Controller for Ldap interaction
 * 
 */
@RestController
@RequestMapping("/ldap")
public class LdapController {

	@Autowired
	private LdapTreeBuilder ldapTreeBuilder;

	@Autowired
	private LdapService ldapService;

	@RequestMapping("/welcome.do")
	public void welcomeHandler() {
	}

	@RequestMapping(value = "/showTree.do", method = RequestMethod.GET)
	public ModelAndView showTree() {
		LdapTree ldapTree = ldapTreeBuilder.getLdapTree(LdapUtils.emptyLdapName());
		HtmlRowLdapTreeVisitor visitor = new PersonLinkHtmlRowLdapTreeVisitor();
		ldapTree.traverse(visitor);
		return new ModelAndView("showTree", "rows", visitor.getRows());
	}

	@RequestMapping(value = "/addPerson.do", method = RequestMethod.POST)
	public String addPerson() {
		LdapPerson person = getPerson();

		ldapService.create(person);
		return "redirect:/showTree.do";
	}

	@RequestMapping(value = "/updatePhoneNumber.do", method = RequestMethod.PUT)
	public String updatePhoneNumber(LdapPerson person) throws LdapPersonNotFound {

		LdapPerson localPerson = ldapService.findByPrimaryKey(person.getCountry(), person.getCompany(),
				person.getFullName());
		if (localPerson == null) {
			throw new LdapPersonNotFound("The given person does not exists. person=" + person);
		}

		localPerson.setPhone(person.getPhone());

		ldapService.update(person);
		return "redirect:/showTree.do";
	}

	@RequestMapping(value="/removePerson.do", method = RequestMethod.DELETE)
	public String removePerson() {
		LdapPerson person = getPerson();

		ldapService.delete(person);
		return "redirect:/showTree.do";
	}

	@RequestMapping("/showPerson.do")
	public ModelMap showPerson(String country, String company, String fullName) {
		LdapPerson person = ldapService.findByPrimaryKey(country, company, fullName);
		return new ModelMap("person", person);
	}

	private LdapPerson getPerson() {
		LdapPerson person = new LdapPerson();
		person.setFullName("John Doe");
		person.setLastName("Doe");
		person.setCompany("company1");
		person.setCountry("Sweden");
		person.setDescription("Test user");
		return person;
	}

	/**
	 * Generates appropriate links for person leaves in the tree.
	 * 
	 */
	private final class PersonLinkHtmlRowLdapTreeVisitor extends HtmlRowLdapTreeVisitor {
		@Override
		protected String getLinkForNode(DirContextOperations node) {
			String[] objectClassValues = node.getStringAttributes("objectClass");
			if (containsValue(objectClassValues, "person")) {
				Name dn = node.getDn();
				String country = encodeValue(LdapUtils.getStringValue(dn, "c"));
				String company = encodeValue(LdapUtils.getStringValue(dn, "ou"));
				String fullName = encodeValue(LdapUtils.getStringValue(dn, "cn"));

				return "showPerson.do?country=" + country + "&company=" + company + "&fullName=" + fullName;
			} else {
				return super.getLinkForNode(node);
			}
		}

		private String encodeValue(String value) {
			try {
				return URLEncoder.encode(value, "UTF8");
			} catch (UnsupportedEncodingException e) {
				// Not supposed to happen
				throw new RuntimeException("Unexpected encoding exception", e);
			}
		}

		private boolean containsValue(String[] values, String value) {
			for (String oneValue : values) {
				if (StringUtils.equals(oneValue, value)) {
					return true;
				}
			}
			return false;
		}
	}

}
