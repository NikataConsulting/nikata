/**
 * 
 */
package com.nikata.rest.utility;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nikata.rest.model.User;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 2:28:30 PM
 */
public final class Utility {
	private static final Random RND = new Random();

	private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 10, 2017 4:00:29 PM
	 * @return
	 */
	public static int randon() {
		return 100000 + RND.nextInt(900000);
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 10, 2017 4:00:26 PM
	 * @param user
	 * @param cache
	 * @return
	 */
	public static boolean match(User user, User cache) {
		LOGGER.info("{POST} Finding discrepancy sent by client & one we have in cache");
		boolean result = Boolean.FALSE;
		if (user.getCountry_id() > 0 && user.getCountry_id() != cache.getCountry_id()) {
			result = Boolean.TRUE;
		} else if (null != user.getDob() && !user.getDob().equals(cache.getDob())) {
			result = Boolean.TRUE;
		} else if (null != user.getEmail() && !user.getEmail().equals(cache.getEmail())) {
			result = Boolean.TRUE;
		} else if (null != user.getGender() && !user.getGender().equals(cache.getGender())) {
			result = Boolean.TRUE;
		} else if (null != user.getMobile() && !user.getMobile().equals(cache.getMobile())) {
			result = Boolean.TRUE;
		} else if (null != user.getName() && !user.getName().equals(cache.getName())) {
			result = Boolean.TRUE;
		} else if (cache.getIsVerified() == 'Y') {
			user.setIsVerified('Y');
		}
		return result;
	}
}
