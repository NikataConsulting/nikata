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
			roleDao.create(r);
			cache.cacheRole(r);
		} else if (action.equals("update")) {
			roleDao.update(r);
			cache.loadRole();
			result = 1;
		} else if (action.equals("delete")) {
			roleDao.delete(r);
			cache.getRoleMap().remove(r.getName());
			result = 1;
		} else {
			throw new Exception("Wrong operation");
		}
		return result;
	}

	public List<Role> getAllRole() {
		return roleDao.readAll();
	}
}
