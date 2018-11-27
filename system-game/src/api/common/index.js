import request from '@/utils/request'

// 文件上传
export function upload(params) {
  request.defaults.headers['Content-Type'] = 'multipart/form-data'
  return request({
    url: '/api/upload/file',
    method: 'post',
    data: params
  })
}
