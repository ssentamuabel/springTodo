package org.pahappa.systems.todo.backend.core.services;

import org.pahappa.systems.todo.backend.models.UserToken;
import org.sers.webutils.model.security.User;

public interface UserTokenService extends GenericService<UserToken> {

	/**
	 * Return the user's active token
	 * @param user
	 * @return
	 */
    UserToken getUserToken(User user);
    
    /**
     * Return the active token if it exists
     * @param token
     * @return
     */
    UserToken getToken(String token);

}
