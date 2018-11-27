import request from '@/utils/request'

export function login(userInfo) {
  return request({
    url: '/api/login',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    },
    data: userInfo
  })
}

export function getInfo() {
  return request({
    url: '/api/admin/info',
    method: 'get'
  })
}

export function getPer(roleId) {
  return request({
    url: '/api/role/per/list/' + roleId,
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/admin/logout',
    method: 'post'
  })
}
