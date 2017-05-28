/**
 * 
 */
package com.nikata.rest.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikata.rest.constant.UserQuery;
import com.nikata.rest.dao.MerchantDao;
import com.nikata.rest.dao.PermissionDao;
import com.nikata.rest.dao.RoleDao;
import com.nikata.rest.dao.UserDao;
import com.nikata.rest.dto.RoleDTO;
import com.nikata.rest.model.Branches;
import com.nikata.rest.model.Client;
import com.nikata.rest.model.Permission;
import com.nikata.rest.model.Role;
import com.nikata.rest.model.User;
import com.nikata.rest.service.BranchService;

/**
 * @author Gaurav Oli
 * @date Mar 5, 2017 2:31:41 PM
 */
@Component
public class NFCCache {
	@Autowired
	private UserDao userDao;

	@Autowired
	private BranchService branchService;

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private MerchantDao merchantDao;

	private Map<Long, User> userMap = new HashMap<>();
	private Map<String, String> paramMap = new HashMap<>();
	private Map<Long, Permission> permissionMap = new HashMap<>();
	private Map<String, List<Permission>> roleMap = new HashMap<>();
	private Map<Long, String> merchantMap = new HashMap<>();

	/**
	 * 
	 * @author Gaurav Oli
	 * @date Mar 8, 2017 3:23:58 PM
	 */
	@PostConstruct()
	public void init() {
		load();
	}

	public void load() {
		try {
			loadUser();

			loadMerchant();

			loadBranch();

			loadPermission();

			loadRole();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 7:27:03 AM
	 */
	private synchronized void loadBranch() {
		paramMap.clear();
		List<Branches> branchList = branchService.getAllBranches();
		if (null != branchList && !branchList.isEmpty()) {
			for (Branches branch : branchList) {
				paramMap.put(branch.getUnique(), "?branch_id=" + branch.getUnique() + "&branch_name=" + branch.getName()
						+ "&branch_pid=" + branch.getFb_page_id());
			}
		}
	}

	/**
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 7:26:44 AM
	 */
	private synchronized void loadUser() {
		userMap.clear();
		List<User> userList = userDao.readAll(UserQuery.GET_ALL_USER);
		if (null != userList && !userList.isEmpty()) {
			for (User user : userList) {
				userMap.put(user.getUser_id(), user);
			}
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 28, 2017 12:06:50 AM
	 */
	public synchronized void loadMerchant() {
		merchantMap.clear();
		List<Client> clients = merchantDao.readAll();
		if (null != clients && !clients.isEmpty()) {
			clients.forEach(client -> {
				merchantMap.put(client.getClient_id(), client.getName());
			});
		}
	}

	/**
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 7:27:54 AM
	 * @throws Exception
	 */
	public synchronized void loadPermission() throws Exception {
		permissionMap.clear();
		List<Permission> permissionList = permissionDao.readAll();
		if (null != permissionList && !permissionList.isEmpty()) {
			for (Permission p : permissionList) {
				permissionMap.put(p.getId(), p);
			}
		}
	}

	/**
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 7:28:33 AM
	 */
	public synchronized void loadRole() {
		roleMap.clear();
		List<Role> roleList = roleDao.readAll();
		if (null != roleList && !roleList.isEmpty()) {
			for (Role r : roleList) {
				cacheRole(r);
			}
		}
	}

	/**
	 * @author Gaurav Oli
	 * @date Apr 22, 2017 7:23:18 AM
	 * @param r
	 */
	private void cacheRole(Role r) {
		if (null != roleMap.get(r.getName())) {
			roleMap.get(r.getName()).add(permissionMap.get(r.getPermission_id()));
		} else {
			List<Permission> permissions = new ArrayList<Permission>();
			permissions.add(permissionMap.get(r.getPermission_id()));
			roleMap.put(r.getName(), permissions);
		}
	}

	/**
	 * 
	 * @author Gaurav Oli
	 * @date May 3, 2017 12:03:18 PM
	 * @param r
	 */
	public void cacheRole(RoleDTO r) {
		if (null != roleMap.get(r.getName())) {
			roleMap.get(r.getName()).addAll(r.getPermissions());
		} else {
			roleMap.put(r.getName(), r.getPermissions());
		}
	}

	public Map<Long, User> getUserMap() {
		return userMap;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public Map<Long, Permission> getPermissionMap() {
		return permissionMap;
	}

	public Map<String, List<Permission>> getRoleMap() {
		return roleMap;
	}

	public Map<Long, String> getMerchantMap() {
		return merchantMap;
	}
}