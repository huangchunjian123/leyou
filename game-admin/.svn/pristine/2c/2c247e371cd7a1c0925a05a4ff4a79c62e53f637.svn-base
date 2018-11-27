package com.xg.admin.service.user;

import com.xg.admin.pojo.system.UserData;
import com.xg.game.api.hessian.DataGrid;

/**
 * 用户操作接口
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public interface IUserService {

	/**
	 * 用户登录
	 * @param name
	 * @param pwd
	 * @return
	 */
	public UserData login(String name, String pwd);

	/**
	 * 获取用户datagrid
	 * @param page
	 * @param rows
	 * @return
	 */
	public DataGrid datagrid(int page, int rows);

	/**
	 * 取用户对象
	 * @param name
	 * @return
	 */
	public UserData getUserByName(String name);

	/**
	 * 取用户对象
	 * @param id
	 * @return
	 */
	public UserData get(int id);

	/**
	 * 添加用户
	 * @param name
	 * @param pwd
	 * @param realName
	 * @param mail
	 * @param mobile
	 * @param roleId
	 */
	public void add(String name, String pwd, String realName, String mail, String mobile, String roleId);

	/**
	 * 编辑用户
	 * @param id
	 * @param realName
	 * @param mail
	 * @param mobile
	 * @param roleId
	 */
	public void update(int id, String realName, String mail, String mobile, String roleId);

	/**
	 * 删除用户
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 删除角色时，要把用户表里的这个有关的全部删除掉
	 * @param roleId
	 */
	public void deleteUserRoles(int roleId);

}
