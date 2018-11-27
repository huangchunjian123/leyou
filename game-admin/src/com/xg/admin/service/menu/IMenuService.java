package com.xg.admin.service.menu;

import java.util.List;
import java.util.Set;

import com.xg.admin.dto.model.Menu;
import com.xg.admin.dto.model.SessionInfo;
import com.xg.admin.dto.model.TreeNode;
import com.xg.admin.pojo.system.MenuData;

/**
 * 菜单操作接口
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public interface IMenuService {

	/**
	 * 获取某用户的权限，菜单或是按钮
	 * @param userId
	 * @param type
	 * @return
	 */
	public Set<MenuData> getAuths(int userId, String type);

	/**
	 * 获得菜单treegrid列表,包括菜单和按钮
	 * @param menuId
	 * @return
	 */
	public List<Menu> treegrid(String menuId);

	/**
	 * 获取左边菜单树,只包括菜单
	 * @param session
	 * @return
	 */
	public List<TreeNode> authMenu(SessionInfo session);

	/**
	 * 获取权限,菜单和按钮,点击设置权限时调用
	 * @param roleId
	 * @return
	 */
	public List<TreeNode> authtreeList(int roleId);

	/**
	 * 编辑菜单
	 * @param id
	 * @param pid
	 * @param name
	 * @param sort
	 * @param type
	 * @param url
	 */
	public void edit(int id, int pid, String name, int sort, String type, String url);

	/**
	 * 添加菜单
	 * @param pid
	 * @param name
	 * @param sort
	 * @param type
	 * @param url
	 */
	public void add(int pid, String name, int sort, String type, String url);

	/**
	 * 删除菜单
	 * @param id
	 */
	public void delete(int id);

}
