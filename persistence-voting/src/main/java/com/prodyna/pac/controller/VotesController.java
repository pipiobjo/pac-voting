package com.prodyna.pac.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.domain.Option;
import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.domain.User;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;

/**
 * 
 * @author bjoern
 *
 */
@RestController
public class VotesController {

	@Autowired
	SurveyGraphRepository surveyRepository;

	@Autowired
	UserGraphRepository userRepository;

	@Autowired
	OptionGraphRepository optionRepository;

	/**
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 */
	@RequestMapping(value = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public void voteOption(@PathVariable("surveyId") String surveyId, @PathVariable("optionId") String optionId,
			@PathVariable("userId") String userId) {
		Survey survey = surveyRepository.findBySurveyId(surveyId);
		if (survey == null) {
			throw new IllegalArgumentException(
					"The 'surveyId' parameter must not be null or empty and must and existing survey");
		}

		Set<Option> options = survey.getOptions();
		Option option = null;
		// expecting not many options so looping is okay
		for (Option opt : options) {
			if (opt.getOptionId().equals(optionId)) {
				option = opt;
			}
		}
		if (option == null) {
			throw new IllegalArgumentException("The given survey=" + surveyId + " has no option=" + optionId);
		}

		User user = userRepository.findByUserId(userId);
		if (user == null) {
			user = new User(userId);
		}
		Set<User> voters = option.getVoters();
		voters.add(user);

		option.setVoters(voters);
		options.add(option);
		survey.setOptions(options);
		surveyRepository.save(survey);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
}
