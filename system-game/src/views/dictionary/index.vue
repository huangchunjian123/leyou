<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus">添加</el-button>
    </div>
    <tree-table :data="list" :columns="columns" border :expandAll="true">
      <el-table-column label="操作" align="left">
        <template slot-scope="scope">
          <el-button type="primary" v-if="!scope.row.dic_parent_id" size="small" icon="el-icon-edit" @click="getDic(scope.row.id)" >编缉</el-button>
          <el-button type="primary" v-if="scope.row.dic_parent_id" size="small" icon="el-icon-edit" @click="getValue(scope.row.id)" >编缉</el-button>
          <el-button type="danger" size="small" icon="el-icon-delete" @click="delData(scope.row.id)" >删除</el-button>
          <el-button type="primary" v-if="!scope.row.dic_parent_id" size="small" icon="el-icon-edit" @click="handleValueCreate(scope.row.id,scope.row.dic_code, scope.row.dic_name)" >添加值</el-button>
        </template>
      </el-table-column>
    </tree-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pagination.curPage" :page-sizes="[10,20,30,50]" :page-size="pagination.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pagination.totalRow">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :model="dicDataForm" :rules="dicRules" ref="dicDataForm" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="名称" prop="dicName">
          <el-input v-model="dicDataForm.dicName"></el-input>
        </el-form-item>
        <el-form-item label="标识码" prop="dicCode">
          <el-input v-model="dicDataForm.dicCode"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'"  type="primary" @click="createDic('dicDataForm')">创建</el-button>
        <el-button v-else type="primary" @click="updateDic('dicDataForm')">更新</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogValueFormVisible">
      <el-form :model="valueDataForm" :rules="valueRules" ref="valueDataForm" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="值名称" prop="dicName">
          <el-input v-model="valueDataForm.dicName"></el-input>
        </el-form-item>
        <el-form-item label="标识值" prop="dicValue">
          <el-input v-model="valueDataForm.dicValue"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogValueFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'"  type="primary" @click="createValue('valueDataForm')">创建</el-button>
        <el-button v-else type="primary" @click="updateValue('valueDataForm')">更新</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import treeTable from '@/components/TreeTable'
  import { getList, checkCode, checkValue, addDic, updateDic, getDicById, delDic } from '@/api/dictionary'
  import { objectToFormData } from '@/utils'
  export default {
    components: { treeTable },
    data() {
      const validateCode = (rule, value, callback) => {
        const params = {}
        params.code = this.dicDataForm.dicCode
        params.id = this.dicDataForm.id
        checkCode(params).then(response => {
          if (response.data.result === true) {
            callback()
          } else {
            callback(new Error(response.msg))
          }
        }).catch(error => {
          console.log(error)
          callback(new Error('服务器错误！'))
        })
      }
      const validateValue = (rule, value, callback) => {
        const params = {}
        params.value = this.valueDataForm.dicValue
        params.parentId = this.valueDataForm.dicParentId
        params.id = this.valueDataForm.id
        checkValue(params).then(response => {
          if (response.data.result === true) {
            callback()
          } else {
            callback(new Error(response.msg))
          }
        }).catch(error => {
          console.log(error)
          callback(new Error('服务器错误！'))
        })
      }
      return {
        dialogFormVisible: false,
        dialogValueFormVisible: false,
        list: [],
        dialogStatus: 'create',
        textMap: {
          update: '更新',
          create: '创建'
        },
        dicDataForm: {
          id: '',
          dicName: '',
          dicCode: ''
        },
        deepDicForm: {},
        valueDataForm: {
          id: '',
          dicParentId: '',
          dicName: '',
          dicCode: '',
          dicValue: ''
        },
        deepValueForm: {},
        dicRules: {
          dicName: [
            { required: true, message: '请输入字典名称', trigger: 'blur' }
          ],
          dicCode: [
            { required: true, message: '请输入字典识标', trigger: 'blur' },
            { trigger: 'blur', validator: validateCode }
          ]
        },
        valueRules: {
          dicName: [
            { required: true, message: '请输入值名称', trigger: 'blur' }
          ],
          dicValue: [
            { required: true, message: '请输入标识值', trigger: 'blur' },
            { trigger: 'blur', validator: validateValue }
          ]
        },
        pagination: {
          curPage: 1,
          pageSize: 10,
          totalRow: 10
        },
        columns: [
          {
            text: '名称',
            value: 'dic_name'
          },
          {
            text: 'code标识',
            value: 'dic_code'
          },
          {
            text: '值',
            value: 'dic_value'
          }
        ]
      }
    },
    created() {
      this.fetchData()
      this.deepDicForm = JSON.parse(JSON.stringify(this.dicDataForm))
      this.deepValueForm = JSON.parse(JSON.stringify(this.valueDataForm))
    },
    methods: {
      fetchData() {
        const other = this
        getList(this.pagination.curPage, { 'pageSize': this.pagination.pageSize }).then(response => {
          other.list = response.data.list
          this.pagination.curPage = response.data.pageNumber
          this.pagination.pageSize = response.data.pageSize
          this.pagination.totalRow = response.data.totalRow
        })
      },
      handleCreate() {
        if (this.dicDataForm.id !== '') {
          this.$refs['dicDataForm'].resetFields()
          this.dicDataForm = JSON.parse(JSON.stringify(this.deepDicForm))
        }
        this.dialogFormVisible = true
        this.dialogStatus = 'create'
      },
      handleValueCreate(id, code) {
        if (this.valueDataForm.id !== '') {
          this.$refs['valueDataForm'].resetFields()
          this.valueDataForm = JSON.parse(JSON.stringify(this.deepValueForm))
        }
        this.valueDataForm.dicParentId = id
        this.valueDataForm.dicCode = code
        this.dialogValueFormVisible = true
        this.dialogStatus = 'create'
      },
      handleSizeChange(val) {
        this.pagination.pageSize = val
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.pagination.curPage = val
        this.fetchData()
      },
      createDic(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            addDic(objectToFormData(this.dicDataForm)).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.$refs[formName].resetFields()
                other.dicDataForm = JSON.parse(JSON.stringify(other.deepDicForm))
                other.dialogFormVisible = false
                other.fetchData()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      updateDic(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateDic(this.dicDataForm).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.$refs[formName].resetFields()
                other.dicDataForm = JSON.parse(JSON.stringify(other.deepDicForm))
                other.dialogFormVisible = false
                other.fetchData()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      getDic(id) {
        const other = this
        getDicById(id).then(response => {
          other.dicDataForm.id = response.data.id
          other.dicDataForm.dicCode = response.data.dicCode
          other.dicDataForm.dicName = response.data.dicName
          other.dialogFormVisible = true
          other.dialogStatus = 'update'
        })
      },
      getValue(id, name) {
        const other = this
        getDicById(id).then(response => {
          other.valueDataForm.id = response.data.id
          other.valueDataForm.dicParentId = response.data.dicParentId
          other.valueDataForm.dicCode = response.data.dicCode
          other.valueDataForm.dicName = response.data.dicName
          other.valueDataForm.dicValue = response.data.dicValue
          other.dialogValueFormVisible = true
          other.dialogStatus = 'update'
        })
      },
      createValue(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            addDic(objectToFormData(this.valueDataForm)).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.$refs[formName].resetFields()
                other.valueDataForm = JSON.parse(JSON.stringify(other.deepValueForm))
                other.dialogValueFormVisible = false
                other.fetchData()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      updateValue(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateDic(this.valueDataForm).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.$refs[formName].resetFields()
                other.valueDataForm = JSON.parse(JSON.stringify(other.deepValueForm))
                other.dialogValueFormVisible = false
                other.fetchData()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      delData(ids) {
        const other = this
        this.$confirm('确认删除该字典', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
          .then(_ => {
            delDic({ ids: ids }).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.fetchData()
              } else {
                other.$message.error(response.msg)
              }
            })
          })
      }
    }
  }
</script>
