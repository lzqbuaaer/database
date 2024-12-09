<template>
  <div>
    <div class="card" style="width: 30%">
      <el-form :model="data.user" label-width="50px" style="padding-right: 50px">
        <el-form-item label="编号" prop="username">
          <el-input v-model="data.user.username" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.user.name" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item v-if="data.user.role==='STUDENT'" label="性别" prop="ssex">
          <el-input v-model="data.user.ssex" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item v-if="data.user.role==='TEACHER'" label="性别" prop="tsex">
          <el-input v-model="data.user.tsex" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item v-if="data.user.role==='STUDENT'" label="班级" prop="sclass">
          <el-input v-model="data.user.sclass" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item v-if="data.user.role==='STUDENT'" label="系别" prop="sdept">
          <el-input v-model="data.user.sdept" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item v-if="data.user.role==='TEACHER'" label="系别" prop="tdept">
          <el-input v-model="data.user.tdept" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item v-if="data.user.role==='TEACHER'" label="邮箱" prop="temail">
          <el-input v-model="data.user.temail" autocomplete="off" disabled/>
        </el-form-item>
      </el-form>
      <el-button type="info" @click="updateStudent" v-if="data.user.role==='STUDENT'">更改学生信息</el-button>
      <el-button type="info" @click="updateTeacher" v-if="data.user.role==='TEACHER'">更改教师信息</el-button>
      <el-button type="primary" @click="updatePassword">更改密码</el-button>
    </div>
    <el-dialog title="修改密码" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.user" label-width="100px" style="padding-right: 50px">
        <el-form-item label="旧密码" prop="oldPW">
          <el-input show-password v-model="data.oldPW" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="新密码" prop="InputPW">
          <el-input show-password v-model="data.InputPW" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="确认新密码" prop="reInputPW">
          <el-input show-password v-model="data.reInputPW" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <div>
    <el-dialog title="信息" width="40%" v-model="data.formVisibleStudent" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="学号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="性别" prop="ssex">
          <el-select v-model="data.form.ssex">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="sclass">
          <el-input v-model="data.form.sclass" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="系别" prop="sdept">
          <el-input v-model="data.form.sdept" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisibleStudent = false">取 消</el-button>
        <el-button type="primary" @click="saveStudent">保 存</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <div>
    <el-dialog title="信息" width="40%" v-model="data.formVisibleTeacher" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="编号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别" prop="tsex">
          <el-select v-model="data.form.tsex" >
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="系别" prop="tdept">
          <el-input v-model="data.form.tdept" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱" prop="temail">
          <el-input v-model="data.form.temail" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisibleTeacher = false">取 消</el-button>
        <el-button type="primary" @click="saveTeacher">保 存</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import axios from "axios";
import router from "@/router";
import md5 from 'js-md5';

const data = reactive({
  oldPW: '',
  InputPW: '',
  reInputPW: '',
  formVisible: false,
  isTrue: 'false',
  user: JSON.parse(localStorage.getItem('student-user') || '{}'),
  form: {},
  formVisibleStudent: false,
  formVisibleTeacher: false,
})

const updatePassword = () => {
  data.formVisible = true
}

const updateStudent = () => {
  data.form = Object.assign({}, data.user);
  data.formVisibleStudent = true
}

const updateTeacher = () => {
  data.form = Object.assign({}, data.user);
  data.formVisibleTeacher = true
}

const save = () => {
  let checkUrl;
  let updateUrl;
  switch (data.user.role) {
    case 'STUDENT':
      checkUrl = 'http://localhost:9090/student/checkPwd';
      updateUrl = 'http://localhost:9090/student/updateBySNO';
      break;
    case 'TEACHER':
      checkUrl = 'http://localhost:9090/teacher/checkPwd';
      updateUrl = 'http://localhost:9090/teacher/updateByTNO';
      break;
    case 'ADMIN':
      checkUrl = "http://localhost:9090/admin/checkPwd";
      updateUrl = 'http://localhost:9090/admin/updateByAdmin';
  }
  axios.request({
    url: checkUrl,
    method: 'GET',
    username: data.user.username,
    password: data.oldPW
  }).then(res => {
    if (res == 0) data.isTrue = 0;
    else data.isTrue = 1;
  })
  if (data.isTrue) {
    if (data.InputPW === data.reInputPW) {
      data.user.password = data.reInputPW;
      axios.request({
        url: updateUrl,
        method: 'PUT',
        data: data.user
      }).then(res => {
        if (res.data.code === '200') {
          data.formVisible = false;
          ElMessage.success('修改成功!');
          router.push('/login');
        } else {
          ElMessage.error("修改失败!");
        }
      })
    } else {
      ElMessage.error("确认密码与新密码不一致！");
    }
  } else {
    ElMessage.error("旧密码错误！");
  }
}

const saveStudent = () => {
  axios.request({
    url: 'http://localhost:9090/student/updateStudent',
    method: 'PUT',
    data: data.form
  }).then(res => {
    if (res.data.code === '200') {
      data.formVisibleStudent = false;
      Object.assign(data.user, data.form);
      ElMessage.success('操作成功!');
    } else {
      ElMessage.error("操作失败!");
    }
  })
}

const saveTeacher = () => {
  axios.request({
    url: 'http://localhost:9090/teacher/updateTeacher',
    method: 'PUT',
    data: data.form
  }).then(res => {
    if (res.data.code === '200') {
      data.formVisibleTeacher = false;
      Object.assign(data.user, data.form);
      ElMessage.success('操作成功!');
    } else {
      ElMessage.error("操作失败!");
    }
  })
}
</script>