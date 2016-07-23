package com.prodyna.pac.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.SurveyService;
import com.prodyna.pac.service.exception.BackendServiceException;

/**
 * Created by bjoern on 04.07.16.
 */
@RestController
public class VotingController {
	private static final Logger LOG = LoggerFactory.getLogger(VotingController.class);

	@Autowired
	SurveyService surveyService;

	@RequestMapping(value = "/surveys", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", maxAge = 3600)
	public List<Survey> getAllSurveys(Principal userPrincipal) throws BackendServiceException {
		// Principal principal1
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		LOG.info("USER=" + principal);
		return surveyService.getAllSurvey(principal);

	}

	@RequestMapping(value = "/surveys", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", maxAge = 3600)
	public Survey createSurvey(Survey survey) throws BackendServiceException {
		// Principal principal1
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		LOG.info("USER=" + principal);
		return surveyService.createSurvey(survey, principal);

	}

	@RequestMapping(value = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}", method = RequestMethod.POST)
	public void vote(
		// @formatter:off
    		Principal principal,
    		@PathVariable("surveyId") String surveyId, 
    		@PathVariable("optionId") String optionId, 
    		@PathVariable("userId") String userId
    		) throws Exception {
    	// @formatter:on

		LOG.info("resource USER=" + principal);
		surveyService.voteSurvey(surveyId, optionId, userId, principal);
	}

}
