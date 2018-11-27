package com.xg.admin.service.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xg.admin.dao.system.RoleDataDao;
import com.xg.admin.pojo.system.RoleData;
import com.xg.admin.service.user.IUserService;
import com.xg.game.api.hessian.DataGrid;

/**
 * 角色实现
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService, InitializingBean {

	@Autowired
	private RoleDataDao roleDao;
	@Autowired
	private IUserService userService;

	private static final Map<Integer, RoleData> rolemap = new ConcurrentHashMap<Integer, RoleData>();

	@Override
	public void afterPropertiesSet() throws Exception {
		List<RoleData> list = roleDao.getAll();
		for (RoleData t : list) {
			rolemap.put(t.getId(), t);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(int page, int rows) {
		DataGrid j = new DataGrid();
		List<RoleData> listAll = new ArrayList<RoleData>();
		for (Entry<Integer, RoleData> en : rolemap.entrySet()) {
			listAll.add(en.getValue());
		}
		int total = listAll.size();
		int start = page;
		int end = rows;
		end = end > total ? total : end;
		List<RoleData> list = listAll.subList(start, end);
		j.setRows(list);
		j.setTotal(total);
		return j;
	}

	@Override
	public void add(String name, String desc) {
		RoleData r = new RoleData(desc, name);
		int cid = roleDao.save(r).intValue();
		r.setId(cid);
		rolemap.put(cid, r);
	}

	@Override
	public void update(int id, String name, String desc) {
		RoleData tr = get(id);
		tr.setRoleDesc(desc);
		tr.setRoleName(name);
		roleDao.update(tr);
	}

	@Override
	public void update(int id, String auths) {
		RoleData tr = get(id);
		tr.setAuthId(auths);
		roleDao.update(tr);
	}

	@Override
	public void deleteRoleAuths(int authId) {
		for (Entry<Integer, RoleData> en : rolemap.entrySet()) {
			RoleData role = en.getValue();
			if (role != null && role.checkAuth(authId)) {
				role.deleteAuth(authId);
				roleDao.update(role);
			}
		}
	}

	@Override
	public void addRoleAuths(int pid, int id) {
		//检查所有列表，如果有pid,就给加id
		for (Entry<Integer, RoleData> en : rolemap.entrySet()) {
			RoleData role = en.getValue();
			if (role != null && role.checkAuth(pid)) {
				role.addAuth(id);
				roleDao.update(role);
			}
		}
	}

	@Override
	public RoleData get(int id) {
		RoleData tr = rolemap.get(id);
		if (tr == null) {
			tr = roleDao.get(id);
			if (tr != null) {
				roleDao.update(tr);
			}
		}
		return tr;
	}

	@Override
	public RoleData get(String name) {
		for (Entry<Integer, RoleData> en : rolemap.entrySet()) {
			if (en.getValue().getRoleName().equals(name)) {
				return en.getValue();
			}
		}
		return null;
	}

	@Override
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			RoleData r = get(Integer.parseInt(id.trim()));
			if (r != null) {
				rolemap.remove(Integer.parseInt(id.trim()));
				roleDao.delete(r);
				//这里也需要把用户表里存的那个角色删除掉
				userService.deleteUserRoles(Integer.parseInt(id.trim()));
			}
		}
	}

	/**
	 * 只有admin登录才显示所有，其它就不显示超级用户
	 * @return
	 */
	@Override
	public List<RoleData> combobox() {
		List<RoleData> list = new ArrayList<RoleData>();
		for (Entry<Integer, RoleData> en : rolemap.entrySet()) {
			list.add(en.getValue());
		}
		return list;
	}

}
