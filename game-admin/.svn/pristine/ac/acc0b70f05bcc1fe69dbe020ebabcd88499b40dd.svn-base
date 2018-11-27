package com.xg.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.paoding.rose.web.annotation.Param;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.dto.good.GoodsDto;
import com.xg.admin.dto.model.NormalGood;
import com.xg.admin.pojo.Goods;
import com.xg.admin.service.good.IGoodService;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.DataGrid;
import com.xg.game.api.hessian.GoodService;
import com.xg.game.api.hessian.MailService;
import com.xg.game.api.hessian.dto.MailDto;

/**
 * 道具控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class GoodController extends BaseController {
	@Autowired
	private IGoodService goodService;

	// 存放临时配置的奖励
	private static final Map<String, String> rewardmap = new ConcurrentHashMap<String, String>();

	/**
	 * 取下拉列表所有道具
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getgoods(@Param("q") String q) {
		List<GoodsDto> list = goodService.getGoodsByname(q, 0, 10);
		return ResultCode.writeJson(list);
	}

	/**
	 * 取下拉列表所有道具
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getnotgoods() {
		List<GoodsDto> list = new ArrayList<GoodsDto>();
		list.add(new GoodsDto(NormalGood.COPPER));
		list.add(new GoodsDto(NormalGood.GOLD));
		return ResultCode.writeJson(list);
	}

	/**
	 * 取所有道具下拉列表
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getallgoods(@Param("q") String q) {
		List<GoodsDto> list = goodService.getGoodsDtoByname(q, 0, 10);
		return ResultCode.writeJson(list);
	}

	/**
	 * 发道具
	 * @return
	 */
	public String goSendGoods() {
		return "good/sendGoods";
	}
	
	public String goBag() {
		return "good/bag";
	}
	public String goBag2() {
		return "good/bag2";
	}
	/**
	 * 给玩家发送道具
	 * @param serverIds
	 * @param playerId
	 * @return
	 */
	public Object sendGoods(@Param("serverIds") String serverIds, @Param("playerId") String accountId,@Param("gold") int gold,
			@Param("yuanbao") int yuanbao , @Param("emailtitle") String emailTile , @Param("emailcontent") String emailContent) {
		String srewards = inv.getParameter("srewards");
		StringBuffer sendGoods = new StringBuffer();
		if (StringUtils.isNotBlank(srewards)) {
			String[] goodstr = srewards.split(",");// 数据格式 包裹类型:id:数量
			for (String goods : goodstr) {
				String[] rew = goods.split(":");
				sendGoods.append(Integer.valueOf(rew[1])).append("*").append(Integer.valueOf(rew[2])).append("*")
				.append(Integer.valueOf(rew[0]));	
				if (Integer.valueOf(rew[0]) == 1){
					sendGoods.append("*").append(Integer.valueOf(rew[3])).append("*").append(Integer.valueOf(rew[4]));
				}
				sendGoods.append(";");
			}
		}
		MailDto paramMailDto = new MailDto();
		List<String> accountIds = new ArrayList<String>();
		accountIds.add(accountId);
		paramMailDto.setAccountIds(accountIds);
		paramMailDto.setTitle(emailTile);
		paramMailDto.setContent(emailContent);
		paramMailDto.setGoods(sendGoods.toString());
		paramMailDto.setYuanbao(yuanbao);
		paramMailDto.setGold(gold);
		for (String serverId : serverIds.split(",")) {
			//			ServerData server = serverService.getByServerId(Integer.valueOf(serverId));
			//			String url = "http://" + server.getServerIp() + ":" + server.getServerPort()
			//					+ "/game-server/api/service/mail";
			String url = buildUrl(Integer.valueOf(serverId), "/game-server/api/service/mail");
			HessianProxyFactory factory = new HessianProxyFactory();
			{
				try {
					MailService mailService = (MailService) factory.create(MailService.class, url);
					if (null == mailService) {
						logger.error("给玩家发送道具时，MailService为空");
					} else {
						int result = mailService.sendMails(paramMailDto);
						if (result != 1) {
							logger.error("给玩家发送道具失敗");
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ResultCode.succ("道具发送成功!");
	}

	/**
	 * 生成奖励
	 * @return
	 */
	public String goEditReward() {
		return "good/editReward";
	}

	/**
	 * 单独选择配置活动奖励,生成奖励字符串
	 */
	public Object geteditreward() {
		if (!rewardmap.isEmpty())
			return ResultCode.writeJson(rewardmap);
		return null;
	}

	/**
	 * 单独选择配置活动奖励,生成奖励字符串
	 */
	public Object editReward() {
		String srewards = inv.getParameter("srewards");
		Map<String, String> map = new HashMap<String, String>();
		String names = "";
		if (StringUtils.isNotBlank(srewards)) {
			JSONObject goodsjson = new JSONObject();
			String[] goodstr = srewards.split(",");// 数据格式 1:1,200,2
			for (String goods : goodstr) {
				String[] rew = goods.split(":");
				NormalGood ng = NormalGood.getById(Integer.parseInt(rew[0]));
				if (ng != null) {
					goodsjson.put(ng.getValue(), Integer.parseInt(rew[1]));
					names += ng.getName() + ":" + rew[1] + ",";
				} else {
					goodsjson.put(Integer.parseInt(rew[0]), Integer.parseInt(rew[1]));
					Goods g = goodService.getGoodsByid(Integer.parseInt(rew[0]));
					names += g.getName() + ":" + rew[1] + ",";
				}
			}
			map.put("rewards", goodsjson.toString());
			map.put("names", names);
			// 每次都存起来到内存中。以便于刷新时数据丢失
			rewardmap.put("rewards", goodsjson.toString());
			rewardmap.put("names", names);
		}
		return ResultCode.writeJson(map);
	}

	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		goodService.initGoods();
		return ResultCode.succ("道具数据刷新成功");
	}
	
	
	
	
	public Object addEquip(@Param("serverId") int serverId, @Param("heroId") String heroId,
			@Param("gid") String gid,@Param("strengthGrade") String strengthGrade,@Param("pinzhi") String pinzhi,@Param("starLevel") String starLevel) {
		
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					if (gid== null ||"".equals(gid)||"0".equals(gid)) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("操作失败,装备不存在。"));
					}
					int goodId = Integer.parseInt(gid);
					//强化等级
					int strenGrade = strengthGrade== null ||"".equals(strengthGrade)? 0:Integer.parseInt(strengthGrade);
					//品质
					int pz = pinzhi== null ||"".equals(pinzhi) ? 0:Integer.parseInt(pinzhi);
					//xingji
					int xj = starLevel== null ||"".equals(starLevel) ? 0:Integer.parseInt(starLevel);
					//(int serverId,int pageSize,int page);
					int isSucss = goodService.addGoods(heroId, 1,goodId,0,strenGrade,pz,starLevel);
					
					if (isSucss==1) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("包裹空间不足%s", heroId));
					}else if (isSucss==2) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("不存在的道具%s", goodId));
					}
					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
	
	public Object addGood(@Param("serverId") int serverId, @Param("heroId") String heroId,
			@Param("gid") String gid,@Param("gnum") String gnum) {
		
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					if (gid== null ||"".equals(gid)||"0".equals(gid)) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("操作失败,装备不存在。"));
					}
					int goodId = Integer.parseInt(gid);
					int goodNum = Integer.parseInt(gnum);
					//(int serverId,int pageSize,int page);
					int isSucss = goodService.addGoods(heroId, 2,goodId,goodNum,0,0);
					if (isSucss==1) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("包裹空间不足%s", heroId));
					}else if (isSucss==2) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("不存在的道具%s", goodId));
					}
					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
	
	
	public Object addGem(@Param("serverId") int serverId, @Param("heroId") String heroId,
			@Param("gid") String gid,@Param("gnum") String gnum) {
		serverId = ResourceUtil.getServerId();
		
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					if (gid== null ||"".equals(gid)||"0".equals(gid)) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("操作失败,装备不存在。"));
					}
					
					int goodId = Integer.parseInt(gid);
					int goodNum = Integer.parseInt(gnum);
					//(int serverId,int pageSize,int page);
					int isSucss = goodService.addGoods(heroId, 3,goodId,goodNum,0,0);
					if (isSucss==1) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("包裹空间不足%s", heroId));
					}else if (isSucss==2) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("不存在的道具%s", goodId));
					}
					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
	
	

	public Object cleanBag(@Param("serverId") int serverId, @Param("heroId") String heroId,
			@Param("cleanselect") String cleanselect,@Param("cleanBagType") String cleanBagType) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					boolean isCleanselect = cleanselect== null ||"".equals(cleanselect)||"0".equals(cleanselect)? Boolean.FALSE:Boolean.TRUE;
					int cleanBagTypeFlag = cleanBagType== null ||"".equals(cleanBagType) ? 0:Integer.parseInt(cleanBagType);
					//(int serverId,int pageSize,int page);
					boolean isSucss = goodService.cleanBag(heroId, cleanBagTypeFlag,isCleanselect);
					if (!isSucss) {
						logger.error(String.format("操作失败%s", heroId));
						return ResultCode.error(String.format("操作失败%s", heroId));
					}
					return ResultCode.succ("操作成功!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}

	public Object queryEquipList(@Param("serverId") int serverId, @Param("page") int page, @Param("rows") int rows,
			@Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					//(int serverId,int pageSize,int page);
					DataGrid result = goodService.getHeroGoodListDto(heroId, 1);
					if (null == result) {
						logger.error(String.format("查找%d服玩家账号失败", serverId));
						return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
					}
					return ResultCode.writeJson(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
	
	public Object queryGoodsList(@Param("serverId") int serverId, @Param("page") int page, @Param("rows") int rows,
			@Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					//(int serverId,int pageSize,int page);
					DataGrid result = goodService.getHeroGoodListDto(heroId, 2);
					if (null == result) {
						logger.error(String.format("查找%d服玩家账号失败", serverId));
						return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
					}
					return ResultCode.writeJson(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
	
	public Object  posStrengthening(@Param("serverId") int serverId, @Param("heroId") String heroId,
			@Param("gid") int gid,	@Param("pinzhi") String pinzhi,@Param("starLevel") String starLevel){
		
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
		
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
	}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Object queryGemsList(@Param("serverId") int serverId, @Param("page") int page, @Param("rows") int rows,
			@Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		String url = buildUrl(serverId, "/game-server/api/service/goods");
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			try {
				GoodService goodService = (GoodService) factory.create(GoodService.class, url);
				if (null == goodService) {
					logger.error("查找玩家账号时，heroService为空");
					return ResultCode.error(String.format("查找%d服玩家账失败", serverId));
				} else {
					//(int serverId,int pageSize,int page);
					DataGrid result = goodService.getHeroGoodListDto(heroId, 3);
					if (null == result) {
						logger.error(String.format("查找%d服玩家账号失败", serverId));
						return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
					}
					return ResultCode.writeJson(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找%d服玩家账号失败", serverId));
	}
}
