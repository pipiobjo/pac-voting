package com.prodyna.pac;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"com.prodyna.pac.controller", "com.prodyna.pac.service"})
public class ResourceApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {

        SpringApplication.run(ResourceApplication.class, args);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/surveys/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We need this to prevent the browser from popping up a dialog on a 401
        http
        .csrf().disable()
        //allow anonymous requests
        .anonymous()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()









//        .anonymous().and()
//        .authorizeRequests()
//            .antMatchers("/**").permitAll()
            //.antMatchers("/rest/**").authenticated()
        ;
        //http.antMatcher("/**").authorizeRequests()
        //        .antMatchers("/index.html", "/home.html", "/", "/login").permitAll().anyRequest()
        //        .authenticated().and().csrf()
        //        .disable();

        // http.authorizeRequests().antMatchers(HttpMethod.POST, "/**").hasRole("WRITER").anyRequest().authenticated();
    }
}