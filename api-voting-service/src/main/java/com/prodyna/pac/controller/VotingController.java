package com.prodyna.pac.controller;

import com.prodyna.pac.service.SurveyService;
import com.prodyna.pac.model.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * Created by bjoern on 04.07.16.
 */
@RestController
public class VotingController {
    private static final Logger LOG = LoggerFactory.getLogger(VotingController.class);

    @Autowired
    SurveyService surveyService;


   @RequestMapping(value = "/surveys", method = RequestMethod.GET)
   @CrossOrigin(origins="*", maxAge=3600)
    public List<Survey> votes(){
       //Principal principal1
       Principal principal = SecurityContextHolder.getContext().getAuthentication();
       LOG.info("USER=" + principal);
       return surveyService.getAllSurvey();

        //return new Message("Hello World");
    }






    @RequestMapping(value = "/user/votes", method = RequestMethod.GET)
    @CrossOrigin(origins="*", maxAge=3600)
    public Message uservotes(){
        //Principal principal1
        //LOG.error("Hey wow we have an principal=" + principal1);


        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("USER=" + principal);
        return new Message("Hello World");
    }







    @RequestMapping(value = "/survey", method = RequestMethod.GET)
    public Message resourcevotes(Principal principal){
        LOG.info("resource USER=" + principal);
        return new Message("Hello World");
    }

    class Message {
        private String id = UUID.randomUUID().toString();
        private String content;

        Message() {
        }

        public Message(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }


}
