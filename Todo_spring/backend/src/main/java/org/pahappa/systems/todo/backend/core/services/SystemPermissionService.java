package org.pahappa.systems.todo.backend.core.services;

import java.util.Set;

import org.sers.webutils.model.security.Permission;
import org.sers.webutils.model.security.User;

/**
 * 
 * @author ttc
 *
 */
public interface SystemPermissionService extends GenericService<Permission> {
	
	/**
	 * This method attaches permissions to the admin of the system the user is logged into
	 * @param permissions
	 * @param user
	 */
	void attachPermsToAdmin(Set<Permission> permissions, User loggedInUser);
}
