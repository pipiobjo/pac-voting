package com.prodyna.pac.model;

import java.util.Set;

public interface Survey {

	String getDescription();

	void setDescription(String description);

	String getTitle();

	void setTitle(String title);

	String getSurveyId();

	void setId(String surveyId);

	Set<Class<? extends Option>> getOptions();

	void setOptions(Set<Class<? extends Option>> options);

	Class<? extends User> getCreator();

	void setCreator(Class<? extends User> creator);

	String toString();
}