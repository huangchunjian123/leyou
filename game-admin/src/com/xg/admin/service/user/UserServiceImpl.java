package com.xg.admin.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xg.admin.dao.system.UserDataDao;
import com.xg.admin.pojo.system.RoleData;
import com.xg.admin.pojo.system.UserData;
import com.xg.admin.service.role.IRoleService;
import com.xg.game.api.hessian.DataGrid;

/**
 * 用户操作实现
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService, InitializingBean {

	@Autowired
	private UserDataDao userDao;
	@Autowired
	private IRoleService roleService;

	private static final Map<Integer, UserData> usermap = new ConcurrentHashMap<Integer, UserData>();

	@Override
	public UserData login(String name, String pwd) {
		for (Entry<Integer, UserData> en : usermap.entrySet()) {
			UserData user = en.getValue();
			if (user != null && user.getName().equals(name) && user.getPwd().equals(pwd)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public UserData getUserByName(String name) {
		for (Entry<Integer, UserData> en : usermap.entrySet()) {
			UserData user = en.getValue();
			if (user != null && user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public UserData get(int id) {
		UserData user = usermap.get(id);
		if (user == null) {
			user = userDao.get(id);
			if (user != null) {
				usermap.put(user.getId(), user);
			}
		}
		return user;
	}

	@Override
	public void deleteUserRoles(int roleId) {
		for (Entry<Integer, UserData> en : usermap.entrySet()) {
			UserData user = en.getValue();
			if (user != null && user.checkRole(roleId)) {
				user.deleteRole(roleId);
				userDao.update(user);
			}
		}
	}

	@Override
	public DataGrid datagrid(int page, int rows) {
		DataGrid j = new DataGrid();
		List<UserData> listAll = new ArrayList<UserData>();
		for (Entry<Integer, UserData> en : usermap.entrySet()) {
			UserData user = en.getValue();
			if (!user.getName().equals("admin"))
				listAll.add(user);
		}
		int total = listAll.size();
		int start = page;
		int end = rows;
		end = end > total ? total : end;
		List<UserData> list = listAll.subList(start, end);
		for (UserData t : list) {
			if (t.getRoleId().equals(""))
				continue;
			StringBuffer sb = new StringBuffer();
			String[] ids = t.getRoleId().split(",");
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					RoleData role = roleService.get(Integer.parseInt(ids[i]));
					if (role != null) {
						sb.append(role.getRoleName());
					}
					if (i < ids.length - 1) {
						sb.append(",");
					}
				}
			}
			t.setRoleNames(sb.toString());
		}
		j.setRows(list);
		j.setTotal(total);
		return j;
	}

	@Override
	public void add(String name, String pwd, String realName, String mail, String mobile, String roleId) {
		UserData u = new UserData();
		u.setName(name);
		u.setPwd(pwd);
		u.setRealName(realName);
		u.setMail(mail);
		u.setMobile(mobile);
		u.setRoleId(roleId);
		u.setModifyDateTime(new Date());
		u.setCreateDateTime(new Date());
		int id = userDao.save(u).intValue();
		u.setId(id);
		usermap.put(id, u);
	}

	@Override
	public void update(int id, String realName, String mail, String mobile, String roleId) {
		UserData u = get(id);
		u.setRealName(realName);
		u.setMail(mail);
		u.setMobile(mobile);
		u.setRoleId(roleId);
		//保存修改时间
		u.setModifyDateTime(new Date());
		userDao.update(u);
	}

	@Override
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			if (!id.trim().equals("0")) {
				usermap.remove(Integer.parseInt(id.trim()));
				userDao.delete(Integer.parseInt(id.trim()));
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<UserData> list = userDao.getAll();
		for (UserData user : list) {
			usermap.put(user.getId(), user);
		}
	}

}
