package com.prodyna.pac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;
import com.prodyna.pac.service.SampleDataService;

@EnableAutoConfiguration
@RestController
public class SurveyController {
    @Autowired
    SurveyGraphRepository surveyRepository;

    @Autowired
    UserGraphRepository userRepository;

    @Autowired
    OptionGraphRepository optionRepository;
    
    
    @Autowired
    SampleDataService sampleService;

    @RequestMapping(value = "/createRandomSurvey", method = RequestMethod.GET)
    public @ResponseBody
    List<Survey> createRandomSurvey() {
        return sampleService.createSampleData();
           }




    @RequestMapping(value="/clearDatabase", method = RequestMethod.GET)
    public void deleteAll() {
        surveyRepository.deleteAll();
        userRepository.deleteAll();
        optionRepository.deleteAll();
    }

    @RequestMapping(value="/findSurveyById/{surveyId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
    public Survey getSurveyById(@PathVariable("surveyId")String surveyId){
    	Survey survey = surveyRepository.findBySurveyId(surveyId);
    	return survey;
    	
    }
}






