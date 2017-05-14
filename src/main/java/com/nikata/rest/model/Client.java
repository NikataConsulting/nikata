/**
 * 
 */
package com.nikata.rest.model;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 1:38:50 PM
 */
public class Client {
	private int client_id;
	@NotNull
	private String name;
	private List<Branches> branches;
	private String created_on;
	private String updated_on;

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Branches> getBranches() {
		return branches;
	}

	public void setBranches(List<Branches> branches) {
		this.branches = branches;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}
}
