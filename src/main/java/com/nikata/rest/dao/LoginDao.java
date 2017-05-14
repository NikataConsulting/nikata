/**
 * 
 */
package com.nikata.rest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.model.Login;

/**
 * @author Gaurav Oli
 * @date Apr 3, 2017 8:08:08 AM
 */
@Repository
public class LoginDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NFCCache nfcCache;

	public Login validateCustomer(Login login) throws Exception {
		try {
			login = jdbcTemplate.queryForObject("SELECT * FROM login WHERE username = ? AND password = ?",
					new Object[] { login.getUsername(), login.getPassword() },
					new BeanPropertyRowMapper<Login>(Login.class));
			if (null != login.getRoles()) {
				login.setPermissions(nfcCache.getRoleMap().get(login.getRoles()));
				login.setPassword(null);
			} else {
				throw new Exception("No role found");
			}
		} catch (EmptyResultDataAccessException e) {
			throw new Exception("No user found");
		}
		return login;
	}
}