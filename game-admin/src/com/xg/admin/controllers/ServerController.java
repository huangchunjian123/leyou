package com.xg.admin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.annotation.Param;

import org.apache.commons.lang3.StringUtils;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.dto.model.ServerTreeNode;
import com.xg.admin.pojo.server.ServerData;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.MailService;
import com.xg.game.api.hessian.NoticeService;
import com.xg.game.api.hessian.dto.MailDto;

/**
 * 服务器控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class ServerController extends BaseController {

	public Object servertree() {
		List<ServerTreeNode> sr = serverService.getTreeeServer();
		return ResultCode.writeJson(sr);
	}

	/**
	 * 发系统邮件
	 * @return
	 */
	public String goSendMails() {
		return "server/sendMails";
	}
	
	public String goSendServerEmails() {
		return "server/sendServerEmails";
	}
	
	public Object sendServerMails(@Param("serverIds") String serverIds,  @Param("title") String title,
			@Param("content") String content, @Param("sendGoods") String sendGoods, @Param("gold") int gold,
			@Param("yuanbao") int yuanbao) {
		
		if (StringUtils.isEmpty(serverIds)) {
					return ResultCode.error(String.format(String.format("请选择要发送的服务器")));
		} else {
			StringBuffer failBuffer = new StringBuffer();
			sendGoods = buildSendGoods(sendGoods);
			for (String serverId : serverIds.split(",")) {
				ServerData serverData = serverService.getByServerId(Integer.valueOf(serverId));
				if (null == serverData) {
					continue;
				}
				if (!sendEmail(serverData, buildMail("", title, content, gold, yuanbao,
						sendGoods))) {
					failBuffer.append(String.format("[服务器%s],",serverId));
//					return ResultCode.error(String.format(String.format("发送系统公告失败")));
				}
			}
			if (!StringUtils.isEmpty(failBuffer.toString())){
				return ResultCode.succ(String.format("发送服务器邮件时，下列服务器列表发送失败:[%s]",failBuffer.toString()));
			}
		}
		return ResultCode.succ(String.format("发送服务器邮件成功"));
	}
	
	private boolean sendEmail(ServerData serverData, MailDto dto) {
		//		String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()
		//				+ "/game-server/api/service/account";
		String url = buildUrl(serverData.getServerId(), "/game-server/api/service/mail");
		//		String url = "http://localhost:8080/game-server/api/service/notice";
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				MailService mailService = (MailService) factory.create(MailService.class, url);
				if (null == mailService) {
					logger.error("给服务器发系统邮件时，noticeService为空");
					return Boolean.FALSE;
				} else {
					int result = mailService.sendServerMails(dto);
					if (result != 0) {
						logger.error("给服务器发系统邮件失敗");
						return Boolean.FALSE;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.TRUE;
	}
	

	/**
	 * 同一批账号发送邮件
	 * @param accountIds
	 * @param title
	 * @param content
	 * @param sendGoods
	 * @param gold
	 * @param yuanbao
	 * @return
	 */
	public Object sendMails(@Param("accountIds") String accountIds, @Param("title") String title,
			@Param("content") String content, @Param("sendGoods") String sendGoods, @Param("gold") int gold,
			@Param("yuanbao") int yuanbao) {
		sendGoods = buildSendGoods(sendGoods);
		Map<String, String> resultMap = new HashMap<String, String>();
		String[] accountIdStr = accountIds.split("\\,");
		for (String accountId : accountIdStr) {
			String[] tempAttr = accountId.split("_");
			if (tempAttr.length != 2) {
				continue;
			}
			//			ServerData serverData = serverService.getByServerId(Integer.valueOf(tempAttr[0]));
			//			String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()
			//					+ "/game-server/api/service/mail";
			String url = buildUrl(Integer.valueOf(tempAttr[0]), "/game-server/api/service/mail");
			HessianProxyFactory factory = new HessianProxyFactory();
			{
				try {
					MailService mailService = (MailService) factory.create(MailService.class, url);
					if (null == mailService) {
						logger.error("给玩家发送道具时，MailService为空");
						resultMap.put(accountId, "error");
						continue;
					} else {
						int result = mailService.sendMails(buildMail(accountId, title, content, gold, yuanbao,
								sendGoods));
						if (result != 1) {
							logger.error("给玩家发送道具失敗");
							resultMap.put(accountId, "error");
							continue;
						}
						resultMap.put(accountId, "success");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ResultCode.writeJson(resultMap);
	}

	private String buildSendGoods(String goods) {
		StringBuffer sendGoods = new StringBuffer();
		if (StringUtils.isNotBlank(goods)) {
			String[] goodstr = goods.split(",");// 数据格式 包裹类型:id:数量
			for (String item : goodstr) {
				String[] rew = item.split(":");
				sendGoods.append(Integer.valueOf(rew[1])).append("*").append(Integer.valueOf(rew[2])).append("*")
						.append(Integer.valueOf(rew[0])).append(";");
			}
		}
		return sendGoods.toString();
	}

	private MailDto buildMail(String accountId, String title, String content, int gold, int yuanbao, String sendGoods) {
		MailDto paramMailDto = new MailDto();
		List<String> ids = new ArrayList<String>();
		ids.add(accountId);
		paramMailDto.setAccountIds(ids);
		paramMailDto.setTitle(title);
		paramMailDto.setContent(content);
		paramMailDto.setGoods(sendGoods.toString());
		paramMailDto.setGold(gold);
		paramMailDto.setYuanbao(yuanbao);
		return paramMailDto;
	}

	public String goSendNotice() {
		return "server/sendNotice";
	}

	/**
	 * 发系统公告
	 * @param serverIds
	 * @param content
	 * @return
	 */
	public Object sendNotice(@Param("serverIds") String serverIds, @Param("content") String content) {
		if (StringUtils.isEmpty(serverIds)) {
//			List<ServerData> list = serverService.getAll();
//			for (ServerData serverData : list) {
//				if (!sendNotice(serverData, content)) {
					return ResultCode.error(String.format(String.format("请选择要发送的服务器")));
//				}
//			}
		} else {
			StringBuffer failBuffer = new StringBuffer();
			for (String serverId : serverIds.split(",")) {
				ServerData serverData = serverService.getByServerId(Integer.valueOf(serverId));
				if (null == serverData) {
					continue;
				}
				if (!sendNotice(serverData, content)) {
					failBuffer.append(String.format("[服务器%s],",serverId));
//					return ResultCode.error(String.format(String.format("发送系统公告失败")));
				}
			}
			if (!StringUtils.isEmpty(failBuffer.toString())){
				return ResultCode.succ(String.format("发送系统公告时，下列服务器列表发送失败:[%s]",failBuffer.toString()));
			}
		}
		return ResultCode.succ(String.format("发送系统公告成功"));
	}
	
	/**
	 * 发系统公告
	 * @param serverIds
	 * @param content
	 * @return
	 */
	public Object sendNotice2(@Param("serverIds") String serverIds, @Param("content") String content,@Param("time") String time,@Param("period") String period) {
		if (StringUtils.isEmpty(serverIds)) {
//			List<ServerData> list = serverService.getAll();
//			for (ServerData serverData : list) {
//				if (!sendNotice(serverData, content)) {
					return ResultCode.error(String.format(String.format("请选择要发送的服务器")));
//				}
//			}
		} else {
			StringBuffer failBuffer = new StringBuffer();
			for (String serverId : serverIds.split(",")) {
				ServerData serverData = serverService.getByServerId(Integer.valueOf(serverId));
				if (null == serverData) {
					continue;
				}
				if (!sendNotice2(serverData, content,time,period)) {
					failBuffer.append(String.format("[服务器%s],",serverId));
//					return ResultCode.error(String.format(String.format("发送系统公告失败")));
				}
			}
			if (!StringUtils.isEmpty(failBuffer.toString())){
				return ResultCode.succ(String.format("发送系统公告时，下列服务器列表发送失败:[%s]",failBuffer.toString()));
			}
		}
		return ResultCode.succ(String.format("发送系统公告成功"));
	}
	

	/**
	 * 发系统公告
	 * @param serverData
	 * @param content
	 * @return
	 */
	private boolean sendNotice2(ServerData serverData, String content,String time,String period) {
		//		String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()
		//				+ "/game-server/api/service/account";
		String url = buildUrl(serverData.getServerId(), "/game-server/api/service/notice");
		//		String url = "http://localhost:8080/game-server/api/service/notice";
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				NoticeService noticeService = (NoticeService) factory.create(NoticeService.class, url);
				if (null == noticeService) {
					logger.error("发系统公告时，noticeService为空");
					return Boolean.FALSE;
				} else {
					int result = noticeService.sendNotice2(content,Integer.parseInt(time),Integer.parseInt(period));
					if (result == 0) {
						logger.error(String.format("发送系统公告失败"));
						return Boolean.FALSE;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 发系统公告
	 * @param serverData
	 * @param content
	 * @return
	 */
	private boolean sendNotice(ServerData serverData, String content) {
		//		String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()
		//				+ "/game-server/api/service/account";
		String url = buildUrl(serverData.getServerId(), "/game-server/api/service/notice");
		//		String url = "http://localhost:8080/game-server/api/service/notice";
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				NoticeService noticeService = (NoticeService) factory.create(NoticeService.class, url);
				if (null == noticeService) {
					logger.error("发系统公告时，noticeService为空");
					return Boolean.FALSE;
				} else {
					int result = noticeService.sendNotice(content);
					if (result == 0) {
						logger.error(String.format("发送系统公告失败"));
						return Boolean.FALSE;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Boolean.TRUE;
	}
}
