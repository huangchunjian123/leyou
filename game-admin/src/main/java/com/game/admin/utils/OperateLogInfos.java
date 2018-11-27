package com.game.admin.utils;



/**
 * 统一管理用户操作日志
 * XXXController+方法
 * @author huangchunjian
 * @package com.game.admin.utils
 */
public class OperateLogInfos{

	public static final String AccountControllerClose1 = "封号成功[封号角色Id:%s-角色名称:%s-封号原因:%s-封号时间:%s-ip:%s-服务器:%s]";
	public static final String AccountControllerClose2 = "解封成功[封号角色Id:%s-角色名称:%s-封号原因:%s-封号时间:%s-ip:%s-服务器:%s]";
	public static final String AccountControllerClose3 = "禁言成功[禁言角色Id:%s-角色名称:%s-禁言时间:%s-操作ip:%s-服务器:%s]";
	public static final String AccountControllerClose4 = "解除禁言成功[禁言角色Id:%s-角色名称:%s-禁言时间:%s-操作ip:%s-服务器:%s]";
	
	public static final String EvilControllerUpdateEvil ="修改善恶值[角色Id:%s-角色名称:%s-善恶值:%s-服务器:%s]";
	
	public static final String GoodControllerSendGoods ="发送道具[账号Id:%s-货币信息:%s-标题:%s-内容:%s-服务器:%s]";
	public static final String GoodControllerAddEquip1 ="添加装备[角色Id:%s-装备名称:%s-装备Id:%s-强化等级:%s-品质:%s-服务器:%s]失败原因包裹空间不足";
	public static final String GoodControllerAddEquip2 ="添加装备[角色Id:%s-装备名称:%s-装备Id:%s-强化等级:%s-品质:%s-服务器:%s]失败原因道具不存在";
	public static final String GoodControllerAddEquip3 ="添加装备[角色Id:%s-装备名称:%s-装备Id:%s-强化等级:%s-品质:%s-服务器:%s]";
	public static final String GoodControllerAddGood1 ="添加物品[角色Id:%s-物品名称:%s-物品Id:%s-物品数量:%s-服务器:%s]失败原因包裹空间不足";
	public static final String GoodControllerAddGood2 ="添加物品[角色Id:%s-物品名称:%s-物品Id:%s-物品数量:%s-服务器:%s]失败原因道具不存在";
	public static final String GoodControllerAddGood3 ="添加物品[角色Id:%s-物品名称:%s-物品Id:%s-物品数量:%s-服务器:%s]";
	public static final String GoodControllerAddGem1 ="添加宝石[角色Id:%s-物品名称:%s-物品Id:%s-物品数量:%s-服务器:%s]失败原因包裹空间不足";
	public static final String GoodControllerAddGem2 ="添加宝石[角色Id:%s-物品名称:%s-物品Id:%s-物品数量:%s-服务器:%s]失败原因道具不存在";
	public static final String GoodControllerAddGem3 ="添加宝石[角色Id:%s-物品名称:%s-物品Id:%s-物品数量:%s-服务器:%s]";
	public static final String GoodControllerCleanBag1 ="清空背包物品[角色Id:%s-服务器:%s]";
	public static final String GoodControllerCleanBag2 ="清空穿戴装备[角色Id:%s-服务器:%s]";
	public static final String GoodControllerCleanBag3 ="清空宝石[角色Id:%s-服务器:%s]";
	public static final String GoodControllerPosStrengthening ="强化槽位[角色Id:%s-槽位名称:%s-槽位Id:%s-升星:%s-强化等级:%s-服务器:%s]";
	
	public static final String HerosControllerAddHero = "添加玩家角色[登录名称:%s-角色名称:%s-角色等级:%s-VIP等级:%s-职业id:%s-金币:%s-元宝:%s-操作ip:%s-服务器:%s]";
	public static final String HerosControllerDelHero = "删除玩家角色[角色id:%s-操作ip:%s-服务器:%s]";
	public static final String HerosControllerDownLine= "踢玩家下线:[角色Id:%s-操作ip:%s-服务器:%s]";
	public static final String HerosControllerAddOrupdate= "编辑和添加玩家坐骑:[角色Id:%s-坐骑Id:%s-坐骑(1普通2稀有):%s-星阶:%s-品阶:%s-天数(没有天数则为普通):%s--操作ip:%s-服务器:%s]";
	public static final String HerosControllerEditorSkill= "编辑技能:[角色Id:%s-技能Id:%s-技能等级:%s-操作ip:%s-服务器:%s]";
	public static final String HerosControllerCloneHero= "克隆账号:[克隆账号Id:%s-登录名称:%s-角色名称:%s-操作ip:%s-服务器:%s]";
	public static final String HerosControllerRecharge = "模拟充值:[角色Id:%s-充值项目Id:%s-操作ip:%s-服务器:%s]";
	
	public static final String InstanceControllerPerfectInstances = "角色爬塔副本跳塔:[角色Id:%s-跳塔id:%s-操作ip:%s-服务器:%s]";
	public static final String InstanceControllerPerfectNineDay = "角色跳九重天副本:[角色Id:%s-跳塔id:%s-操作ip:%s-服务器:%s]";
	
	public static final String LifeControllerSendLifeGoods = "发送命格物品[角色id:%s-物品信息:%s-碎片数量:%s-操作ip:%s-服务器:%s]";
	
	public static final String LoginControllerLogin = "系统登录:[登录名称:%s-登录Ip:%s]";
	public static final String LoginControllerLogout = "系统退出:[登录名称:%s-登录Ip:%s]";
	
	public static final String MenuControllerEdit = "编辑菜单管理:[菜单Id:%s-菜单名称:%s-排序序号:%s-菜单类型:%s-URL:%s-操作IP:%s]";
	public static final String MenuControllerAdd = "添加菜单管理成功:[菜单名称:%s-排序序号:%s-菜单类型:%s-URL:%s-操作IP:%s]";
	public static final String MenuControllerDeleteMenu ="删除菜单成功:[菜单Id:%s-操作IP:%s]";
	
	public static final String NewbieControllerEditNewBies = "操作玩家新手引导成功:[角色Id:%s-跳过新手引导Id:%s-操作ip:%s-服务器:%s]";
	
	public static final String RoleControllerAdd = "添加GM后台角色:[角色新名称:%s-描述:%s-操作IP:%s]";
	public static final String RoleControllerEdit = "编辑GM后台角色:[角色新名称:%s-描述:%s-操作IP:%s]";
	public static final String RoleControllerUpdateAuth = "编辑GM后台角色权限:[权限id:%s-权限:%s-操作IP:%s]";
	public static final String RoleControllerDeleteRole = "删除GM后台角色:[角色id:%s-操作IP:%s]";
	
	public static final String RunControllerDownLine = "踢完家下线:[角色Id:%s-操作ip:%s-服务器:%s]";
	
	public static final String ServerControllerAddServer ="添加服务器:[服务器名称:%s-服务器Id:%s-mysql服务器:%s-是否合过服:%s-合服id:%s-是否是特殊服务器:%s-描述:%s-登录ip:%s-Redis:%s-服务器:%s-操作IP:%s]";
	public static final String ServerControllerUpdateServer ="修改服务器:[服务器名称:%s-服务器Id:%s-mysql服务器:%s-是否合过服:%s-合服id:%s-是否是特殊服务器:%s-描述:%s-登录ip:%s-Redis:%s-服务器:%s-操作IP:%s]";
	public static final String ServerControllerdeleteServer = "删除服务器[服务器id:%s-操作IP:%s]";
	public static final String ServerControllerSendServerMails = "发送服务器邮件:[发送Ip:%s-标题:%s-内容:%s-附件:%s-邮箱类型:%s-设定发送时间:%s-操作ip:%s-服务器:%s]";
	public static final String ServerControllerSendMails1 = "发送角色邮件:[角色Id:%s-标题:%s-内容:%s-附件:%s-操作ip:%s-服务器:%s]";
	public static final String ServerControllerSendNotice2 ="发送全服系统公告:[服务器IP:%s-公告内容:%s-间隔时间:%s秒-发送次数:%s次-操作ip:%s-服务器:%s]";
	
	public static final String TaskControllerPerfectTasks = "操作完成任务:[角色Id:%s-完成任务Id:%s-操作ip:%s-服务器:%s]";
	
	public static final String UnionControllerCreateUnion = "创建帮派成功:[角色Id:%s-帮派名称:%s-会标颜色:%s-会标文字:%s-帮会宣言:%s-操作Ip:%s-服务器:%s]";
	public static final String UnionControllerEditUnion = "编辑帮派成功:[角色Id:%s-帮派名称:%s-会标颜色:%s-会标文字:%s-帮会宣言:%s-帮会等级:%s-帮会当前职位:%s-操作Ip:%s-服务器:%s]";
	public static final String UnionControllerunionSetting =  "同步本服务器帮派信息:[服务器:%s-操作Ip:%s]";
	
	public static final String UserControllerAdd = "添加账号成功[登录名称:%s-真实姓名:%s-操作IP:%s]";
	public static final String UserControllerEdit = "编辑账号成功[%s-%s-%s-%s-%s]-[%s-%s-%s-%s-%s]操作IP:%s]";
	public static final String UserControllerDeleteUser ="删除账号[id:%s-姓名:%s:-操作IP:%s]";
	
	public static final String UtilsControllerCreateCdKey = "生成cdkey兑换码:%s个-操作ip:%s";
	
}
