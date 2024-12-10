<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" v-model="data.cname" placeholder="请输入课程名查询"
                :prefix-icon="Search"/>
      <el-input style="width: 300px; margin-right: 10px" v-model="data.cno" placeholder="请输入课程号查询"
                :prefix-icon="Search"/>
      <el-input style="width: 300px; margin-right: 10px" v-model="data.tno" placeholder="请输入教师编号查询"
                :prefix-icon="Search" v-if="user.role!=='TEACHER'"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd" v-if="user.role!=='TEACHER'">新增</el-button>
        <el-button type="success" @click="exportData">导出</el-button>
        <el-upload style="display: inline-block; margin-left:10px" action="http://localhost:9090/course/import"
                   :show-file-list="false" :on-success="importSuccess" v-if="user.role!=='TEACHER'">
          <el-button type="info">导入</el-button>
        </el-upload>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="课程编号" prop="cno"></el-table-column>
        <el-table-column label="课程名称" prop="cname"></el-table-column>
        <el-table-column label="任课教师编号" prop="tno"></el-table-column>
        <el-table-column label="课程学分" prop="ccredit"></el-table-column>
        <el-table-column label="课程描述" prop="cdescribe"></el-table-column>
        <el-table-column label="时间" align="center">
          <template #default="scope">
            {{ data.weekDays[scope.row.cday - 1] }}-第{{ scope.row.ctime }}大节
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)" v-if="user.role!=='TEACHER'">编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.cno)" v-if="user.role!=='TEACHER'">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"
                     @current-change="handleCurrentChange"/>
    </div>

    <el-dialog title="信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="课程编号" prop="cno">
          <el-input v-model="data.form.cno" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="课程名称" prop="cname">
          <el-input v-model="data.form.cname" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="教师编号" prop="tno">
          <el-input v-model="data.form.tno" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="课程学分" prop="ccredit">
          <el-input v-model="data.form.ccredit" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="课程描述" prop="cdescribe">
          <el-input v-model="data.form.cdescribe" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="星期：" prop="cday">
          <el-select v-model="data.form.cday">
            <el-option
                v-for="(day, index) in data.weekDays"
                :key="index"
                :label="day"
                :value="index + 1"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程节号：" prop="ctime">
          <el-select v-model="data.form.ctime">
            <el-option label="1" value="1"/>
            <el-option label="2" value="2"/>
            <el-option label="3" value="3"/>
            <el-option label="4" value="4"/>
            <el-option label="5" value="5"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import {reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import axios from "axios";
import {Search} from "@element-plus/icons-vue";

const user = JSON.parse(localStorage.getItem('student-user') || '{}');
const data = reactive({
  pageNum: 1,
  pageSize: 10,
  username: user.name,
  userRole: user.role,
  cname: '',
  cno: '',
  ccredit: '',
  cdescribe: '',
  tno: '',
  formVisible: false,
  form: {},
  tableData: [],
  total: 0,
  weekDays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
})
const load = () => {
  axios.get('http://localhost:9090/course/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.username,
      userRole: data.userRole,
      cname: data.cname,
      cno: data.cno,
      tno: data.userRole === "TEACHER" ? user.username : data.tno,

    }
  }).then(res => {
    console.log(res.data.data);
    ElMessage.success('查询完成！');
    data.tableData = res.data.data?.list || []
    data.total = res.data.data?.total || 0
  })
}
load();//调用函数获取后台数据
const handleCurrentChange = (pageNum) => {
  load();//点击翻页时重新加载数据
}
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const exportData = () => {
  // 构建查询字符串
  const params = new URLSearchParams({
    cname: data.cname || '',  // 课程名称
    cno: data.cno || '',      // 课程编号
    tno: data.userRole === "TEACHER" ? user.username : data.tno,      // 教师编号
  });

  // 打开导出链接，传递查询参数
  window.open(`http://localhost:9090/course/export?${params.toString()}`);
}

const importSuccess = (res) => {
  if (res.code === '200') {
    ElMessage.success("导入成功")
    load()
  } else {
    ElMessage.error(res.msg)
  }
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true

}
const save = () => {
  console.log(data.form.id);
  axios.request({
    url: data.form.id ? 'http://localhost:9090/course/updateByCNO' : 'http://localhost:9090/course/addCourseInfo',
    method: data.form.id ? 'PUT' : 'POST',
    data: data.form,
    params: {
      username: data.username,
      userRole: data.userRole
    }
  }).then(res => {
    if (res.data.code === '200') {
      load();
      data.formVisible = false;
      ElMessage.success('操作成功!');
    } else {
      ElMessage.error("操作失败!");
    }
  })
}
const handleDelete = (cno) => {
  console.log(cno);
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', {type: 'warning'}).then(res => {
    axios.delete('http://localhost:9090/course/deleteByCNO/' + cno,
        {
          params: {
            username: data.username,
            userRole: data.userRole
          }
        }
    ).then(res => {
      if (res.data.code === '200') {
        load();
        ElMessage.success('操作成功!');
      } else {
        ElMessage.error("操作失败!");
      }
    })
  }).catch(err => {
  })
}
const reset = () => {
  data.cname = '';
  data.cno = '';
  data.tno = '';
  load();
}
</script>