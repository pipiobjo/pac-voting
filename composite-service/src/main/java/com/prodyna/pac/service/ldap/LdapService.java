package com.prodyna.pac.service.ldap;

import java.util.List;

import org.springframework.ldap.core.LdapTemplate;

import com.prodyna.pac.service.ldap.impl.LdapPerson;

public interface LdapService {

	void create(LdapPerson person);

	void update(LdapPerson person);

	void delete(LdapPerson person);

	List<String> getAllPersonNames();

	List<LdapPerson> findAll();

	LdapPerson findByPrimaryKey(String country, String company, String fullname);

	void setLdapTemplate(LdapTemplate ldapTemplate);

}