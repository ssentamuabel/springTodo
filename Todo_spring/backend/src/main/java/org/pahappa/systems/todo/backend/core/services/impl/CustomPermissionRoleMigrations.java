package org.pahappa.systems.todo.backend.core.services.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.pahappa.systems.todo.backend.models.security.PermissionInterpreter;
import org.pahappa.systems.todo.backend.models.security.RolesInterpreter;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.migrations.Migration;
import org.sers.webutils.model.security.Permission;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.server.core.dao.PermissionDao;
import org.sers.webutils.server.core.dao.RoleDao;
import org.sers.webutils.server.core.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomPermissionRoleMigrations {

	@Autowired
	PermissionDao permissionDao;

	@Autowired
	RoleDao roleDao;
	
	@Autowired
	PermissionService permissionService;

	@Migration(orderNumber = 1)
	public void savePermissions() {

		//Migrating Permissions
		for (Permission permission : PermissionInterpreter.reflectivelyGetPermissions()) {
			if (permissionDao.searchUniqueByPropertyEqual("name", permission.getName()) == null) {

				try {
					permission.setRecordStatus(RecordStatus.ACTIVE);
					permission.setDateCreated(new Date());
					permission.setDateChanged(new Date());
//					Permission saved = permissionDao.mergeBG(permission);
					permissionDao.mergeBG(permission);

//					Role role = new Role();
//					role.setRecordStatus(RecordStatus.ACTIVE);
//					role.setDescription(permission.getDescription());
//					role.setName(permission.getName());
//					role.setPermissions(new HashSet<>(Arrays.asList(new Permission[] { saved })));
//					roleDao.mergeBG(role);
				} catch (Exception exe) {
					System.out.println("Permission already exists");
				}
			}
		}
		
		//Migrating Roles
		for (Role role : RolesInterpreter.reflectivelyGetRoles()) {
			if (roleDao.searchUniqueByPropertyEqual("name", role.getName()) == null) {

				try {
					Permission perm = permissionService.getPermissionByName("perm_web_access");
					role.setRecordStatus(RecordStatus.ACTIVE);
					role.setDateCreated(new Date());
					role.setDateChanged(new Date());
					role.setPermissions(new HashSet<>(Arrays.asList(new Permission[] { perm })));
					roleDao.mergeBG(role);
				} catch (Exception exe) {
					System.out.println("Role already exists");
				}
			}
		}
		
	}
}
