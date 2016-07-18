package com.prodyna.pac.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.pac.CompositeApplication;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.persistence.SurveyPersistenceService;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CompositeApplication.class)
@WebIntegrationTest(randomPort = true)
public class SurveyControllerRestTest {

	@Autowired
	SurveyPersistenceService persistence;

	RestTemplate restTemplate = new TestRestTemplate();

	
	
	// Will contain the random free port number
		@Value("${local.server.port}")
		private int port;
	 
		/**
		 * Returns the base url for your rest interface
		 * 
		 * @return
		 */
		private String getBaseUrl() {
			return "http://localhost:" + port;
		}

	
	
	
	
	/**
	 * @param requestMappingUrl
	 *            should be exactly the same as defined in your RequestMapping
	 *            value attribute (including the parameters in {})
	 *            RequestMapping(value = yourRestUrl)
	 * @param serviceReturnTypeClass
	 *            should be the the return type of the service
	 * @param parametersInOrderOfAppearance
	 *            should be the parameters of the requestMappingUrl ({}) in
	 *            order of appearance
	 * @return the result of the service, or null on error
	 */
	protected <T> T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object... parametersInOrderOfAppearance) {
		// Add correct headers, none for this example
		final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
		try {
			// Do a call the the url
			final ResponseEntity<T> entity = restTemplate.exchange(getBaseUrl() + requestMappingUrl, HttpMethod.GET, requestEntity, serviceReturnTypeClass,
					parametersInOrderOfAppearance);
			// Return result
			return entity.getBody();
		} catch (final Exception ex) {
			// Handle exceptions
		}
		return null;
	}
	
	
	/**
	 * @param requestMappingUrl
	 *            should be exactly the same as defined in your RequestMapping
	 *            value attribute (including the parameters in {})
	 *            RequestMapping(value = yourRestUrl)
	 * @param serviceListReturnTypeClass
	 *            should be the the generic type of the list the service
	 *            returns, eg: List<serviceListReturnTypeClass>
	 * @param parametersInOrderOfAppearance
	 *            should be the parameters of the requestMappingUrl ({}) in
	 *            order of appearance
	 * @return the result of the service, or null on error
	 */
	protected <T> List<T> getList(final String requestMappingUrl, final Class<T> serviceListReturnTypeClass, final Object... parametersInOrderOfAppearance) {
		final ObjectMapper mapper = new ObjectMapper();
		final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
		try {
			// Retrieve list
			final ResponseEntity<List> entity = restTemplate.exchange(getBaseUrl() + requestMappingUrl, HttpMethod.GET, requestEntity, List.class, parametersInOrderOfAppearance);
			final List<Map<String, String>> entries = entity.getBody();
			final List<T> returnList = new ArrayList<T>();
			for (final Map<String, String> entry : entries) {
				// Fill return list with converted objects
				returnList.add(mapper.convertValue(entry, serviceListReturnTypeClass));
			}
			return returnList;
		} catch (final Exception ex) {
			// Handle exceptions
		}
		return null;
	}
	
	@Test
	public void testSurveys(){
		
		String baseUrl = getBaseUrl();
		Assert.assertNotNull("Expecting given baseUrl", baseUrl);
		//List<Survey> list = getList("/surveys", Survey.class, Collections.emptyList());
//		Assert.assertNotNull("Expecting an non empty list", list);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("VOTING_EXECUTIVE_USER", "bob");
		headers.set("VOTING_EXEUCTING_AS_ROLE", "ROLE_USER");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		
		
		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "surveys",HttpMethod.GET, entity, String.class);

//		need mocking of survey persistence ... of course
//		MatcherAssert.assertThat("Expecting response code to be OK", response.getStatusCode() , equalTo(HttpStatus.OK));

		
		
	}
	
}
