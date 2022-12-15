package org.pahappa.systems.todo.backend.core.services.impl;

import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.todo.backend.constants.TokenStatus;
import org.pahappa.systems.todo.backend.core.services.UserTokenService;
import org.pahappa.systems.todo.backend.models.UserToken;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

@Service
@Transactional
public class UserTokenServiceImpl extends GenericServiceImpl<UserToken> implements UserTokenService {

	@Override
	public UserToken saveInstance(UserToken entityInstance) throws ValidationFailedException, OperationFailedException {
		if(StringUtils.isBlank(entityInstance.getToken()))
			throw new ValidationFailedException("Missing Token");
		if(entityInstance.getUser() == null)
			throw new ValidationFailedException("Missing User");
		return super.merge(entityInstance);
	}

	@Override
	public UserToken getUserToken(User user) {
		Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		search.addFilterEqual("tokenStatus", TokenStatus.ACTIVE);
		search.addFilterEqual("user", user);
		return super.searchUnique(search);
	}
	
	@Override
	public UserToken getToken(String token) {
		Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		search.addFilterEqual("tokenStatus", TokenStatus.ACTIVE);
		search.addFilterEqual("token", token);
		return super.searchUnique(search);
	}

	@Override
	public boolean isDeletable(UserToken instance) throws OperationFailedException {
		return true;
	}

}
