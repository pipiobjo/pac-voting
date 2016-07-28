package com.prodyna.pac.service.persistence;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import com.prodyna.pac.service.exception.PersistenceException;

/**
 * Handle all the persistence logic of of surveys and all the other voting
 * objects
 * 
 * @author bjoern
 *
 */
public interface SurveyPersistenceService {
	/**
	 * 
	 * @param surveyId
	 * @return
	 * @throws Exception
	 */
	@HystrixCommand
	Survey getSurveyBySurveyId(String surveyId) throws Exception;

	/**
	 * 
	 * @param creator
	 * @return
	 */
	@HystrixCommand
	List<Survey> getSurveyByCreator(String creator);

	/**
	 * 
	 * @return
	 */
	@HystrixCommand
	List<Survey> getAllSurveys();

	/**
	 * Vote on a survey
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 * @return
	 * @throws PersistenceException 
	 */
	@HystrixCommand
	Survey voteSurvey(String surveyId, String optionId, String userId) throws PersistenceException;

	/**
	 * Creating a survey with options, creator and votes
	 * 
	 * @param survey
	 * @return
	 * @throws PersistenceException 
	 */
	@HystrixCommand
	Survey createSurvey(Survey survey) throws PersistenceException;

	/**
	 * Updating a surveya with options, creator and votes
	 * 
	 * @param survey
	 * @return
	 * @throws PersistenceException 
	 */
	@HystrixCommand
	Survey updateSurvey(Survey survey) throws PersistenceException;

	/**
	 * Get User by given Id
	 * 
	 * @param executingUser
	 * @return null if no User found
	 */
	@HystrixCommand
	VotingUser getUser(String executingUser);

}