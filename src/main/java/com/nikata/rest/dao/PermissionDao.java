/**
 * 
 */
package com.nikata.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
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
	 * @date May 28, 2017 12:34:08 PM
	 * @param p
	 * @throws Exception
	 */
	public void create(final Permission p) throws Exception {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO permission(name, description, updated_on) VALUES(?, ?, CURRENT_TIMESTAMP)",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, p.getName());
					ps.setString(2, p.getDescription());
					return ps;
				}
			}, keyHolder);
			p.setId(keyHolder.getKey().longValue());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 28, 2017 12:34:08 PM
	 * @param p
	 * @throws Exception
	 */
	public void update(final Permission p) throws Exception {
		try {
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"UPDATE permission SET name=?, description=?, updated_on = CURRENT_TIMESTAMP WHERE id = ?");
					ps.setString(1, p.getName());
					ps.setString(2, p.getDescription());
					ps.setLong(3, p.getId());
					return ps;
				}
			});
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 28, 2017 12:37:51 PM
	 * @param p
	 * @throws Exception
	 */
	public void delete(Permission p) throws Exception {
		try {
			this.jdbcTemplate.update("DELETE FROM permission WHERE id = ?", new Object[] { p.getId() });
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
