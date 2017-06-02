/**
 * 
 */
package com.nikata.rest.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nikata.rest.exception.DBException;
import com.nikata.rest.exception.NoUserOtpFound;
import com.nikata.rest.exception.OtpNotMatch;
import com.nikata.rest.model.Otp;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 2:32:41 PM
 */
@Repository
public class OtpDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(OtpDao.class);

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 8:00:58 PM
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public int create(String sql) throws DataAccessException {
		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 13, 2017 5:07:10 PM
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public Otp readByKey(String sql) throws DataAccessException {
		Otp otp = null;
		try {
			otp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Otp>(Otp.class));
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("No data found, sql: " + sql, e);
		}
		return otp;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 8:01:02 PM
	 * @param matchOtp
	 * @param updateOtp
	 * @param otp
	 * @throws NoUserOtpFound
	 * @throws OtpNotMatch
	 * @throws DBException
	 */
	public int verify(String matchOtp, String updateOtp, int otp, long user_id)
			throws NoUserOtpFound, OtpNotMatch, DBException {
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			Otp obj = jdbcTemplate.queryForObject(matchOtp, new BeanPropertyRowMapper<Otp>(Otp.class));
			if (null != obj) {
				if (obj.getOtp() == otp) {
					result = jdbcTemplate.update(updateOtp);
					result = jdbcTemplate.update("UPDATE users set isVerified = 'Y' where user_id = " + user_id);
				} else {
					LOGGER.info("OTP not match");
					throw new OtpNotMatch();
				}
			} else {
				LOGGER.info("User id not avaiable in otp table");
				throw new NoUserOtpFound();
			}
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			throw new DBException(e);
		}
		return result;
	}
}
