package com.xg.admin.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.apache.commons.lang3.StringUtils;
import org.python.google.common.base.Splitter;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.DataGrid;
import com.xg.game.api.hessian.RoleService;
import com.xg.game.api.hessian.dto.RoleDto;

/**
 * 账号控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class RolesJobController extends BaseController {

	/**
	 * 跳转至任务列表页面
	 * goList
	 * @return   
	 * String  
	 * @exception
	 */
	public String goRoles() {
		return "roles/list";
	}

	/**
	 * 查找玩家账号
	 * @param loginName
	 * @param serverId
	 * @return
	 */
	/**
	 * @param serverId
	 * @param heroId
	 * @return
	 */
	public Object queryRoleList(@Param("serverId") int serverId, @Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/roles");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);
		{
			try {
				RoleService roleService = (RoleService) factory.create(RoleService.class, url);
				if (null == roleService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
				} else {

					List<RoleDto> result = roleService.queryRoleList(heroId);
					if (null == result || result.isEmpty()) {
						logger.error(String.format("查找%s服玩家账号失败", heroId));
						return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
					}

					DataGrid dataGrid = new DataGrid();
					dataGrid.setRows(result);
					dataGrid.setTotal(result.size());
					return ResultCode.writeJson(dataGrid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
	}

	public Object takeOnAllJustEquips(@Param("serverId") int serverId, @Param("heroId") String heroId,
			@Param("rolechose") String rolechose, @Param("roleselect") String roleselect,
			@Param("isequipstrength") String isequipstrength, @Param("equipstrengthgrade") String equipstrengthgrade) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/roles");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);
		{
			try {
				RoleService roleService = (RoleService) factory.create(RoleService.class, url);
				if (null == roleService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
				} else {
					List<Integer> jobList = new ArrayList<>();
					Iterable<String> jobArr = Splitter.onPattern(",").split(rolechose);
					for (Iterator iterator = jobArr.iterator(); iterator.hasNext();) {
						String job = (String) iterator.next();
						jobList.add(Integer.parseInt(job));
					}
					boolean isUseDefault = roleselect == null || "".equals(roleselect) || "0".equals(roleselect) ? Boolean.TRUE
							: Boolean.FALSE;
					boolean isStrengthen = isequipstrength == null || "".equals(isequipstrength)
							|| "0".equals(isequipstrength) ? Boolean.FALSE : Boolean.TRUE;
					int strengthenGade = equipstrengthgrade == null || "".equals(equipstrengthgrade) ? 0 : Integer
							.parseInt(equipstrengthgrade);

					boolean isSucss = roleService.takeOnAllJustEquips(heroId, jobList, isUseDefault, isStrengthen,
							strengthenGade);
					if (!isSucss) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("操作失败%s", heroId));
					}
					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
	}

	public Object editRole(@Param("serverId") int serverId, @Param("heroId") String heroId, @Param("job") int job,
			@Param("roleGrade") int roleGrade, @Param("nowExp") int nowExp, @Param("hunqiId1") int hunqiId1,
			@Param("hunqiGrade1") int hunqiGrade1, @Param("hunqiId2") int hunqiId2,
			@Param("hunqiGrade2") int hunqiGrade2, @Param("hunqiId3") int hunqiId3,
			@Param("hunqiGrade3") int hunqiGrade3, @Param("zhanhunGrade") int zhanhunGrade,
			@Param("zhanhunQuality") int zhanhunQuality, @Param("zhanhunSubQuality") int zhanhunSubQuality) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/roles");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);
		{
			try {
				RoleService roleService = (RoleService) factory.create(RoleService.class, url);
				if (null == roleService) {
					logger.error("api为空");
					return ResultCode.error(String.format("api为空"));
				} else {
					int isSucss = roleService.editRole(heroId, job, roleGrade, nowExp, hunqiId1, hunqiGrade1, hunqiId2,
							hunqiGrade2, hunqiId3, hunqiGrade3, zhanhunGrade, zhanhunQuality, zhanhunSubQuality,
							Boolean.FALSE);
					if (isSucss == 1 || isSucss == 2) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("数据不存在"));
					}
					if (isSucss == -1) {
						return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
					}

					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
	}

	/**
	 * 
	 * @param serverId
	 * @param heroId
	 * @param job
	 * @param srewards  主动技能列表
	 * @param equip_srewards 装备技能列表
	 * @return
	 */
	public Object editskill(@Param("serverId") int serverId, @Param("heroId") String heroId, @Param("job") int job,
			@Param("srewards") String srewards, @Param("equip_srewards") String equip_srewards) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/roles");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);
		{
			try {
				RoleService roleService = (RoleService) factory.create(RoleService.class, url);
				if (null == roleService) {
					logger.error("api为空");
					return ResultCode.error(String.format("api为空"));
				} else {

					if (StringUtils.isEmpty(srewards) || job <= 0 || StringUtils.isEmpty(heroId)) {
						return ResultCode.error(String.format("参数错误"));
					}

					int isSucss = roleService.editskill(heroId, job, srewards, equip_srewards);
					if (isSucss == 1 || isSucss == 2) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("数据不存在"));
					}
					if (isSucss == -1) {
						return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
					}

					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%s服玩家账号失败", heroId));
	}
}
