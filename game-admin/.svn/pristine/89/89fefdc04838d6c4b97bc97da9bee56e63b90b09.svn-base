package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.pojo.system.RoleData;
import com.xg.admin.pojo.system.UserData;
import com.xg.admin.service.role.IRoleService;
import com.xg.admin.service.user.IUserService;
import com.xg.admin.utils.ExceptionUtil;
import com.xg.admin.utils.ResultCode;

/**
 * 用户控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class UserController extends BaseController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired IRoleService roleService;
	
	public Object rolecombobox() {
		List<RoleData> list = roleService.combobox();
		return ResultCode.writeJson(list);
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	public String goUser() {
		return "system/user";
	}

	/**
	 * 获得用户datagrid
	 * @param page
	 * @param rows
	 * @return
	 */
	public Object datagrid(@Param("page") int page,@Param("rows") int rows) {
		if (page < 0)
			page = 1;
		int pagesize = rows == 0 ? 10 : rows;
		int offset = (page - 1) * pagesize;
        //每页显示条数  
		return ResultCode.writeJson(userService.datagrid(offset,pagesize));
	}

	/**
	 * 添加一个用户
	 * @param name
	 * @param pwd
	 * @param realName
	 * @param mail
	 * @param mobile
	 * @param roleId
	 * @return
	 */
	public Object add(@Param("name") String name,
			@Param("pwd") String pwd,
			@Param("realName") String realName,
			@Param("mail") String mail,
			@Param("mobile") String mobile,
			@Param("roleId") String[] roleId) {
		try {
			UserData tu = userService.getUserByName(name);
			if(tu != null){
				return ResultCode.succ("用户已存在！");
			}
			userService.add(name,pwd,realName,mail,mobile,changeRoleIds(roleId));
			return ResultCode.succ("添加用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("添加用户失败！");
		}
	}

	/**
	 * 编辑一个用户
	 * @param id
	 * @param realName
	 * @param mail
	 * @param mobile
	 * @param roleId
	 * @return
	 */
	public Object edit(@Param("id") int id,
			@Param("realName") String realName,
			@Param("mail") String mail,
			@Param("mobile") String mobile,
			@Param("roleId") String roleId) {
		try {
			UserData tu = userService.get(id);
			if(tu == null){
				return ResultCode.succ("用户不存在！");
			}
			userService.update(id, realName, mail, mobile,roleId.substring(0,roleId.length()-1));
			return ResultCode.succ("编辑用户成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("编辑用户失败！");
		}
	}

	/**
	 * 删除一个或多个用户
	 * @param id
	 * @return
	 */
	public Object deleteUser(@Param("id") String id) {
		userService.delete(id);
		return ResultCode.succ("删除用户成功!");
	}

	private String changeRoleIds(String[] roleId){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<roleId.length;i++){
			sb.append(roleId[i]);
			if (i < roleId.length - 1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
