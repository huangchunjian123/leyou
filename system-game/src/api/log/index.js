import request from '@/utils/request'

// 登录日志
export function getLoginList(page, params) {
  return request({
    url: '/api/log/login/' + page,
    method: 'get',
    params
  })
}
// 创角日志
export function getCreateRoleList(page, params) {
  return request({
    url: '/api/log/createrole/' + page,
    method: 'get',
    params
  })
}
// 角色登出日志
export function getRoleLogoutList(page, params) {
  return request({
    url: '/api/log/rolelogout/' + page,
    method: 'get',
    params
  })
}
// 在线人数日志
export function getOnlineUserList(page, params) {
  return request({
    url: '/api/log/onlineuser/' + page,
    method: 'get',
    params
  })
}
// 角色升级日志
export function getLevelUpList(page, params) {
  return request({
    url: '/api/log/levelup/' + page,
    method: 'get',
    params
  })
}
// 货币增加日志
export function getAddYuanBaoList(page, params) {
  return request({
    url: '/api/log/addyuanbao/' + page,
    method: 'get',
    params
  })
}
// 角色充值日志
export function getAddCashList(page, params) {
  return request({
    url: '/api/log/addcash/' + page,
    method: 'get',
    params
  })
}
// 货币消耗日志
export function getCostYuanBaoList(page, params) {
  return request({
    url: '/api/log/costyuanbao/' + page,
    method: 'get',
    params
  })
}
// 游戏商城销售日志
export function getShopTradeList(page, params) {
  return request({
    url: '/api/log/shoptrade/' + page,
    method: 'get',
    params
  })
}
// 物品获得日志
export function getGainItemList(page, params) {
  return request({
    url: '/api/log/gainitem/' + page,
    method: 'get',
    params
  })
}
// 物品消耗日志
export function getLoseItemList(page, params) {
  return request({
    url: '/api/log/loseitem/' + page,
    method: 'get',
    params
  })
}
// 角色信息快照日志
export function getCharDataList(page, params) {
  return request({
    url: '/api/log/chardata/' + page,
    method: 'get',
    params
  })
}
// 新任务日志
export function getNewTaskList(page, params) {
  return request({
    url: '/api/log/newtask/' + page,
    method: 'get',
    params
  })
}
// 领取任务日志
export function getStartTaskList(page, params) {
  return request({
    url: '/api/log/starttask/' + page,
    method: 'get',
    params
  })
}
// 结束任务日志
export function getEndTaskList(page, params) {
  return request({
    url: '/api/log/endtask/' + page,
    method: 'get',
    params
  })
}
// 开启竞技场日志
export function getStarTarenaList(page, params) {
  return request({
    url: '/api/log/startarena/' + page,
    method: 'get',
    params
  })
}
// 结束竞技场日志
export function getEndTarenaList(page, params) {
  return request({
    url: '/api/log/endtarena/' + page,
    method: 'get',
    params
  })
}
// 排行榜
export function getRankList(page, params) {
  return request({
    url: '/api/log/rank/' + page,
    method: 'get',
    params
  })
}
// 聊天日志
export function getChatLogList(page, params) {
  return request({
    url: '/api/log/chatlog/' + page,
    method: 'get',
    params
  })
}
// 家族排行榜
export function getJiaZuRankList(page, params) {
  return request({
    url: '/api/log/jiazurank/' + page,
    method: 'get',
    params
  })
}
// 抽卡兑换
export function getExchangeList(page, params) {
  return request({
    url: '/api/log/exchange/' + page,
    method: 'get',
    params
  })
}
// 抽卡
export function getRecruitList(page, params) {
  return request({
    url: '/api/log/recruit/' + page,
    method: 'get',
    params
  })
}
// 查看创角日志
export function getWelfareList(page, params) {
  return request({
    url: '/api/log/welfare/' + page,
    method: 'get',
    params
  })
}

// 查看个人日志
export function getPersonList(page, params) {
  return request({
    url: '/api/log/person/' + page,
    method: 'get',
    params
  })
}

// 新副本
export function getNewInstanceList(page, params) {
  return request({
    url: '/api/log/newinstance/' + page,
    method: 'get',
    params
  })
}
// 开启副本
export function getStartInstanceList(page, params) {
  return request({
    url: '/api/log/startinstance/' + page,
    method: 'get',
    params
  })
}

// 结束副本
export function getEndInstanceList(page, params) {
  return request({
    url: '/api/log/startinstance/' + page,
    method: 'get',
    params
  })
}
// 伙伴物品
export function getPetItemList(page, params) {
  return request({
    url: '/api/log/petitem/' + page,
    method: 'get',
    params
  })
}
