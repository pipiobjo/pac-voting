package com.prodyna.pac.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by bjoern on 07.03.16.
 */
@EnableNeo4jRepositories("com.prodyna.pac.repo")
@EnableTransactionManagement
@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.prodyna.pac", "com.prodyna.pac.repo", "com.prodyna.domain", "com.prodyna.controller"})
public class MyNeo4jConfigurationTest extends Neo4jConfiguration {


	@Override
	public SessionFactory getSessionFactory() {
		return new SessionFactory(getConfiguration(), "com.prodyna.pac.domain");
}

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");
        return config;
}

}
