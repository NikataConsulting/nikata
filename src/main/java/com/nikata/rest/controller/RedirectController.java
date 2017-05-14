package com.nikata.rest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nikata.rest.cache.NFCCache;

/**
 * 
 */

/**
 * @author Gaurav Oli
 * @date Mar 20, 2017 8:04:52 AM
 */
@RestController
public class RedirectController {
	@Autowired
	private NFCCache nfcCache;
	
	private RedirectView redirectView = new RedirectView();
	
	@Value("${redirect.base.url}")
	private String baseUrl;
	
	@RequestMapping("/{name}")
	public RedirectView localRedirect(@PathVariable String name) {
	    redirectView.setUrl(baseUrl+nfcCache.getParamMap().get(name));
	    return redirectView;
	}
}
