package com.xg.admin.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.pojo.Bloodyplace;
import com.xg.admin.service.bloodypalace.IBloodyService;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.HeroService;

/**
 * 闯关控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class BloodyController extends BaseController {
	@Autowired
	private IBloodyService bloodyService;

	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		bloodyService.initData();
		return ResultCode.succ("闯关数据刷新成功");
	}

	/**
	 * 取闯关下拉列表
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getBloodys(@Param("serverId") int serverId) {
		List<Bloodyplace> list = bloodyService.getBloodyplaces();
		Collections.sort(list, new Comparator<Bloodyplace>(){

			@Override
			public int compare(Bloodyplace o1, Bloodyplace o2) {
				if (o1.getId()<o2.getId()) {
					return -1;
				}else 	if (o1.getId()>o2.getId()){
					return 1;
				}
				return 0;
			}});
		return ResultCode.writeJson(list);
	}
	
	public Object perfectBloodyPalace(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroId") String heroId,@Param("bloodyId") int bloodyId) {
		serverId = ResourceUtil.getServerId();
		if (null == heroId || "".equals(heroId)||0==bloodyId) {
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
					int result = heroService.perfectBloodyPalace(heroId,bloodyId);
					if (result == 1) {
						logger.error(String.format("账号【%s】不存在", heroId));
						return ResultCode.error(String.format("账号【%s】不存在", heroId));
					}else if (result == 2) {
						logger.error(String.format("参数错误bloodyId", bloodyId));
						return ResultCode.error(String.format("参数错误bloodyId", bloodyId));
					}
					return ResultCode.succ("操作成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，操作失败");
	}

	public int getBloodyId(@Param("pid") int pid, @Param("serverId") int serverId,
	@Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		if (null == heroId || "".equals(heroId)) {
			return 0;
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return 0;
				} else {
					//(int serverId,int pageSize,int page);
					int result = heroService.getBloodyId(heroId);
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}
