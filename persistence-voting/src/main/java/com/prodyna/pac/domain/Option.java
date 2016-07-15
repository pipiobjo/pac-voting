package com.prodyna.pac.domain;

import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
/**
 * Created by bjoern on 07.03.16.
 */
@NodeEntity
public class Option {
	@GraphId
	private Long graphId;

	private String optionId;

	private String description;

	@Relationship(type = "OPTION_CREATED_BY", direction = Relationship.OUTGOING)
	private User creator;

	@Relationship(type = "OPTION_VOTED_BY", direction = Relationship.OUTGOING)
	private Set<User> voters = new HashSet<User>();

	@Relationship(type="SURVEY_HAS_VOTING_OPTIONS", direction = Relationship.INCOMING)
	private Set<Survey> surveysAssigned = new HashSet<>();
	
	
	public Option() {

	}

	/**
	 *
	 * @param description
	 * @param creator
	 */
	public Option(String description, User creator) {
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

	@Relationship(type = "OPTION_CREATED_BY", direction = Relationship.OUTGOING)
	public User getCreator() {
		return creator;
	}

	@Relationship(type = "OPTION_CREATED_BY", direction = Relationship.OUTGOING)
	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Relationship(type = "OPTION_VOTED_BY", direction = Relationship.OUTGOING)
	public Set<User> getVoters() {
		return this.voters;
	}

	@Relationship(type = "OPTION_VOTED_BY", direction = Relationship.OUTGOING)
	public void setVoters(Set<User> voters) {
		this.voters = voters;
	}

	/**
	 * @return the surveysAssigned
	 */
	public Set<Survey> getSurveysAssigned() {
		return surveysAssigned;
	}

	/**
	 * @param surveysAssigned the surveysAssigned to set
	 */
	public void setSurveysAssigned(Set<Survey> surveysAssigned) {
		this.surveysAssigned = surveysAssigned;
	}
	
	
	

}
