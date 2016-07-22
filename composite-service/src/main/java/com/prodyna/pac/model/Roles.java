package com.prodyna.pac.model;
/** 
 * The roles must match to the defined ldap groups
 * @author bjoern
 *
 */
public enum Roles {
	ADMIN("admin"),
	MANAGER("manager"),
	USER("user");


	private String roleName;

	Roles(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}
}