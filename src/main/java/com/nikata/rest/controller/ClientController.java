/**
 * 
 */
package com.nikata.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.dto.Response;
import com.nikata.rest.model.User;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 2:29:05 PM
 */
@RestController
@RequestMapping("/nfc/client")
public class ClientController {
	@RequestMapping(value = "/{client_id}", method = RequestMethod.GET)
	public Response get(@PathVariable User user) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Response getAll(@PathVariable User user) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response post() {
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Response put() {
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete() {
		return null;
	}
}
