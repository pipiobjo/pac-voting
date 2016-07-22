package com.prodyna.pac.service.ldap.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;

import javax.naming.Name;

@Component
public class LdapTreeBuilder {

	@Autowired
	private LdapTemplate ldapTemplate;
	public LdapTreeBuilder(){}
	
	public LdapTreeBuilder(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTree getLdapTree(Name root) {
		DirContextOperations context = ldapTemplate.lookupContext(root);
		return getLdapTree(context);
	}

	private LdapTree getLdapTree(final DirContextOperations rootContext) {
		final LdapTree ldapTree = new LdapTree(rootContext);
		ldapTemplate.listBindings(rootContext.getDn(),
				new AbstractContextMapper<Object>() {
					@Override
					protected Object doMapFromContext(DirContextOperations ctx) {
						Name dn = ctx.getDn();
						dn = LdapUtils.prepend(dn, rootContext.getDn());
						ldapTree.addSubTree(getLdapTree(ldapTemplate
								.lookupContext(dn)));
						return null;
					}
				});

		return ldapTree;
	}
}
