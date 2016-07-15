package com.prodyna.pac.main;

import java.io.IOException;

/**
 * Created by bjoern on 11.02.16.
 */

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableDiscoveryClient
@SpringBootApplication
@EnableNeo4jRepositories("com.prodyna.pac.repo")
@EnableAutoConfiguration
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.prodyna.pac", "com.prodyna.pac.config", "com.prodyna.pac.controller", "com.prodyna.pac.service"})
public class PersistenceApplication  extends Neo4jConfiguration {
	private Logger LOG = LoggerFactory.getLogger(PersistenceApplication.class);

	@Value("${db.driverClass}")
	private String driverClass;
	@Value("${db.connectionURL}")
	private String URL;
	@Value("${db.username}")
	public String USER;
	@Value("${db.password}")
	public String PASSWD;

	@Override
	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Session getSession() throws Exception {
		return super.getSession();
	}

	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		LOG.info("Neo4J Configuration: DriverClass=" + driverClass + " URL=" + URL + " User=" + USER);
		org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
		config.driverConfiguration().setDriverClassName(driverClass).setCredentials(USER, PASSWD)
				.setURI(URL);
		return config;
	}
	@Override
	@Bean
	public SessionFactory getSessionFactory() {
		return new SessionFactory(getConfiguration(), "com.prodyna.pac.domain");
	}

    public static void main(String[] args) throws IOException {

        String javaVersion = System.getProperty("java.version");

        DefaultArtifactVersion minVersion = new DefaultArtifactVersion("1.8.0");
        DefaultArtifactVersion version = new DefaultArtifactVersion(javaVersion);

        if (version.compareTo(minVersion) < 0 ) {
            throw new UnsupportedClassVersionError("Expecting Java Version higher than "+minVersion+", but your version is " + javaVersion);
        }


        SpringApplication.run(PersistenceApplication.class, args);
    }




}
