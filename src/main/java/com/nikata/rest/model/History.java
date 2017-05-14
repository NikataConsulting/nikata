/**
 * 
 */
package com.nikata.rest.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 1:54:30 PM
 */
public class History {
	private int history_id;
	@Min(1)
	private long user_id;
	@NotNull
	private String post_id;
	@NotNull
	private String branch_id;
	private String created_on;
	private String updated_on;

	public int getHistory_id() {
		return history_id;
	}

	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
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
