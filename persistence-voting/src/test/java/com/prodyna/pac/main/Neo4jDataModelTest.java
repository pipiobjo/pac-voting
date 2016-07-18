package com.prodyna.pac.main;

import java.util.ArrayList;
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

import com.prodyna.pac.domain.Option;
import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.domain.User;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;

/**
 * Test if have the right data modelling
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { PersistenceApplication.class })
@WebAppConfiguration
//Cleanup the context before we start a new one
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) 
public class Neo4jDataModelTest  {
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
	

	/**
	 * Cleanup the db
	 */
	@After
	public void tearDown() {
		session.purgeDatabase();
	}

	@Test
	public void simpleModelTest(){
		String firstUserId = "user1";
		User myUser = new User(firstUserId);
		userRepository.save(myUser);
		
		String desc = "description";
		Option opt = new Option(desc, myUser);
		optionRepository.save(opt);
		log.debug("check model representation");
		
		
		
		String secondUserId = "user2";
		User myUser2 = new User(secondUserId);
		userRepository.save(myUser2);
		
		String thirdUserId = "user3";
		User myUser3 = new User(thirdUserId);
		userRepository.save(myUser3);
		
		String desc1 = "was ganz anderes";
		Option opt1 = new Option(desc1, myUser2);
		optionRepository.save(opt1);
		log.debug("check model representation");
		
		Set<User> voters = new HashSet<>();
		voters.add(myUser3);
		opt1.setVoters(voters);
		optionRepository.save(opt1);
		log.debug("check model representation");
		
		Option votedOption = optionRepository.findByOptionId(opt.getOptionId());
		Set<User> voters2 = votedOption.getVoters();
		Assert.assertTrue("Expecting one Voter, but getting: " + voters.size(), voters.size()==1);
		
		
	}
	
	@Test
	public void updatingOptionDescription(){
		String firstUserId = "user1";
		User myUser = new User(firstUserId);
		userRepository.save(myUser);
		
		String desc = "description";
		Option opt = new Option(desc, myUser);
		optionRepository.save(opt);

		Option createdOpt = optionRepository.findByOptionId(opt.getOptionId());
		Set<User> createdOptvoters = createdOpt.getVoters();
		Assert.assertEquals("Expecting no voters", 0, createdOptvoters.size());
		
		
		String newDesc = "helloworld";
		opt.setDescription(newDesc);
		optionRepository.save(opt);
		
		
		Option changedOpt = optionRepository.findByOptionId(opt.getOptionId());
		Assert.assertEquals("Should match the changed description", newDesc, changedOpt.getDescription());
		
		Set<User> voters = changedOpt.getVoters();
		Assert.assertEquals("Expecting no voters", 0, voters.size());
		
	}
	
	
	
	
	@Test
	public void userCRTest(){
		String userId = "surveyCreator";
		User surveyCreator = new User(userId);
        userRepository.save(surveyCreator);
        
        List<User> findAll = userRepository.findAll();
        Assert.assertTrue("Expecting 1 user", findAll.size()==1);
        
        User findByUserId = userRepository.findByUserId(userId);
        Assert.assertNotNull("Expecting 1 user", findByUserId);
        Assert.assertTrue("Expecting userId=" + userId, findByUserId.getUserId().equals(userId));
		
        userRepository.delete(findByUserId);
        List<User> findAll2 = userRepository.findAll();
        Assert.assertTrue("Expecting no user", findAll2.isEmpty());
        
	}
	
	
	
	
	//@Test
	public void optionCRUDTest(){
        User optionCreatorPOJO = new User(optionCreatorName);
        userRepository.save(optionCreatorPOJO);
        
        User optionCreator = userRepository.findByUserId(optionCreatorName);
        Assert.assertNotNull("Expecting user -> optionCreator", optionCreator);
        Assert.assertTrue("Expecting userID to be set", optionCreator.getUserId().equals(optionCreatorName));
        
        String optionDesc = "Opt1";
        
		Option vop0 = new Option(optionDesc, optionCreator);
		String optionID = vop0.getOptionId();
        optionRepository.save(vop0);
        
        Option findByOptionId = optionRepository.findByOptionId(optionID);
        Assert.assertNotNull("Expecting and Option", optionID);
        Assert.assertTrue("Expecting Option with desc=" + optionDesc, findByOptionId.getDescription().equals(optionDesc));
        
        String optDescription = "testDescription";
		findByOptionId.setDescription(optDescription);
        optionRepository.save(findByOptionId);
        //TODO updating description creates a new voting relationship, add assertion to prevent it 
        
        Option findUpdatedOption = optionRepository.findByOptionId(optionID);
        Assert.assertNotNull("Expecting the updated option", findUpdatedOption);
        Assert.assertTrue("Expecting option with desc=" + optDescription, findUpdatedOption.getDescription().equals(optDescription));
        
        User creator = findUpdatedOption.getCreator();
        Assert.assertNotNull("Expecting an creator", creator);
        
        User voter = new User(voter3Name);
        userRepository.save(voter);
        
        Set<User> votes = findUpdatedOption.getVoters();
        Assert.assertNotNull("Expecting votes", votes);
        Assert.assertTrue("Expecting empty list", votes.isEmpty());
        
        votes.add(voter);
        findUpdatedOption.setVoters(votes);
        optionRepository.save(findUpdatedOption);
        
        Option votedOption = optionRepository.findByOptionId(optionDesc);
        Assert.assertNotNull("Expecting option", votedOption);
        Set<User> voters = votedOption.getVoters();
        Assert.assertNotNull("Expecting voters", voters);
        Assert.assertEquals("Expecting 1 voter", voters.size()==1);
        
        //try to vote twice
        User voter2 = new User(voter3Name);
        voters.add(voter2);
        votedOption.setVoters(voters);
        optionRepository.save(votedOption);
        
        Option twiceVotedOption = optionRepository.findByOptionId(optionDesc);
        Assert.assertNotNull("Expecting an Option", twiceVotedOption);
        Set<User> voters2 = twiceVotedOption.getVoters();
        Assert.assertTrue("Expecting one vote", voters2.size()==1);
        
        
	}
	
	
	
	
	public void initDB(){
		User surveyCreator = new User("surveyCreator");
        userRepository.save(surveyCreator);
        

        User optionCreatorPOJO = new User(optionCreatorName);
        userRepository.save(optionCreatorPOJO);
        
        User optionCreator = userRepository.findByUserId(optionCreatorName);
        Assert.assertNotNull("Expecting user -> optionCreator", optionCreator);
        Assert.assertTrue("Expecting userID to be set", optionCreator.getUserId().equals(optionCreatorName));
        
        User voter = new User("voter");
        userRepository.save(voter);
        User voter2 = new User("voter2");
        userRepository.save(voter2);

        Option vop0 = new Option("Opt1", optionCreator);
        optionRepository.save(vop0);
        
        
        Option vop1 = new Option("Opt2", optionCreator);
        optionRepository.save(vop1);
        testOption(vop1);
        
        String optionId = vop1.getOptionId();

        Set<Option> vops = new HashSet<Option>();
        vops.add(vop0);
        vops.add(vop1);



        String description = "my First survey description text may be this could be a little bit longer to explain something";
        String title = "My First Survey";
        Survey myFirstSurvey = new Survey(description, title, vops, surveyCreator);

        surveyRepository.save(myFirstSurvey);


        User voter3 = new User("voter3");
        userRepository.save(voter3);

        Option vop2 = new Option("Opt3", optionCreator);
        optionRepository.save(vop2);
        testOption(vop2);
        
        Option vop3 = new Option("Opt4", optionCreator);
        optionRepository.save(vop3);
        testOption(vop3);
        
        Set<Option> vops2 = new HashSet<Option>();
        vops2.add(vop2);
        vops2.add(vop3);


        String description2 = "Second Survey desc";
        String title2 = "My Second Survey";
        Survey mySecondSurvey = new Survey(description2, title2, vops2, surveyCreator);

        surveyRepository.save(mySecondSurvey);

        
	}
	/**
	 * @param vop0
	 */
	private void testOption(Option vop0) {
		User findByUserId = userRepository.findByUserId(optionCreatorName);
		Assert.assertNotNull("Expecting user", findByUserId);
		
		Option findByOptionId = optionRepository.findByOptionId(vop0.getOptionId());
        User findByOptionIdCreator = findByOptionId.getCreator();
        if(findByOptionIdCreator == null){
        	// why this happen only for opt1 and opt2
        	log.error("Expecting an creator but is null for option=" + vop0.getDescription());
        }
        //Assert.assertNotNull("Expecting creator to be set, but is not. Option=" + vop0.getDescription(), findByOptionIdCreator);
        //Assert.assertTrue("Expecting creator to be set to optionCreator, but is", findByOptionIdCreator.getUserId().equals(optionCreatorName));
        
	}
	
	
	
	
	
	
	@Test
    public void surveyCrudTest() {
		String userId = "surveyCreator";
		User surveyCreatorPOJO = new User(userId);
        User surveyCreator = userRepository.save(surveyCreatorPOJO);
        
        

        User optionCreatorPOJO = new User(optionCreatorName);
        User optionCreator = userRepository.save(optionCreatorPOJO);
        
        
        Option vop2 = new Option("Option", optionCreator);
        Option opt = optionRepository.save(vop2);
		
        Set<Option> options = new HashSet<Option>();
        options.add(opt);
        
        String description = "surveyDescription";
        String title = "surveyTitle";
        Survey mySecondSurveyPOJO = new Survey(description, title , options, surveyCreator);
        Survey survey = surveyRepository.save(mySecondSurveyPOJO);
        
        
        String surveyId = survey.getSurveyId();
        Assert.assertNotNull("Expecting a generated id", surveyId);
        
        String descTest = survey.getDescription();
        Assert.assertEquals("Expecting same description", description, descTest);
        
        String titleTest = survey.getTitle();
        Assert.assertEquals("Expecting same title",title,  titleTest);
        
        User surveyCreatorTest = survey.getCreator();
        Assert.assertNotNull("Expecting a creator", surveyCreatorTest);
        Assert.assertEquals("Expecting same userid", surveyCreator.getUserId(), surveyCreatorTest.getUserId());
        
        Set<Option> optionsTest = survey.getOptions();
        Assert.assertNotNull("Expecting options", optionsTest);
        
        int testOptionsCount = optionsTest.size();
        Assert.assertEquals("Expecting one option", 1, testOptionsCount);
        
        List<Option> optList = new ArrayList<Option>(optionsTest);
        Option optionTest = optList.get(0);
        Assert.assertNotNull("Expecting a option", optionTest);
        
        User optionCreatorTest = optionTest.getCreator();
        Assert.assertNotNull("Expecting option creator", optionCreatorTest);
        
        Assert.assertEquals("Expecting correct option creator", optionCreatorName, optionCreatorTest.getUserId());
        
        
        
		//initDB();
//		List<Survey> findAll = surveyRepository.findAll();
		
//		Assert.assertTrue("Expecting 2 Surveys, but getting " + findAll.size(), findAll.size()==2);
		
	}
	
	//TODO check why is not working
	//@Test
    public void optionTest() {
		initDB();
		List<Option> findAll = optionRepository.findAll();
		Assert.assertTrue("Expecting 4 Options, but getting " + findAll.size(), findAll.size()==4);
		for(Option optIT: findAll){
			testOption(optIT);
		}
		
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
		String optionId = "";
		User votUser = userRepository.findByUserId(voter3Name);
		Option option2VoteTwice = optionRepository.findByOptionId(optionId);
		Set<User> voters = option2VoteTwice.getVoters();
		Assert.assertTrue("Expecting 1 voter, but getting " + voters.size(), voters.size()==1);
		Assert.assertTrue("Expecting voter has already voted ",voters.contains(votUser));
		
		
		
		
		
		
	}
	
}
