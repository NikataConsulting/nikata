/**
 * 
 */
package com.nikata.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.dao.MerchantDao;
import com.nikata.rest.dto.Response;
import com.nikata.rest.model.Client;

/**
 * @author Gaurav Oli
 * @date Apr 29, 2017 5:55:31 PM
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/app/merchant")
public class MerchantController {

	@Autowired
	private MerchantDao merchantDao;
	
	@RequestMapping(method= RequestMethod.GET)
	public Response read(Client client, Response response) throws Exception {
		response.setHttpCode(200);
		response.setMessage("success");
		response.setPayload(merchantDao.read(client.getClient_id()));
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response create(@RequestBody Client client, Response response) {
		try {
			merchantDao.create(client);
			response.setHttpCode(200);
			response.setMessage("success");
		} catch (Exception e) {
			response.setHttpCode(400);
			response.setMessage("failure");
		}
		return response;
	}
}