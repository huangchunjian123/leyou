package com.game.admin.service.role;

import java.util.List;

import com.game.admin.pojo.system.RoleData;
import com.game.api.hessian.DataGrid;

/**
 * 角色操作接口
 * @author huangchunjian 
 *
 */
public interface IRoleService {
	
	/**
	 * 根据角色名称获取角色
	 * @param name
	 * @return
	 */
	public RoleData get(String name);

	/**
	 * 根据角色id获取角色
	 * @param id
	 * @return
	 */
	public RoleData get(int id);
	
	/**
	 * 查询角色datagrid
	 * @param page
	 * @param rows
	 * @return
	 */
	public DataGrid datagrid(int page,int rows);

	/**
	 * 添加角色
	 * @param name
	 * @param desc
	 */
	public void add(String name,String desc);

	/**
	 * 修改角色
	 * @param id
	 * @param name
	 * @param desc
	 */
	public void update(int id,String name,String desc);

	/**
	 * 设置权限
	 * @param id
	 * @param auths
	 */
	public void update(int id,String auths);

	/**
	 * 删除一个或多个角色
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得角色下拉列表
	 * 
	 * @return
	 */
	public List<RoleData> combobox();

	/**
	 * 刪除角色权限
	 * @param id
	 */
	public void deleteRoleAuths(int id);
	
	/**
	 * 添加角色权限
	 * @param pid
	 * @param id
	 */
	public void addRoleAuths(int pid,int id);
}
