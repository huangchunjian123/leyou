package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.gemstone.GemstoneDto;
import com.xg.admin.service.gemstone.IGemstoneService;
import com.xg.admin.utils.ResultCode;

/**
 * 宝石控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class GemstoneController extends BaseController {
	@Autowired
	private IGemstoneService gemstoneService;

	/**
	 * 取所有宝石下拉列表
	 * 
	 * @param name
	 * @param pageo
	 * @param rows
	 */
	public Object getallgemstone(@Param("q") String q) {
		List<GemstoneDto> list = gemstoneService.getGemstoneDtoByname(q, 0, 10);
		return ResultCode.writeJson(list);
	}
	
	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		gemstoneService.initGemstone();
		return ResultCode.succ("宝石数据刷新成功");
	}
}
