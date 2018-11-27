import request from '@/utils/request'

// 获取字典列表
export function getList(page, params) {
  return request({
    url: '/api/dic/page/' + page,
    method: 'get',
    params
  })
}

// 添加字典
export function addDic(params) {
  request.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
  return request({
    url: '/api/dic',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: params
  })
}

// 更新字典
export function updateDic(params) {
  request.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
  return request({
    url: '/api/dic',
    method: 'put',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    params
  })
}

// 检查code是否可用
export function checkCode(params) {
  return request({
    url: '/api/dic/check/code',
    method: 'get',
    params
  })
}

// 检查value是否可用
export function checkValue(params) {
  return request({
    url: '/api/dic/check/value',
    method: 'get',
    params
  })
}

// 获取字典
export function getDicById(id) {
  return request({
    url: '/api/dic/' + id,
    method: 'get'
  })
}

// 删除字典
export function delDic(params) {
  return request({
    url: '/api/dic',
    method: 'delete',
    params
  })
}

// 删除字典
export function getDicName(params) {
  return request({
    url: '/api/dic/name',
    method: 'get',
    params
  })
}
