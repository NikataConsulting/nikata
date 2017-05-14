/**
 * 
 */
package com.nikata.rest.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nikata.rest.model.Permission;

/**
 * @author Gaurav Oli
 * @date Apr 8, 2017 9:24:06 PM
 */
@Repository
public class PermissionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 12, 2017 8:05:16 AM
	 * @return
	 * @throws Exception 
	 */
	public List<Permission> readAll() throws Exception {
		try {
			return jdbcTemplate.query("SELECT * FROM permission",
					new BeanPropertyRowMapper<Permission>(Permission.class));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 12, 2017 8:05:19 AM
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int create(String sql) throws Exception {
		try {
			return jdbcTemplate.update(sql);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
