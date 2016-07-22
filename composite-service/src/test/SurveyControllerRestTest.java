package com.prodyna.pac.config;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.prodyna.pac.CompositeApplication;
import com.prodyna.pac.model.Option;
import com.prodyna.pac.model.Roles;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import com.prodyna.pac.service.persistence.SurveyPersistenceService;

//@ActiveProfiles("test")
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

	private List<Survey> testSurveys;

	/**
	 * Returns the base url for your rest interface
	 * 
	 * @return
	 */
	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	
	//@Before
	public void setupMocking() throws Exception{
		VotingUser surveyCreator = new VotingUser();
		surveyCreator.setUserId("surveyCreator");
		
		VotingUser optionCreator = new VotingUser();
		optionCreator.setUserId("optionCreator");
		
		VotingUser optionVoter = new VotingUser();
		optionVoter.setUserId("optionVoter");
		
		
		Option opt1 = new Option();
		opt1.setCreator(optionCreator);
		opt1.setDescription("Batman");
	
		Option opt2 = new Option();
		opt2.setCreator(optionCreator);
		opt2.setDescription("Superman");
	
		List<Option> options = new ArrayList<>();
		options.add(opt1);
		options.add(opt2);
		
		Survey s1 = new Survey("Batman or Superman ", "Super Heros", options, surveyCreator);
		
		testSurveys.add(s1);
		
		Mockito.when(persistence.getAllSurveys()).thenReturn(testSurveys);
	}
	
//	ignore mocking from services is not working
	@Test
	public void testSurveys() {

		String baseUrl = getBaseUrl();
		Assert.assertNotNull("Expecting given baseUrl", baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("VOTING_EXECUTIVE_USER", "bob");
		headers.set("VOTING_EXEUCTING_AS_ROLE", Roles.USER.getRoleName());
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "surveys", HttpMethod.GET, entity,
				String.class);

		// need mocking of survey persistence ... of course
		MatcherAssert.assertThat("Expecting response code to be OK", response.getStatusCode(), equalTo(HttpStatus.OK));

	}

}
