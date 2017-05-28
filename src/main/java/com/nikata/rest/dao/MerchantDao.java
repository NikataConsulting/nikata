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
import com.nikata.rest.model.Branches;
import com.nikata.rest.model.Client;

@Repository
public class MerchantDao {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 28, 2017 12:06:04 AM
	 * @return
	 */
	public List<Client> readAll() {
		return jdbcTemplate.query("SELECT * FROM clients", new BeanPropertyRowMapper<Client>(Client.class));
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 6:55:43 AM
	 * @return
	 * @throws Exception
	 */
	public Client read(long clientId) throws Exception {
		try {
			Client client = jdbcTemplate.queryForObject("SELECT * FROM clients WHERE client_id=" + clientId,
					new BeanPropertyRowMapper<Client>(Client.class));
			if (null != client) {
				List<Branches> branches = jdbcTemplate.query("SELECT * FROM branches WHERE client_id = " + clientId,
						new BeanPropertyRowMapper<Branches>(Branches.class));
				if (null != branches && !branches.isEmpty()) {
					client.setBranches(branches);
				}
			}
			return client;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 6:55:52 AM
	 * @param SQL
	 * @return
	 * @throws Exception
	 */
	public Client create(final Client c) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO clients(name, updated_on) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, c.getName());
					ps.setDate(2, new Date(new java.util.Date().getTime()));
					return ps;
				}
			}, keyHolder);

			if (row > 0) {
				c.setClient_id(keyHolder.getKey().intValue());
			} else {
				transactionManager.rollback(status);
				throw new Exception("Client Not Created");
			}
			for (final Branches b : c.getBranches()) {
				row = this.jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(
								"INSERT INTO BRANCHES(client_id, name, 'unique', address, primary_contact, secondary_contact, email, country_id, fb_url, fb_page_id, updated_on) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
						ps.setLong(1, c.getClient_id());
						ps.setString(2, b.getName());
						ps.setString(3, b.getUnique());
						ps.setString(4, b.getAddress());
						ps.setString(5, b.getPrimary_contact());
						ps.setString(6, b.getSecondary_contact());
						ps.setString(7, b.getEmail());
						ps.setInt(8, 5001);
						ps.setString(9, b.getFb_url());
						ps.setString(10, b.getFb_page_id());
						ps.setDate(11, new Date(new java.util.Date().getTime()));
						return ps;
					}
				}, keyHolder);
				if (row > 0) {
					b.setBranch_id(keyHolder.getKey().intValue());
				} else {
					transactionManager.rollback(status);
					throw new Exception("Branch Not Created");
				}
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(e.getMessage());
		}
		return c;
	}

	public long update(String SQL) throws Exception {
		try {
			return jdbcTemplate.update(SQL);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}