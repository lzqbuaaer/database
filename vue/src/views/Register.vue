<template>
  <div>
    <div class="login-container">
      <div style="width: 400px" class="login-box">
        <div style="front-weight:bold;front-size:24px; text-align:center; margin-bottom: 30px">欢 迎 您 注 册</div>
        <el-form ref="formRef" :model="data.form" :rules="rules">
          <el-form-item prop="username">
            <el-input prefix-icon="User" v-model="data.form.username" placeholder="输入您想设置的管理员账号"/>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password prefix-icon="Lock" v-model="data.form.password" placeholder="输入密码"/>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input show-password prefix-icon="Lock" v-model="data.form.confirmPassword" placeholder="请确认密码"/>
          </el-form-item>
          <el-form-item prop="role">
            <el-select style="width: 100%" v-model="data.form.role">
              <el-option value="ADMIN" label="管理员"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click.native="register">注册</el-button>
            <el-button type="info" style="margin-left: 63%; " @click.native="resetForm">重置</el-button>
          </el-form-item>
          <div style="text-align: center; margin-top: 20px">
            <router-link to="/login">已有帐号，前往登录</router-link>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";
import qs from 'qs'
import axios from "axios";

const data = reactive({
  form: {
    role: 'ADMIN'
  },
})

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次确认密码'))
  } else if (value !== data.form.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
  ],
  confirmPassword: [
    {required: true, message: '请再次输入密码', trigger: 'blur'},
    {validator: validatePass, trigger: 'blur'}
  ],
})

const formRef = ref()

const register = () => {


  formRef.value.validate((valid) => {

    if (valid) {
      let tabledata = JSON.stringify(data.form);
      console.log(tabledata);
      axios.post('http://localhost:9090/register', tabledata, {
        headers: {
          "Content-Type": "application/json",
        },
      }).then(res => {
        console.log(res.data.code);
        if (res.data.code === '200') {
          ElMessage.success('注册成功');
          router.push('/login');
        } else {
          ElMessage.error("用户名已经存在！");
        }
      })
    } else {
      console.log('error submit!')
    }


  })
}
const resetForm = () => {
  formRef.value.resetFields();   // 表单重置
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("../assets/imgs/bg1.jpg");
  background-size: cover;
}

.login-box {
  background-color: rgba(255, 255, 255, 0.85);
  box-shadow: 0 0 10px rgba(0, 0, 0.1);
  border: 1px solid #addd;
  padding: 30px;
  -webkit-user-select: none;
}

.login_Button {
  -webkit-user-select: auto; /* Safari */
  user-select: none;
}
</style>