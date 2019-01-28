<template>
  <div class="app-container">
    <div v-if="search" class="filter-container">
      <el-input placeholder="角色ID" v-model="params.roleId" style="width: 80px;" class="filter-item"/>
      <el-input placeholder="角色名称" v-model="params.roleName" style="width: 120px;" class="filter-item"/>
      <el-input placeholder="用户ID" v-model="params.userId" style="width: 80px;" class="filter-item"/>
      <el-input placeholder="帐号" v-model="params.account" style="width: 80px;" class="filter-item"/>
       <el-select v-model="params_yuanbaotype" placeholder="元宝类型" clearable style="width: 120px" class="filter-item">
        <el-option v-for="item in yuanbaotypeOptions" :key="item.label" :label="item.label" :value="item.value" />
      </el-select>
       <el-input placeholder="最小值" v-model="params.numbermin" style="width: 80px;" class="filter-item"/>~<el-input placeholder="最大值" v-model="params.numbermax" style="width: 80px;" class="filter-item"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-select v-model="params.service" placeholder="服务端" clearable style="width: 100px" class="filter-item">
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
  import {getAddYuanBaoList} from '@/api/log'
  import {isEmpty, formatDate} from '@/utils'

  export default {
    name: 'addyuanbao',
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

         yuanbaotypeOptions: [{
            value: '10200001',
            label: '金币'
        }, {
            value: '10200002',
            label: '元宝'
        }, {
            value: '10200003',
            label: '绑宝元宝'
        }, {
            value: '10200004',
            label: '精髓'
        }, {
            value: '10200011',
            label: '竞技场积分'
        }],
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
            text: '等级',
            value: 'params7'
          },
          {
            text: '平台',
            value: 'params4'
          },
          {
            text: '操作系统',
            value: 'params12',
          },
          {
            text: '总金额',
            value: 'params14'
          },
          {
            text: '服务器',
            value: 'serverid'
          },
          {
            text: '元宝类型',
            value: 'params15',
          },
          {
            text: '元宝路径',
            value: 'params9'
          },
          {
            text: '元宝',
            value: 'params13'
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
        params.yuanbaotype = this.params_yuanbaotype
        params.numbermin = this.params.numbermin
        params.numbermax = this.params.numbermax
        return new Promise((resolve, reject) => {
          getAddYuanBaoList(curPage, params).then(response => {
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
            const tHeader = ['记录时间', '角色ID', '角色名称', '等级', '平台', '操作系统', '总金额', '服务器', '元宝类型', '元宝路径', '元宝', '用户ID', '帐号']
            const filterVal = ['logtime', 'roleid', 'rolename', 'params7', 'params4', 'params12', 'params14', 'serverid', 'params15', 'params9', 'params13', 'userid', 'account']
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
