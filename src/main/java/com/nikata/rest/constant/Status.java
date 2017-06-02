/**
 * 
 */
package com.nikata.rest.constant;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 12:18:07 PM
 */
public enum Status {
	DB(900, "DB Exception"), REST(901, "REST Client Exception"), UNKNOWN(902, ""), QUERY_BUILD(901,
			"Query building Exception"), CACHE(800, "Cache Exception"), USER_NOT_FOUND(450,
					"User not found"), USER_ALREADY_REGISTERED(451, "User already registered"), OTP_NOT_SENT(500,
							"OTP not sent"), USER_NOT_FOUND_IN_OTP(501, "User not found in otp table"), OTP_INVALID(502,
									"Invalid OTP"), VERIFIED_USER(503,
											"User already verified"), MOBILE_EXIST(505, "Mobile number already exist");

	private int status;
	private String message;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:59 PM
	 * @param status
	 * @param message
	 */
	private Status(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
