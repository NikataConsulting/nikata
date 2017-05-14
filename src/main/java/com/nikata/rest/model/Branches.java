/**
 * 
 */
package com.nikata.rest.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 1:43:19 PM
 */
public class Branches {
	private int branch_id;
	@Min(1)
	private int client_id;
	@Min(1)
	private int country_id;
	@NotNull
	private String name;
	@NotNull
	private String unique;
	@NotNull
	private String address;
	@NotNull
	private String primary_contact;
	@NotNull
	private String secondary_contact;
	@NotNull
	private String email;
	@NotNull
	private String fb_url;
	@NotNull
	private String fb_page_id;
	private String created_on;
	private String updated_on;

	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrimary_contact() {
		return primary_contact;
	}

	public void setPrimary_contact(String primary_contact) {
		this.primary_contact = primary_contact;
	}

	public String getSecondary_contact() {
		return secondary_contact;
	}

	public void setSecondary_contact(String secondary_contact) {
		this.secondary_contact = secondary_contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFb_url() {
		return fb_url;
	}

	public void setFb_url(String fb_url) {
		this.fb_url = fb_url;
	}

	public String getFb_page_id() {
		return fb_page_id;
	}

	public void setFb_page_id(String fb_page_id) {
		this.fb_page_id = fb_page_id;
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
