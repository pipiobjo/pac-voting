package com.prodyna.pac;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
//import org.springframework.cloud.netflix.turbine.amqp.EnableTurbineAmqp;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.StandardEnvironment;
@EnableTurbine
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard

public class TurbineApplication {

    public static void main(String[] args) {

        //boolean cloudEnvironment = new StandardEnvironment().acceptsProfiles("cloud");
        //new SpringApplicationBuilder(TurbineApplication.class).web(!cloudEnvironment).run(args);

        new SpringApplicationBuilder(TurbineApplication.class).run(args);
    }
}

