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
		return user.getExecutingUserRole().contains(Roles.ADMIN.getRoleName());
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.service.role.RoleService#isManager(com.prodyna.pac.model.ExecutingUser)
	 */
	@Override
	public Boolean isManager(ExecutingUser user){
		boolean hasManagerRoleAssigned =  user.getExecutingUserRole().contains(Roles.MANAGER.getRoleName());
		boolean hadAdminRoleAssigned = user.getExecutingUserRole().contains(Roles.ADMIN.getRoleName());
		
		if(hasManagerRoleAssigned || hadAdminRoleAssigned){
			return true;
		}
		return false;
	}

	
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.service.role.RoleService#isUser(com.prodyna.pac.model.ExecutingUser)
	 */
	@Override
	public Boolean isUser(ExecutingUser user){
		boolean hasManagerRoleAssigned =  user.getExecutingUserRole().contains(Roles.MANAGER.getRoleName());
		boolean hadAdminRoleAssigned = user.getExecutingUserRole().contains(Roles.ADMIN.getRoleName());
		boolean hadUserRoleAssigned = user.getExecutingUserRole().contains(Roles.USER.getRoleName());
		
		if(hadUserRoleAssigned || hasManagerRoleAssigned || hadAdminRoleAssigned){
			return true;
		}
		return false;
	}
	@Override
	public Boolean isAnonymous(ExecutingUser user) {
		boolean hasManagerRoleAssigned =  user.getExecutingUserRole().contains(Roles.MANAGER.getRoleName());
		boolean hadAdminRoleAssigned = user.getExecutingUserRole().contains(Roles.ADMIN.getRoleName());
		boolean hadUserRoleAssigned = user.getExecutingUserRole().contains(Roles.USER.getRoleName());
		boolean hadUserGuestRoleAssigned = user.getExecutingUserRole().contains(Roles.GUEST.getRoleName());
		
		if(hadUserGuestRoleAssigned || hadUserRoleAssigned || hasManagerRoleAssigned || hadAdminRoleAssigned){
			return true;
		}

		return false;
	}
}
