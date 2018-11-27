import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权

const whiteList = ['/login'] // 不重定向白名单

router.beforeEach((to, from, next) => {
  console.log('beforeEach==============================================')
  console.log('vue-router before')
  NProgress.start()
  console.log(getToken())
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      store.dispatch('GetInfo').then(res => { // 拉取用户信息
        if (store.getters.permission === null) {
          store.dispatch('GetPermission').then(_ => { // 获取权限
            console.log(store.getters.permission)
            if (Object.keys(store.getters.permission).length > 0) {
              debugger
              store.getters.permission.forEach(function(item) {
                router.options.routes.push(item)
              })
              router.addRoutes(router.options.routes)
            }
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          })
        } else if (to.matched.length === 0) {
          next({ path: '/404' })
        } else {
          next()
        }
      }).catch((err) => {
        store.dispatch('FedLogOut').then(() => {
          Message.error(err || 'Verification failed, please login again')
          next({ path: '/' })
        })
      })
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
      NProgress.done()
    }
  }
})
router.afterEach(() => {
  NProgress.done() // 结束Progress
})
