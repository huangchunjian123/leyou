package com.xg.admin.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.python.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.dto.instance.InstanceDto;
import com.xg.admin.service.instance.IInstanceService;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.HeroService;

/**
 * 副本控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class InstanceController extends BaseController {
	@Autowired
	private IInstanceService instanceService;

	/**
	 * 取所有副本下拉列表
	 * @param q
	 * @return
	 */
	public Object getallinstance(@Param("q") String q) {
		List<InstanceDto> list = instanceService.getByName(q, 0, 10);
		return ResultCode.writeJson(list);
	}

	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		instanceService.initInstance();
		return ResultCode.succ("副本数据刷新成功");
	}
	
	/**
	 *  所有剧情副本
	 * @param q
	 * @return
	 */
	public Object getJuQing_allinstance(@Param("q") String q) {
		List<InstanceDto> list = instanceService.getList_juqing_instance();
		Collections.sort(list, new Comparator<InstanceDto>(){

			@Override
			public int compare(InstanceDto o1, InstanceDto o2) {
				if (o1.getId()<o2.getId()) {
					return -1;
				} else if (o1.getId()>o2.getId()) {
					return 1;
				} 
				return 0;
			}});
		return ResultCode.writeJson(list);
	}
	
	/**
	 所有完成的剧情副本
	 * @param q
	 * @return
	 */
	public Object getComplete_JuQing_allinstance(@Param("pid") int pid, @Param("serverId") int serverId,
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
					List<Integer> result = heroService.getCompletedInstances(heroId);
					List<InstanceDto> list = Lists.newArrayList();
					for (Iterator<Integer> iterator = result.iterator(); iterator.hasNext();) {
						Integer instanceId = iterator.next();
						list.add(new InstanceDto(instanceId, instanceService.get(instanceId).getName(), 0));
					}
					
					Collections.sort(list, new Comparator<InstanceDto>(){

						@Override
						public int compare(InstanceDto o1, InstanceDto o2) {
							if (o1.getId()<o2.getId()) {
								return -1;
							} else if (o1.getId()>o2.getId()) {
								return 1;
							} 
							return 0;
						}});
					
					return ResultCode.writeJson(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，操作失败");
	}
	
	public Object perfectInstances(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroId") String heroId,@Param("mainInstanceId") int mainInstanceId) {
		/*serverId = ResourceUtil.getServerId();*/
		if (null == heroId || "".equals(heroId)||0==mainInstanceId) {
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
					int result = heroService.perfectInstance(heroId,mainInstanceId);
					if (result == 1) {
						logger.error(String.format("账号【%s】不存在", heroId));
						return ResultCode.error(String.format("账号【%s】不存在", heroId));
					}
					return ResultCode.succ("操作成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，操作失败");
	}
}
