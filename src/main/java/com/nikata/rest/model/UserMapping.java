/**
 * 
 */
package com.nikata.rest.model;

import java.util.List;

/**
 * @author Gaurav Oli
 * @date Apr 3, 2017 8:03:36 AM
 */
public class UserMapping {
	private List<String> permissions;
	private Customer customer;

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
