package com.prodyna.pac.service.ldap.impl;

import org.springframework.ldap.core.DirContextOperations;

public interface LdapTreeVisitor {

	public void visit(DirContextOperations node, int currentDepth);
}
