package com.prodyna.pac;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
/**
 * Created by bjoern on 29.02.16.
 */
@EnableHystrix
@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.prodyna.pac.service","com.prodyna.pac.util", "com.prodyna.pac.rest", "com.prodyna.pac.config"})
public class CompositeApplication {
    public static void main(String[] args) {

        SpringApplication.run(CompositeApplication.class, args);
    }
}
