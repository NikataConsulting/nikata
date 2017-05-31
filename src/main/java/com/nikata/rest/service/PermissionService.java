/**
 * 
 */
package com.nikata.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikata.rest.cache.NFCCache;
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

	@Autowired
	private NFCCache cache;

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
	public void create(Permission p, String action) throws QueryException, Exception {
		if (action.equals("add")) {
			permissionDao.create(p);
			cache.getPermissionMap().put(p.getId(), p);
		} else if (action.equals("update")) {
			permissionDao.update(p);
			cache.getPermissionMap().put(p.getId(), p);
		} else if (action.equals("delete")) {
			permissionDao.delete(p);
			cache.getPermissionMap().remove(p.getId());
		} else {
			throw new Exception("Wrong operation");
		}
		cache.loadRole();
	}

	public List<Permission> read() throws Exception {
		return permissionDao.readAll();
	}
}
