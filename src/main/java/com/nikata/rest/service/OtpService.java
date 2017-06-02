/**
 * 
 */
package com.nikata.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nikata.rest.constant.OtpQuery;
import com.nikata.rest.dao.OtpDao;
import com.nikata.rest.dao.UserDao;
import com.nikata.rest.exception.DBException;
import com.nikata.rest.exception.MobileExistException;
import com.nikata.rest.exception.NoUserOtpFound;
import com.nikata.rest.exception.OtpNotMatch;
import com.nikata.rest.model.Otp;
import com.nikata.rest.utility.Utility;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 2:35:11 PM
 */
@Service
public class OtpService {
	@Autowired
	private OtpDao otpDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${sms.gateway.url}")
	private String baseUrl;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:41 PM
	 * @param user
	 * @return
	 * @throws MobileExistException
	 */
	public int create(long user_id, String mobile)
			throws DataAccessException, RestClientException, MobileExistException {
		int result = 0;
		String response = null;
		if (null == userDao.readByKey(OtpQuery.checkMobileExist(mobile))) {
			otpDao.create(OtpQuery.updateUserMobile(user_id, mobile));
			Otp otpObj = otpDao.readByKey(OtpQuery.getOtpIfExist(user_id));
			if (null != otpObj && otpObj.getOtp() > 0) {
				response = restTemplate.getForObject(OtpQuery.createURL(baseUrl, otpObj.getOtp(), mobile),
						String.class);
				if (response.contains("Success")) {
					result = 1;
				}
			} else {
				int otp = Utility.randon();
				response = restTemplate.getForObject(OtpQuery.createURL(baseUrl, otp, mobile), String.class);
				if (response.contains("Success")) {
					result = otpDao.create(OtpQuery.add(user_id, otp));
				}
			}
		} else {
			throw new MobileExistException("Mobile already exist");
		}
		return result;
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 8:30:46 PM
	 * @param user_id
	 * @param otp
	 * @return
	 * @throws NoUserOtpFound
	 * @throws OtpNotMatch
	 * @throws DBException
	 */
	public int update(long user_id, int otp) throws NoUserOtpFound, OtpNotMatch, DBException {
		return otpDao.verify(OtpQuery.matchOtp(user_id, otp), OtpQuery.updateOtp(user_id, otp), otp, user_id);
	}

	public void updateUserMobile(long user_id, String mobile) throws MobileExistException {
		otpDao.create(OtpQuery.updateUserMobile(user_id, mobile));
	}
}
