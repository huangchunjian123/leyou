/**
 * Created by jiachenpan on 16/11/18.
 */

export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (('' + time).length === 10) time = parseInt(time) * 1000
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1]
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

export function formatTime(time, option) {
  time = +time * 1000
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) { // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return d.getMonth() + 1 + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
  }
}

// takes a {} object and returns a FormData object
export function objectToFormData(obj, form, namespace) {
  const fd = form || new FormData()
  var formKey

  for (var property in obj) {
    if (obj.hasOwnProperty(property)) {
      if (namespace) {
        formKey = namespace + '[' + property + ']'
      } else {
        formKey = property
      }

      // if the property is an object, but not a File,
      // use recursivity.
      if (typeof obj[property] === 'object' && !(obj[property] instanceof File)) {
        objectToFormData(obj[property], fd, property)
      } else {
        // if it's a string or a File object
        fd.append(formKey, obj[property])
      }
    }
  }
  return fd
}

export function formatDate(date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (const k in o) {
    const str = o[k] + ''
    if (new RegExp(`(${k})`).test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str))
    }
  }
  return fmt
}

function padLeftZero(str) {
  return ('00' + str).substr(str.length)
}

// 判断字符是否为空的方法
export function isEmpty(obj) {
  if (typeof obj === undefined || obj === null || obj === '') {
    return true
  } else {
    return false
  }
}

// 递归获取
export function toRouters(data) {
  const routesList = []
  if (data instanceof Array) {
    data.forEach(function(item) {
      if (item.permission_type === 0) {
        const routers = {}
        routers.meta = { title: item.permission_name, icon: item.icon }
        if (item.parent_id === null || item.parent_id === '') {
          routers.path = '/' + item.visit_url
          routers.component = resolve => require(['../views/layout/Layout'], resolve)
        } else {
          routers.path = item.visit_url
          routers.component = resolve => require(['../views/' + item.visit_url + ''], resolve)
        }
        routers.name = routers.path

        if (item.children !== undefined) {
          const children = []
          item.children.forEach(function(item) {
            const temp = toRouters(item)
            if (temp !== '' && temp !== {}) {
              children.push(temp)
            }
          })
          if (children !== [] && (item.parent_id === null || item.parent_id === '')) {
            routers.redirect = routers.path + '/' + children[0].path
            routers.children = children
          }
        }
        routesList.push(routers)
      }
    })
    return routesList
  } else {
    if (data.permission_type === 0) {
      const routers = {}
      routers.meta = { title: data.permission_name, icon: data.icon }
      if (data.parent_id === null || data.parent_id === '') {
        routers.path = '/' + data.visit_url
        routers.component = resolve => require(['../views/layout/Layout'], resolve)
      } else {
        routers.path = data.visit_url
        routers.component = resolve => require(['../views/' + data.visit_url + ''], resolve)
      }
      routers.name = data.visit_url
      if (data.children !== undefined) {
        const children = []
        data.children.forEach(function(item) {
          const temp = toRouters(item)
          if (temp !== '' && temp !== {}) {
            children.push(temp)
          }
        })
        if (children !== [] && (data.parent_id === null || data.parent_id === '')) {
          routers.redirect = routers.path + '/' + children[0].path
          routers.children = children
        }
      }
      return routers
    }
  }
}
