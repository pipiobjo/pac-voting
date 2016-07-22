/**
 * 
 */
package com.prodyna.pac.service.voting.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodyna.pac.model.ExecutingUser;
import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import com.prodyna.pac.service.exception.ActionNotAllowedExcpetion;
import com.prodyna.pac.service.exception.PersistenceException;
import com.prodyna.pac.service.exception.VotingServiceException;
import com.prodyna.pac.service.ldap.LdapService;
import com.prodyna.pac.service.persistence.SurveyPersistenceService;
import com.prodyna.pac.service.role.RoleService;
import com.prodyna.pac.service.voting.VotingService;

/**
 * Handles all Voting business logic including role checks
 * @author bjoern
 *
 */
@Service
public class VotingServiceImpl implements VotingService {

	@Autowired
	SurveyPersistenceService persistence;
	@Autowired
	LdapService ldap;
	@Autowired
	RoleService roleService;

	@Override
	public List<Survey> getAllSurveys(ExecutingUser eU) throws ActionNotAllowedExcpetion  {
		if(!roleService.isUser(eU)){
			throw new ActionNotAllowedExcpetion("You are not allowed to vote");
		}
		return persistence.getAllSurveys();
	}

	/**
	 * Logic to create a survey
	 */
	@Override
	public Survey createSurvey(Survey survey, ExecutingUser eU) throws VotingServiceException {

		if (roleService.isUser(eU)) {

			VotingUser user = persistence.getUser(eU.getExecutingUser());

			if (user != null) {

				user = new VotingUser();
				user.setUserId(eU.getExecutingUser());
			}

			survey.setCreator(user);

			// TODO Option Validation

			try {
				return persistence.createSurvey(survey);
			} catch (PersistenceException e) {
				throw new VotingServiceException("Error while creating Survey", e);
			}
		} else {
			throw new ActionNotAllowedExcpetion("Creating surveys is not allowed");
		}

	}
	/**
	 * Vote on a survey
	 */
	@Override
	public Survey voteSurvey(String surveyId, String optionId, String userId, ExecutingUser eU)
			throws ActionNotAllowedExcpetion {
		if(!roleService.isUser(eU)){
			throw new ActionNotAllowedExcpetion("You are not allowed to vote");
		}
		return persistence.voteSurvey(surveyId, optionId, userId);
	}

}
