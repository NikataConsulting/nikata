/**
 * 
 */
package com.nikata.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.nikata.rest.model.MerchantUser;

/**
 * @author Gaurav Oli
 * @date May 26, 2017 1:25:53 PM
 */
@Repository
public class MerchantUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	public long createMerchantUser(MerchantUser user) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		long userId = 0;
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO merchant_user(merchant_id, firstname, lastname, email, contact, address, roles, updated_on) VALUES(?,?,?,?,?,?,?,CURRENT_TIMESTAMP)",
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, user.getMerchant_id());
					ps.setString(2, user.getFirstname());
					ps.setString(3, user.getLastname());
					ps.setString(4, user.getEmail());
					ps.setString(5, user.getContact());
					ps.setString(6, user.getAddress());
					ps.setString(7, user.getRoles());
					return ps;
				}
			}, keyHolder);
			user.setId(keyHolder.getKey().longValue());
			if (row > 0) {
				row = this.jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(
								"INSERT INTO login(id, username, password, updated_on) VALUES(?,?,?,CURRENT_TIMESTAMP)",
								Statement.RETURN_GENERATED_KEYS);
						ps.setLong(1, user.getId());
						ps.setString(2, user.getFirstname() + String.valueOf(Math.random()).split("\\.")[1].substring(0, 4));
						ps.setString(3, "nikata@123");
						return ps;
					}
				}, keyHolder);
			}

			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e);
		}
		return userId;
	}

	public void updateMerchantUser(MerchantUser user) throws Exception {
		try {
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							" UPDATE merchant_user SET merchant_id = ?, firstname = ?, lastname = ?, email = ?, contact = ?, address = ?, roles = ?, updated_on = CURRENT_TIMESTAMP WHERE id = ?",
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, user.getMerchant_id());
					ps.setString(2, user.getFirstname());
					ps.setString(3, user.getLastname());
					ps.setString(4, user.getEmail());
					ps.setString(5, user.getContact());
					ps.setString(6, user.getAddress());
					ps.setString(7, user.getRoles());
					ps.setLong(8, user.getId());
					return ps;
				}
			});
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 26, 2017 1:52:08 PM
	 * @param user
	 * @throws Exception
	 */
	public void deleteMerchantUser(MerchantUser user) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			int row = this.jdbcTemplate.update("DELETE from merchant_user where id = ?", new Object[] { user.getId() });

			if (row > 0) {
				row = this.jdbcTemplate.update("DELETE from login where id = ?", new Object[] { user.getId() });
				if (row < 1) {
					transactionManager.rollback(status);
					throw new Exception("Unable to delete merchant user from login Table");
				}
			} else {
				transactionManager.rollback(status);
				throw new Exception("Unable to delete merchant user from merchant_user Table");
			}

			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 26, 2017 2:25:38 PM
	 * @param user
	 * @throws Exception
	 */
	public void updateRole(MerchantUser user) throws Exception {
		try {
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							" UPDATE merchant_user SET roles = ?, updated_on = CURRENT_TIMESTAMP WHERE id = ?",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getRoles());
					ps.setLong(2, user.getId());
					return ps;
				}
			});
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
