package com.prodyna.pac.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AbstractOption {

	private String optionId;
	private String description;
	private AbstractUser creator;
	private Set< Class ? extends AbstractUser> voters = new HashSet<>();
	private Set<AbstractSurvey> surveysAssigned = new HashSet<>();

	/**
	 * Creates an empty Option
	 */
	public AbstractOption() {
		super();
	}

	/**
	 * Creates an Option with a random optionId
	 * 
	 * @param description
	 * @param creator
	 */
	public AbstractOption(String description, AbstractUser creator) {
		this.description = description;
		this.creator = creator;
		this.optionId = UUID.randomUUID().toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the optionId
	 */
	public String getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId
	 *            the optionId to set
	 */
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public AbstractUser getCreator() {
		return creator;
	}

	public void setCreator(AbstractUser creator) {
		this.creator = creator;
	}

	public Set<AbstractUser> getVoters() {
		return this.voters;
	}

	public void setVoters(Set<AbstractUser> voters) {
		this.voters = voters;
	}

	/**
	 * @return the surveysAssigned
	 */
	public Set<AbstractSurvey> getSurveysAssigned() {
		return surveysAssigned;
	}

	/**
	 * @param surveysAssigned
	 *            the surveysAssigned to set
	 */
	public void setSurveysAssigned(Set<AbstractSurvey> surveysAssigned) {
		this.surveysAssigned = surveysAssigned;
	}

}