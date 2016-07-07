package com.prodyna.pac.util;

/**
 * Created by bjoern on 28.05.16.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 *
 */
@Component
public class ServiceUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceUtils.class);

    @Autowired
    private LoadBalancerClient loadBalancer;


    /**
     * @param serviceId
     * @return
     */
    public URI getServiceUrl(String serviceId) {
        return getServiceUrl(serviceId, null);
    }

    /**
     * @param serviceId
     * @param fallbackUri
     * @return
     */
    protected URI getServiceUrl(String serviceId, String fallbackUri) {
        URI uri = null;
        try {
            ServiceInstance instance = loadBalancer.choose(serviceId);

            if (instance == null) {
                throw new RuntimeException("Can't find a service with serviceId = " + serviceId);
            }

            uri = instance.getUri();
            LOG.debug("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);

        } catch (RuntimeException e) {
            // Eureka not available, use fallback if specified otherwise rethrow the error
            if (fallbackUri == null) {
                throw e;

            } else {
                uri = URI.create(fallbackUri);
                LOG.warn("Failed to resolve serviceId '{}'. Fallback to URL '{}'.", serviceId, uri);
            }
        }

        return uri;
    }

    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }

    /**
     * Clone an existing result as a new one, filtering out http headers that not should be moved on and so on...
     *
     * @param result
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> createResponse(ResponseEntity<T> result) {

        // TODO: How to relay the transfer encoding??? The code below makes the fallback method to kick in...
        ResponseEntity<T> response = createResponse(result.getBody(), result.getStatusCode());
//        LOG.info("NEW HEADERS:");
//        response.getHeaders().entrySet().stream().forEach(e -> LOG.info("{} = {}", e.getKey(), e.getValue()));
//        String ct = result.getHeaders().getFirst(HTTP.CONTENT_TYPE);
//        if (ct != null) {
//            LOG.info("Add without remove {}: {}", HTTP.CONTENT_TYPE, ct);
////            response.getHeaders().remove(HTTP.CONTENT_TYPE);
//            response.getHeaders().add(HTTP.CONTENT_TYPE, ct);
//        }
        return response;
    }

    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }
}