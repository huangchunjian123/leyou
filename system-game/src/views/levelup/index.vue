<template>
  <div class="app-container">
    <div  v-if="search" class="filter-container">
      <el-input placeholder="角色ID" v-model="params.roleId" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="角色名称" v-model="params.roleName" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="用户ID" v-model="params.userId" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="帐号" v-model="params.account" style="width: 200px;" class="filter-item"/>
      <el-select v-model="params.service" placeholder="服务端" clearable style="width: 130px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item"/>
      </el-select>
      <el-date-picker v-model="params.date" style="vertical-align: middle;margin-bottom: 10px;" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
      </el-date-picker>
      <el-button  class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">{{button.serach}}</el-button>
    </div>
    <table-model ref="table" :pageFun="getPage" :sortKey="'logtime'" :isLoad="false" :columns="columns">
      <template slot="Button" slot-scope="button">
         <el-button   type="warning" size="small" icon="el-icon-menu" @click="" >{{button.permission}}</el-button>
      </template>
    </table-model>
  </div>
</template>
<script>
  import tableModel from '@/components/Table'
  import { getLevelUpList } from '@/api/log'
  import { isEmpty, formatDate } from '@/utils'

  export default {
    name: 'levelup',
    props: {
      search: {
        type: Boolean,
        default: true
      },
      params: {
        type: Object,
        default: () => {
          return {}
        }
      },
      isLoad: {
        type: Boolean,
        defalut: false
      }
    },
    mounted() {
      if (this.isLoad) {
        this.handleSearch()
      }
    },
    components: { tableModel },
    data() {
      return {
        button: {
          permission: '权限分配',
          serach: '搜索'
        },
        importanceOptions: [1, 2, 3],
        columns: [
          {
            text: '记录时间',
            value: 'logtime',
            sort: true
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
            text: '等级',
            value: 'params6'
          },
          {
            text: '升级时间',
            value: 'params5'
          },
          {
            text: '平台',
            value: 'params4'
          },
          {
            text: '操作系统',
            value: 'params1',
            dicCode: 'os'
          },
          {
            text: '服务器',
            value: 'serverid'
          },
          {
            text: '总金额',
            value: 'params2'
          },
          {
            text: 'PID',
            value: 'params3'
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
        if (this.params.date !== null && this.params.date !== undefined) {
          params.startDate = formatDate(this.params.date[0], 'yyyy-MM-dd hh:mm:ss')
          params.endDate = formatDate(this.params.date[1], 'yyyy-MM-dd hh:mm:ss')
        }
        params.service = this.params.service
        params.roleId = this.params.roleId
        params.roleName = this.params.roleName
        params.userId = this.params.userId
        params.account = this.params.account
        params.pageSize = pageSize
        return new Promise((resolve, reject) => {
          getLevelUpList(curPage, params).then(response => {
            resolve(response)
          }).catch(error => {
            reject(error)
          })
        })
      },
      handleSearch() {
        if (isEmpty(this.params.service) && isEmpty(this.params.date) && isEmpty(this.params.roleId) && isEmpty(this.params.roleName) && isEmpty(this.params.userId) && isEmpty(this.params.account)) {
          this.$message.warning('请选择搜索条件')
          return false
        }
        this.$refs.table.getPage(1, 10)
      }
    }

  }
</script>

<style scoped>

</style>
