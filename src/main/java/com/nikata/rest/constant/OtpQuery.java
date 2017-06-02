/**
 * 
 */
package com.nikata.rest.constant;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 2:38:21 PM
 */
public final class OtpQuery {
	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:48 PM
	 * @param user
	 * @param otp
	 * @return
	 */
	public static String add(long user_id, int otp) {
		return "INSERT INTO otp (otp, user_id, updated_on) VALUES (" + otp + "," + user_id + ", CURRENT_TIMESTAMP)";
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:51 PM
	 * @param url
	 * @param otp
	 * @param mobile
	 * @return
	 */
	public static String createURL(String url, int otp, String mobile) {
		return url + "+" + mobile
				+ "&message=Welcome to Nikata, for your first time login you'll need the activation PIN:" + otp;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 13, 2017 5:08:29 PM
	 * @param user_id
	 * @param otp
	 * @return
	 */
	public static String matchOtp(long user_id, int otp) {
		return "SELECT * FROM otp WHERE user_id = " + user_id + " AND otp = " + otp;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 13, 2017 5:09:08 PM
	 * @param user_id
	 * @return
	 */
	public static String getOtpIfExist(long user_id) {
		return "SELECT * FROM otp WHERE user_id = " + user_id;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 13, 2017 5:08:33 PM
	 * @param user_id
	 * @param otp
	 * @return
	 */
	public static String updateOtp(long user_id, int otp) {
		return "UPDATE otp SET isUsed = 'Y' WHERE user_id = " + user_id + " AND otp = " + otp;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 13, 2017 5:35:16 PM
	 * @param user
	 * @return
	 */
	public static String updateUserMobile(long user_id, String mobile) {
		return "UPDATE users SET mobile = " + mobile + " WHERE user_id = " + user_id;
	}

	public static String checkMobileExist(String mobile) {
		return "SELECT * FROM users WHERE mobile = " + mobile;
	}
}
