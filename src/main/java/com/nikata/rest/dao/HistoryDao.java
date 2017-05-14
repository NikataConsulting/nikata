/**
 * 
 */
package com.nikata.rest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Gaurav Oli
 * @date Mar 21, 2017 2:41:19 PM
 */
@Repository
public class HistoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 21, 2017 2:42:35 PM
	 * @param sql
	 * @return
	 */
	public int save(String sql) {
		return jdbcTemplate.update(sql);
	}
	
	
}