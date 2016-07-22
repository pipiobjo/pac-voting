package com.prodyna.pac.model;

import java.util.Set;

public class AbstractUser {

	private String userId;
	private Set<AbstractOption> votes;
	private Set<AbstractSurvey> createdSurveys;
	private Set<AbstractOption> ceatedOptions;

	public AbstractUser() {
		super();
	}

	public AbstractUser(String userId) {
		this.userId = userId;
	}

	public String getUserId() {

		return userId;
	}

	public String toString() {
		return "UserId=" + userId;
	}

	public void setVotes(Set<AbstractOption> votes) {
		this.votes = votes;
	}

	public Set<AbstractOption> getVotes() {

		return this.votes;
	}

	public Set<AbstractSurvey> getCreatedSurveys() {

		return createdSurveys;
	}

	public void setCreatedSurveys(Set<AbstractSurvey> createdSurveys) {
		this.createdSurveys = createdSurveys;
	}

	public Set<AbstractOption> getCreatedOptions() {
		return this.ceatedOptions;
	}

	public void setCreatedOptions(Set<AbstractOption> createdOptions) {
		this.ceatedOptions = createdOptions;
	}


}