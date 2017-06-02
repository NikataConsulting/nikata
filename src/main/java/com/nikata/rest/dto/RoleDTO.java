/**
 * 
 */
package com.nikata.rest.dto;

import java.util.List;

import com.nikata.rest.model.Permission;

/**
 * @author Gaurav Oli
 * @date May 3, 2017 8:12:38 AM
 */
public class RoleDTO {
	private long id;
	private String name;
	private String description;
	private List<Permission> permissions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
