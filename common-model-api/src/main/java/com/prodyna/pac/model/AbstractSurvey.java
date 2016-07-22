package com.prodyna.pac.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AbstractSurvey {

	private String description;
	private String title;
	private String surveyId;
	private Set<AbstractOption> options = new HashSet<AbstractOption>();
	private AbstractUser creator;

	public AbstractSurvey() {

	}

	public AbstractSurvey(String description, String title, Set<AbstractOption> options, AbstractUser creator) {

		this.description = description;
		this.title = title;
		this.options = options;
		this.creator = creator;
		this.surveyId = UUID.randomUUID().toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getSurveyId() {

		return this.surveyId;
	}

	public void setId(String surveyId) {
		this.surveyId = surveyId;
	}

	public Set<AbstractOption> getOptions() {

		return options;
	}

	public void setOptions(Set<AbstractOption> options) {

		this.options = options;
	}

	public AbstractUser getCreator() {
		return creator;
	}

	public void setCreator(AbstractUser creator) {

		this.creator = creator;
	}

	public String toString() {
		String results = title + "'s Options include\n";
		if (options != null) {
			for (AbstractOption vop : options) {
				results += "\t- OptionId" + vop.getOptionId() + "\n";
			}
		}
		results += "\t creator=" + this.creator.toString();
		return results;
	}

}