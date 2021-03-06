package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.model.Menu;
import com.xg.admin.dto.model.SessionInfo;
import com.xg.admin.dto.model.TreeNode;
import com.xg.admin.service.menu.IMenuService;
import com.xg.admin.service.user.IUserService;
import com.xg.admin.utils.Constants;
import com.xg.admin.utils.ExceptionUtil;
import com.xg.admin.utils.ResultCode;

/**
 *  菜单控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class MenuController extends BaseController {

	private static final Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;

	/**
	 * 首页获得菜单树
	 */
	public Object ctrlTree() {
		SessionInfo session = (SessionInfo) inv.getRequest().getSession().getAttribute(Constants.SESSIONKEY);
		List<TreeNode> list = menuService.authMenu(session);
		return ResultCode.writeJson(list);
	}

	/**
	 * 跳转到菜单管理页面
	 * @return
	 */
	public String goMenu() {
		return "system/menu";
	}

	/**
	 * 获得菜单treegrid
	 */
	public Object treegrid() {
		List<Menu> list = menuService.treegrid("");
		return ResultCode.writeJson(list);
	}

	/**
	 * 显示权限树
	 * @param roleId
	 * @return
	 */
	public Object authtree(@Param("roleId") int roleId) {
		if (roleId == 0) {
			roleId = 1;
		}
		List<TreeNode> list = menuService.authtreeList(roleId);
		return ResultCode.writeJson(list);
	}

	/**
	 * 编辑菜单
	 * @param id
	 * @param pid
	 * @param name
	 * @param sort
	 * @param type
	 * @param url
	 * @return
	 */
	public Object edit(@Param("id") int id, @Param("pid") int pid, @Param("name") String name,
			@Param("sort") int sort, @Param("type") String type, @Param("url") String url) {
		try {
			menuService.edit(id, pid, name, sort, type, url);
			return ResultCode.succ("编辑成功!请手动刷新左侧功能菜单树！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("编辑失败！");
		}
	}

	/**
	 * 添加菜单
	 * @param pid
	 * @param name
	 * @param sort
	 * @param type
	 * @param url
	 * @return
	 */
	public Object add(@Param("pid") int pid, @Param("name") String name, @Param("sort") int sort,
			@Param("type") String type, @Param("url") String url) {
		try {
			menuService.add(pid, name, sort, type, url);
			return ResultCode.succ("添加成功!请手动刷新左侧功能菜单树！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("添加失败！");
		}
	}

	/**
	 * 删除一个菜单
	 * @param id
	 * @return
	 */
	public Object deleteMenu(@Param("id") int id) {
		try {
			menuService.delete(id);
			return ResultCode.succ("删除成功！请手动刷新左侧功能菜单树！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("删除失败！");
		}
	}

}
