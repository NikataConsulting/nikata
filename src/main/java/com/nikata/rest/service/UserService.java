/**
 * 
 */
package com.nikata.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nikata.rest.constant.UserQuery;
import com.nikata.rest.dao.UserDao;
import com.nikata.rest.model.User;

/**
 * @author Gaurav Oli
 * @date Mar 7, 2017 8:18:48 PM
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:13 PM
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public long create(User user) throws Exception {
		return userDao.create(UserQuery.add(user));
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:17 PM
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public long update(User user) throws Exception {
		return userDao.create(UserQuery.update(user));
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:20 PM
	 * @param userId
	 * @return
	 */
	public User readyByUserId(int userId) throws DataAccessException {
		return userDao.readByKey(UserQuery.readByUserId(userId));
	}

}
