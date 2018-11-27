package com.xg.admin.controllers;

import net.paoding.rose.web.annotation.Param;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.AccountService;
import com.xg.game.api.hessian.DataGrid;
import com.xg.game.api.hessian.MailService;
import com.xg.game.api.hessian.dto.AccountDto;

/**
 * 玩家账号控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class AccountController extends BaseController {
	/**
	 * 去查找玩家账号
	 * @return
	 */
	public String goQueryAccount() {
		return "account/list";
	}
	
	public String goAccountEmail(){
		return "account/emaillist";
	}

	/**
	 * 查找玩家账号
	 * @param loginName
	 * @param serverId
	 * @return
	 */
	public Object queryAccount(@Param("loginName") String loginName, @Param("serverId") int serverId) {
		String url = buildUrl(serverId, "/game-server/api/service/account");
		//		ServerData serverData = serverService.getByServerId(serverId);
		//		String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()
		//				+ "/game-server/api/service/account";
		//		String url = "http://localhost:8080/game-server/api/service/account";
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				AccountService accountService = (AccountService) factory.create(AccountService.class, url);
				if (null == accountService) {
					logger.error("查找玩家账号时，AccountService为空");
					return ResultCode.error(String.format("查找S%d服玩家账号:%s失败", serverId, loginName));
				} else {
					AccountDto result = accountService.getAccountDto(loginName, serverId);
					if (null == result) {
						logger.error(String.format("查找S%d服玩家账号:%s失败", serverId, loginName));
						return ResultCode.error(String.format("查找S%d服玩家账号:%s失败", serverId, loginName));
					}
					return ResultCode.writeJson(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("查找S%d服玩家账号:%s失败", serverId, loginName));
	}
	/**
	 * 封号禁言 解封 
	 * @param heroId
	 * @param type
	 * @param serverid
	 * @param operator
	 * @param ranks
	 * @param rankTime
	 * @return
	 */
	public Object close(@Param("heroId") String heroId,@Param("type") String type, @Param("serverid") int serverid,
			@Param("operator") int operator,@Param("ranks")String ranks,@Param("rankTime")String rankTime) {
		
		String url = buildUrl(serverid, "/game-server/api/service/account");
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				AccountService accountService = (AccountService) factory.create(AccountService.class, url);
				if (null == accountService) {
					logger.error("操作时，AccountService为空");
					return ResultCode.error(String.format("操作S%d服玩家账号:%s失败", serverid, heroId));
				} else {
					int result = accountService.close(heroId, operator,ranks,rankTime,type);

					return ResultCode.succ(String.format("操作S%d服玩家账号:%s成功", serverid, heroId));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("操作S%d服玩家账号:%s失败", serverid, heroId));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 封号
	 * @param loginName
	 * @param serverId
	 * @param content
	 * @return
	 */
	public Object forbidden(@Param("loginName") String loginName, @Param("serverId") int serverId,
			@Param("content") String content) {
		
		String url = buildUrl(serverId, "/game-server/api/service/account");	
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				AccountService accountService = (AccountService) factory.create(AccountService.class, url);
				if (null == accountService) {
					logger.error("封号时，AccountService为空");
					return ResultCode.error(String.format("封号S%d服玩家账号:%s失败", serverId, loginName));
				} else {
					int result = accountService.forbidden(loginName, serverId, content);
					
					return ResultCode.succ(String.format("封号S%d服玩家账号:%s成功", serverId, loginName));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("封号S%d服玩家账号:%s失败", serverId, loginName));
	}
	
	public Object  getAccountMails(@Param("serverId") int serverId,@Param("accountId") String accountId) {
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
						DataGrid result =   mailService.getAccountMails(accountId);
						if (null == result) {
							logger.error(String.format("查找%d服玩家邮件失败", serverId));
							return ResultCode.error(String.format("查找%d服玩家邮件失败", serverId));
						}
						return ResultCode.writeJson(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return ResultCode.error(String.format("查找%d服玩家邮件失败", serverId));
	}

	/**
	 * 解封
	 * @param loginName
	 * @param serverId
	 * @return
	 */
	public Object enabled(@Param("loginName") String loginName, @Param("serverId") int serverId) {
		String url = buildUrl(serverId, "/game-server/api/service/account");
		//		ServerData serverData = serverService.getByServerId(serverId);
		//		String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()
		//				+ "/game-server/api/service/account";
		//		String url = "http://localhost:8080/game-server/api/service/account";
		HessianProxyFactory factory = new HessianProxyFactory();
		{
			try {
				AccountService accountService = (AccountService) factory.create(AccountService.class, url);
				if (null == accountService) {
					logger.error("解封时，AccountService为空");
					return ResultCode.error(String.format("解封S%d服玩家账号:%s失败", serverId, loginName));
				} else {
					int result = accountService.enabled(loginName, serverId);
					if (result == 0) {
						logger.error(String.format("解封S%d服玩家账号:%s失败", serverId, loginName));
						return ResultCode.error(String.format("解封S%d服玩家账号:%s失败", serverId, loginName));
					}
					return ResultCode.succ(String.format("解封S%d服玩家账号:%s成功", serverId, loginName));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error(String.format("解封S%d服玩家账号:%s失败", serverId, loginName));
	}
}
