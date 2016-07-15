/**
 * 
 */
package com.prodyna.pac.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prodyna.pac.domain.Option;
import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.domain.User;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;

/**
 * @author bjoern
 *
 */
@Component
public class SampleDataService {

	@Autowired
	SurveyGraphRepository surveyRepository;

	@Autowired
	UserGraphRepository userRepository;

	@Autowired
	OptionGraphRepository optionRepository;

	public List<Survey> createSampleData() {
		User surveyCreator = new User("surveyCreator");
		userRepository.save(surveyCreator);

		User optionCreator = new User("optionCreator");
		userRepository.save(optionCreator);
		User voter = new User("voter");
		userRepository.save(voter);
		User voter2 = new User("voter2");
		userRepository.save(voter2);

		Option vop0 = new Option("Opt1", optionCreator);
		// vop0.vote(voter);
		Option vop1 = new Option("Opt2", optionCreator);
		// vop1.vote(voter);
		// vop1.vote(voter2);

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
		// vop2.vote(voter);
		Option vop3 = new Option("Opt4", optionCreator);
		// vop3.vote(voter);
		// vop3.vote(voter2);
		// vop3.vote(voter3);

		Set<Option> vops2 = new HashSet<Option>();
		vops2.add(vop2);
		vops2.add(vop3);

		String description2 = "Second Survey desc";
		String title2 = "My Second Survey";
		Survey mySecondSurvey = new Survey(description2, title2, vops2, surveyCreator);

		surveyRepository.save(mySecondSurvey);

		return surveyRepository.findAll();

	}

}
