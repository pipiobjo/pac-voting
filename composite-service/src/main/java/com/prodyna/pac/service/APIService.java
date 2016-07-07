package com.prodyna.pac.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prodyna.pac.jsonResources.SurveyResource;
import com.prodyna.pac.jsonResources.UserResource;
import com.prodyna.pac.model.Option;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

/**
 * Created by bjoern on 29.02.16.
 */
@RestController
public class APIService {

    @Autowired
    private LoadBalancerClient loadBalancer;

    private static final Logger LOG = LoggerFactory.getLogger(APIService.class);

    @Autowired
    private SurveyPersistenceService surveyPersistenceService;

    @HystrixCommand(groupKey = "voting-get-survey-by-id")
    @RequestMapping("/survey/{surveyId}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable String surveyId) throws Exception {

        Survey surveyBySurveyId = surveyPersistenceService.getSurveyBySurveyId(surveyId);

        return createOkResponse(surveyBySurveyId);
    }
    @HystrixCommand(groupKey = "voting-get-survey-by-creator")
    @RequestMapping("/survey/creator/{creatorId}")
    public ResponseEntity<List<Survey>> getSurveyByOwner(@PathVariable String creatorId){
        List<Survey> surveyByCreator = surveyPersistenceService.getSurveyByCreator(creatorId);

        return createOkResponse(surveyByCreator);
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




/*



    @RequestMapping("/dadfadsfdasfasdfasfd/{surveyId}")
    @HystrixCommand(fallbackMethod = "defaultSurveyComposite")
    public ResponseEntity<String> getSurveyComposite(
            @PathVariable int surveyId,
            @RequestHeader(value = "Authorization") String authorizationHeader,
            Principal currentUser) {

                LOG.info("ProductApi: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, surveyId);
                URI uri = loadBalancer.choose("voting-rest").getUri();
                String url = uri.toString() + "/surveys/" + surveyId;
                LOG.debug("GetProductComposite from URL: {}", url);

                ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
                LOG.info("GetProductComposite http-status: {}", result.getStatusCode());
                LOG.debug("GetProductComposite body: {}", result.getBody());

                return result;
            }

    */
/**
     * Fallback method for getProductComposite()
     *
     * @param productId
     * @return
     *//*

    public ResponseEntity<String> defaultProductComposite(
            @PathVariable int productId,
            @RequestHeader(value = "Authorization") String authorizationHeader,
            Principal currentUser) {

        LOG.warn("Using fallback method for product-composite-service. User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);
        return new ResponseEntity<String>("", HttpStatus.BAD_GATEWAY);
    }
*/

}
