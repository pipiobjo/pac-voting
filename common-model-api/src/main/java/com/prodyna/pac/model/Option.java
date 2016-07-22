package com.prodyna.pac.model;

import java.util.Set;

public interface Option {

	/**
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * 
	 * @param description
	 */
	void setDescription(String description);

	/**
	 * @return the optionId
	 */
	String getOptionId();

	/**
	 * @param optionId
	 *            the optionId to set
	 */
	void setOptionId(String optionId);

	User getCreator();

	void setCreator(User creator);

	Set<User> getVoters();

	void setVoters(Set<User> voters);

	/**
	 * @return the surveysAssigned
	 */
	Set<Survey> getSurveysAssigned();

	/**
	 * @param surveysAssigned the surveysAssigned to set
	 */
	void setSurveysAssigned(Set<Survey> surveysAssigned);

}