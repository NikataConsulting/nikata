package com.nikata.rest.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nikata.rest.dto.RoleDTO;
import com.nikata.rest.model.Permission;
import com.nikata.rest.model.Role;

@Repository
public class RoleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 6:55:43 AM
	 * @return
	 */
	public List<Role> readAll() {
		return jdbcTemplate.query("SELECT * FROM role", new BeanPropertyRowMapper<Role>(Role.class));
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 6:55:52 AM
	 * @param SQL
	 * @return
	 * @throws Exception
	 */
	public void create(final String SQL, final RoleDTO r) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			for (final Permission p : r.getPermissions()) {
				this.jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(SQL);
						ps.setLong(1, r.getId());
						ps.setString(2, r.getName());
						ps.setString(3, r.getDescription());
						ps.setLong(4, p.getId());
						ps.setDate(5, new Date(new java.util.Date().getTime()));
						return ps;
					}
				});
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e.getMessage());
		}
	}

	public long update(String SQL) throws Exception {
		try {
			return jdbcTemplate.update(SQL);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}