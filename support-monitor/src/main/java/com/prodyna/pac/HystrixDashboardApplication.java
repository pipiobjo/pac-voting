package com.prodyna.pac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
//@Controller
//@Configuration
@EnableDiscoveryClient
@ComponentScan("com.prodyna.pac.config")
public class HystrixDashboardApplication{ 
//extends SpringBootServletInitializer {
//    @RequestMapping("/")
//    public String home() {
//        return "forward:/hystrix";
//    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(HystrixDashboardApplication.class).web(true);
//    }
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}