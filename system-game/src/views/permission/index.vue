<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus">添加</el-button>
    </div>

    <tree-table :data="list" :columns="columns" border :expandAll="true">

      <el-table-column label="操作" align="left">
        <template slot-scope="scope">
          <el-button type="primary" size="small" icon="el-icon-edit" @click="getData(scope.row.id)" >编缉</el-button>
          <el-button type="danger" size="small" icon="el-icon-delete" @click="delData(scope.row.id)" >删除</el-button>
          <el-button type="primary" v-if="scope.row.permission_type == 0" size="small" icon="el-icon-edit" @click="handleChildrenCreate(scope.row.id, scope.row.permission_name)" >添加下级</el-button>
        </template>
      </el-table-column>
    </tree-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :model="dataForm" :rules="rules" ref="dataForm" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item v-if="parentName" label="父级">
          <el-input v-model="parentName" disabled ></el-input>
        </el-form-item>
        <el-form-item label="路径" prop="visitUrl">
          <el-input v-model="dataForm.visitUrl"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="permissionName">
          <el-input v-model="dataForm.permissionName"></el-input>
        </el-form-item>
        <el-form-item label="权限类型">
          <el-select v-model="dataForm.permissionType" placeholder="请选权限类型">
            <el-option v-for="item in permissionOption" :key="item.value" :label="item.lable" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图标">
          <!--<el-input v-model="dataForm.icon"></el-input>-->
          <el-select v-model="dataForm.icon" placeholder="请选权限类型">
            <el-option  value="" >无</el-option>
            <el-option v-for="item of iconsMap" :value="item" :key="item" ><svg-icon class-name="disabled" :icon-class="item" /></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'"  type="primary" @click="createData('dataForm')">创建</el-button>
        <el-button v-else type="primary" @click="updateData('dataForm')">更新</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import treeTable from '@/components/TreeTable'
  import { getList, addPermission, updatePermission, getPermission, delPermission, checkPath } from '@/api/permission'
  import { objectToFormData } from '@/utils'
  import icons from './generateIconsView'
  export default {
    components: { treeTable },
    data() {
      const validatePath = (rule, value, callback) => {
        const params = {}
        params.path = value
        params.parentId = this.dataForm.parentId
        params.id = this.dataForm.id
        checkPath(params).then(response => {
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
        list: [],
        parentName: '',
        dialogStatus: 'create',
        textMap: {
          update: '更新',
          create: '创建'
        },
        iconsMap: [],
        dataForm: {
          id: '',
          permissionName: '',
          permissionType: 0,
          visitUrl: '',
          icon: '',
          sort: '',
          parentId: ''
        },
        rules: {
          visitUrl: [
            { required: true, message: '请输入访问路径', trigger: 'blur' },
            { trigger: 'blur', validator: validatePath }
          ],
          permissionName: [
            { required: true, message: '请输入名称', trigger: 'blur' }
          ]
        },
        permissionOption: [
          { value: 0, lable: '菜单' },
          { value: 1, lable: '功能' }
        ],
        columns: [
          {
            text: '名称',
            value: 'permission_name',
            width: 200
          },
          {
            text: '路径',
            value: 'visit_url'
          },
          {
            text: '图标',
            value: 'icon'
          }
        ]
      }
    },
    created() {
      this.fetchData()
    },
    mounted() {
      console.log(this.req)
      const iconsMap = icons.state.iconsMap.map((i) => {
        return i.default.id.split('-')[1]
      })
      this.iconsMap = iconsMap
    },
    methods: {
      fetchData() {
        const other = this
        getList().then(response => {
          other.list = response.data
        })
      },
      update(id) {
        console.log('update')
      },
      handleCreate() {
        if (this.dataForm.id !== '' || this.dataForm.parentId !== '') {
          this.$refs['dataForm'].resetFields()
          this.resetForm()
        }
        this.parentName = ''
        this.dialogFormVisible = true
        this.dialogStatus = 'create'
      },
      handleChildrenCreate(id, parentName) {
        if (this.$refs['dataForm'] !== undefined) {
          this.$refs['dataForm'].resetFields()
        }
        this.resetForm()
        this.dataForm.parentId = id
        this.dialogFormVisible = true
        this.parentName = parentName
        this.dialogStatus = 'create'
      },
      resetForm() {
        this.dataForm.id = ''
        this.dataForm.parentId = ''
        this.dataForm.permissionName = ''
        this.dataForm.permissionType = 0
        this.dataForm.visitUrl = ''
        this.dataForm.icon = ''
        this.dataForm.sort = ''
      },
      createData(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            other.dialogFormVisible = false
            addPermission(objectToFormData(other.dataForm)).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.fetchData()
                other.$refs['dataForm'].resetFields()
                other.resetForm()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      updateData(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            other.dialogFormVisible = false
            updatePermission(this.dataForm).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.fetchData()
                other.$refs['dataForm'].resetFields()
                other.resetForm()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      getData(id, parentName) {
        const other = this
        if (this.$refs['dataForm'] !== undefined) {
          this.$refs['dataForm'].resetFields()
        }

        this.parentName = parentName

        getPermission(id).then(response => {
          other.dataForm.id = response.data.id
          other.dataForm.parentId = response.data.parentId
          other.dataForm.permissionName = response.data.permissionName
          other.dataForm.permissionType = response.data.permissionType
          other.dataForm.visitUrl = response.data.visitUrl
          other.dataForm.icon = response.data.icon
          other.dataForm.sort = response.data.sort
          other.dialogFormVisible = true
          other.dialogStatus = 'update'
        })
      },
      delData(id) {
        const other = this
        this.$confirm('确认删除该权限', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
          .then(_ => {
            delPermission(id).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.fetchData()
              } else {
                other.$message.error(response.msg)
              }
            })
          })
          .catch(_ => {})
      }
    }
  }
</script>
