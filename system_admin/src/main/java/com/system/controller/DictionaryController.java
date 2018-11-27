package com.system.controller;

import com.system.bean.Dictionary;
import com.system.common.Const;
import com.system.common.util.Ret;
import com.system.common.util.StrUtil;
import com.system.service.IDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/27 上午10:44
 */
@Api(description = "字典操作接口")
@RestController
@RequestMapping("/dic")
public class DictionaryController {
    @Autowired
    IDictionaryService dictionaryService;

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取字典列表",notes = "获取字典分页列表")
    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "分页行数", required = false)
    public String getPage(@PathVariable Integer page, Integer pageSize){
        if(null == pageSize){
            pageSize = Const.PAGE_SIZE;
        }
        return Ret.msgSuccess(dictionaryService.getPage(page,pageSize));
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取名称列表（值）",notes = "获取名称列表（值）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", value = "code标识", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "value", value = "值", required = true)
    })
    public String getName(String code,String value){
        if(null == code || null == value){
            return Ret.msgSetVal("参数code和value不能为空");
        }
        return Ret.msgSuccess(dictionaryService.getName(code,value));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加字典",notes = "添加字典")
    public String add(@RequestBody Dictionary dictionary){
        if (null == dictionary.getDicParentId()) {
            if (null != dictionaryService.getByCode(dictionary.getDicCode(),null)) {
                return Ret.msgSetVal(dictionary.getDicCode()+"已存在");
            }
        } else {
            if (null != dictionaryService.getByValue(dictionary.getDicValue(),dictionary.getDicParentId(),null)){
                return Ret.msgSetVal(dictionary.getDicValue()+"已存在");
            }
        }


        if(dictionaryService.save(dictionary)){
            return Ret.msgAdd();
        }

        return Ret.msgAddFail();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新字典",notes = "更新字典")
    public String update(@RequestBody Dictionary dictionary){
        if (null == dictionary.getDicParentId()) {
            if(null != dictionaryService.getByCode(dictionary.getDicCode(),dictionary.getId())){
                return Ret.msgSetVal(dictionary.getDicCode()+"已存在");
            }
        } else {
            if(null != dictionaryService.getByValue(dictionary.getDicValue(),dictionary.getDicParentId(),dictionary.getId())){
                return Ret.msgSetVal(dictionary.getDicValue()+"已存在");
            }
        }

        if(dictionaryService.update(dictionary)){
            return Ret.msgUpdate();
        }

        return Ret.msgUpdateFail();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取字典",notes = "根据id获取字典")
    @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "字典主键", required = true)
    public String getDictionary(@PathVariable Integer id){
        return Ret.msgSuccess(dictionaryService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除字典",notes = "删除字典")
    @ApiImplicitParam(paramType = "query", dataType = "String", name = "ids", value = "id数组", required = true)
    public String delete(Integer [] ids){
        if (dictionaryService.delete(ids)){
            return Ret.msgDel();
        }
        return Ret.msgDelFail();
    }

    @RequestMapping(value = "/check/code", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "检查code是否可用",notes = "检查code是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", value = "code标识", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "字典主键", required = false)
    })
    public String checkCode(String code, Integer id){
        Map<Object, Object> result = new HashMap<>();

        if (StrUtil.isBlank(code)) {
            return Ret.msgSetVal("参数code不能为空");
        }

        if (null != dictionaryService.getByCode(code, id)) {
            result.put("result",false);
            return Ret.msgSuccess(code+"已存在",result);
        }
        result.put("result",true);
        return Ret.msgSuccess(result);
    }

    @RequestMapping(value = "/check/value", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "检查value是否可用",notes = "检查value是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "value", value = "值", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "parentId", value = "父级主键", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "字典主键", required = false)
    })
    public String checkValue(String value, Integer parentId, Integer id){
        Map<Object, Object> result = new HashMap<>();
        if (StrUtil.isBlank(value)) {
            return Ret.msgSetVal("参数value不能为空");
        }
        if(null != dictionaryService.getByValue(value,parentId,id)){
            result.put("result",false);
            return Ret.msgSuccess(value+"已存在",result);
        }
        result.put("result",true);
        return Ret.msgSuccess(result);
    }
}
