import request from '@/utils/request'

// 获取列表
export function getList(page, params) {
  return request({
    url: '/api/admin/page/' + page,
    method: 'get',
    params
  })
}

// 添加
export function addAdmin(params) {
  request.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
  return request({
    url: '/api/admin',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: params
  })
}

// 更新
export function updateAdmin(params) {
  request.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
  return request({
    url: '/api/admin',
    method: 'put',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    params
  })
}

// 帐号验证
export function checkAccount(params) {
  return request({
    url: '/api/admin/checkAccount',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: params
  })
}

// 获取详情
export function getAdminById(id) {
  return request({
    url: '/api/admin/' + id,
    method: 'get'
  })
}

// 删除
export function delAdmin(id) {
  return request({
    url: '/api/admin/' + id,
    method: 'delete'
  })
}
