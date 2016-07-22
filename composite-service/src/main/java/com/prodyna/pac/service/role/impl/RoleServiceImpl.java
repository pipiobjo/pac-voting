package com.prodyna.pac.service.role.impl;

import org.springframework.stereotype.Component;

import com.prodyna.pac.model.ExecutingUser;
import com.prodyna.pac.model.Roles;
import com.prodyna.pac.service.role.RoleService;


/**
 * Role check if users role name equals the "constants" 
 * 
 * TODO active ldap check
 *
 * @author bjoern
 *
 */
@Component
public class RoleServiceImpl implements RoleService {

	private RoleServiceImpl() {
	}
	/* (non-Javadoc)
	 * @see com.prodyna.pac.service.role.RoleService#isAdmin(com.prodyna.pac.model.ExecutingUser)
	 */
	@Override
	public Boolean isAdmin(ExecutingUser user){
		return Roles.ADMIN.getRoleName().equals(user.getExecutingUserRole());
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.service.role.RoleService#isManager(com.prodyna.pac.model.ExecutingUser)
	 */
	@Override
	public Boolean isManager(ExecutingUser user){
		boolean hasManagerRoleAssigned =  Roles.MANAGER.getRoleName().equals(user.getExecutingUserRole());
		boolean hadAdminRoleAssigned = Roles.ADMIN.getRoleName().equals(user.getExecutingUserRole());
		
		if(hasManagerRoleAssigned || hadAdminRoleAssigned){
			return true;
		}else{
			return false;
		}
	}

	
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.service.role.RoleService#isUser(com.prodyna.pac.model.ExecutingUser)
	 */
	@Override
	public Boolean isUser(ExecutingUser user){
		boolean hasManagerRoleAssigned =  Roles.MANAGER.getRoleName().equals(user.getExecutingUserRole());
		boolean hadAdminRoleAssigned = Roles.ADMIN.getRoleName().equals(user.getExecutingUserRole());
		boolean hadUserRoleAssigned = Roles.USER.getRoleName().equals(user.getExecutingUserRole());
		
		if(hadUserRoleAssigned || hasManagerRoleAssigned || hadAdminRoleAssigned){
			return true;
		}else{
			return false;
		}
	}
}
