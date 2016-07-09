package com.prodyna.pac.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.pac.model.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by bjoern on 06.07.16.
 */
@Component
public class SurveyService {
    private final static String FIND_ALL_SURVEYS = "/surveys";
    private final RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;


    public SurveyService(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
        converter.setObjectMapper(mapper);
        this.restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>>singletonList(converter));
    }



    /**
     * Method to get all Survey for unauthorized view
     * @return
     */
    public List<Survey> getAllSurvey(){
        ServiceInstance votingService = loadBalancer.choose("composite-service");
        String votingpersistenServiceURL = votingService.getUri() + FIND_ALL_SURVEYS;


        ResponseEntity<List<Survey>> rateResponse =
                restTemplate.exchange(votingpersistenServiceURL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Survey>>() {
                        });
        List<Survey> surveys = rateResponse.getBody();
        return surveys;
    }
}
