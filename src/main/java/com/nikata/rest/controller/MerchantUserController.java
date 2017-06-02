/**
 * 
 */
package com.nikata.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.constant.Constants;
import com.nikata.rest.constant.Status;
import com.nikata.rest.dao.MerchantUserDao;
import com.nikata.rest.dto.Response;
import com.nikata.rest.model.MerchantUser;

/**
 * @author Gaurav Oli
 * @date May 19, 2017 4:24:21 PM
 */
@RestController
@RequestMapping("/app/merchant/user")
public class MerchantUserController {

	@Autowired
	private MerchantUserDao dao;

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantUserController.class);

	@RequestMapping(method = RequestMethod.POST)
	public Response post(@RequestBody MerchantUser user, Response response, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		LOGGER.info("{POST} Request came to save merchant user, user details are: " + user.toString());
		try {
			dao.createMerchantUser(user);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
			response.setPayload(user);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Exception {}", e);
		}
		System.out.println("Response: " + response.toString());
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Response put(@RequestBody MerchantUser user, Response response, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		LOGGER.info("{PUT} Request came to update merchant user, user details are: " + user.toString());
		try {
			dao.updateMerchantUser(user);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Exception {}", e);
		}
		System.out.println("Response: " + response.toString());
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete(@RequestBody MerchantUser user, Response response, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		LOGGER.info("{DELETE} Request came to delete merchant user, user details are: " + user.toString());
		try {
			dao.deleteMerchantUser(user);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Exception {}", e);
		}
		System.out.println("Response: " + response.toString());
		return response;
	}

	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public Response role(@RequestBody MerchantUser user, Response response, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		LOGGER.info("{POST} Request came to update/add/delete roles from merchant user, user details are: "
				+ user.toString());
		try {
			dao.updateRole(user);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Exception {}", e);
		}
		System.out.println("Response: " + response.toString());
		return response;
	}
}
