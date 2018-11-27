package com.game.admin.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.python.google.common.collect.Lists;
import org.python.modules.synchronize;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.game.admin.dto.model.Menu;
import com.game.admin.dto.model.SessionInfo;
import com.game.admin.dto.model.TreeNode;
import com.game.admin.pojo.ServerDto;
import com.game.admin.pojo.system.MenuData;
import com.game.admin.service.menu.IMenuService;
import com.game.admin.service.user.IUserService;
import com.game.admin.utils.Constants;
import com.game.admin.utils.ExceptionUtil;
import com.game.admin.utils.HttpclientUtil;
import com.game.admin.utils.OperateLogInfos;
import com.game.admin.utils.ResultCode;
import com.google.gson.Gson;

import net.paoding.rose.web.annotation.Param;

/**
 * 
 * @author huangchunjian
 *
   huangchunjian1741@dingtalk.com
 */
public class MenuController extends BaseController {

	private static final Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;

	/**
	 * 首页获得菜单树
	 */
	public Object ctrlTree() {
		
		SessionInfo session = (SessionInfo) inv.getRequest().getSession().getAttribute(Constants.SESSIONKEY);
		List<TreeNode> list = menuService.authMenu(session);
		//获得对应的服务器列表
		List<TreeNode> tree = new ArrayList<TreeNode>();
		
//		MenuData t = new MenuData(1, "m1", 1, "02", "http://localhost:");
//		t.setId(1);
		
		String serverdtas = HttpclientUtil.get("http://47.98.247.73/lxmobile/dist/server_test_http.txt");
		String[] serverArr= serverdtas.split("\r\n");
		List<ServerDto> serlist = Lists.newArrayList();
		for (int i = 0; i < serverArr.length; i++) {
			ServerDto serverDto = JSON.parseObject(serverArr[i], ServerDto.class);
		
			MenuData t = new MenuData(i, serverDto.getName(), i, "01", "http://"+serverDto.getHost() +":"+serverDto.getPort());
			TreeNode tn = new TreeNode(t);
			tn.setId(i);
			tn.setText(serverDto.getName());
			tn.setState("open");
			tn.setChecked(true);
			tree.add(tn);
//			System.err.println(tn.getAttributes().get("url"));
		}
		
//		TreeNode tn = new TreeNode();
//		tn.setId(1);
//		tn.setText("服1");
//		tn.setState("open");
//		tn.setChecked(true);
//		
//		TreeNode tn2 = new TreeNode();
//		tn2.setId(2);
//		tn2.setText("服2");
//		tn2.setState("open");
//		tn2.setChecked(true);
		
//		tree.add(tn);
//		tree.add(tn2);
		return ResultCode.writeJson(tree);
	}
	
	
	public static void main(String[] args) {
		ServerDto serverDto = new ServerDto();
		serverDto.setHost("1");
		serverDto.setName("2");
		serverDto.setPort("3");
//		System.err.println(JSON.toJSONString(serverDto));
//		System.err.println(JSON.parseObject(JSON.toJSONString(serverDto),ServerDto.class));
	}
	public Object ctrlTreeChilds(@Param("id") String id,@Param("url") String url) {
		
//		SessionInfo session = (SessionInfo) inv.getRequest().getSession().getAttribute(Constants.SESSIONKEY);
//		List<TreeNode> list = menuService.authMenu(session);
		//获得对应的服务器列表
		
//		System.err.println(url);
		
		List<TreeNode> tree = new ArrayList<TreeNode>();
 		String gsonString = HttpclientUtil.get(url+"/gm/xdbtables/getbbtables");
		
        if (gsonString == null || "".equals(gsonString)) {
        	return ResultCode.error("服务器未启动");  
        }
        
        Gson gson = new Gson();
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
		 try {
				map = gson.fromJson(gsonString, map.getClass());
		} catch (Exception e) {
			return ResultCode.error("服务器未处理此动作");  
		}
	
		
		 if (map == null ) {
			 return ResultCode.error("服务器未处理此动作");  
	        }
		List<String> tablenames = (List<String>)map.get("result");
		Collections.sort(tablenames );
		int mid=1;
		for (Iterator iterator = tablenames.iterator(); iterator.hasNext();) {
			String tname = (String) iterator.next();
			MenuData t = new MenuData(mid, tname, mid, "02", "xdbData/goDatas?tablename="+tname
					+"&surl="+com.game.admin.utils.Escape.escape(url));
			t.setId(mid);
			TreeNode tn = new TreeNode(t);
			tn.setId(mid);
			tn.setText(id+"_t_"+tname);
			tn.setState("open");
			tn.setChecked(true);
			tree.add(tn);
//			System.err.println(tn.getAttributes().get("url"));
			mid++;
		}
		
		 return JSON.toJSON(tree);
//		return ResultCode.writeJson(tree);
	}

	/**
	 * 跳转到菜单管理页面
	 * @return
	 */
	public String goMenu() {
		return "system/menu";
	}

	/**
	 * 获得菜单treegrid
	 */
	public Object treegrid() {
		List<Menu> list = menuService.treegrid("");
		return ResultCode.writeJson(list);
	}

	/**
	 * 显示权限树
	 * @param roleId
	 * @return
	 */
	public Object authtree(@Param("roleId") int roleId) {
		if (roleId == 0) {
			roleId = 1;
		}
		List<TreeNode> list = menuService.authtreeList(roleId);
		return ResultCode.writeJson(list);
	}

	/**
	 * 编辑菜单
	 * @param id
	 * @param pid
	 * @param name
	 * @param sort
	 * @param type
	 * @param url
	 * @return
	 */
	public Object edit(@Param("id") int id, @Param("pid") int pid, @Param("name") String name,
			@Param("sort") int sort, @Param("type") String type, @Param("url") String url) {
		try {
			menuService.edit(id, pid, name, sort, type, url);
			String TYPEN= type.equals("01")? "菜单" : "按钮";
			recordOperateLog(String.format(OperateLogInfos.MenuControllerEdit,id,name,sort,TYPEN,url
					,getSessionInfo().getIp()), 1);
			return ResultCode.succ("编辑成功!请手动刷新左侧功能菜单树！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("编辑失败！");
		}
	}

	/**
	 * 添加菜单
	 * @param pid
	 * @param name
	 * @param sort
	 * @param type
	 * @param url
	 * @return
	 */
	public Object add(@Param("pid") int pid, @Param("name") String name, @Param("sort") int sort,
			@Param("type") String type, @Param("url") String url) {
		try {
			menuService.add(pid, name, sort, type, url);
			String TYPEN= type.equals("01")? "菜单" : "按钮";
			recordOperateLog(String.format(OperateLogInfos.MenuControllerAdd,name,sort,TYPEN,url,getSessionInfo().getIp()), 1);
			return ResultCode.succ("添加成功!请手动刷新左侧功能菜单树！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("添加失败！");
		}
	}

	/**
	 * 删除一个菜单
	 * @param id
	 * @return
	 */
	public Object deleteMenu(@Param("id") int id) {
		try {
			menuService.delete(id);
			recordOperateLog(String.format(OperateLogInfos.MenuControllerDeleteMenu,id,getSessionInfo().getIp()), 1);
			return ResultCode.succ("删除成功！请手动刷新左侧功能菜单树！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			return ResultCode.error("删除失败！");
		}
	}

}
