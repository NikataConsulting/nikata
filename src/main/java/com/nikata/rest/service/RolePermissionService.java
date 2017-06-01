/**
 * 
 */
package com.nikata.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.dao.RoleDao;
import com.nikata.rest.dao.RolePermissionDao;
import com.nikata.rest.dto.RoleDTO;
import com.nikata.rest.model.Role;

/**
 * @author Gaurav Oli
 * @date May 31, 2017 6:30:04 PM
 */
@Service
public class RolePermissionService {
	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private NFCCache cache;

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 28, 2017 9:00:41 AM
	 * @return
	 */
	public List<Role> getAllRole() {
		return roleDao.readAll();
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 28, 2017 9:00:36 AM
	 * @param r
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public void create(RoleDTO r) throws Exception {
		rolePermissionDao.create(r);
		cache.loadRole();
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 31, 2017 6:34:33 PM
	 * @param r
	 * @throws Exception
	 */
	public void delete(RoleDTO r) throws Exception {
		rolePermissionDao.delete(r);
		cache.loadRole();
	}
}
