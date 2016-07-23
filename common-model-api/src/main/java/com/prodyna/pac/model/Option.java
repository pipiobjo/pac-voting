package com.prodyna.pac.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bjoern on 07.03.16.
 */
public class Option {
	String optionId;

	String description;

	public VotingUser creator;

	public Set<VotingUser> voters = new HashSet<VotingUser>();

	public Option() {

	}

	/**
	 *
	 * @param description
	 * @param creator
	 */
	public Option(String description, VotingUser creator) {
		this.description = description;
		this.creator = creator;
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
	 * 
	 * @return
	 */

	public VotingUser getCreator() {
		return creator;
	}

	/**
	 * 
	 * @param creator
	 */
	public void setCreator(VotingUser creator) {
		this.creator = creator;
	}

	/**
	 * 
	 * @return
	 */
	public Set<VotingUser> getVoters() {
		return this.voters;
	}

	/**
	 * 
	 * @param voters
	 */
	public void setVoters(Set<VotingUser> voters) {
		this.voters = voters;
	}

	/**
	 * 
	 * @param user
	 */
	public void vote(VotingUser user) {
		if (this.voters == null) {
			this.voters = new HashSet<VotingUser>();
		}
		this.voters.add(user);
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

}
