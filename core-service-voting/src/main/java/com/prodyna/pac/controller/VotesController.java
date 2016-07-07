/*
package com.prodyna.pac.controller;

*/
/**
 * Created by bjoern on 11.02.16.
 *//*


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


import Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
public class VotesController {

    @Autowired
    private DiscoveryClient discoveryClient;
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }


    @RequestMapping(value = "/votes", method = RequestMethod.GET)
    public @ResponseBody
    Vote greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in Vote ====");
        return new Vote(counter.incrementAndGet(), String.format(template, name));
    }

}






*/
