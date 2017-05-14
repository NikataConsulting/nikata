/**
 * 
 */
package com.nikata.rest.model;

/**
 * @author Gaurav Oli
 * @date Apr 8, 2017 9:21:06 PM
 */
public class Role {
	private long id;
	private String name;
	private String description;
	private long permission_id;
	private String permissionName;

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

	public long getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(long permission_id) {
		this.permission_id = permission_id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}
