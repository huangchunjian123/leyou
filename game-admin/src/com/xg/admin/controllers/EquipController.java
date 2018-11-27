package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.equip.EquipDto;
import com.xg.admin.service.equip.IEquipService;
import com.xg.admin.utils.ResultCode;
/**
 * 装备控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class EquipController extends BaseController {
	@Autowired
	private IEquipService equipService;

	/**
	 * 取所有装备下拉列表
	 * 
	 * @param name
	 * @param pageo
	 * @param rows
	 */
	public Object getallequip(@Param("q") String q) {
		List<EquipDto> list =  equipService.getEquipDtoByname(q, 0, 10);
		return ResultCode.writeJson(list);
	}
	
	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		equipService.initEquip();
		return ResultCode.succ("装备数据刷新成功");
	}
}
