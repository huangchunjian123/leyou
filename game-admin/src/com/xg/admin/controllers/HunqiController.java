package com.xg.admin.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.hunqi.HunqiDto;
import com.xg.admin.service.hunqi.IHunqiService;
import com.xg.admin.utils.ResultCode;

/**
 * 魂器控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class HunqiController extends BaseController {
	@Autowired
	private IHunqiService hunqiService;


	/**
	 * 取职业技能下拉列表
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getHunqibyJob(@Param("job") int job) {
		List<HunqiDto> list = hunqiService.getHunqisByTypeJob( job);
		Collections.sort(list, new Comparator<HunqiDto>(){

			@Override
			public int compare(HunqiDto o1, HunqiDto o2) {
				if (o1.getId()<o2.getId()) {
					return -1;
				}else 	if (o1.getId()>o2.getId()){
					return 1;
				}
				return 0;
			}});
		return ResultCode.writeJson(list);
	}
	
	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		hunqiService.initData();
		return ResultCode.succ("魂器数据刷新成功");
	}

}
