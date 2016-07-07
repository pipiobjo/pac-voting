package com.prodyna.pac.main;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by bjoern on 07.03.16.
 */
@EnableTransactionManagement
@EnableAutoConfiguration
@Configuration
@EnableNeo4jRepositories("com.prodyna.pac.repo")
@ComponentScan("com.prodyna.pac")
public class MyNeo4jConfiguration extends Neo4jConfiguration {
    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://localhost:7474";
    public static final String USER = System.getenv("NEO4J_USER") != null ? System.getenv("NEO4J_USER") : "neo4j";
    public static final String PASSWD = System.getenv("NEO4J_PASSWD") != null ? System.getenv("NEO4J_PASSWD") : "test";


    @Bean
    public Neo4jServer neo4jServer() {
        RemoteServer remoteServer = new RemoteServer(URL, USER, PASSWD);

        return remoteServer;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.prodyna.pac.domain");
    }

  /* @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(embeddedDBPath);
    }*/

    @Override
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }

}
