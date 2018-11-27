package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.npc.NpcDto;
import com.xg.admin.service.npc.INpcService;
import com.xg.admin.utils.ResultCode;
/**
 * 称号控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class TitleController extends BaseController {
	@Autowired
	private INpcService npcService;
	
	/**
	 * 取所有npc下拉列表
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getallnpcs(@Param("q") String q) {
		List<NpcDto> list = npcService.getNpcDtoByname(q, 0, 10);
		return ResultCode.writeJson(list);
	}
	
	/**
	 * 刷新称号内存数据
	 * @return
	 */
	public Object refresh() {
		npcService.initNpcs();
		return ResultCode.succ("称号数据刷新成功");
	}
}
