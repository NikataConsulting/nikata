/**
 * 
 */
package com.nikata.rest.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nikata.rest.dto.RoleDTO;
import com.nikata.rest.model.Permission;

/**
 * @author Gaurav Oli
 * @date May 31, 2017 3:06:44 PM
 */
@Repository
public class RolePermissionDao {
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
	public List<Map<String, Object>> getRolePermission() {
		return jdbcTemplate.queryForList("SELECT * FROM role_permission");
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 31, 2017 5:54:48 PM
	 * @param r
	 * @throws Exception
	 */
	public void create(final RoleDTO r) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			jdbcTemplate.update("DELETE FROM role_permission WHERE role_id = ?", new Object[] { r.getId() });
			for (Permission p : r.getPermissions()) {
				this.jdbcTemplate.update("INSERT INTO role_permission(role_id, 	permission_id) VALUES(?,?)",
						new Object[] { r.getId(), p.getId() });
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
	/*
	 * public void delete(RoleDTO r) throws Exception { TransactionDefinition
	 * def = new DefaultTransactionDefinition(); TransactionStatus status =
	 * transactionManager.getTransaction(def); try { for (Permission p :
	 * r.getPermissions()) { jdbcTemplate.
	 * update("DELETE FROM role_permission WHERE role_id = ? AND permission_id = ?"
	 * , new Object[] { r.getId(), p.getId() }); }
	 * transactionManager.commit(status); } catch (Exception e) {
	 * transactionManager.rollback(status); throw new Exception(e.getMessage());
	 * } }
	 */
}