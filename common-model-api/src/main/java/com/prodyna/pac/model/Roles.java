package com.prodyna.pac.model;
/** 
 * The roles must match to the defined ldap groups
 * @author bjoern
 *
 */
public enum Roles {
	ADMIN("ROLE_ADMIN"),
	MANAGER("ROLE_MANAGER"),
	GUEST("ROLE_GUEST"),
	USER("ROLE_USERS");


	private String roleName;

	Roles(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}
}