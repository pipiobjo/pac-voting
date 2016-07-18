package com.prodyna.pac.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.persistence.SurveyPersistenceService;

/**
 * Exposing the Survey Rest API.
 */
@RestController
public class SurveyController {

    @Autowired
    private SurveyPersistenceService surveyService;


    @RequestMapping(value = "/surveys", method = RequestMethod.GET)
    public List<Survey> getAllSurvey() throws Exception {
        return surveyService.getAllSurveys();
    }

    
    @RequestMapping(value = "/surveys", method = RequestMethod.POST)
    public List<Survey> createSurvey(Survey survey) throws Exception {
        return surveyService.createSurvey(survey);
    }
    
    
    
    
    @RequestMapping(value = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}", method = RequestMethod.POST)
    public Survey voteOption(@PathVariable("surveyId") String surveyId, @PathVariable("optionId") String optionId, @PathVariable("userId") String userId) throws Exception {
        return surveyService.voteSurvey(surveyId, optionId, userId);
    }
    
    
    
}
