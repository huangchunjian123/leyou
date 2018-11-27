package com.xg.admin.service.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xg.admin.dao.system.MenuDataDao;
import com.xg.admin.dto.model.Menu;
import com.xg.admin.dto.model.SessionInfo;
import com.xg.admin.dto.model.TreeNode;
import com.xg.admin.pojo.system.MenuData;
import com.xg.admin.pojo.system.RoleData;
import com.xg.admin.pojo.system.UserData;
import com.xg.admin.service.role.IRoleService;
import com.xg.admin.service.user.IUserService;

/**
 * 菜单操作实现
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService, InitializingBean {

	@Autowired
	private MenuDataDao menuDao;
	@Resource(name = "roleService")
	private IRoleService roleService;
	@Resource(name = "userService")
	private IUserService userService;

	private static final Map<Integer, MenuData> menumap = new ConcurrentHashMap<Integer, MenuData>();

	/**
	 * 显示菜单和按钮权限列表
	 */
	@Override
	public List<Menu> treegrid(String menuId) {
		List<Menu> tree = new ArrayList<Menu>();
		List<MenuData> tmenus = getMenuAuthByid(-1);
		for (MenuData t : tmenus) {
			t.setPid(-1);//一级菜单没父类
			tree.add(treegriddg(t));
		}
		return tree;
	}

	private Menu treegriddg(MenuData data) {
		Menu m = new Menu(data);
		List<MenuData> listChild = getMenuAuthByid(data.getId());
		if (listChild != null && listChild.size() > 0) {
			Collections.sort(listChild, new MenuComparator());// 排序
			List<Menu> children = new ArrayList<Menu>();
			for (MenuData r : listChild) {
				Menu tn = treegriddg(r);
				children.add(tn);
			}
			m.setChildren(children);
		}
		return m;
	}

	/**
	 * 树菜单显示,根据这个玩家的角色显示不同的菜单
	 */
	@Override
	public List<TreeNode> authMenu(SessionInfo session) {
		List<TreeNode> tree = new ArrayList<TreeNode>();
		//从根目录开始查找
		Set<MenuData> setauths = getAuths(session.getUserId(), MenuData.menu);
		List<MenuData> l = getMenuList(-1);
		for (MenuData t : l) {
			t.setPid(-1);
			//查找这个节点下的所有的元素，只要有一个在，就显示这个父节点
			tree.add(authMenuNode(session, t, setauths));
		}
		return tree;
	}

	private boolean checkAuths(Set<MenuData> auths, MenuData tm) {
		for (MenuData t : auths) {
			if (tm.getId() == t.getId()) {
				return true;
			}
		}
		return false;
	}

	private TreeNode authMenuNode(SessionInfo session, MenuData t, Set<MenuData> auths) {
		TreeNode node = new TreeNode(t);
		List<MenuData> listChild = getMenuList(t.getId());
		if (listChild != null && listChild.size() > 0) {
			List<TreeNode> children = new ArrayList<TreeNode>();
			Collections.sort(listChild, new MenuComparator());// 排序
			for (MenuData r : listChild) {
				if (session.getLoginName().equals("admin") || isShow(r, auths)) {
					TreeNode tn = authMenuNode(session, r, auths);
					children.add(tn);
				}
			}
			node.setChildren(children);
		}
		return node;
	}

	/**
	 * 只要子节点里有这家伙，父节点就一定显示
	 * @param t
	 * @param auths
	 * @return
	 */
	private boolean isShow(MenuData t, Set<MenuData> auths) {
		List<MenuData> listChild = getMenuList(t.getId());
		//不为空说明有子节点
		if (listChild != null && listChild.size() > 0) {
			for (MenuData r : listChild) {
				//如果父节点
				if (isParent(r.getId())) {
					return isShow(r, auths);
				} else {
					if (checkAuths(auths, r)) {
						return true;
					}
				}
			}
		} else {
			if (checkAuths(auths, t)) {
				return true;
			}
		}
		return false;
	}

	private boolean isParent(int cid) {
		List<MenuData> listChild = getMenuList(cid);
		if (listChild != null && listChild.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询这家伙能显示哪些菜单
	 * @param userId
	 * @param type 1:菜单  2：按钮
	 * @return
	 */
	@Override
	public Set<MenuData> getAuths(int userId, String type) {
		Set<MenuData> set = new HashSet<MenuData>();
		UserData user = userService.get(userId);
		if (user != null && StringUtils.isNotEmpty(user.getRoleId())) {
			String[] roles = user.getRoleId().split(",");
			for (String roleId : roles) {
				RoleData role = roleService.get(Integer.parseInt(roleId));
				if (role != null && StringUtils.isNotEmpty(role.getAuthId())) {
					String[] auths = role.getAuthId().split(",");
					for (String mid : auths) {
						MenuData tm = get(Integer.parseInt(mid));
						if (tm.getType().equals(type)) {
							set.add(tm);
						}
					}
				}
			}
		}
		return set;
	}

	private boolean isCheckedAuth(int menuId, String auths) {
		if (StringUtils.isEmpty(auths)) {
			return false;
		}
		for (String id : auths.split(",")) {
			if (id.equals(menuId + "")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据权限id，显示右边树权树哪些需要勾选
	 */
	@Override
	public List<TreeNode> authtreeList(int roleId) {
		RoleData role = roleService.get(roleId);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		//从根目录开始查找
		List<MenuData> l = getMenuAuthByid(-1);
		for (MenuData t : l) {
			t.setPid(-1);//一级菜单没父类
			tree.add(ahtutree(t, role.getAuthId()));
		}
		return tree;
	}

	private TreeNode ahtutree(MenuData t, String auths) {
		TreeNode node = new TreeNode(t);
		//是否选中
		node.setChecked(isCheckedAuth(t.getId(), auths));
		List<MenuData> listChild = getMenuAuthByid(t.getId());
		if (listChild != null && listChild.size() > 0) {
			Collections.sort(listChild, new MenuComparator());// 排序
			List<TreeNode> children = new ArrayList<TreeNode>();
			for (MenuData r : listChild) {
				TreeNode tn = ahtutree(r, auths);
				children.add(tn);
			}
			node.setChildren(children);
		}
		return node;
	}

	@Override
	public void edit(int id, int pid, String name, int sort, String type, String url) {
		MenuData t = get(id);
		t.setName(name);
		t.setUrl(url);
		t.setSort(sort);
		t.setType(type);
		if (pid != t.getPid()) {
			t.setPid(pid);
			//转化权限，删除掉旧的id，添加新的
			roleService.deleteRoleAuths(id);
			//给新的节点下添加id
			roleService.addRoleAuths(pid, id);
		}
		menuDao.update(t);
	}

	@Override
	public void add(int pid, String name, int sort, String type, String url) {
		MenuData t = new MenuData(pid, name, sort, type, url);
		int cid = menuDao.save(t).intValue();
		t.setId(cid);
		menumap.put(cid, t);
	}

	private MenuData get(int id) {
		MenuData tm = menumap.get(id);
		if (tm == null) {
			tm = menuDao.get(id);
			if (tm != null) {
				menumap.put(id, tm);
			}
		}
		return tm;
	}

	@Override
	public void delete(int id) {
		del(id);
	}

	private void del(int id) {
		MenuData t = get(id);
		if (t != null) {
			menumap.remove(id);
			roleService.deleteRoleAuths(id);
			List<MenuData> menus = getMenuAuthByid(t.getId());
			if (menus != null && !menus.isEmpty()) {
				//说明当前菜单下面还有子菜单
				for (MenuData tmenu : menus) {
					del(tmenu.getId());
				}
			}
			menuDao.delete(t);
		}
	}

	private List<MenuData> getMenuList(int id) {
		List<MenuData> list = new ArrayList<MenuData>();
		for (Entry<Integer, MenuData> en : menumap.entrySet()) {
			MenuData tm = en.getValue();
			if (tm.getType().equals("01") && tm.getPid() == id) {
				list.add(tm);
			}
		}
		return list;
	}

	private List<MenuData> getMenuAuthByid(int id) {
		List<MenuData> list = new ArrayList<MenuData>();
		for (Entry<Integer, MenuData> en : menumap.entrySet()) {
			MenuData tm = en.getValue();
			if (tm.getPid() == id) {
				list.add(tm);
			}
		}
		return list;
	}

	private class MenuComparator implements Comparator<MenuData> {
		public int compare(MenuData o1, MenuData o2) {
			int i1 = o1.getSort();
			int i2 = o2.getSort();
			return i1 - i2;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<MenuData> list = menuDao.getMenuAll();
		for (MenuData tm : list) {
			menumap.put(tm.getId(), tm);
		}
	}
}
