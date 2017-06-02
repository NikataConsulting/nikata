/**
 * 
 */
package com.nikata.rest.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nikata.rest.model.Branches;

/**
 * @author Gaurav Oli
 * @date Mar 20, 2017 5:16:04 PM
 */
@Repository
public class BranchDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(BranchDao.class);

	public List<Branches> getAllBranches(String sql) {
		List<Branches> branchList = null;
		try {
			branchList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branches>(Branches.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("No data found, sql: " + sql, e);
		}
		return branchList;
	}
}
