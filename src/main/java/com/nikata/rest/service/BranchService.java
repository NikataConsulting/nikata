/**
 * 
 */
package com.nikata.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikata.rest.constant.BranchQuery;
import com.nikata.rest.dao.BranchDao;
import com.nikata.rest.model.Branches;

/**
 * @author Gaurav Oli
 * @date Mar 20, 2017 5:15:25 PM
 */
@Service
public class BranchService {
	@Autowired
	private BranchDao branchDao;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 18, 2017 2:03:55 PM
	 * @return
	 */
	public List<Branches> getAllBranches() {
		return branchDao.getAllBranches(BranchQuery.getAllBranch());
	}
}
