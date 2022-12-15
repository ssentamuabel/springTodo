package org.pahappa.systems.todo.backend.models.security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.sers.webutils.model.security.Role;

public class RolesInterpreter {

	public static final List<Role> reflectivelyGetRoles() {

		List<Role> roles = new ArrayList<Role>();

		for (Field field : RoleConstants.class.getFields()) {

			if (field.isAnnotationPresent(SystemRole.class)) {
				SystemRole roleAnnotation = field.getAnnotation(SystemRole.class);
				Role role = new Role();
				role.setName(roleAnnotation.name());
				role.setDescription(roleAnnotation.description());
				roles.add(role);
			}
		}
		return roles;
	}
}
