package com.xg.admin.controllers;

import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.dto.nickname.NickNameDto;
import com.xg.admin.service.nickname.INickNameService;
import com.xg.admin.utils.ResultCode;
/**
 * 称号控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class NicknameController extends BaseController {
	@Autowired
	private INickNameService nickNameService;

	/**
	 * 取所有称号下拉列表
	 * 
	 * @param name
	 * @param pageo
	 * @param rows
	 */
	public Object getallnickname(@Param("q") String q) {
		List<NickNameDto> list =  nickNameService.getNickNameDtoByName(q, 0, 10);
		return ResultCode.writeJson(list);
	}
	
	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		nickNameService.initNickName();
		return ResultCode.succ("称号数据刷新成功");
	}
}
