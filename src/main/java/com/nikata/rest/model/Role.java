/**
 * 
 */
package com.nikata.rest.model;

import java.sql.Date;

/**
 * @author Gaurav Oli
 * @date Apr 8, 2017 9:21:06 PM
 */
public class Role {
	private long id;
	private String name;
	private String description;
	private Date created_on;
	private Date updated_on;

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

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}
}
