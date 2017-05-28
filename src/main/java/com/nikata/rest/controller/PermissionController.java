/**
 * 
 */
package com.nikata.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.constant.Constants;
import com.nikata.rest.dto.Response;
import com.nikata.rest.model.Permission;
import com.nikata.rest.service.PermissionService;

/**
 * @author Gaurav Oli
 * @date Apr 12, 2017 8:05:48 AM
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/app/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private NFCCache cache;

	@RequestMapping(method = RequestMethod.GET)
	public Response get(Response response) {
		try {
			response.setPayload(permissionService.read());
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setHttpCode(404);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response post(@RequestBody Permission permission, Response response) {
		try {
			permissionService.create(permission, "add");
			response.setPayload(permission);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setHttpCode(404);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Response put(@RequestBody Permission permission, Response response) {
		if (permission.getId() > 0) {
			if (null != cache.getPermissionMap().get(permission.getId())) {
				try {
					permissionService.create(permission, "update");
					response.setPayload(permission);
					response.setHttpCode(200);
					response.setMessage(Constants.SUCCESS);
				} catch (Exception e) {
					response.setHttpCode(404);
					response.setMessage(e.getMessage());
				}
			} else {
				response.setHttpCode(404);
				response.setMessage("Bad update request, Permission ID is not available in DB");
			}
		} else {
			response.setHttpCode(404);
			response.setMessage("Permission ID is a mandatory field.");
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete(@RequestBody Permission permission, Response response) {
		if (permission.getId() > 0) {
			if (null != cache.getPermissionMap().get(permission.getId())) {
				try {
					permissionService.create(permission, "delete");
					response.setHttpCode(200);
					response.setMessage(Constants.SUCCESS);
				} catch (Exception e) {
					response.setHttpCode(404);
					response.setMessage(e.getMessage());
				}
			} else {
				response.setHttpCode(404);
				response.setMessage("Bad update request, Permission ID is not available in DB");
			}
		} else {
			response.setHttpCode(404);
			response.setMessage("Permission ID is a mandatory field.");
		}
		return response;
	}
}
