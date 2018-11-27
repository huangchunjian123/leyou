package com.xg.admin.controllers;

import net.paoding.rose.web.annotation.Param;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.pojo.system.RoleData;
import com.xg.admin.service.role.IRoleService;
import com.xg.admin.utils.ExceptionUtil;
import com.xg.admin.utils.ResultCode;

/**
 * 角色控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class RoleController extends BaseController {

	private static final Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private IRoleService roleService;

	/**
	 * 跳转到角色管理页面
	 * 
	 * @return
	 */
	public String goRole() {
		return "system/role";
	}

	/**
	 * 获得角色datagrid
	 * @param page
	 * @param rows
	 * @return
	 */
	public Object datagrid(@Param("page") int page, @Param("rows") int rows) {
		if (page < 0){
			page = 1;
		}
		int pagesize = (rows == 0) ? 10 : rows;
		return ResultCode.writeJson(roleService.datagrid(page, pagesize));
	}

	/**
	 * 添加一个角色
	 * @param name
	 * @param desc
	 * @return
	 */
	public Object add(@Param("roleName") String roleName, @Param("roleDesc") String roleDesc) {
		try {
			RoleData role = roleService.get(roleName);
			if (role != null) {
				return ResultCode.error("角色名已存在!");
			}
			roleService.add(roleName, roleDesc);
			return ResultCode.succ("添加角色成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("添加角色成功!");
		}
	}

	/**
	 * 编辑一个角色
	 * @param id
	 * @param name
	 * @param desc
	 * @return
	 */
	public Object edit(@Param("id") int id, @Param("roleName") String roleName, @Param("roleDesc") String roleDesc) {
		try {
			RoleData role = roleService.get(id);
			if (role == null) {
				return ResultCode.error("角色不存在!");
			}
			roleService.update(id, roleName, roleDesc);
			return ResultCode.succ("编辑角色成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("编辑角色失败!");
		}
	}

	/**
	 * 编辑权限
	 * @param id
	 * @param auths
	 * @return
	 */
	public Object updateAuth(@Param("id") int id, @Param("auths") String auths) {
		try {
			roleService.update(id, auths);
			return ResultCode.succ("权限设置成功!");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("权限设置失败!");
		}
	}

	/**
	 * 删除一个或多个角色
	 * @param id
	 * @return
	 */
	public Object deleteRole(@Param("id") String id) {
		roleService.delete(id);
		return ResultCode.succ("删除角色成功!");
	}

	/**
	 * 获得角色下拉列表
	 */
	public Object roleCombobox() {
		return ResultCode.writeJson(roleService.combobox());
	}

}
