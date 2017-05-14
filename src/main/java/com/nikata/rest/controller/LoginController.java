/**
 * 
 */
package com.nikata.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.constant.Constants;
import com.nikata.rest.dao.LoginDao;
import com.nikata.rest.dto.Response;
import com.nikata.rest.model.Login;

/**
 * @author Gaurav Oli
 * @date Apr 2, 2017 1:20:36 PM
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/app/login")
public class LoginController {

	@Autowired
	private LoginDao loginDao;

	@RequestMapping(method = RequestMethod.GET)
	public Response login(Response response, Login login, HttpServletResponse httpResponse) {
		try {
			response.setPayload(loginDao.validateCustomer(login));
			response.setMessage(Constants.SUCCESS);
			response.setHttpCode(200);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
}
