package com.prodyna.pac.controller;

/**
 * Created by bjoern on 11.02.16.
**/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.repo.OptionGraphRepository;
import com.prodyna.pac.repo.SurveyGraphRepository;
import com.prodyna.pac.repo.UserGraphRepository;

@RestController
public class VotesController {

    @Autowired
    SurveyGraphRepository surveyRepository;

    @Autowired
    UserGraphRepository userRepository;

    @Autowired
    OptionGraphRepository optionRepository;

    @RequestMapping(value = "/vote/surveys/{surveyId}/option/{optionId}/users/{userId}", method = RequestMethod.POST)
    public Survey voteOption(@PathVariable("surveyId") String surveyId, @PathVariable("optionId") String optionId, @PathVariable("userId") String userId) throws Exception {
        return surveyRepository.findBySurveyId(surveyId);
        
    }

}






