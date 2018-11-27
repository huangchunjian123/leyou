package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.skill.SkillsDto;
import com.xg.admin.service.skill.ISkillService;
import com.xg.admin.utils.ResultCode;

/**
 * 技能控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class SkillController extends BaseController {
	@Autowired
	private ISkillService skillService;


	/**
	 * 取职业技能下拉列表
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getskllsbyJob(@Param("q") String q,@Param("type") int type,@Param("job") int job) {
		List<SkillsDto> list = skillService.getSkillsByTypeJob(q,type, job);
		return ResultCode.writeJson(list);
	}
	
	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		skillService.initSkills();
		return ResultCode.succ("技能数据刷新成功");
	}

}
