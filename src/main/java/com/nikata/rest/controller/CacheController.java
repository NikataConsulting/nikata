/**
 * 
 */
package com.nikata.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.dto.Response;

/**
 * @author Gaurav Oli
 * @date Mar 22, 2017 12:49:30 PM
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value="/app/reload")
public class CacheController {
	
	@Autowired
	private NFCCache nfcCache;
	
	@RequestMapping(method=RequestMethod.GET)
	public Response reload(Response response){
		nfcCache.load();
		return response;
	}
	
}
