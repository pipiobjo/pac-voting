package com.prodyna.pac.main;

/**
 * Created by bjoern on 11.02.16.
 */

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;


@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@Import(MyNeo4jConfiguration.class)
public class EurekaClientApplication  {
//    private static String embeddedDBPath = "target/accessingdataneo4j.db";

    public static void main(String[] args) throws IOException {

        String javaVersion = System.getProperty("java.version");

        DefaultArtifactVersion minVersion = new DefaultArtifactVersion("1.8.0");
        DefaultArtifactVersion version = new DefaultArtifactVersion(javaVersion);

        if (version.compareTo(minVersion) < 0 ) {
            throw new UnsupportedClassVersionError("Expecting Java Version higher than "+minVersion+", but your version is " + javaVersion);
        }


        //FileUtils.deleteRecursively(new File(embeddedDBPath));
        SpringApplication.run(EurekaClientApplication.class, args);
    }




}
