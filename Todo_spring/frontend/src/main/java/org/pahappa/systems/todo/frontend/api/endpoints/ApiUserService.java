 package org.pahappa.systems.todo.frontend.api.endpoints;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.pahappa.systems.todo.backend.constants.TokenStatus;
import org.pahappa.systems.todo.backend.core.services.UserTokenService;
import org.pahappa.systems.todo.backend.models.UserToken;
import org.pahappa.systems.todo.frontend.api.TokenGenerator;
import org.pahappa.systems.todo.frontend.api.models.ApiSecurtiy;
import org.pahappa.systems.todo.frontend.api.models.ApiUtils;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.security.service.impl.ApiAuthenticationHandler;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@Path("/v1/Users")
public class ApiUserService {

	@POST
	@Path("/LoginUser")
	@Produces("application/json")
	@Consumes("application/json")
	public Response loginUser(ApiSecurtiy apiSecurity) throws JSONException, IOException, Exception {
		JSONObject result = new JSONObject();

		if (apiSecurity == null) {
			result.put(ApiUtils.STATUS_PARAM, ApiUtils.FAILURE_TOKEN);
			result.put(ApiUtils.RESPONSE_PARAM, "No Data specified.");
		} else {

			User userAccount = ApplicationContextProvider.getBean(ApiAuthenticationHandler.class)
					.getWebPortalUser(apiSecurity.getUsername(), apiSecurity.getPassword());

			if (userAccount == null) {
				return ApiUtils.composeFailureMessage("Invalid username or password.");
			}

			// If the user does exist, create a token for them and send it back in the
			// response. The token is created on every Login
			UserToken existingToken = ApplicationContextProvider.getBean(UserTokenService.class)
					.getUserToken(userAccount);
			if (existingToken != null) {
				existingToken.setTokenStatus(TokenStatus.EXPIRED);
				ApplicationContextProvider.getBean(UserTokenService.class).saveInstance(existingToken);
			}
			UserToken userToken = new UserToken();
			userToken.setUser(userAccount);
			userToken.setToken(TokenGenerator.generateNewToken());
			UserToken savedToken = ApplicationContextProvider.getBean(UserTokenService.class).saveInstance(userToken);

			JSONObject userObj = new JSONObject();
			userObj.put("username", userAccount.getUsername());
			userObj.put("firstName", userAccount.getFirstName());
			userObj.put("lastName", userAccount.getLastName());
			userObj.put("fullName", userAccount.getFullName());
			userObj.put("email", userAccount.getEmailAddress());
			userObj.put("token", savedToken.getToken());
			result.put("user", userObj);
			result.put(ApiUtils.STATUS_PARAM, ApiUtils.SUCCESSFUL_TOKEN);
			result.put(ApiUtils.RESPONSE_PARAM, "User logged in succesfully");
			return Response.status(200).entity("" + result).build();
		}
		return Response.status(400).entity("" + result).build();
	}

	@POST
	@Path("/GetUserProfile")
	@Produces("application/json")
	@Consumes("application/json")
	public Response getUserProfile(ApiSecurtiy apiSecurity) throws JSONException {
		JSONObject result = new JSONObject();

		if (apiSecurity == null) {
			result.put(ApiUtils.STATUS_PARAM, ApiUtils.FAILURE_TOKEN);
			result.put(ApiUtils.RESPONSE_PARAM, "No Data specified.");
		} else {

			User userAccount = ApplicationContextProvider.getBean(UserService.class)
					.getUserByUsername(apiSecurity.getUsername());

			if (userAccount == null) {
				return ApiUtils.composeFailureMessage("User with that username does not exist");
			}

			UserToken existingToken = ApplicationContextProvider.getBean(UserTokenService.class)
					.getToken(apiSecurity.getToken());

			if (existingToken == null)
				return ApiUtils.composeFailureMessage("Token does not exist.");

			JSONObject memberObj = new JSONObject();
			memberObj.put("id", userAccount.getId());
			memberObj.put("username", userAccount.getUsername());
			memberObj.put("firstName", userAccount.getFirstName());
			memberObj.put("lastName", userAccount.getLastName());
			memberObj.put("fullName", userAccount.getFullName());
			memberObj.put("email", userAccount.getEmailAddress());
			memberObj.put("gender", userAccount.getGender());
			memberObj.put("phoneNumber", userAccount.getPhoneNumber());
			memberObj.put("isAdministrator", userAccount.hasAdministrativePrivileges());

			result.put("user", memberObj);
			result.put(ApiUtils.STATUS_PARAM, ApiUtils.SUCCESSFUL_TOKEN);
			return Response.status(200).entity("" + result).build();
		}
		return Response.status(400).entity("" + result).build();
	}

	@POST
	@Path("/LogoutUser")
	@Produces("application/json")
	@Consumes("application/json")
	public Response logoutUser(ApiSecurtiy apiSecurity) throws JSONException, IOException, Exception {
		JSONObject result = new JSONObject();

		if (apiSecurity == null) {
			result.put(ApiUtils.STATUS_PARAM, ApiUtils.FAILURE_TOKEN);
			result.put(ApiUtils.RESPONSE_PARAM, "No Data specified.");
		} else {

			User userAccount = ApplicationContextProvider.getBean(UserService.class)
					.getUserByUsername(apiSecurity.getUsername());

			if (userAccount == null) {
				return ApiUtils.composeFailureMessage("Invalid username or password.");
			}

			UserToken existingToken = ApplicationContextProvider.getBean(UserTokenService.class)
					.getToken(apiSecurity.getToken());

			if (existingToken == null)
				return ApiUtils.composeFailureMessage("Token does not exist.");

			existingToken.setTokenStatus(TokenStatus.EXPIRED);
			ApplicationContextProvider.getBean(UserTokenService.class).saveInstance(existingToken);

			JSONObject userObj = new JSONObject();
			userObj.put("username", userAccount.getUsername());
			userObj.put("expiredToken", existingToken.getToken());
			result.put("user", userObj);
			result.put(ApiUtils.STATUS_PARAM, ApiUtils.SUCCESSFUL_TOKEN);
			result.put(ApiUtils.RESPONSE_PARAM, "User logged out succesfully");
			return Response.status(200).entity("" + result).build();
		}
		return Response.status(400).entity("" + result).build();
	}

}
