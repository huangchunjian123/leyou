package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dao.data.InstanceDao;
import com.xg.admin.dto.monster.MonsterDto;
import com.xg.admin.service.monster.IMonsterService;
import com.xg.admin.utils.ResultCode;

/**
 * 怪物控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class MonsterController extends BaseController {
	@Autowired
	private IMonsterService monsterService;
	@Autowired
	private InstanceDao instanceDao;

	/**
	 * 取所有怪下拉列表
	 * 
	 * @param name
	 * @param pageo
	 * @param rows
	 */
	public Object getallmonster(@Param("q") String q) {
		String instanceId = inv.getParameter("instanceId");
		if (StringUtils.isEmpty(instanceId)) {
			List<MonsterDto> list = monsterService.getList(q, 0, 10);
			return ResultCode.writeJson(list);
		} else {
			List<MonsterDto> list = monsterService.getList(Integer.valueOf(instanceId), q, 0, 10);
			return ResultCode.writeJson(list);
		}
	}

	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		monsterService.initMonster();
		return ResultCode.succ("怪数据刷新成功");
	}
}
