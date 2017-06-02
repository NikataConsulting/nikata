package com.nikata.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikata.rest.dto.Response;

public class ErrorHandleController {

	/**
	 * This method handles all unhandled exceptions
	 * 
	 * @author Gaurav Oli
	 * @version 1.0
	 * @timestamp Nov 3, 2016 11:16:13 AM
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/error")
	public Response error(HttpServletRequest request, Response response) {
		response.setHttpCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(HttpStatus.BAD_REQUEST.name());
		return response;
	}

	/*
	 * @Override public String getErrorPath() { return "/error"; }
	 */

}
