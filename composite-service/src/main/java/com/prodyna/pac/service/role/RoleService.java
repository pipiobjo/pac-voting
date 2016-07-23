package com.prodyna.pac.service.role;

import com.prodyna.pac.model.ExecutingUser;
/**
 * Handles the role checks of a given Execution User
 * @author bjoern
 *
 */
public interface RoleService {

	/**
	 * Check if User has role admin
	 */
	Boolean isAdmin(ExecutingUser user);

	/**
	 * Checks if user has role manager or the upper role admin
	 * @param user
	 * @return
	 */
	Boolean isManager(ExecutingUser user);

	/**
	 * Checks if user has role user or one of the upper roles admin, manager
	 * @param user
	 * @return
	 */
	Boolean isUser(ExecutingUser user);
	
	/**
	 * Checks if user is an unauthenticated user
	 * @param user
	 * @return
	 */
	Boolean isAnonymous(ExecutingUser user);

}