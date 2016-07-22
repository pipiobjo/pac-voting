package com.prodyna.pac.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.model.ExecutingUser;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.persistence.SurveyPersistenceService;
import com.prodyna.pac.service.voting.VotingService;

/**
 * Exposing the Survey Rest API.
 */
@RestController
public class SurveyController {

	@Autowired
	private SurveyPersistenceService surveyService;

	@Autowired
	VotingService votingService;

	@RequestMapping(value = "/surveys", method = RequestMethod.GET)
	public List<Survey> getAllSurvey(
		// @formatter:off
			@RequestHeader("VOTING_EXECUTIVE_USER") String executingUser,
			@RequestHeader("VOTING_EXEUCTING_AS_ROLE") String executingUserRole)
			throws Exception {
		// 	@formatter:on	
		ExecutingUser eU = new ExecutingUser(executingUser, executingUserRole);
		return votingService.getAllSurveys(eU);
	}

	@RequestMapping(value = "/surveys", method = RequestMethod.POST)
	public Survey createSurvey(
		// @formatter:off
			Survey survey, 
			@RequestHeader("VOTING_EXECUTIVE_USER") String executingUser,
			@RequestHeader("VOTING_EXEUCTING_AS_ROLE") String executingUserRole) 
			throws Exception {
		// 	@formatter:on
		ExecutingUser eU = new ExecutingUser(executingUser, executingUserRole);
		return votingService.createSurvey(survey, eU);
	}

	@RequestMapping(value = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}", method = RequestMethod.POST)
	public Survey voteOption(
		// @formatter:off
    		@PathVariable("surveyId") String surveyId, 
    		@PathVariable("optionId") String optionId, 
    		@PathVariable("userId") String userId,
    		@RequestHeader("VOTING_EXECUTIVE_USER") String executingUser, 
    		@RequestHeader("VOTING_EXEUCTING_AS_ROLE") String executingUserRole
    		) throws Exception {
    	// @formatter:on
		ExecutingUser eU = new ExecutingUser(executingUser, executingUserRole);
		return votingService.voteSurvey(surveyId, optionId, userId, eU);
	}

}
