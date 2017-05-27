/**
 * 
 */
package com.nikata.rest.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Gaurav Oli
 * @date May 26, 2017 1:18:14 PM
 */
public class MerchantUser {
	private long id;
	@Min(1)
	private long merchant_id;
	@NotNull
	@NotEmpty
	private String firstname;
	@NotNull
	@NotEmpty
	private String lastname;
	@NotNull
	@NotEmpty
	private String email;
	@NotNull
	@NotEmpty
	private String contact;
	@NotNull
	@NotEmpty
	private String address;
	private String roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(long merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "MerchantUser [id=" + id + ", merchant_id=" + merchant_id + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", contact=" + contact + ", address=" + address + ", roles=" + roles
				+ "]";
	}
}
