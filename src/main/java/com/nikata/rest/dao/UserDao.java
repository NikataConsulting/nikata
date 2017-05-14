/**
 * 
 */
package com.nikata.rest.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nikata.rest.model.User;

/**
 * @author Gaurav Oli
 * @date Mar 6, 2017 2:18:25 PM
 */
@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:39 PM
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public int create(String sql) throws DataAccessException {
		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:43 PM
	 * @param sql
	 * @return
	 */
	public User readByKey(String sql) throws DataAccessException {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("No data found, sql: "+sql, e);
		}
		return user;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:47 PM
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public List<User> readAll(String sql) throws DataAccessException {
		List<User> userList = null;
		try {
			userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("No data found, sql: "+sql, e);
		}
		return userList;
	}
}