package com.prodyna.pac.service.persistence;

import java.util.List;

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
	Survey getSurveyBySurveyId(String surveyId) throws Exception;

	/**
	 * 
	 * @param creator
	 * @return
	 */
	List<Survey> getSurveyByCreator(String creator);

	/**
	 * 
	 * @return
	 */
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
	Survey voteSurvey(String surveyId, String optionId, String userId) throws PersistenceException;

	/**
	 * Creating a survey with options, creator and votes
	 * 
	 * @param survey
	 * @return
	 * @throws PersistenceException 
	 */
	Survey createSurvey(Survey survey) throws PersistenceException;

	/**
	 * Updating a surveya with options, creator and votes
	 * 
	 * @param survey
	 * @return
	 * @throws PersistenceException 
	 */
	Survey updateSurvey(Survey survey) throws PersistenceException;

	/**
	 * Get User by given Id
	 * 
	 * @param executingUser
	 * @return null if no User found
	 */
	VotingUser getUser(String executingUser);

}