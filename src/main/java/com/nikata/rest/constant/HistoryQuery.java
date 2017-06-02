/**
 * 
 */
package com.nikata.rest.constant;

import com.nikata.rest.model.History;

/**
 * @author Gaurav Oli
 * @date Mar 21, 2017 2:45:06 PM
 */
public class HistoryQuery {
	public static String save(History h) {
		return "INSERT INTO history(user_id, branch_id, post_id) VALUES(" + h.getUser_id() + ",'" + h.getBranch_id()
				+ "','" + h.getPost_id() + "')";
	}
}