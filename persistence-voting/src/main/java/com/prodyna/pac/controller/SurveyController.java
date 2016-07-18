package com.prodyna.pac.controller;

/**
 * Created by bjoern on 11.02.16.
 */

import com.prodyna.pac.domain.Option;
import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.domain.User;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;
import com.prodyna.pac.service.SampleDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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
        System.out.println("==== in Survey ====");
        return sampleService.createSampleData();
           }




    @RequestMapping(value="/clearDatabase", method = RequestMethod.GET)
    public void deleteAll() {
        surveyRepository.deleteAll();
        userRepository.deleteAll();
        optionRepository.deleteAll();
    }

    @RequestMapping(value="/findSurveyById/{surveyId}", method = RequestMethod.GET)
    public Survey getSurveyById(@PathVariable("surveyId")String surveyId){
    	Survey survey = surveyRepository.findBySurveyId(surveyId);
    	return survey;
    	
    }
}






