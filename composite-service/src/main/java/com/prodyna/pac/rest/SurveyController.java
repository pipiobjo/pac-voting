package com.prodyna.pac.rest;

import com.prodyna.pac.model.Survey;
import com.prodyna.pac.service.SurveyPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by bjoern on 06.07.16.
 */
@RestController
public class SurveyController {

    @Autowired
    private SurveyPersistenceService surveyService;


    @RequestMapping(value = "/surveys", method = RequestMethod.GET)
    public List<Survey> getAllSurvey() throws Exception {
        return surveyService.getAllSurveys();
    }
}
