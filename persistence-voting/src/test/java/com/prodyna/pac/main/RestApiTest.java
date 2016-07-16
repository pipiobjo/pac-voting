package com.prodyna.pac.main;

import static io.restassured.RestAssured.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.restassured.RestAssured;
import com.prodyna.pac.domain.User;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * try to test the rest api nothing seems to be right 
 * @author bjoern
 *
 */

/**
 * @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
 *                                                                          TransactionalTestExecutionListener.class})
 * @RunWith(SpringJUnit4ClassRunner.class) @SpringApplicationConfiguration(classes
 *                                         = PersistenceApplication.class)
 * @WebAppConfiguration
 * @DirtiesContext(classMode =
 *                           DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) @IntegrationTest({"server.port=0","batch.metrics.enabled=true"})
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0"})
public class RestApiTest {

	@Autowired
	private OptionGraphRepository optionRepository;

	@Autowired
	private SurveyGraphRepository surveyRepository;

	@Autowired
	private UserGraphRepository userRepository;

	 @Autowired
	    EmbeddedWebApplicationContext server;
	 
	@Value("${local.server.port}")
	  private int serverPort;
	
	@Test
	public void readHome() throws Exception {
		 
//		test disabled connection refused
//		when().get("/").then().log();
	}
	

    @Before
    public void setup() throws Exception {
    	RestAssured.port = serverPort;

    }



	// @After
	// public void tearDown() {
	// session.purgeDatabase();
	// }

	@Test
	public void restCRUDUsers() {
		//User myUser = new User("tom");
		// userRepository.save(myUser);

		// when().get("/").then().log();

	}

}
