/**
 * 
 */
package com.nikata.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikata.rest.constant.HistoryQuery;
import com.nikata.rest.dao.HistoryDao;
import com.nikata.rest.model.History;

/**
 * @author Gaurav Oli
 * @date Mar 21, 2017 2:43:01 PM
 */
@Service
public class HistoryService {
	@Autowired
	private HistoryDao historyDao;

	public int save(History history) {
		return historyDao.save(HistoryQuery.save(history));
	}
}
