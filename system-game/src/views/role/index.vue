<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus">添加</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="delConfirm" type="danger" icon="el-icon-delete">删除</el-button>
    </div>
    <table-model ref="tableModel" :pageFun="getPage" :controlColumn="true" :editFun="getData" :delFun="deleteData" :columns="columns">
      <template slot="Button" slot-scope="button">
         <el-button   type="warning" size="small" icon="el-icon-menu" @click="getPer(button.row)" >权限分配</el-button>
      </template>
    </table-model>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">

      <el-form :model="dataForm" :rules="rules" ref="dataForm" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="角色名" prop="roleName">
          <el-input v-model="dataForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="dataForm.description"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'"  type="primary" @click="createData('dataForm')">创建</el-button>
        <el-button v-else type="primary" @click="updateData('dataForm')">更新</el-button>
      </div>

    </el-dialog>

    <el-dialog :title="perTree.dialogPermissionName+'-权限管理'" :visible.sync="dialogPermission">
      <el-tree
        :data="perTree.treeData"
        show-checkbox
        :default-checked-keys="perTree.defaultData"
        :default-expand-all="true"
        :check-strictly="true"
        @check="setFunction"
        node-key="id"
        ref="tree"
        :props="perTree.defaultProps">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPermission = false">取消</el-button>
        <el-button type="primary" @click="confirmPermission">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import tableModel from '@/components/Table'
  import { getList, addRole, updateRole, getRoleById, delRole, getRolePer, addRolePer } from '@/api/role'
  import { objectToFormData } from '@/utils'
  import { getList as treeList } from '@/api/permission'
  export default {
    components: { tableModel },
    data() {
      return {
        dialogStatus: '',
        textMap: {
          update: '更新',
          create: '创建'
        },
        dialogFormVisible: false,
        dialogPermission: false,

        perTree: {
          dialogPermissionName: '',
          permissionId: '',
          treeData: [],
          defaultData: [],
          defaultProps: {
            children: 'children',
            label: 'permission_name'
          }
        },
        columns: [
          {
            text: '名称',
            value: 'roleName',
            width: 200
          },
          {
            text: '描述',
            value: 'description'
          }
        ],
        dataForm: {
          id: '',
          roleName: '',
          description: ''
        },
        rules: {
          roleName: [
            { required: true, message: '请输入角色名', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      getPage(curPage, pageSize) {
        const params = { 'pageSize': pageSize }
        return new Promise((resolve, reject) => {
          getList(curPage, params).then(response => {
            resolve(response)
          }).catch(error => {
            reject(error)
          })
        })
      },
      createData(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            other.dialogFormVisible = false
            addRole(objectToFormData(this.dataForm)).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.$refs.tableModel.getPage()
                other.$refs['dataForm'].resetFields()
                other.restForm()
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
            updateRole(this.dataForm).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.$refs.tableModel.getPage()
                other.$refs['dataForm'].resetFields()
                other.resetForm()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      getData(id) {
        const other = this
        if (this.$refs['dataForm'] !== undefined) {
          this.$refs['dataForm'].resetFields()
        }

        getRoleById(id).then(response => {
          other.dataForm.id = response.data.id
          other.dataForm.roleName = response.data.roleName
          other.dataForm.description = response.data.description
          other.dialogFormVisible = true
          other.dialogStatus = 'update'
        })
      },
      deleteData(id) {
        return new Promise((resolve, reject) => {
          delRole(id).then(response => {
            resolve(response)
          }).catch(error => {
            reject(error)
          })
        })
      },
      delConfirm() {
        const ids = this.$refs.tableModel.sels.map(item => item.id)
        const other = this
        if (ids.length < 1) {
          return this.$message.warning('请选择要删除的数据')
        }

        this.$confirm('确认删除选中的' + ids.length + '条数据', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
          .then(_ => {
            this.deleteData(ids).then(response => {
              other.$message.success(response.msg)
              other.$refs.tableModel.getPage()
            })
          })
          .catch(_ => {})
      },
      handleCreate() {
        this.dialogFormVisible = true
        this.dialogStatus = 'create'
        if (this.dataForm.id !== '') {
          this.$refs['dataForm'].resetFields()
          this.resetForm()
        }
      },
      resetForm() {
        this.dataForm.id = ''
        this.dataForm.roleName = ''
        this.dataForm.description = ''
      },
      getPer(row) {
        this.perTree.permissionId = row.id
        this.perTree.dialogPermissionName = row.roleName
        this.dialogPermission = true
        this.getRolePerData(this.perTree.permissionId).then(response => {
          this.perTree.defaultData = response.data.map(item => item.perId)
          this.getTreeList()
        })
      },
      getTreeList() {
        const other = this
        treeList().then(response => {
          other.perTree.treeData = response.data
        })
      },
      confirmPermission() {
        const permission = this.$refs.tree.getCheckedKeys()
        this.addRolePer(this.perTree.permissionId, permission)
      },
      async getRolePerData(id) {
        const role = getRolePer(id)
        return role
      },
      addRolePer(id, ids) {
        const other = this
        addRolePer(id, objectToFormData({ 'permissions': ids.join() })).then(response => {
          other.$message.success(response.msg)
          other.dialogPermission = false
        })
      },
      setFunction(node, data) {
        let checked = false
        const nodes = this.$refs.tree.getNode(node.id)
        checked = nodes.checked

        if (checked) {
          if (nodes.parent.key !== undefined) {
            this.checkParent(nodes.parent, true)
          }

          if (nodes.childNodes.length > 0) {
            this.checkChildren(nodes.childNodes, true)
          }
        } else {
          if (nodes.childNodes.length > 0) {
            this.checkChildren(nodes.childNodes, false)
          }
          if (nodes.parent.key !== undefined) {
            this.checkParent(nodes.parent, false)
          }
        }
      },
      checkParent(node, checked) {
        if (checked) {
          this.$refs.tree.setChecked(node, checked)
          if (node.parent.key !== undefined) {
            this.checkParent(node.parent, checked)
          }
        } else {
          let flag = false
          node.childNodes.forEach(function(item) {
            if (item.checked) {
              flag = true
            }
          })

          if (!flag) {
            this.$refs.tree.setChecked(node, checked)
            if (node.parent.key !== undefined) {
              this.checkParent(node.parent, checked)
            }
          }
        }
      },
      checkChildren(nodes, checked) {
        const other = this
        nodes.forEach(function(node) {
          other.$refs.tree.setChecked(node, checked)
          if (node.childNodes.length > 0) {
            other.checkChildren(node.childNodes, checked)
          }
        })
      }
    }

  }
</script>

<style scoped>

</style>
