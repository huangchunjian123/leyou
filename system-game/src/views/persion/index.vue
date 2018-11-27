<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="角色ID" v-model="params.roleId" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="角色名称" v-model="params.roleName" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="用户ID" v-model="params.userId" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="帐号" v-model="params.account" style="width: 200px;" class="filter-item"/>
      <el-select v-model="params.service" placeholder="服务端" clearable style="width: 130px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item"/>
      </el-select>
      <el-date-picker v-model="params.date" style="vertical-align: middle;margin-bottom: 10px;" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
      </el-date-picker>
      <el-button  class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">{{buttons.serach}}</el-button>
    </div>
    <table-model ref="table" :pageFun="getPage" :sortKey="'logtime'" :controlColumn="true" :isLoad="false" :columns="columns">
      <template slot="Button" slot-scope="button">
          <el-button type="primary" size="small"  @click="detailLog(button.row)" >{{buttons.detail}}</el-button>
      </template>
    </table-model>

    <el-dialog title="日志详情" :visible.sync="dialogFormVisible">
      <el-form v-for="logs in logData" :inline="true"  class="demo-form-inline">
        <el-form-item v-for="log in logs" :label="log.key" >
          {{log.value}}
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<style>
  .form .form-left {
    width:50%; padding-left:1rem; padding-right:1rem; padding-top:1rem;
  }
  .form .form-right {
    width:50%; padding-left:1rem; padding-right:1rem; padding-top:1rem;
  }
</style>
<script>
  import tableModel from '@/components/Table'
  import { getPersonList } from '@/api/log'
  import { isEmpty, formatDate } from '@/utils'
  import { getDicName } from '@/api/dictionary'

  export default {
    components: {
      tableModel
    },
    data() {
      return {
        dialogFormVisible: false,
        logData: null,
        tableComponent: '',
        buttons: {
          detail: '详情',
          serach: '搜索'
        },
        importanceOptions: [1, 2, 3],
        params: {
          service: '',
          date: null,
          roleId: '',
          roleName: '',
          userId: '',
          account: ''
        },
        columns: [
          {
            text: '记录时间',
            value: 'logtime',
            sort: true
          },
          {
            text: '日志类型',
            value: 'logname'
          },
          {
            text: '角色ID',
            value: 'roleid'
          },
          {
            text: '角色名称',
            value: 'rolename'
          },
          {
            text: '用户ID',
            value: 'userid'
          },
          {
            text: '帐号',
            value: 'account'
          }
        ]
      }
    },
    methods: {
      getPage(curPage, pageSize) {
        const params = {}
        if (this.params.date !== null) {
          params.startDate = formatDate(this.params.date[0], 'yyyy-MM-dd hh:mm:ss')
          params.endDate = formatDate(this.params.date[1], 'yyyy-MM-dd hh:mm:ss')
        }
        params.service = this.params.service
        params.pageSize = pageSize
        params.roleId = this.params.roleId
        params.roleName = this.params.roleName
        params.userId = this.params.userId
        params.account = this.params.account
        return new Promise((resolve, reject) => {
          getPersonList(curPage, params).then(response => {
            resolve(response)
          }).catch(error => {
            reject(error)
          })
        })
      },
      handleSearch() {
        if (isEmpty(this.params.date)) {
          this.$message.warning('请选择时间范围')
          return false
        }
        if (isEmpty(this.params.service) && isEmpty(this.params.roleId) && isEmpty(this.params.roleName) && isEmpty(this.params.userId) && isEmpty(this.params.account)) {
          this.$message.warning('请选择搜索条件')
          return false
        }
        this.$refs.table.getPage(1, 10)
      },
      detailLog(data) {
        this.tableComponent = data.logname
        this.logData = this.logDetail(this.tableComponent, data)
        this.dialogFormVisible = true
      },
      logDetail(logname, data) {
        let tempData = {}
        switch (logname) {
          case 'rolelogin':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params6,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params1),
              'ip地址': data.params2,
              '总金额': data.params3,
              '服务器': data.serverid
            }
            break
          case 'addcash':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '等级': data.params3,
              '平台': data.params2,
              '操作系统': getDicName('os', data.params6),
              '总金额': data.params8,
              '服务器': data.serverid,
              '元宝类型': getDicName('yuanbaotype', data.serverid),
              '元宝路径': data.serverid,
              '元宝': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'addyuanbao':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '等级': data.params7,
              '平台': data.params4,
              '操作系统': getDicName('os', data.params12),
              '总金额': data.params14,
              '服务器': data.serverid,
              '元宝类型': getDicName('yuanbaotype', data.params15),
              '元宝路径': data.params9,
              '元宝': data.params13,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'chardata':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params4,
              '平台': data.params3,
              '操作系统': getDicName('os', data.params7),
              '职业': data.params1,
              '总金额': data.params8,
              '当前家族ID': data.params9,
              '创建时间': data.params6,
              '战斗': data.params5,
              'VIP等级': data.params10
            }
            break
          case 'chatlog':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params8,
              '平台': data.params6,
              '操作系统': getDicName('os', data.params2),
              '总金额': data.params4,
              'VIP等级': data.params7,
              '频道': data.params3,
              '消息': data.params1
            }
            break
          case 'costyuanbao':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '等级': data.params7,
              '平台': data.params4,
              '操作系统': getDicName('os', data.params12),
              '总金额': data.params14,
              '服务器': data.serverid,
              '元宝类型': getDicName('yuanbaotype', data.params15),
              '元宝路径': data.params9,
              '元宝': data.params13,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'levelup':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '等级': data.params6,
              '升级时间': data.params5,
              '平台': data.params4,
              '操作系统': getDicName('os', data.params1),
              '服务器': data.serverid,
              '总金额': data.params2,
              'PID': data.params3,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'endtarena':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params1),
              '敌方角色ID': data.params4,
              '竞技场ID': data.params2,
              '战斗': data.params8,
              '时间': data.params7,
              '结果': data.params6
            }
            break
          case 'endtask':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '操作系统': getDicName('os', data.params1),
              '平台': data.params3,
              '用户ID': data.userid,
              '帐号': data.account,
              '任务类型ID': data.params6,
              '任务ID': data.params8,
              '战斗': data.params7,
              '时间': data.params5,
              '结果': data.params4
            }
            break
          case 'exchange':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params7,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params1),
              '总金额': data.params2,
              '战斗': data.params8,
              '招募类型': data.params4,
              '要对换的物品ID': data.params6
            }
            break
          case 'gainitem':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params8,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params2),
              '物品类型': data.params7,
              '物品ID': data.params6,
              '物品数量': data.params3,
              '物品路径': data.params1
            }
            break
          case 'jiazurank':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '家族ID': data.params6,
              '家族名称': data.params2,
              'leader角色id': data.params8,
              'leader角色名称': data.params3,
              '家族等级': data.params5,
              '家族成员': data.params1,
              '排名': data.params9,
              '得分': data.params7
            }
            break
          case 'welfare':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params7,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params1),
              '总金额': data.params2,
              '战斗': data.params8,
              '类型': data.params4,
              'subtype': data.params6
            }
            break
          case 'loseitem':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params8,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params2),
              '物品类型': data.params7,
              '物品ID': data.params6,
              '物品数量': data.params3,
              '物品路径': data.params1
            }
            break
          case 'rank':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '等级': data.params7,
              '平台': data.params4,
              '操作系统': getDicName('os', data.params12),
              '总金额': data.params14,
              '服务器': data.serverid,
              '元宝类型': getDicName('yuanbaotype', data.params15),
              '元宝路径': data.params9,
              '元宝': data.params13,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'createrole':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '职业': data.params1,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params2),
              'MAC地址': data.params4,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'shoptrade':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '操作系统': getDicName('os', data.params6),
              '平台': data.params3,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params5,
              '物品类型': data.params9,
              '物品ID': data.params4,
              '物品数量': data.params1,
              '元宝类型': getDicName('yuanbaotype', data.params10),
              '元宝': data.params7,
              '总金额': data.params8
            }
            break
          case 'startarena':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params1),
              '敌方角色ID': data.params4,
              '竞技场ID': data.params2,
              '战斗': data.params6
            }
            break
          case 'starttask':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '操作系统': getDicName('os', data.params1),
              '平台': data.params3,
              '用户ID': data.userid,
              '帐号': data.account,
              '任务类型ID': data.params4,
              '任务ID': data.params6,
              '战斗': data.params5
            }
            break
          case 'rolelogout':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '等级': data.params3,
              '平台': data.params2,
              '操作系统': getDicName('os', data.params6),
              '总金额': data.params8,
              '剩余虚拟币': data.params4,
              '剩余元宝': data.params5,
              '绑定剩余元宝': data.params7,
              '在线时长（秒）': data.params9,
              '服务器': data.serverid,
              'PID': data.params1,
              '用户ID': data.userid,
              '帐号': data.account
            }
            break
          case 'recruit':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '等级': data.params6,
              '平台': data.params5,
              '操作系统': getDicName('os', data.params1),
              '总金额': data.params2,
              '战斗': data.params7,
              '类型': data.params4
            }
            break
          case 'startinstance':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '平台': data.params3,
              '操作系统': getDicName('os', data.params1),
              '战斗': data.params6,
              'chapterID': data.params4,
              'ectypeID': data.params5
            }
            break
          case 'endinstance':
            tempData = {
              '记录时间': data.logtime,
              '角色ID': data.roleid,
              '角色名称': data.rolename,
              '服务器': data.serverid,
              '用户ID': data.userid,
              '帐号': data.account,
              '平台': data.params3,
              '操作系统': getDicName('os', data.params1),
              '战斗': data.params6,
              'chapterID': data.params4,
              'ectypeID': data.params5
            }
            break
        }
        const dataArray = []
        const tempKey = Object.keys(tempData)
        for (let i = 0; i < tempKey.length; i++) {
          debugger
          const tempArray = []
          const t = {}
          t.key = tempKey[i]
          t.value = tempData[tempKey[i]]
          tempArray.push(t)
          if (i < tempKey.length) {
            const s = {}
            ++i
            s.key = tempKey[i]
            s.value = tempData[tempKey[i]]
            tempArray.push(s)
          }
          dataArray.push(tempArray)
        }

        return dataArray
      }
    }

  }
</script>

<style scoped>
  .el-form-item__label{
    margin-left: 20%;
  }
  .el-form-item{
    width: 40%;
  }
</style>
