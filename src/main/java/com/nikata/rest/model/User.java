/**
 * 
 */
package com.nikata.rest.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 1:31:23 PM
 */
public class User {
	@Min(1)
	private long user_id;
	private int country_id;
	@NotNull
	private String name;
	private String gender;
	private String email;
	private String mobile;
	private String dob;
	private char isVerified = 'N';
	private String created_on;
	private String updated_on;

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public char getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(char isVerified) {
		this.isVerified = isVerified;
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

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", country_id=" + country_id + ", name=" + name + ", gender=" + gender
				+ ", email=" + email + ", mobile=" + mobile + ", dob=" + dob + ", isVerified=" + isVerified
				+ ", created_on=" + created_on + ", updated_on=" + updated_on + "]";
	}
}
