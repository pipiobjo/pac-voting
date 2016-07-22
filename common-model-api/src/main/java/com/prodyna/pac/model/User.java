package com.prodyna.pac.model;

import java.util.Set;

public interface User {

	String getUserId();

	Long getGraphId();

	String toString();

	//Voted OPTIONS
	void setVotes(Set<Option> votes);

	Set<Option> getVotes();

	//Created Surveys
	Set<Survey> getCreatedSurveys();

	void setCreatedSurveys(Set<Survey> createdSurveys);

	//Created Options
	Set<Option> getCreatedOptions();

	void setCreatedOptions(Set<Option> createdOptions);

}