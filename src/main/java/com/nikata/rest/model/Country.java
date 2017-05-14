/**
 * 
 */
package com.nikata.rest
.model;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 1:56:38 PM
 */
public class Country {
	private int country_id;
	private String country_name;

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
}
