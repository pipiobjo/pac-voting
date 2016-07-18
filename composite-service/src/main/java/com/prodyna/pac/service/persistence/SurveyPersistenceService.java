package com.prodyna.pac.service.persistence;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prodyna.pac.model.Option;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import com.prodyna.pac.service.persistence.jsonResources.VotingUserResource;

/**
 * Survey Service to call with persistence layer, no business logic Created by
 * bjoern.
 */
@Component
public class SurveyPersistenceService {
	private static final String FIND_SURVEY_BY_ID_REQ_URL_EXTENSION_TEMPLATE = "/surveys/search/findBySurveyId?surveyId={surveyId}";
	private static final String FIND_ALL_SURVEYS_REQ_URL_EXTENSION_TEMPLATE = "/surveys";
	private static final String FIND_SURVEY_BY_OWNER_REQ_URL_EXTENSION_TEMPLATE = "/surveys/search/findByCreator?{votingUserId}";
	private static final String VOTE_ON_SURVEY_TEMPLATE = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}";
	private static final Logger LOG = LoggerFactory.getLogger(SurveyPersistenceService.class);
	private final RestTemplate restTemplate;

	@Autowired
	private LoadBalancerClient loadBalancer;

	public SurveyPersistenceService() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);

		this.restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>>singletonList(converter));

	}

	@HystrixCommand
	public Survey getSurveyBySurveyId(String surveyId) throws Exception {
		ServiceInstance votingService = loadBalancer.choose("core-service-voting");
		String votingpersistenServiceURL = votingService.getUri() + FIND_SURVEY_BY_ID_REQ_URL_EXTENSION_TEMPLATE;
		String url = votingpersistenServiceURL.replace("{surveyId}", surveyId);
//
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("surveyId", surveyId);
		Resource<Survey> surveyResource = null;
//		// get main object
//
//		Traverson traverson;
//		Survey survey = null;
//		try {
//			traverson = new Traverson(new URI(url), MediaTypes.HAL_JSON);
//
//			survey = traverson.follow("creator", "options").toObject(Survey.class);
//		} catch (Exception e) {
//			LOG.error("Error while reading", e);
//		}

		
		
		Survey resultSurvey = null;
		this.restTemplate.getForObject(votingpersistenServiceURL, Survey.class, map);
		ResponseEntity<Resource<Survey>> responseEntity =

				this.restTemplate.exchange(votingpersistenServiceURL, HttpMethod.GET, null,
						new ParameterizedTypeReference<Resource<Survey>>() {
						}, map);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			surveyResource = responseEntity.getBody();
			resultSurvey = surveyResource.getContent();
		}
		// get options
		 Link options = surveyResource.getLink("options");
		 String optionURL = options.getHref();
		
		 ResponseEntity<Resources<Option>> responseEntityOptions =
		 restTemplate.exchange(optionURL, HttpMethod.GET, null,
		 new ParameterizedTypeReference<Resources<Option>>() {
		 });
		 Resources<Option> optionResources = responseEntityOptions.getBody();
		 List<Option> surveyOptions = new
		 ArrayList<>(optionResources.getContent());
		
		 resultSurvey.setOptions(surveyOptions);
		
		 // get creator
		
		 Link creatorLinks = surveyResource.getLink("creator");
		 String creatorURL = creatorLinks.getHref();
		
		 VotingUserResource surveyCreatorRes =
		 restTemplate.getForObject(creatorURL, VotingUserResource.class, map);
		
		 resultSurvey.setCreator(surveyCreatorRes.getContent());
		
		 return resultSurvey;

	}

	@HystrixCommand
	public List<Survey> getSurveyByCreator(String creator) {
		ServiceInstance votingService = loadBalancer.choose("core-service-voting");

		String votingpersistenServiceURL = votingService.getUri() + FIND_SURVEY_BY_OWNER_REQ_URL_EXTENSION_TEMPLATE;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("votingUserId", creator);

		ResponseEntity<PagedResources<Survey>> responseEntity = restTemplate.exchange(votingpersistenServiceURL,
				HttpMethod.GET, null, new ParameterizedTypeReference<PagedResources<Survey>>() {
				});
		PagedResources<Survey> options1 = responseEntity.getBody();
		return new ArrayList<>(options1.getContent());

	}

	@HystrixCommand
	public List<Survey> getAllSurveys() throws Exception {
		List<Survey> resultSurveys = new ArrayList<>();
		ServiceInstance votingService = loadBalancer.choose("core-service-voting");
		String votingpersistenServiceURL = votingService.getUri() + FIND_ALL_SURVEYS_REQ_URL_EXTENSION_TEMPLATE;

		ResponseEntity<Resources<Survey>> responseEntity = restTemplate.exchange(votingpersistenServiceURL,
				HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Survey>>() {
				});
		Resources<Survey> options1 = responseEntity.getBody();
		List<Survey> simpleSurveys = new ArrayList<>(options1.getContent());

		for (Survey s : simpleSurveys) {
			Survey surveyBySurveyId = getSurveyBySurveyId(s.getSurveyId());
			resultSurveys.add(surveyBySurveyId);
		}

		return resultSurveys;

	}

	@HystrixCommand
	@Transactional
	/**
	 * Vote on a survey
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 * @return
	 */
	public Survey voteSurvey(String surveyId, String optionId, String userId) {
		ServiceInstance votingService = loadBalancer.choose("core-service-voting");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("surveyId", surveyId);
		map.put("optionId", optionId);
		map.put("userId", userId);

		String url = votingService.getUri() + VOTE_ON_SURVEY_TEMPLATE;

		ResponseEntity<Survey> response = restTemplate.getForEntity(url, Survey.class, map);
		Survey survey = response.getBody();

		return survey;
	}

	public List<Survey> createSurvey(Survey survey) {
		// TODO Auto-generated method stub
		return null;
	}

}
