/**
 * 
 */
package com.nikata.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.constant.Constants;
import com.nikata.rest.constant.Status;
import com.nikata.rest.dto.Response;
import com.nikata.rest.exception.DBException;
import com.nikata.rest.exception.MobileExistException;
import com.nikata.rest.exception.NoUserOtpFound;
import com.nikata.rest.exception.OtpNotMatch;
import com.nikata.rest.service.OtpService;

/**
 * @author Gaurav Oli
 * @date Mar 8, 2017 2:26:15 PM
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/app/otp")
public class OtpController {

	@Autowired
	private NFCCache cache;

	@Autowired
	private OtpService otpService;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:22:09 PM
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Response get(@RequestParam(required=true) long user_id,
						@RequestParam(required=true) String mobile, Response response, HttpServletResponse httpResponse) {
		try {
			if (null == cache.getUserMap().get(user_id)) {
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
				response.setHttpCode(Status.USER_NOT_FOUND.getStatus());
				response.setMessage(Status.USER_NOT_FOUND.getMessage());
			} else if (cache.getUserMap().get(user_id).getIsVerified() == 'Y') {
				response.setHttpCode(Status.VERIFIED_USER.getStatus());
				response.setMessage(Status.VERIFIED_USER.getMessage());
				response.setPayload(cache.getUserMap().get(user_id));
			} else {
				if (null == cache.getUserMap().get(user_id).getMobile()) {
					otpService.updateUserMobile(user_id, mobile);
					cache.getUserMap().get(user_id).setMobile(mobile);
				}
				
				if (otpService.create(user_id, mobile) == 1) {
					response.setHttpCode(HttpStatus.OK.value());
					response.setMessage(Constants.SUCCESS);
				} else {
					response.setHttpCode(Status.OTP_NOT_SENT.getStatus());
					response.setMessage(Status.OTP_NOT_SENT.getMessage());
				}
			}
		} catch (MobileExistException e) {
			httpResponse.setStatus(Status.MOBILE_EXIST.getStatus());
			response.setHttpCode(Status.MOBILE_EXIST.getStatus());
			response.setMessage(Status.MOBILE_EXIST.getMessage());
		} catch (DataAccessException e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.DB.getStatus());
			response.setMessage(e.getMessage());
		} catch (RestClientException e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.REST.getStatus());
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public Response put(@RequestParam(required = true) long user_id, @RequestParam(required = true) int otp,
			Response response, HttpServletResponse httpResponse) {
		if (null == cache.getUserMap().get(user_id)) {
			response.setHttpCode(Status.USER_NOT_FOUND.getStatus());
			response.setMessage(Status.USER_NOT_FOUND.getMessage());
		} else if (cache.getUserMap().get(user_id).getIsVerified() == 'Y') {
			response.setHttpCode(Status.VERIFIED_USER.getStatus());
			response.setMessage(Status.VERIFIED_USER.getMessage());
		} else {
			try {
				if (1 == otpService.update(user_id, otp)) {
					cache.getUserMap().get(user_id).setIsVerified('Y');
					response.setHttpCode(HttpStatus.OK.value());
					response.setMessage(Constants.SUCCESS);
				}
			} catch (NoUserOtpFound e) {
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
				response.setHttpCode(Status.USER_NOT_FOUND_IN_OTP.getStatus());
				response.setMessage(Status.USER_NOT_FOUND_IN_OTP.getMessage());
			} catch (OtpNotMatch e) {
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
				response.setHttpCode(Status.OTP_INVALID.getStatus());
				response.setMessage(Status.OTP_INVALID.getMessage());
			} catch (DBException e) {
				httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				response.setHttpCode(Status.DB.getStatus());
				response.setMessage(e.getMessage());
			} catch (Exception e) {
				httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				response.setHttpCode(Status.UNKNOWN.getStatus());
				response.setMessage(e.getMessage());
			}
		}
		return response;
	}

}