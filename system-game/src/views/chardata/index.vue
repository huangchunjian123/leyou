<template>
  <div class="app-container">
    <div v-if="search" class="filter-container">
      <el-input placeholder="角色ID" v-model="params.roleId" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="角色名称" v-model="params.roleName" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="用户ID" v-model="params.userId" style="width: 200px;" class="filter-item"/>
      <el-input placeholder="帐号" v-model="params.account" style="width: 200px;" class="filter-item"/>
      <el-select v-model="params.service" placeholder="服务端" clearable style="width: 130px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item"/>
      </el-select>
      <el-date-picker v-model="params.date" style="vertical-align: middle;margin-bottom: 10px;" type="datetimerange"
                      range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
      </el-date-picker>
<el-select v-model="params_chosepagesize" placeholder="条数" clearable style="width: 80px" class="filter-item">
        <el-option v-for="item in pagesizeOptions" :key="item.label" :label="item.label" :value="item.value" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">{{button.serach}}
      </el-button>
 <el-button class="fr el-button--success" @click="exportExcel">导出excel</el-button>
    </div>
    <table-model ref="table" :pageFun="getPage" :sortKey="'logtime'" :isLoad="false" :columns="columns">
      <template slot="Button" slot-scope="button">
        <el-button type="warning" size="small" icon="el-icon-menu">{{button.permission}}</el-button>
      </template>
    </table-model>
  </div>
</template>
<script>
  import tableModel from '@/components/Table'
  import {getCharDataList} from '@/api/log'
  import {isEmpty, formatDate} from '@/utils'

  export default {
    name: 'chardata',
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
    components: {tableModel},
    data() {
      return {
        button: {
          permission: '权限分配',
          serach: '搜索'
        },
        importanceOptions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
pagesizeOptions: [{
            value: '10',
            label: '10'
        }, {
            value: '100',
            label: '100'
        }, {
            value: '1000',
            label: '1000'
        }, {
            value: '10000',
            label: '10000'
        }],
        params_chosepagesize: '10',
        params_yuanbaotype:'',
        excellist:'',
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
            text: '服务器',
            value: 'serverid'
          },
          {
            text: '用户ID',
            value: 'userid'
          },
          {
            text: '帐号',
            value: 'account'
          },
          {
            text: '等级',
            value: 'params4'
          },
          {
            text: '平台',
            value: 'params3'
          },
          {
            text: '操作系统',
            value: 'params7',
          },
          {
            text: '职业',
            value: 'params1'
          },
          {
            text: '总金额',
            value: 'params8'
          },
          {
            text: '当前家族ID',
            value: 'params9'
          },
          {
            text: '创建时间',
            value: 'params6'
          },
          {
            text: '战斗',
            value: 'params5'
          },
          {
            text: 'VIP等级',
            value: 'params10'
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
          getCharDataList(curPage, params).then(response => {
this.excellist = response.data.list
            resolve(response)
          }).catch(error => {
            reject(error)
          })
        })
      },
      handleSearch() {
        if (typeof this.params.service === 'undefined' || isEmpty(this.params.service) || isEmpty(this.params.date)) {
          this.$message.warning('请选择搜索条件时间区间和对应的服务器')
          return false
        }
        this.$refs.table.getPage(1, this.params_chosepagesize)
      },
        exportExcel() {        
         import('@/vendor/Export2Excel').then(excel => {
            const tHeader = ['记录时间', '角色ID', '角色名称','等级', '服务器','用户ID', '帐号', '平台', '操作系统',  '职业', '总金额','当前家族ID','创建时间', '战斗', 'VIP等级']
           const filterVal = ['logtime', 'roleid', 'rolename','params4', 'serverid','userid', 'account', 'params3','params7', 'params1', 'params8', 'params9','params6', 'params5', 'params10']
            const list = this.excellist
            const data = this.formatJson(filterVal, list)
            excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: this.filename,
            autoWidth: this.autoWidth,
            bookType: this.bookType
            })
        })
     },
　　　　formatJson(filterVal, jsonData) {
　　　　　　return jsonData.map(v => filterVal.map(j => v[j]))
　　　　}
    }

  }
</script>

<style scoped>

</style>
