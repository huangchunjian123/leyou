package com.system.controller;

import com.system.bean.Admin;
import com.system.common.Const;
import com.system.service.ILogService;
import com.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * aili
 * @author zzy
 */
@Api(description = "日志操作接口")
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ILogService logService;

    @RequestMapping(value = "/login/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看登录日志",notes = "查看登录日志")
    public String getLogin(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_ACTION,Const.EVENT_TYPE_ROLE_LOGIN,sort);
    }

    @RequestMapping(value = "/createrole/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看创角日志",notes = "查看创角日志")
    public String getCreateRole(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_ACTION,Const.EVENT_TYPE_CREATE_ROLE,sort);
    }

    @RequestMapping(value = "/rolelogout/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "角色登出日志",notes = "角色登出日志")
    public String getRoleLogout(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_ACTION,Const.EVENT_TYPE_ROLE_LOGOUT,sort);
    }

    @RequestMapping(value = "/onlineuser/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "在线人数日志",notes = "在线人数日志")
    public String getOnlineUser(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_ONLINE_USER,sort);
    }

    @RequestMapping(value = "/levelup/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "角色升级日志",notes = "角色升级日志")
    public String getLevelUp(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_LEVELUP,sort);
    }

    @RequestMapping(value = "/addcash/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "角色充值日志",notes = "角色充值日志")
    public String getAddCash(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_ACTION,Const.EVENT_TYPE_ADDCASH,sort);
    }

    @RequestMapping(value = "/addyuanbao/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "货币增加日志",notes = "货币增加日志")
    public String getAddYuanBao(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_ACTION,Const.EVENT_TYPE_ADD_YUANBAO,sort);
    }

    @RequestMapping(value = "/costyuanbao/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "货币消耗日志",notes = "货币消耗日志")
    public String getCostYuanBao(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_ACTION,Const.EVENT_TYPE_COST_YUANBAO,sort);
    }

    @RequestMapping(value = "/shoptrade/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "游戏商城销售日志",notes = "游戏商城销售日志")
    public String getShopTrade(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_SHOP_TRADE,sort);
    }

    @RequestMapping(value = "/gainitem/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "物品获得日志",notes = "物品获得日志")
    public String getGainItem(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_GAIN_ITEM,sort);
    }

    @RequestMapping(value = "/loseitem/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "物品消耗日志",notes = "物品消耗日志")
    public String getLoseItem(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_LOSE_ITEM,sort);
    }

    @RequestMapping(value = "/chardata/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "角色信息快照日志",notes = "角色信息快照日志")
    public String getCharData(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_CHAR_DATA,sort);
    }

    @RequestMapping(value = "/starttask/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "领取任务日志",notes = "领取任务日志")
    public String getStartTask(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_START_TASK,sort);
    }

    @RequestMapping(value = "/endtask/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "结束任务日志",notes = "结束任务日志")
    public String getEndTask(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_END_TASK,sort);
    }

    @RequestMapping(value = "/startarena/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "开启竞技场日志",notes = "开启竞技场日志")
    public String getStartArena(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_START_ARENA,sort);
    }

    @RequestMapping(value = "/endtarena/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "结束竞技场日志",notes = "结束竞技场日志")
    public String getEndArena(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_END_ARENA,sort);
    }

    @RequestMapping(value = "/chatlog/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "聊天日志",notes = "聊天日志")
    public String getChatLog(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_CHAT_LOG,sort);
    }

    @RequestMapping(value = "/rank/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "排行榜",notes = "排行榜")
    public String getRank(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_RANK,sort);
    }

    @RequestMapping(value = "/jiazurank/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "家族排行榜",notes = "家族排行榜")
    public String getJiaZuRank(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_JIAZU_RANK,sort);
    }

    @RequestMapping(value = "/exchange/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "抽卡兑换",notes = "抽卡兑换")
    public String getExchange(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_EXCHANGE,sort);
    }

    @RequestMapping(value = "/recruit/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "抽卡",notes = "抽卡")
    public String getRecruit(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_RECRUIT,sort);
    }

    @RequestMapping(value = "/welfare/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看创角日志",notes = "查看创角日志")
    public String getWelfare(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_TYPE_WELFARE,sort);
    }

    @RequestMapping(value = "/startinstance/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看开户副本日志",notes = "查看开户副本日志")
    public String getStartInstance(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_START_INSTANCE,sort);
    }

    @RequestMapping(value = "/endinstance/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看结束副本日志",notes = "查看结束副本日志")
    public String getEndInstance(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,Const.LOG_TYPE_PROGRAM,Const.EVENT_END_INSTANCE,sort);
    }

    /*@RequestMapping(value = "/person", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看个人日志",notes = "查看个人日志")
    public String getPerson(String service,String startDate,String endDate,String roleId,String roleName,String userId,String account){
        return logService.findPerson(service,startDate,endDate,roleId,roleName,userId,account,null,null);
    }*/

    @RequestMapping(value = "/person/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看个人日志",notes = "查看个人日志")
    public String getPerson(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,null,null,sort);
    }


    @RequestMapping(value = "/persons/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看个人日志",notes = "查看个人日志")
    public String getPersons(@PathVariable("page") int page, Integer pageSize,String service,String startDate,String endDate,String roleId,String roleName,String userId,String account,String sort){
        return logService.findAll(page,pageSize,service,startDate,endDate,roleId,roleName,userId,account,null,null,sort);
    }

}
