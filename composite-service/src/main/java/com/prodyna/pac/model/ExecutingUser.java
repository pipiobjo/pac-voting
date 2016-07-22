package com.prodyna.pac.model;

/**
 * The Model for the executing user, the parameters from the spring securtity principal from the api artefact
 * @author bjoern
 *
 */
public class ExecutingUser {

	private String executingUser;
	private String executingUserRole;

	public ExecutingUser(String executingUser, String executingUserRole) {
		this.executingUser = executingUser; 
		this.executingUserRole = executingUserRole;
	}

	/**
	 * @return the executingUser
	 */
	public String getExecutingUser() {
		return executingUser;
	}

	/**
	 * @param executingUser the executingUser to set
	 */
	public void setExecutingUser(String executingUser) {
		this.executingUser = executingUser;
	}

	/**
	 * @return the executingUserRole
	 */
	public String getExecutingUserRole() {
		return executingUserRole;
	}

	/**
	 * @param executingUserRole the executingUserRole to set
	 */
	public void setExecutingUserRole(String executingUserRole) {
		this.executingUserRole = executingUserRole;
	}
	
	

}
