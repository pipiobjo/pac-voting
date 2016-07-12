package com.prodyna.pac.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.prodyna.pac.config.MyNeo4jConfigurationTest;
import com.prodyna.pac.domain.Option;
import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.domain.User;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;

/**
 * Created by bjoern on 07.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyNeo4jConfigurationTest.class })
@WebAppConfiguration
//Cleanup the context before we start a new one
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Neo4jConfigurationSimpleTest  {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OptionGraphRepository optionRepository;
	
	@Autowired
	private SurveyGraphRepository surveyRepository;

	@Autowired
	private UserGraphRepository userRepository;

	@Autowired 
	Session session;
	
	
	String optionCreatorName = "optionCreator";
	String voter3Name = "voter3";
	String optionId; 
	
	@Before
	public void initDB(){
		User surveyCreator = new User("surveyCreator");
        userRepository.save(surveyCreator);
        

        User optionCreator = new User(optionCreatorName);
        userRepository.save(optionCreator);
        User voter = new User("voter");
        userRepository.save(voter);
        User voter2 = new User("voter2");
        userRepository.save(voter2);

        Option vop0 = new Option("Opt1", optionCreator);
        optionRepository.save(vop0);
        Option vop1 = new Option("Opt2", optionCreator);
        optionRepository.save(vop1);
        optionId = vop1.getOptionId();

        Set<Option> vops = new HashSet<Option>();
        vops.add(vop0);
        vops.add(vop1);



        String description = "my First survey description text may be this should be a little bit longer to explain something";
        String title = "My First Survey";
        Survey myFirstSurvey = new Survey(description, title, vops, surveyCreator);

        surveyRepository.save(myFirstSurvey);


        User voter3 = new User("voter3");
        userRepository.save(voter3);

        Option vop2 = new Option("Opt3", optionCreator);
        optionRepository.save(vop2);
        
        Option vop3 = new Option("Opt4", optionCreator);
        optionRepository.save(vop3);
        
        Set<Option> vops2 = new HashSet<Option>();
        vops2.add(vop2);
        vops2.add(vop3);


        String description2 = "Second Survey desc";
        String title2 = "My Second Survey";
        Survey mySecondSurvey = new Survey(description2, title2, vops2, surveyCreator);

        surveyRepository.save(mySecondSurvey);

        
	}
	@After
	public void tearDown() {
		session.purgeDatabase();
	}

	
	
	
	@Test
    public void surveyCrudTest() {
		List<Survey> findAll = surveyRepository.findAll();
		
		Assert.assertTrue("Expecting 2 Surveys, but getting " + findAll.size(), findAll.size()==2);
		
	}
	
	@Test
    public void optionTest() {
		List<Option> findAll = optionRepository.findAll();
		Assert.assertTrue("Expecting 4 Options, but getting " + findAll.size(), findAll.size()==4);
		
		User oC = userRepository.findByUserId(optionCreatorName);
		Assert.assertNotNull("Excpecting a user",oC);
		
		//testing already created relationships
		Set<Option> createdOptions = oC.getCreatedOptions();
		Assert.assertNotNull("Expecting created Options", createdOptions);
		
		int size = createdOptions.size();
		Assert.assertEquals("Expecting options", 4, size);
		
		for (Option option : createdOptions){
			Option findByOptionId = optionRepository.findByOptionId(option.getOptionId());
			Assert.assertNotNull("Getting option byId", findByOptionId);
		}
		//testing new relationships
		Set<Survey> surveys = new HashSet<>();
		User oV = userRepository.findByUserId(voter3Name);
		for(Option option : createdOptions){
			Set<User> voters = option.getVoters();
			Assert.assertNotNull("Expecting empty Set, not null", voters);
			Assert.assertTrue("Expecting empty Set", voters.isEmpty());
			voters.add(oV);
			option.setVoters(voters);
			
			Set<Survey> surveysAssigned = option.getSurveysAssigned();
			Assert.assertNotNull("Expecting Survey for option" + option.getDescription() , surveysAssigned);
			Assert.assertTrue("Expecting more than 0 surveys", surveysAssigned.size()>0);
			surveys.addAll(surveysAssigned);
			
		}
		for(Survey s: surveys){
			Survey survey = surveyRepository.findBySurveyId(s.getSurveyId());
			Set<Option> options = survey.getOptions();
			Assert.assertTrue("Expecting more than 0 options", options.size()>0);
			for(Option opt: options){
				Set<User> voters = opt.getVoters();
				Assert.assertNotNull("Expecting a voter for survey=" + s.getDescription() + " option=" + opt.getDescription(), voters);
				Assert.assertEquals("Expecting one voter", 1, voters.size());
			}
			
		}
		User votUser = userRepository.findByUserId(voter3Name);
		Option option2VoteTwice = optionRepository.findByOptionId(this.optionId);
		Set<User> voters = option2VoteTwice.getVoters();
		Assert.assertTrue("Expecting 1 voter, but getting " + voters.size(), voters.size()==1);
		Assert.assertTrue("Expecting voter has already voted ",voters.contains(votUser));
		
		
		
		
		
		
	}
	
}
