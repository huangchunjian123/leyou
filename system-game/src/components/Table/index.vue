<template>
  <div>
  <el-table ref="table" :data="list"  v-loading.body="listLoading" element-loading-text="加载" @row-click="handleRowChange" @selection-change="selsChange" border fit highlight-current-row  >
    <el-table-column v-if="selection" type="selection">
    </el-table-column>
    <el-table-column v-for="(column, index) in columns" :key="column.value" :label="column.text" :width="column.width" :sortable="column.sort" :prop="column.value">
      <template slot-scope="scope">
        {{scope.row[column.value]}}
      </template>
    </el-table-column>
    <el-table-column v-if="controlColumn" label="操作" align="center">
      <template slot-scope="scope">
        <el-button v-if="editFun" type="primary" size="small" icon="el-icon-edit" @click="editData(scope.row.id)" >编缉</el-button>
        <el-button v-if="delFun" type="danger" size="small" icon="el-icon-delete" @click="delData(scope.row.id)" >删除</el-button>
        <slot name="Button" :row="scope.row" slot-scope="button"></slot>
      </template>
    </el-table-column>
  </el-table>
  <div v-if="paging" class="pagination-container">
    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pagination.curPage" :page-sizes="[10,20,30,50]" :page-size="pagination.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pagination.totalRow">
    </el-pagination>
  </div>
  </div>
</template>

<script>
    import { getDicName } from '@/api/dictionary'
    export default {
      name: 'tableModel',
      props: {
        pageFun: { // 获取分页的方法
          type: Function,
          default: null
        },
        delFun: { // 删除方法
          type: Function,
          default: null
        },
        editFun: { // 编辑方法
          type: Function,
          default: null
        },
        columns: { // 分行
          type: Array,
          default: () => []
        },
        selection: {
          type: Boolean,
          default: false
        },
        isLoad: {
          type: Boolean,
          default: true
        },
        sortKey: {
          type: String,
          default: null
        },
        controlColumn: { // 操作列
          type: Boolean,
          default: false
        },
        paging: {
          type: Boolean,
          default: true
        }
      },
      data() {
        return {
          pagination: {
            curPage: 1,
            pageSize: 10,
            totalRow: 0
          },
          list: [],
          sels: [],
          listLoading: true
        }
      },
      created() {
        if (this.isLoad) {
          this.getPage(this.pagination.curPage, this.pagination.pageSize)
        } else {
          this.listLoading = false
        }
      },
      methods: {
        getPage(curPage, PageSize) {
          this.listLoading = true
          this.pageFun(curPage, PageSize).then(response => {
            const dicColumns = []
            this.columns.forEach(column => {
              if (column.dicCode !== undefined && column.dicCode !== '') {
                const dicColumn = {}
                dicColumn.column = column.value
                dicColumn.code = column.dicCode
                dicColumns.push(dicColumn)
              }
            })
            if (dicColumns.length > 0) {
              const tempData = JSON.parse(JSON.stringify(response.data.list))
              this.setDicValueForList(tempData, dicColumns).then(() => {
                this.list = tempData
                this.pagination.curPage = response.data.pageNumber
                this.pagination.pageSize = response.data.pageSize
                this.pagination.totalRow = response.data.totalRow
                this.listLoading = false
              })
            } else {
              this.list = response.data.list
              this.pagination.curPage = response.data.pageNumber
              this.pagination.pageSize = response.data.pageSize
              this.pagination.totalRow = response.data.totalRow
              this.listLoading = false
            }

            /* this.list.forEach(l => {
              dicColumns.forEach(v => {
                this.getDicValue(v.code, l[v.column]).then(response => {
                  l[v.column] = response.data
                })
              })
            }) */
          })
        },
        setDicValueForList: async function(dataList, dicColumns) { // 同步设置字典
          for (const data of dataList) {
            for (const columns of dicColumns) {
              await this.getDicValue(columns.code, data[columns.column]).then(response => {
                data[columns.column] = response.data
              })
            }
          }
        },
        getDicValue(code, value) {
          return new Promise((resolve, reject) => {
            getDicName({ code: code, value: value }).then(response => {
              resolve(response)
            }).catch(error => {
              reject(error)
            })
          })
        },
        handleSizeChange(val) {
          this.pagination.pageSize = val
          this.getPage(this.pagination.curPage, this.pagination.pageSize)
        },
        handleCurrentChange(val) {
          this.pagination.curPage = val
          this.getPage(this.pagination.curPage, this.pagination.pageSize)
        },
        handleRowChange(row, event, column) {
          this.$refs.table.toggleRowSelection(row)
        },
        selsChange(sels) {
          this.sels = sels
        },
        delData(id) {
          const other = this
          this.$confirm('确认删除该数据？', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
            .then(_ => {
              this.delFun(id).then(response => {
                if (response.code === 0) {
                  other.$message.success(response.msg)
                  other.getPage(this.pagination.curPage, this.pagination.pageSize)
                } else {
                  other.$message.info(response.msg)
                }
              })
            })
            .catch(_ => {})
        },
        editData(id) {
          this.editFun(id)
        }
      }
    }
</script>
