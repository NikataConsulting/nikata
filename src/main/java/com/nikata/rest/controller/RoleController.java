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
import com.nikata.rest.service.RoleService;

/**
 * @author Gaurav Oli
 * @date Apr 22, 2017 7:32:14 AM
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/app/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private NFCCache cache;

	@RequestMapping(method = RequestMethod.GET)
	public Response get(Response response, ArrayList<RoleDTO> list) {
		try {
			List<Role> roles = roleService.getAllRole();
			if (null != roles && !roles.isEmpty()) {
				for (Role role : roles) {
					RoleDTO roleDTO = new RoleDTO();
					roleDTO.setId(role.getId());
					roleDTO.setName(role.getName());
					roleDTO.setDescription(role.getDescription());
					roleDTO.setPermissions(cache.getRoleMap().get(roleDTO.getName()));
					list.add(roleDTO);
				}
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
		try {
			roleService.create(r, "add");
			response.setPayload(r);
			response.setHttpCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setHttpCode(404);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Response put(@RequestBody RoleDTO role, Response response) {
		if (role.getId() > 0) {
			try {
				response.setPayload(roleService.create(role, "update"));
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
				response.setPayload(roleService.create(role, "delete"));
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