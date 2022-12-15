package org.pahappa.systems.todo.backend.models.security;

public final class RoleConstants {
	private RoleConstants() {

	}
	
	@SystemRole(name = "Api user", description = "Has role for api users")
	public static final String ROLE_API_USER = "Api User";
	
}
