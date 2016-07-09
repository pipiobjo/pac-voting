package com.prodyna.pac.main;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@ComponentScan("com.prodyna.pac")
public class MyNeo4jConfiguration extends Neo4jConfiguration {
    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://localhost:7474";
    public static final String USER = System.getenv("NEO4J_USER") != null ? System.getenv("NEO4J_USER") : "neo4j";
    public static final String PASSWD = System.getenv("NEO4J_PASSWD") != null ? System.getenv("NEO4J_PASSWD") : "test";



	@Override
	public SessionFactory getSessionFactory() {
		return new SessionFactory("com.prodyna.pac.domain");
}

    @Override
	@Bean
	public Session getSession() throws Exception {
		return super.getSession();
}

}
