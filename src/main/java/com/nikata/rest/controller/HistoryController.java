/**
 * 
 */
package com.nikata.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.constant.Constants;
import com.nikata.rest.constant.Status;
import com.nikata.rest.dto.Response;
import com.nikata.rest.model.History;
import com.nikata.rest.model.User;
import com.nikata.rest.service.HistoryService;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 2:26:44 PM
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/app/history")
public class HistoryController {
	@Autowired
	private HistoryService historyService;

	@Autowired
	private NFCCache cache;

	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);

	@RequestMapping(value = "/{history_id}", method = RequestMethod.GET)
	public Response get(@PathVariable User user) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Response getAll(@PathVariable User user) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response post(@RequestBody History history, Response response, HttpServletResponse httpResponse) {
		try {
			if (null != cache.getUserMap().get(history.getUser_id())) {
				if (1 == historyService.save(history)) {
					response.setHttpCode(HttpStatus.OK.value());
					response.setMessage(Constants.SUCCESS);
				} else {
					response.setHttpCode(HttpStatus.BAD_REQUEST.value());
					response.setMessage(Constants.FAILURE);
				}
			} else {
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
				response.setHttpCode(Status.USER_NOT_FOUND.getStatus());
				response.setMessage(Status.USER_NOT_FOUND.getMessage());
			}
		} catch (DataAccessException e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.DB.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Query building Exception {}", e);
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
			response.setHttpCode(Status.UNKNOWN.getStatus());
			response.setMessage(e.getMessage());
			LOGGER.error("Unhandled/Unknown Exception {}", e);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Response put() {
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public Response delete() {
		return null;
	}
}
