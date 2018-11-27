import { login, logout, getInfo, getPer } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { toRouters } from '@/utils'

const user = {
  state: {
    token: getToken(),
    userInfo: {},
    permission: null
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo
    },
    SET_PERMISSION: (state, permission) => {
      state.permission = permission
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const data = response.data
          setToken(data.token)
          commit('SET_TOKEN', data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const data = response.data
          if (response.code === 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_USER_INFO', data)
            resolve(response)
          } else {
            reject(response.msg)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户权限
    GetPermission({ commit, state }) {
      return new Promise((resolve, reject) => {
        getPer(state.userInfo.adminType).then(response => {
          if (response.code === 0) {
            console.log(toRouters(response.data))
            commit('SET_PERMISSION', toRouters(response.data))
            resolve(response)
          } else {
            reject(response.msg)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
