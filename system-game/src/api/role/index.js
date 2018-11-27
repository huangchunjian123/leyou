import request from '@/utils/request'

// 获取列表
export function getList(page, params) {
  return request({
    url: '/api/role/page/' + page,
    method: 'get',
    params
  })
}

export function getAllList() {
  return request({
    url: '/api/role',
    method: 'get'
  })
}

// 添加
export function addRole(params) {
  request.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
  return request({
    url: '/api/role',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: params
  })
}

// 更新
export function updateRole(params) {
  request.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
  return request({
    url: '/api/role',
    method: 'put',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    params
  })
}

// 获取详情
export function getRoleById(id) {
  return request({
    url: '/api/role/' + id,
    method: 'get'
  })
}

// 获取权限
export function getRolePer(id) {
  return request({
    url: '/api/role/per/' + id,
    method: 'get'
  })
}

// 添加权限
export function addRolePer(id, params) {
  request.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
  return request({
    url: '/api/role/per/' + id,
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: params
  })
}

// 删除
export function delRole(id) {
  return request({
    url: '/api/role/' + id,
    method: 'delete'
  })
}
