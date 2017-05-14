/**
 * 
 */
package com.nikata.rest.constant;

import com.nikata.rest.exception.QueryException;
import com.nikata.rest.model.User;

/**
 * @author Gaurav Oli
 * @date Mar 6, 2017 2:21:34 PM
 */
public final class UserQuery {

	public static String add(User user) throws QueryException {
		StringBuilder insert = new StringBuilder("INSERT INTO users (");
		StringBuilder value = new StringBuilder(" VALUES(");
		if (user.getUser_id() > 0) {
			insert.append("user_id");
			value.append(user.getUser_id() + ",");
		} else {
			throw new QueryException("facebook user_id not found in request");
		}

		if (null != user.getName() || !user.getName().isEmpty()) {
			insert.append(",name");
			value.append("'" + user.getName() + "',");
		} else {
			throw new QueryException("facebook user's name not found in request");
		}

		if (null != user.getGender() && !user.getGender().isEmpty()) {
			insert.append(",gender");
			value.append("'"+user.getGender() + "',");
		}

		if (null != user.getEmail() && !user.getEmail().isEmpty()) {
			insert.append(",email");
			value.append("'"+user.getEmail() + "',");
		}

		if (null != user.getMobile() && !user.getMobile().isEmpty()) {
			insert.append(",mobile");
			value.append("'"+user.getMobile() + "',");
		}

		if (null != user.getDob() && !user.getDob().isEmpty()) {
			insert.append(",dob");
			value.append("'"+user.getDob() + "',");
		}

		insert.append(",country_id)");
		value.append(5001 + ")");
		return insert.append(value.toString()).toString();
	}

	public static String update(User user) throws QueryException {
		StringBuilder update = new StringBuilder("UPDATE users SET ");
		if (null != user.getName() || user.getName().isEmpty()) {
			update.append("name = '" + user.getName()+"'");
		}

		if (null != user.getGender() && !user.getGender().isEmpty()) {
			update.append(",gender = '" + user.getGender()+"'");
		}

		if (null != user.getEmail() && !user.getEmail().isEmpty()) {
			update.append(",email = '" + user.getEmail()+"'");
		}

		if (null != user.getMobile() && !user.getMobile().isEmpty()) {
			update.append(",mobile = '" + user.getMobile()+"'");
		}

		if (null != user.getDob() && !user.getDob().isEmpty()) {
			update.append(",dob = '" + user.getDob()+"'");
		}

		if (user.getUser_id() > 0) {
			update.append(" WHERE user_id = " + user.getUser_id());
		} else {
			throw new QueryException("facebook user_id not found in request, can't update");
		}
		return update.toString();
	}
	
	public static String readByUserId(int userId) {
		return "SELECT * FROM users WHERE user_id = "+userId;
	}

	public static final String GET_ALL_USER = "SELECT * FROM users";
}
