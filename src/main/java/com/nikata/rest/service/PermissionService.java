/**
 * 
 */
package com.nikata.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikata.rest.dao.PermissionDao;
import com.nikata.rest.exception.QueryException;
import com.nikata.rest.model.Permission;

/**
 * @author Gaurav Oli
 * @date Apr 9, 2017 11:46:49 AM
 */
@Service
public class PermissionService {

	@Autowired
	private PermissionDao permissionDao;
	
	/**
	 * 
	 * @author Gaurav Oli
	 * @date Apr 12, 2017 8:05:28 AM
	 * @param p
	 * @param action
	 * @return
	 * @throws QueryException
	 * @throws Exception
	 */
	public int create(Permission p, String action) throws QueryException, Exception {
		if (action.equals("add")) {
			return permissionDao.create("INSERT INTO permission(name, description, updated_on) VALUES('" + p.getName()
					+ "','" + p.getDescription() + "', CURRENT_TIMESTAMP)");
		} else if (action.equals("update")) {
			return permissionDao.create("UPDATE permission SET name='" + p.getName() + "', description='"
					+ p.getDescription() + "', updated_on = CURRENT_TIMESTAMP WHERE id = "+p.getId());
		} else if (action.equals("delete")) {
			return permissionDao.create("DELETE FROM permission WHERE id =" + p.getId());
		} else {
			throw new Exception("Wrong operation");
		}
	}
	
	public List<Permission> read() throws Exception {
		return permissionDao.readAll();
	}
}
