/**
 * 
 */
package com.nikata.rest.model;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 2:25:11 PM
 */
public class Otp {
	private int otp;
	private long user_id;
	private char isUsed;
	private String created_on;
	private String updated_on;

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public char getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(char isUsed) {
		this.isUsed = isUsed;
	}

	public String getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
}
