package com.prodyna.pac.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@EnableAutoConfiguration
@Configuration
public class LdapConfiguration {
	private Logger log = LoggerFactory.getLogger(LdapConfiguration.class);
	@Value("${ldap.url}")
	String ldapUrl;
	@Value("${ldap.base}")
	String ldapBase;
	@Value("${ldap.user}")
	String ldapUser;
	@Value("${ldap.password}")
	String ldapPassword;

	@Autowired
	Environment env;

	@Bean
	public LdapContextSource contextSource() {
		// why this doesn't work
		String requiredProperty = env.getRequiredProperty("ldap.base");
		log.info("requiredProperty="+requiredProperty);
		String[] activeProfiles = env.getActiveProfiles();
		log.info("activeProfiles=");
		for(String pro : activeProfiles){
			log.info(pro);
		}

		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapUrl);
		contextSource.setBase(ldapBase);
		contextSource.setUserDn(ldapUser);
		contextSource.setPassword(ldapPassword);
		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(contextSource());
	}

}
