package com.prodyna.pac.service.ldap.impl;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import com.prodyna.pac.service.ldap.LdapService;

/**
 * Does all the ldap communication
 * @author bjoern
 *
 */
@Service
public class LdapServiceImpl implements LdapService {
	private Logger log = LoggerFactory.getLogger(LdapServiceImpl.class);
	 
	 public LdapServiceImpl() {
	 
	 }
	 
	 @Autowired(required = true)
	 private LdapTemplate ldapTemplate;
	 
		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#create(com.prodyna.pac.service.ldap.impl.LdapPerson)
		 */
		@Override
		public void create(LdapPerson person) {
			Name dn = buildDn(person);
			DirContextAdapter context = new DirContextAdapter(dn);
			mapToContext(person, context);
			ldapTemplate.bind(dn, context, null);
		}

		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#update(com.prodyna.pac.service.ldap.impl.LdapPerson)
		 */
		@Override
		public void update(LdapPerson person) {
			Name dn = buildDn(person);
			DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
			mapToContext(person, context);
			ldapTemplate.modifyAttributes(dn, context.getModificationItems());
		}

		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#delete(com.prodyna.pac.service.ldap.impl.LdapPerson)
		 */
		@Override
		public void delete(LdapPerson person) {
			ldapTemplate.unbind(buildDn(person));
		}

		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#getAllPersonNames()
		 */
		@Override
		public List<String> getAllPersonNames() {
	        return ldapTemplate.search(query()
	                .attributes("cn")
	                .where("objectclass").is("person"),
	                new AttributesMapper<String>() {
	                    public String mapFromAttributes(Attributes attrs) throws NamingException {
	                        return attrs.get("cn").get().toString();
	                    }
	                });
	    }

		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#findAll()
		 */
		@Override
		public List<LdapPerson> findAll() {
			return ldapTemplate.search(query()
	                .where("objectclass").is("person"),
	                PERSON_CONTEXT_MAPPER);
		}

		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#findByPrimaryKey(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public LdapPerson findByPrimaryKey(String country, String company, String fullname) {
			LdapName dn = buildDn(country, company, fullname);
			return ldapTemplate.lookup(dn, PERSON_CONTEXT_MAPPER);
		}

		private LdapName buildDn(LdapPerson person) {
			return buildDn(person.getCountry(), person.getCompany(), person.getFullName());
		}

		private LdapName buildDn(String country, String company, String fullname) {
	        return LdapNameBuilder.newInstance()
	                .add("c", country)
	                .add("ou", company)
	                .add("cn", fullname)
	                .build();
		}

		private void mapToContext(LdapPerson person, DirContextAdapter context) {
			context.setAttributeValues("objectclass", new String[] { "top", "person" });
			context.setAttributeValue("cn", person.getFullName());
			context.setAttributeValue("sn", person.getLastName());
			context.setAttributeValue("description", person.getDescription());
			context.setAttributeValue("telephoneNumber", person.getPhone());
		}

		/**
		 * Maps from DirContextAdapter to Person objects. A DN for a person will be
		 * of the form <code>cn=[fullname],ou=[company],c=[country]</code>, so
		 * the values of these attributes must be extracted from the DN. For this,
		 * we use the LdapName along with utility methods in LdapUtils.
		 */
		private final static ContextMapper<LdapPerson> PERSON_CONTEXT_MAPPER = new AbstractContextMapper<LdapPerson>() {
	        @Override
			public LdapPerson doMapFromContext(DirContextOperations context) {
	        	LdapPerson person = new LdapPerson();

	            LdapName dn = LdapUtils.newLdapName(context.getDn());
				person.setCountry(LdapUtils.getStringValue(dn, 0));
				person.setCompany(LdapUtils.getStringValue(dn, 1));
				person.setFullName(context.getStringAttribute("cn"));
				person.setLastName(context.getStringAttribute("sn"));
				person.setDescription(context.getStringAttribute("description"));
				person.setPhone(context.getStringAttribute("telephoneNumber"));

				return person;
			}
		};

		/* (non-Javadoc)
		 * @see com.prodyna.pac.service.ldap.impl.LdapService#setLdapTemplate(org.springframework.ldap.core.LdapTemplate)
		 */
		@Override
		public void setLdapTemplate(LdapTemplate ldapTemplate) {
			this.ldapTemplate = ldapTemplate;
		}

}
