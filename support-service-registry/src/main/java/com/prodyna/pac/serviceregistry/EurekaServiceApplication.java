/**
 * Created by bjoern on 11.02.16.
 */
package com.prodyna.pac.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServiceApplication.class, args);

    }
}
