package com.nikata.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikata.rest.cache.NFCCache;
import com.nikata.rest.dao.RoleDao;
import com.nikata.rest.dto.RoleDTO;
import com.nikata.rest.exception.QueryException;
import com.nikata.rest.model.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private NFCCache cache;

	public long create(RoleDTO r, String action) throws QueryException, Exception {
		long result = 0;
		if (action.equals("add")) {
			roleDao.create("INSERT INTO role(id, name, description, permission_id, updated_on) VALUES(?, ?,?,?,?)", r);
			cache.cacheRole(r);
		} else if (action.equals("update")) {
			result = roleDao.update("UPDATE role SET name='" + r.getName() + "', description='" + r.getDescription()
					+ "', permission_id = " + r.getPermissions().get(0).getId() + ",updated_on = CURRENT_TIMESTAMP WHERE id = "
					+ r.getId());
			if (result > 0) {
				cache.loadRole();
			}
		} else if (action.equals("delete")) {
			result = roleDao.update("DELETE FROM role WHERE id =" + r.getId());
			if (result > 0) {
				cache.getRoleMap().remove(r.getName());
			}
		} else {
			throw new Exception("Wrong operation");
		}
		return result;
	}

	public List<Role> getAllRole() {
		return roleDao.readAll();
	}
}
