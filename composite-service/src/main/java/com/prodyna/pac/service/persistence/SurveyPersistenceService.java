package com.prodyna.pac.service.persistence;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prodyna.pac.model.Survey;

public interface SurveyPersistenceService {

	Survey getSurveyBySurveyId(String surveyId) throws Exception;

	/**
	 * 
	 * @param creator
	 * @return
	 */
	List<Survey> getSurveyByCreator(String creator);

	List<Survey> getAllSurveys() throws Exception;

	/**
	 * Vote on a survey
	 * 
	 * @param surveyId
	 * @param optionId
	 * @param userId
	 * @return
	 */
	Survey voteSurvey(String surveyId, String optionId, String userId);

	List<Survey> createSurvey(Survey survey);

}