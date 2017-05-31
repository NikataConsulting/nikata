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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mysql.jdbc.Statement;
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
		return jdbcTemplate.query("SELECT r.id, r.name, r.description, rp.permission_id FROM role r LEFT OUTER JOIN role_permission rp ON r.id=rp.role_id", new BeanPropertyRowMapper<Role>(Role.class));
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 6:55:52 AM
	 * @param SQL
	 * @return
	 * @throws Exception
	 */
	public void create(final RoleDTO r) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO role(id, name, description, updated_on) VALUES(?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, r.getId());
					ps.setString(2, r.getName());
					ps.setString(3, r.getDescription());
					ps.setDate(4, new Date(new java.util.Date().getTime()));
					return ps;
				}
			}, keyHolder);
			r.setId(keyHolder.getKey().longValue());
			
			if (keyHolder.getKey() != null && (null != r.getPermissions() && !r.getPermissions().isEmpty())) {
				long role_id = keyHolder.getKey().longValue();
				for (Permission p : r.getPermissions()) {
					this.jdbcTemplate.update("INSERT INTO role_permission(role_id, 	permission_id) VALUES(?,?)",
							new Object[] { role_id, p.getId() });
				}
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 26, 2017 9:54:38 AM
	 * @param SQL
	 * @return
	 * @throws Exception
	 */
	public void update(RoleDTO r) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"UPDATE role SET name = ?, description = ?, updated_on = CURRENT_TIMESTAMP");
					ps.setString(1, r.getName());
					ps.setString(2, r.getDescription());
					return ps;
				}
			});

			if (row > 0) {
				this.jdbcTemplate.update("DELETE FROM role_permission WHERE role_id = ?", new Object[] { r.getId() });
				if (null != r.getPermissions() && !r.getPermissions().isEmpty()) {
					for (Permission p : r.getPermissions()) {
						this.jdbcTemplate.update("INSERT INTO role_permission(role_id, 	permission_id) VALUES(?,?)",
								new Object[] { r.getId(), p.getId() });

					}
				}
			} else {
				transactionManager.rollback(status);
				throw new Exception("Unable to update role in role Table");
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 26, 2017 9:54:42 AM
	 * @param r
	 * @throws Exception
	 */
	public void delete(RoleDTO r) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			int row = this.jdbcTemplate.update("DELETE FROM role_permission WHERE role_id = ?",
					new Object[] { r.getId() });
			if (row > 0) {
				row = this.jdbcTemplate.update("DELETE FROM role WHERE id = ?", new Object[] { r.getId() });
			} else {
				transactionManager.rollback(status);
				throw new Exception("Unable to delete role from role_permission Table");
			}
			if (row < 1) {
				transactionManager.rollback(status);
				throw new Exception("Unable to delete role from role_permission Table");
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e.getMessage());
		}
	}
}