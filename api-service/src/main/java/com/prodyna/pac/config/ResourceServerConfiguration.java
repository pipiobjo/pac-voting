package com.prodyna.pac.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	    Logger log = LoggerFactory.getLogger(ResourceServerConfiguration.class);

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        // We need this to prevent the browser from popping up a dialog on a 401
	        http
	        .csrf().disable()
	        //allow anonymous requests
	        .anonymous()
	        .and()
	        // authorizedRequests
	        .authorizeRequests()
	        //allow reading surveys "GET" for everybody
	        .antMatchers(HttpMethod.GET, "/surveys").permitAll()
	        // allow writing access only for authenticated users
	        .antMatchers(HttpMethod.POST, "/**").authenticated()
	        .antMatchers(HttpMethod.PUT, "/**").authenticated()
	        .antMatchers(HttpMethod.DELETE, "/**").authenticated()
	        ;

	    }
}
