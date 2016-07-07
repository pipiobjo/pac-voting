package com.prodyna.pac.serviceproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjoern on 12.02.16.
 */
@RestController
@EnableDiscoveryClient
public class LittleRestController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        List<ServiceInstance> result = this.discoveryClient.getInstances(applicationName);
        if (result == null){
            result = new ArrayList<ServiceInstance>();
        }
        return result;
    }
}
