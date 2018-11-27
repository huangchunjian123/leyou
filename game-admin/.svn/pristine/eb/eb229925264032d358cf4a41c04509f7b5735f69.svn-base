package com.xg.admin.controllers;

import net.paoding.rose.web.annotation.Param;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.DataGrid;
import com.xg.game.api.hessian.HeroService;
import com.xg.game.api.hessian.dto.AddHeroDto;
import com.xg.game.api.hessian.dto.CloneHeroDto;

/**
 * 账号控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class HerosController extends BaseController {

	/**
	 * 跳转至任务列表页面
	 * goList
	 * @return   
	 * String  
	 * @exception
	 */
	public String goHeros() {
		return "heros/list";
	}

	public Object queryHeroList2(@Param("serverId") int serverId, @Param("page") int page, @Param("rows") int rows,
			@Param("qName") String qName) {
		if (StringUtils.isEmpty(qName)) {
			return ResultCode.writeJson(new DataGrid());
		}
		String url = buildUrl(serverId, "/game-server/api/service/accounthero");
		if ("".equals(url)) {
			return ResultCode.writeJson(new DataGrid());
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					//(int serverId,int pageSize,int page);
					DataGrid result = heroService.getHeroListDto(serverId, rows, page, qName);
					if (null == result) {
						logger.error(String.format("查找%d服玩家账号失败", serverId));
						return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
					}
					return ResultCode.writeJson(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
	
	/**
	 * 查找玩家账号
	 * @param loginName
	 * @param serverId
	 * @return
	 */
	public Object queryHeroList(@Param("serverId") int serverId, @Param("page") int page, @Param("rows") int rows,
			@Param("qName") String qName) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/accounthero");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					//(int serverId,int pageSize,int page);
					DataGrid result = heroService.getHeroListDto(serverId, rows, page, qName);
					if (null == result) {
						logger.error(String.format("查找%d服玩家账号失败", serverId));
						return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
					}
					return ResultCode.writeJson(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}

	public Object addHero(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroInfoJson") String heroInfoJson) {
		serverId = ResourceUtil.getServerId();
		AddHeroDto heroData = JSON.parseObject(heroInfoJson, AddHeroDto.class);
		heroData.setPid(pid);
		heroData.setServerId(serverId);

		String loginname = heroData.getLoginName();
		String loginPsd = heroData.getLoginPsd();
		String heroname = heroData.getHeroName();
		int teamgrade = heroData.getTeamGrade();
		int vipgrade = heroData.getVipGrade();
		if (null == loginname || "".equals(loginname)) {
			return ResultCode.error("参数错误loginname");
		}
		if (null == loginPsd || "".equals(loginPsd)) {
			return ResultCode.error("参数错误loginPsd");
		}
		if (null == heroname || "".equals(heroname)) {
			return ResultCode.error("参数错误heroname");
		}
		if (teamgrade < 0 || vipgrade < 0) {
			return ResultCode.error("参数错误vipgrade or teamgrade");
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return ResultCode.error("不存在的api");
				} else {
					//(int serverId,int pageSize,int page);
					int result = heroService.addHero(heroData);
					if (result == 1) {
						logger.error(String.format("创建的账号失败，账号名【%s】已经存在", loginname));
						return ResultCode.error(String.format("创建的账号失败，账号名【%s】已经存在", loginname));
					}
					return ResultCode.succ("创建的账号成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，创建账号失败");
	}
	
	public Object cloneHero(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroInfoJson") String heroInfoJson) {
		serverId = ResourceUtil.getServerId();
		CloneHeroDto heroData = JSON.parseObject(heroInfoJson, CloneHeroDto.class);
		heroData.setPid(pid);
		heroData.setServerId(serverId);
		String loginname = heroData.getLoginName();
		String heroname = heroData.getHeroName();
		if (null == heroData.getCloneHeroId() || "".equals(heroData.getCloneHeroId())) {
			return ResultCode.error("参数错误:cloneheroId");
		}
		if (null == loginname || "".equals(loginname)) {
			return ResultCode.error("参数错误");
		}
		if (null == heroname || "".equals(heroname)) {
			return ResultCode.error("参数错误");
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return ResultCode.error("不存在的api");
				} else {
					//(int serverId,int pageSize,int page);
					int result = heroService.cloneHero(heroData);
					if (result == 1) {
						logger.error(String.format("克隆的账号失败 克隆对象【%s】不存在", heroInfoJson));
						return ResultCode.error(String.format("克隆的账号失败，克隆对象【%s】不存在", heroInfoJson));
					}else if (result == 2) {
						logger.error(String.format("克隆的账号失败，账号名【%s】已经存在", loginname));
						return ResultCode.error(String.format("克隆的账号失败，账号名【%s】已经存在", loginname));
					}else if (result == 3) {
						return ResultCode.error(String.format("克隆功能被屏蔽"));
					}
					return ResultCode.succ("克隆的账号成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，克隆账号失败");
	}

	public Object delHero(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		if (null == heroId || "".equals(heroId)) {
			return ResultCode.error("参数错误heroId");
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return ResultCode.error("不存在的api");
				} else {
					//(int serverId,int pageSize,int page);
					int result = heroService.delHero(heroId);
					if (result == 1) {
						logger.error(String.format("删除的账号失败，账号【%s】不存在", heroId));
						return ResultCode.error(String.format("删除的账号失败，账号【%s】不存在", heroId));
					}
					return ResultCode.succ("删除的账号成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，删除账号失败");
	}
	public Object editHero(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroId") String heroId,@Param("heroInfoJson") String heroInfoJson) {
		serverId = ResourceUtil.getServerId();
		if (null == heroId || "".equals(heroId)) {
			return ResultCode.error("参数错误heroId");
		}
		AddHeroDto heroData = JSON.parseObject(heroInfoJson, AddHeroDto.class);
		heroData.setPid(pid);
		heroData.setServerId(serverId);
		heroData.setHeroId(heroId);
		int teamgrade = heroData.getTeamGrade();
		int vipgrade = heroData.getVipGrade();
		if (teamgrade < 0 || vipgrade < 0) {
			return ResultCode.error("参数错误vipgrade or teamgrade");
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return ResultCode.error("不存在的api");
				} else {
					//(int serverId,int pageSize,int page);
					int result = heroService.editHero(heroData);
					if (result == 1) {
						logger.error(String.format("编辑的账号失败，账号【%s】不存在", heroId));
						return ResultCode.error(String.format("编辑的账号失败，账号【%s】不存在", heroId));
					}
					return ResultCode.succ("编辑的账号成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，删除账号失败");
	}
	

}
