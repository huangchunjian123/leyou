<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎您！</div>
    <el-card shadow="hover" class="box-card">
      <div class="component-item user-info">
        <div>
        <pan-thumb width="100px" height="100px" :image="userInfo.headPortrait">
          {{userInfo.name}}
        </pan-thumb>
      </div>
        <div class="user-info-cont">
          <div class="user-info-name">{{userInfo.name}}</div>
          <div>{{userInfo.name}}</div>
        </div>
      </div>
      <div class="user-info-list">上次登录时间：<span>{{userInfo.lastLogin}}</span></div>
      <div class="user-info-list">上次登录地点：<span>{{userInfo.lastLoginIp}}</span></div>
    </el-card>
    <el-card shadow="hover">
      <div slot="header" class="clearfix">
        <span>语言详情</span>
      </div>
      Vue
      <el-progress :percentage="57.2" color="#42b983"></el-progress>
      JavaScript
      <el-progress :percentage="29.8" color="#f1e05a"></el-progress>
      CSS
      <el-progress :percentage="11.9"></el-progress>
      HTML
      <el-progress :percentage="1.1" color="#f56c6c"></el-progress>
    </el-card>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import PanThumb from '@/components/PanThumb'

  export default {
    name: 'dashboard',
    components: {
      PanThumb
    },
    computed: {
      ...mapGetters([
        'userInfo'
      ])
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }
    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }

  .user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
  }

  .user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
  }

  .user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
  }

  .user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
  }

  .user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
  }
</style>
