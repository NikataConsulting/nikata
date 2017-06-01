/**
 * 
 */
package com.nikata.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.constant.Constants;
import com.nikata.rest.dto.Response;
import com.nikata.rest.dto.RoleDTO;
import com.nikata.rest.model.Role;
import com.nikata.rest.service.RolePermissionService;

/**
 * @author Gaurav Oli
 * @date May 31, 2017 3:13:32 PM
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/app/role/permission")
public class RolePermissionController {

	@Autowired
	private RolePermissionService service;

	@Autowired
	private NFCCache cache;

	@RequestMapping(method = RequestMethod.GET)
	public Response get(Response response, ArrayList<RoleDTO> list) {
		try {
			List<Role> roles = service.getAllRole();
			if (null != roles && !roles.isEmpty()) {
				roles.forEach(r -> {
					RoleDTO roleDTO = new RoleDTO();
					roleDTO.setId(r.getId());
					roleDTO.setName(r.getName());
					roleDTO.setDescription(r.getDescription());
					if (null != cache.getRoleMap().get(roleDTO.getName())
							&& !cache.getRoleMap().get(roleDTO.getName()).isEmpty()) {
						roleDTO.setPermissions(cache.getRoleMap().get(roleDTO.getName()));
					} else {
						roleDTO.setPermissions(new ArrayList<>());
					}
					list.add(roleDTO);
				});
			}
			response.setPayload(list);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setHttpCode(404);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response post(@RequestBody RoleDTO r, Response response) {
		if (r.getId() > 0) {
			try {
				service.create(r);
				response.setPayload(r);
				response.setHttpCode(200);
				response.setMessage(Constants.SUCCESS);
			} catch (Exception e) {
				response.setHttpCode(404);
				response.setMessage(e.getMessage());
			}
		} else {
			response.setHttpCode(404);
			response.setMessage("Role ID is a mandatory field.");
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete(@RequestBody RoleDTO role, Response response) {
		if (role.getId() > 0) {
			try {
				service.delete(role);
				response.setHttpCode(200);
				response.setMessage(Constants.SUCCESS);
			} catch (Exception e) {
				response.setHttpCode(404);
				response.setMessage(e.getMessage());
			}
		} else {
			response.setHttpCode(404);
			response.setMessage("Role ID is a mandatory field.");
		}
		return response;
	}
}
