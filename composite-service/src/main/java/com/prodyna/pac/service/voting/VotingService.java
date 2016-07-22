package com.prodyna.pac.service.voting;

import java.util.List;

import com.prodyna.pac.model.ExecutingUser;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.exception.ActionNotAllowedExcpetion;
import com.prodyna.pac.service.exception.VotingServiceException;
//TODO write more usefull javadoc
/**
 * Definition of all the business methods
 * @author bjoern
 *
 */
public interface VotingService {

	/**
	 * 
	 * @param eU
	 * @return
	 * @throws ActionNotAllowedExcpetion
	 */
	List<Survey> getAllSurveys(ExecutingUser eU) throws VotingServiceException;

	/**
	 * 
	 * @param survey
	 * @param eU
	 * @return
	 * @throws ActionNotAllowedExcpetion
	 */
	Survey createSurvey(Survey survey, ExecutingUser eU) throws VotingServiceException;

	/**
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 * @param eU
	 * @return
	 * @throws ActionNotAllowedExcpetion
	 */
	Survey voteSurvey(String surveyId, String optionId, String userId, ExecutingUser eU) throws VotingServiceException;

}
