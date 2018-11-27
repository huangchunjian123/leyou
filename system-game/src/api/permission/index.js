import request from '@/utils/request'

// 获取列表
export function getList(params) {
  return request({
    url: '/api/per',
    method: 'get',
    params
  })
}

// 添加
export function addPermission(params) {
  request.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
  return request({
    url: '/api/per',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: params
  })
}

// 更新
export function updatePermission(params) {
  request.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
  return request({
    url: '/api/per',
    method: 'put',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    params
  })
}

// 获取详情
export function getPermission(id) {
  return request({
    url: '/api/per/' + id,
    method: 'get'
  })
}

// 删除
export function delPermission(id) {
  return request({
    url: '/api/per/' + id,
    method: 'delete'
  })
}

// 路径检查
export function checkPath(params) {
  return request({
    url: '/api/per/checkPath',
    method: 'get',
    params
  })
}
