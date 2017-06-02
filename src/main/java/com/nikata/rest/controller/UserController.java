/**
 * 
 */
package com.nikata.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.constant.Constants;
import com.nikata.rest.constant.Status;
import com.nikata.rest.dto.Response;
import com.nikata.rest.exception.QueryException;
import com.nikata.rest.model.User;
import com.nikata.rest.service.UserService;
import com.nikata.rest.utility.Utility;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 12:49:35 PM
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/app/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private NFCCache cache;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:30 PM
	 * @param user_id
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public Response get(@PathVariable int user_id, Response response, HttpServletResponse httpResponse) {
		LOGGER.info("{GET} Request came to fetch user by, userid: " + user_id);
		User user = cache.getUserMap().get(user_id);
		try {
			if (null == user) {
				LOGGER.info("{GET} User is not available in cache, fetch from DB now");
				user = userService.readyByUserId(user_id);
			}

			if (null != user) {
				response.setHttpCode(HttpStatus.OK.value());
				response.setMessage(Constants.SUCCESS);
				response.setPayload(user);
				cache.getUserMap().put(user.getUser_id(), user);
			} else {
				LOGGER.info("{GET} User is not avaiable in database too, userid: ", user_id);
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
				response.setHttpCode(Status.USER_NOT_FOUND.getStatus());
				response.setMessage(Status.USER_NOT_FOUND.getMessage());
			}
		} catch (EmptyResultDataAccessException e) {
			httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
			response.setHttpCode(Status.USER_NOT_FOUND.getStatus());
			response.setMessage(Status.USER_NOT_FOUND.getMessage());
			LOGGER.error("Database Exception {}", e);
		} catch (DataAccessException e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.DB.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Query building Exception {}", e);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Unhandled/Unknown Exception {}", e);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAll() {
		return null;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:25 PM
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response post(@RequestBody User user, Response response, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		LOGGER.info("{POST} Request came to save or update user, user details are: " + user.toString());
		try {
			if (null != cache.getUserMap().get(user.getUser_id())) {
				LOGGER.info("{POST} User found in cache, userid: " + user.getUser_id());
				response.setHttpCode(Status.USER_ALREADY_REGISTERED.getStatus());
				response.setMessage(Status.USER_ALREADY_REGISTERED.getMessage());

				if (Utility.match(user, cache.getUserMap().get(user.getUser_id()))) {
					LOGGER.info("{POST} Discrepancy found, Updating user object in DB");
					userService.update(user);
					cache.getUserMap().put(user.getUser_id(), user);
				}

				response.setPayload(cache.getUserMap().get(user.getUser_id()));
			} else {
				LOGGER.info("{POST} Creating user object in database, user details are: " + user.toString());
				if (userService.create(user) == 1) {
					LOGGER.info("{POST} User created successfully");
					response.setHttpCode(HttpStatus.OK.value());
					response.setMessage(Constants.SUCCESS);
					response.setPayload(user);
					cache.getUserMap().put(user.getUser_id(), user);
				} else {
					LOGGER.info("{POST}, Failure while creating object");
					response.setMessage(Constants.FAILURE);
				}
			}
		} catch (DataAccessException e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.DB.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Database Exception {}", e);
		} catch (QueryException e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.QUERY_BUILD.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Query building Exception {}", e);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Unhandled/Unknown Exception {}", e);
		}
		System.out.println("Response: " + response.toString());
		return response;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:15 PM
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response put(@RequestBody User user, Response response, HttpServletResponse httpResponse) {
		if (null == cache.getUserMap().get(user.getUser_id())) {
			response.setHttpCode(Status.USER_NOT_FOUND.getStatus());
			response.setMessage(Status.USER_NOT_FOUND.getMessage());
		} else {
			try {
				response.setHttpCode(HttpStatus.OK.value());
				if (userService.update(user) == 1) {
					response.setMessage(Constants.SUCCESS);
					cache.getUserMap().put(user.getUser_id(), user);
				} else {
					response.setMessage(Constants.FAILURE);
				}
			} catch (DataAccessException e) {
				httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				response.setHttpCode(Status.DB.getStatus());
				response.setMessage(e.getMessage());
				LOGGER.error("Database Exception {}", e);
			} catch (QueryException e) {
				httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				response.setHttpCode(Status.QUERY_BUILD.getStatus());
				response.setMessage(e.getMessage());
				LOGGER.error("Query building Exception {}", e);
			} catch (Exception e) {
				httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				response.setHttpCode(Status.UNKNOWN.getStatus());
				response.setMessage(e.getMessage());
				LOGGER.error("Unhandled/Unknown Exception {}", e);
			}
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete() {
		return null;
	}
}