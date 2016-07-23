package com.prodyna.pac.service;

import java.net.URI;
import java.security.Principal;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.pac.model.ExecutingUser;
import com.prodyna.pac.model.Roles;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.exception.BackendServiceException;

/**
 * Created by bjoern on 06.07.16.
 */
@Component
public class SurveyService {
	private static final String FIND_ALL_SURVEYS = "/surveys";
	private static final String CREATE_SURVEY = "/surveys";
	private static final String VOTE_ON_SURVEY_OPTION = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}";
	private Logger log = LoggerFactory.getLogger(SurveyService.class);
	private final RestTemplate restTemplate;

	@Autowired
	private LoadBalancerClient loadBalancer;

	/**
	 * Configures the jackson configuration for all the requests
	 */
	public SurveyService() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
		converter.setObjectMapper(mapper);
		this.restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>>singletonList(converter));
	}

	/**
	 * Method to get all Survey for unauthorized view
	 * 
	 * @param principal
	 * @return
	 * @throws BackendServiceException
	 */
	public List<Survey> getAllSurvey(Principal principal) throws BackendServiceException {
		ServiceInstance votingService = loadBalancer.choose("composite-service");
		String votingpersistenServiceURL = votingService.getUri() + FIND_ALL_SURVEYS;

		MultiValueMap<String, String> headers = getAuthHeader(principal);
		
		HttpEntity<List<Survey>> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Survey>> surveysResponse = restTemplate.exchange(votingpersistenServiceURL, HttpMethod.GET,
				requestEntity, new ParameterizedTypeReference<List<Survey>>() {
				});
		HttpStatus statusCode = surveysResponse.getStatusCode();
		if (statusCode == HttpStatus.OK) {
			List<Survey> surveys = surveysResponse.getBody();
			return surveys;
		}
		String msg = "Error while getting surveys from composite service. Response " + surveysResponse;
		log.error(msg);
		throw new BackendServiceException(msg);
	}

	
	/**
	 * 
	 * @param survey
	 * @param principal
	 * @return
	 * @throws BackendServiceException
	 */
	public Survey createSurvey(Survey survey, Principal principal) throws BackendServiceException {
		ServiceInstance votingService = loadBalancer.choose("composite-service");
		String votingCompositeURL = votingService.getUri() + CREATE_SURVEY;

		MultiValueMap<String, String> headers = getAuthHeader(principal);
		HttpEntity<Survey> requestEntity = new HttpEntity<>(survey, headers);

		return restTemplate.postForObject(votingCompositeURL, requestEntity, Survey.class);
	}

	/**
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 * @param principal
	 * @return
	 * @throws BackendServiceException 
	 */
	public void voteSurvey(String surveyId, String optionId, String userId, Principal principal) throws BackendServiceException {
		ServiceInstance votingService = loadBalancer.choose("composite-service");
		String votingCompositeURL = votingService.getUri() + VOTE_ON_SURVEY_OPTION;
		
		MultiValueMap<String, String> headers = getAuthHeader(principal);
		HttpEntity<Survey> requestEntity = new HttpEntity<>(headers);
		
		Map<String, Object> map = new HashMap<>();
		map.put("surveyId", surveyId);
		map.put("optionId", optionId);
		map.put("userId", userId);
		
		restTemplate.postForObject(votingCompositeURL, requestEntity, Survey.class, map);
	}

	
	/**
	 * @param principal
	 * @return
	 * @throws BackendServiceException
	 */
	private MultiValueMap<String, String> getAuthHeader(Principal principal) throws BackendServiceException {
		ExecutingUser user = getExecutingUserFromPrincipal(principal);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("VOTING_EXECUTIVE_USER", user.getExecutingUser());
		headers.add("VOTING_EXEUCTING_AS_ROLE", user.getExecutingUserRole());
		return headers;
	}

	/**
	 * @param principal
	 * @return
	 * @throws BackendServiceException
	 */
	private ExecutingUser getExecutingUserFromPrincipal(Principal principal) throws BackendServiceException {
		ExecutingUser user = null;
		if (principal instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
			user = new ExecutingUser("anonymous", Roles.GUEST.getRoleName());
		} else if (principal instanceof org.springframework.security.oauth2.provider.OAuth2Authentication) {
			OAuth2Authentication userDetails = (OAuth2Authentication) principal;

			String role = userDetails.getAuthorities().toString();
			user = new ExecutingUser(userDetails.getName(), role);

		} else {
			throw new BackendServiceException("Unknown principal implementation: " + principal.getClass().getName());
		}
		return user;
	}

}
