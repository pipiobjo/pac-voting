package com.prodyna.pac.service.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
/**
 * Setting general Jackson Mappings
 */
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

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("surveyId", surveyId);
		Resource<Survey> surveyResource = null;
		// // get main object
		//
		// Traverson traverson;
		// Survey survey = null;
		// try {
		// traverson = new Traverson(new URI(url), MediaTypes.HAL_JSON);
		//
		// survey = traverson.follow("creator",
		// "options").toObject(Survey.class);
		// } catch (Exception e) {
		// LOG.error("Error while reading", e);
		// }

		this.restTemplate.getForObject(votingpersistenServiceURL, Survey.class, map);
		ResponseEntity<Resource<Survey>> responseEntity =

				this.restTemplate.exchange(votingpersistenServiceURL, HttpMethod.GET, null,
						new ParameterizedTypeReference<Resource<Survey>>() {
						}, map);

		Survey resultSurvey = null;
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			surveyResource = responseEntity.getBody();

			resultSurvey = buildSurveyFromResource(surveyResource);
		}
		return resultSurvey;

	}

	/**
	 * Build the survey pojo from the hal resources, follow the links
	 * 
	 * @param surveyResource
	 * @return
	 */
	private Survey buildSurveyFromResource(Resource<Survey> surveyResource) {
		// get survey attributes
		Survey mySurvey = surveyResource.getContent();

		// get options
		mySurvey = getOptionsForSurvey(surveyResource, mySurvey);

		// get creator
		mySurvey = getSurveyCreator(surveyResource, mySurvey);

		return mySurvey;
	}

	/**
	 * @param map
	 * @param surveyResource
	 * @param resultSurvey
	 */
	private Survey getSurveyCreator(Resource<Survey> surveyResource, Survey resultSurvey) {
		Link creatorLinks = surveyResource.getLink("creator");
		String creatorURL = creatorLinks.getHref();

		ResponseEntity<Resource<VotingUser>> surveyCreatorRes = restTemplate.exchange(creatorURL, HttpMethod.GET, null,
				new ParameterizedTypeReference<Resource<VotingUser>>() {
				});

		resultSurvey.setCreator(surveyCreatorRes.getBody().getContent());
		return resultSurvey;
	}

	/**
	 * @param surveyResource
	 * @param resultSurvey
	 */
	private Survey getOptionsForSurvey(Resource<Survey> surveyResource, Survey resultSurvey) {
		Link options = surveyResource.getLink("options");
		String optionURL = options.getHref();

		ResponseEntity<Resources<Resource<Option>>> responseEntityOptions = restTemplate.exchange(optionURL,
				HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Option>>>() {
				});
		Resources<Resource<Option>> optionResources = responseEntityOptions.getBody();

		List<Option> resultOptions = new ArrayList<>();
		List<Resource<Option>> surveyOptions = new ArrayList<>(optionResources.getContent());
		for (Resource<Option> option : surveyOptions) {
			Option optionPojo = option.getContent();

			// getting voters
			optionPojo = getOptionVotersByLink(option, optionPojo);

			// getting creator
			optionPojo = getOptionCreatorByLink(option, optionPojo);

			resultOptions.add(optionPojo);
		}

		resultSurvey.setOptions(resultOptions);
		return resultSurvey;
	}

	/**
	 * @param option
	 * @param optionPojo
	 * @return
	 */
	private Option getOptionVotersByLink(Resource<Option> option, Option optionPojo) {
		Link voterLinklink = option.getLink("voters");
		String voterLinkhref = voterLinklink.getHref();
		ResponseEntity<Resources<Resource<VotingUser>>> creatorResponseEntity = restTemplate.exchange(voterLinkhref,
				HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<VotingUser>>>() {
				});

		Set<VotingUser> voters = new HashSet<>();
		Resources<Resource<VotingUser>> votersResources = creatorResponseEntity.getBody();
		for (Resource<VotingUser> voterResource : votersResources) {
			VotingUser votingUser = voterResource.getContent();
			voters.add(votingUser);
		}
		optionPojo.setVoters(voters);
		return optionPojo;
	}

	/**
	 * @param option
	 * @param optionPojo
	 */
	private Option getOptionCreatorByLink(Resource<Option> option, Option optionPojo) {
		Link creatorLink = option.getLink("creator");
		String creatorLinkHref = creatorLink.getHref();

		ResponseEntity<Resource<VotingUser>> creatorResponseEntity = restTemplate.exchange(creatorLinkHref,
				HttpMethod.GET, null, new ParameterizedTypeReference<Resource<VotingUser>>() {
				});
		VotingUser creator = creatorResponseEntity.getBody().getContent();

		optionPojo.setCreator(creator);
		return optionPojo;
	}

	/**
	 * 
	 * @param creator
	 * @return
	 */
	@HystrixCommand
	public List<Survey> getSurveyByCreator(String creator) {
		ServiceInstance votingService = loadBalancer.choose("core-service-voting");

		String votingpersistenServiceURL = votingService.getUri() + FIND_SURVEY_BY_OWNER_REQ_URL_EXTENSION_TEMPLATE;

		Map<String, Object> map = new HashMap<>();
		map.put("votingUserId", creator);

		ResponseEntity<Resources<Resource<Survey>>> responseEntity = restTemplate.exchange(votingpersistenServiceURL,
				HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Survey>>>() {
				});
		List<Survey> resultSurvey = new ArrayList<>();
		Resources<Resource<Survey>> surveyResources = responseEntity.getBody();
		for (Resource<Survey> surveyRes : surveyResources) {
			Survey mySurvey = buildSurveyFromResource(surveyRes);
			resultSurvey.add(mySurvey);

		}

		return resultSurvey;

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

	/**
	 * Vote on a survey
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 * @return
	 */
	@HystrixCommand
	@Transactional
	public Survey voteSurvey(String surveyId, String optionId, String userId) {
		ServiceInstance votingService = loadBalancer.choose("core-service-voting");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("surveyId", surveyId);
		map.put("optionId", optionId);
		map.put("userId", userId);

		String url = votingService.getUri() + VOTE_ON_SURVEY_TEMPLATE;

		ResponseEntity<Survey> response = restTemplate.getForEntity(url, Survey.class, map);
		return response.getBody();

	}

	public List<Survey> createSurvey(Survey survey) {
		// TODO Auto-generated method stub
		return null;
	}

}
