<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus">添加</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="delSConfirm" type="danger" icon="el-icon-delete">删除</el-button>
    </div>

    <el-table ref="table" :default-sort = "{prop: 'loginNum', order: 'descending'}" :data="list" v-loading.body="listLoading" @row-click="handleRowChange" @selection-change="selsChange"  element-loading-text="Loading" border fit highlight-current-row >
      <el-table-column type="selection">
      </el-table-column>
      <el-table-column align="center" label='ID' width="95">
        <template slot-scope="scope">
          {{scope.$index}}
        </template>
      </el-table-column>
      <el-table-column label="姓名" >
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column label="帐号" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.account}}</span>
        </template>
      </el-table-column>
      <el-table-column label="登入次数" prop="loginNum" sortable align="center">
        <template slot-scope="scope">
          <span>{{scope.row.loginNum}}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center">
        <template slot-scope="scope">
          {{scope.row.gender == '0'?'男':'女'}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="small" icon="el-icon-edit" @click="getData(scope.row.id)" >编缉</el-button>
          <el-button type="danger" size="small" icon="el-icon-delete" @click="delConfirm(scope.row.id)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.curPage" :page-sizes="[10,20,30,50]" :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="page.totalPage">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">

      <el-form :model="dataForm" :rules="rules" ref="dataForm" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="帐号" prop="account">
          <el-input v-model="dataForm.account"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="dataForm.name"></el-input>
        </el-form-item>
        <el-form-item label="姓别">
          <el-radio-group v-model="dataForm.gender">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action=""
            :http-request="uploadFile"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload">
            <img v-if="dataForm.headPortrait" :src="dataForm.headPortrait" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="dataForm.isEnable">
            <el-radio label="0">启用</el-radio>
            <el-radio label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="管理员类型" prop="adminType">
          <el-select v-model="dataForm.adminType" placeholder="请选择管理员类型">
            <el-option v-for="item in adminOption" :key="item.id" :label="item.roleName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="dataForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="dataForm.email"></el-input>
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

  </div>



</template>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

<script>
  import { getList, addAdmin, checkAccount, getAdminById, updateAdmin, delAdmin } from '@/api/admin'
  import { getAllList } from '@/api/role'
  import { upload } from '@/api/common'
  import { validateEmail } from '@/utils/validate'
  import { objectToFormData } from '@/utils'

  export default {
    data() {
      const validateAcc = (rule, value, callback) => {
        const formData = new FormData()
        formData.append('account', value)
        formData.append('id', this.dataForm.id)
        checkAccount(formData).then(response => {
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

      const emailValidate = (rule, value, callback) => {
        if (value !== '') {
          if (!validateEmail(value)) {
            callback(new Error('请输入正确的邮箱格式'))
          }
        }

        callback()
      }

      return {
        list: null,
        listLoading: true,
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '更新',
          create: '创建'
        },
        page: {
          curPage: 1,
          pageSize: 10,
          totalPage: 1
        },
        sels: [],
        dataForm: {
          id: '',
          account: '',
          headPortrait: '',
          name: '',
          gender: '0',
          phone: '',
          email: '',
          isEnable: '0',
          adminType: '',
          description: ''
        },
        adminOption: [],
        rules: {
          account: [
            { required: true, message: '请输入帐号', trigger: 'change' },
            { pattern: /^[a-zA-Z]\w{2,9}$/, trigger: 'blur', message: '以字母开头，长度在3-10之间， 只能包含字符、数字和下划线' },
            { trigger: 'blur', validator: validateAcc }
          ],
          adminType: [
            { required: true, message: '请选择管理员类型', trigger: 'change' }
          ],
          name: [
            { required: true, message: '请输入您的姓名', trigger: 'change' }
          ],
          phone: [
            { pattern: /^[0-9]{11}$/, trigger: 'change', message: '请输入正确的手机号' }
          ],
          email: [
            { trigger: 'change', validator: emailValidate }
          ]
        }
      }
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger'
        }
        return statusMap[status]
      }
    },
    created() {
      this.fetchData()
      this.getRole()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getList(this.page.curPage, { 'pageSize': this.page.pageSize }).then(response => {
          this.list = response.data.list
          this.page.curPage = response.data.pageNumber
          this.page.pageSize = response.data.pageSize
          this.page.totalPage = response.data.totalPage
          this.listLoading = false
        })
      },
      getRole() {
        getAllList().then(response => {
          this.adminOption = response.data
        })
      },
      getData(id) {
        const other = this
        if (this.$refs['dataForm'] !== undefined) {
          this.$refs['dataForm'].resetFields()
        }

        getAdminById(id).then(response => {
          other.dataForm.id = response.data.id
          other.dataForm.name = response.data.name
          other.dataForm.headPortrait = response.data.headPortrait
          other.dataForm.account = response.data.account
          other.dataForm.gender = response.data.gender
          other.dataForm.phone = response.data.phone
          other.dataForm.email = response.data.email
          other.dataForm.isEnable = response.data.isEnable
          other.dataForm.adminType = response.data.adminType
          other.dataForm.description = response.data.description
          other.dialogFormVisible = true
          other.dialogStatus = 'update'
        })
      },
      handleSizeChange(val) {
        this.page.pageSize = val
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.page.curPage = val
        this.fetchData()
      },
      handleCreate() {
        this.dialogFormVisible = true
        this.dialogStatus = 'create'
        if (this.dataForm.id !== '') {
          this.$refs['dataForm'].resetFields()
          this.resetForm()
        }
      },
      uploadFile(item) {
        const formData = new FormData()
        formData.append('file', item.file)
        const other = this
        upload(formData).then(response => {
          other.dataForm.headPortrait = '/api/' + response.data
        })
      },
      resetForm() {
        this.dataForm.id = ''
        this.dataForm.name = ''
        this.dataForm.headPortrait = ''
        this.dataForm.account = ''
        this.dataForm.gender = '0'
        this.dataForm.phone = ''
        this.dataForm.email = ''
        this.dataForm.isEnable = '0'
        this.dataForm.adminType = ''
        this.dataForm.description = ''
      },
      createData(formName) {
        const other = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            other.dialogFormVisible = false
            addAdmin(objectToFormData(this.dataForm)).then(response => {
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
            updateAdmin(this.dataForm).then(response => {
              if (response.code === 0) {
                other.$message.success(response.msg)
                other.fetchData()
                this.$refs['dataForm'].resetFields()
                this.resetForm()
              } else {
                other.$message.error(response.msg)
              }
            })
          }
        })
      },
      delConfirm(id) {
        this.$confirm('确认删除该管理员？', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
          .then(_ => {
            this.delData(id)
          })
          .catch(_ => {})
      },
      delSConfirm() {
        const ids = this.sels.map(item => item.id)
        if (ids.length < 1) {
          return this.$message.warning('请选择要删除的管理员')
        }

        this.$confirm('确认删除选中的？' + ids.length + '个管理员', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
          .then(_ => {
            this.delData(ids)
          })
          .catch(_ => {})
      },
      selsChange(sels) {
        this.sels = sels
      },
      delData(id) {
        const other = this
        delAdmin(id).then(response => {
          if (response.code === 0) {
            other.$message.success(response.msg)
            other.fetchData()
          } else {
            other.$message.error(response.msg)
          }
        })
      },
      handleRowChange(row, event, column) {
        this.$refs.table.toggleRowSelection(row)
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isLt2M
      }
    }
  }
</script>
