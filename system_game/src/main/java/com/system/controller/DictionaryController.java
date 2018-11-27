package com.system.controller;

import com.system.bean.Dictionary;
import com.system.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzy
 * @Date 2018/7/27 上午10:44
 */
@Api(description = "字典操作接口")
@RestController
@RequestMapping("/dic")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取字典列表",notes = "获取字典分页列表")
    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "分页行数", required = false)
    public String getPage(@PathVariable Integer page, Integer pageSize){
        return dictionaryService.getPage(page,pageSize);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加字典",notes = "添加字典")
    public String add(Dictionary dictionary){
        return dictionaryService.add(dictionary);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新字典",notes = "更新字典")
    public String update(Dictionary dictionary){
        return dictionaryService.update(dictionary);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取字典",notes = "根据id获取字典")
    @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "字典主键", required = true)
    public String getDictionary(@PathVariable Integer id){
        return dictionaryService.getDictionary(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除字典",notes = "删除字典")
    @ApiImplicitParam(paramType = "query", dataType = "String", name = "ids", value = "id数组", required = true)
    public String delete(Integer [] ids){
        return dictionaryService.delete(ids);
    }

    @RequestMapping(value = "/check/code", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "检查code是否可用",notes = "检查code是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", value = "code标识", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "字典主键", required = false)
    })
    public String checkCode(String code, Integer id){

        return dictionaryService.checkCode(code, id);
    }


    @RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取名称列表（值）",notes = "获取名称列表（值）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", value = "code标识", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "value", value = "值", required = true)
    })
    public String getName(String code,String value){
        return dictionaryService.getName(code,value);
    }


    @RequestMapping(value = "/check/value", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "检查value是否可用",notes = "检查value是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "value", value = "值", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "parentId", value = "父级主键", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "字典主键", required = false)
    })
    public String checkValue(String value, Integer parentId, Integer id){

        return dictionaryService.checkValue(value,parentId,id);
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取字典列表",notes = "根据code获取字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "code", name = "value", value = "标识码", required = true)
    })
    public String getDicListByCode(String code){
        return dictionaryService.getDicListByCode(code);
    }
}
