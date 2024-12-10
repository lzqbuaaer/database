<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" v-model="data.cname" placeholder="请输入课程名查询" :prefix-icon="Search"/>
      <el-input style="width: 300px; margin-right: 10px" v-model="data.cno" placeholder="请输入课程号查询" :prefix-icon="Search"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="success" @click="exportData">导出</el-button>
        <el-upload v-if="data.user.role!=='STUDENT'"
                   style="display: inline-block; margin-left:10px"
                   action="http://localhost:9090/studentCourse/import"
                   :show-file-list="false" :on-success="importSuccess">
          <el-button type="info">导入</el-button>
        </el-upload>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="选课编号" prop="scId"></el-table-column>
        <el-table-column label="课程编号" prop="cno"></el-table-column>
        <el-table-column label="课程名称" prop="cname"></el-table-column>
        <el-table-column label="任课教师编号" prop="tno" v-if="data.user.role!=='TEACHER'"></el-table-column>
        <el-table-column label="课程学分" prop="ccredit"></el-table-column>
        <el-table-column label="学号" prop="sno" v-if="data.user.role!=='STUDENT'"></el-table-column>
        <el-table-column label="学生名称" prop="sname" v-if="data.user.role!=='STUDENT'"></el-table-column>
        <el-table-column label="成绩" prop="grade" v-if="true"></el-table-column>
        <el-table-column label="操作" align="center" width="300">
          <template v-slot="scope">
            <el-button type="danger" @click="del(scope.row)">退课</el-button>
            <el-button type="primary" @click="addGrade(scope.row)" v-if="data.user.role!=='STUDENT'">打分</el-button>
            <el-button type="info" @click="goToCoursePage(scope.row.cno)">课程详情</el-button>
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
        <el-form-item label="课程名称" prop="cname">
          <el-input v-model="data.form.cname" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="学号" prop="sno">
          <el-input v-model="data.form.sno" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="姓名" prop="sname">
          <el-input v-model="data.form.sname" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="分数" prop="grade">
          <el-input v-model="data.form.grade" autocomplete="off" />
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
import {useRouter} from 'vue-router';

const router = useRouter();
const data = reactive({
  pageNum: 1,
  pageSize:10,
  scId:'',
  cname:'',
  cno:'',
  ccredit:'',
  cdescribe:'',
  tno:'',
  grade:'',
  sname:'',
  user:JSON.parse(localStorage.getItem('student-user') || '{}'),
  tableData: [],
  form:{},
  total:0
})
const load=()=>{
  let params={
    pageNum:data.pageNum,
    pageSize:data.pageSize,
    username:data.user.name,
    userRole:data.user.role,
    cname:data.cname,
    cno:data.cno,
  }
  if(data.user.role==='STUDENT'){
    params.sno=data.user.username;
  }else if(data.user.role==='TEACHER'){
    params.tno=data.user.username;
  }
  axios.get('http://localhost:9090/studentCourse/selectPage',{
    params:params
  }).then( res=>{
    console.log(res.data.data);
    data.tableData=res.data.data?.list||[]
    data.total=res.data.data?.total||0
  })
}
load();//调用函数获取后台数据
const handleCurrentChange=(pageNum)=>{
  load();//点击翻页时重新加载数据
}
const del = (row) => {
  ElMessageBox.confirm('您确定退选该课程吗?', '确认', { type: 'warning' }).then(res => {
    axios.delete('http://localhost:9090/studentCourse/deleteByScId/'+row.scId,
        {
          params:{
            username:data.user.name,
            userRole:data.user.role
          }
        }).then(res=>{
      if(res.data.code=== '200'){
        load();
        ElMessage.success('操作成功!');
      }else {
        ElMessage.error("操作失败!");
      }
    })
  }).catch(err => {})
}
const addGrade=(row)=>{
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const save=()=>{
  axios.put('http://localhost:9090/studentCourse/addGrade',{
    scId: data.form.scId,
    grade: data.form.grade,
  },{
    params:{
      username:data.user.name,
      userRole:data.user.role
    }
  }).then(res=>{
    if(res.data.code=== '200'){
      load();
      data.formVisible = false;
      ElMessage.success('操作成功!');
    }else {
      ElMessage.error("操作失败!");
    }
  })
}
const reset=()=>{
  data.cname='';
  data.cno='';
  load();
}

const exportData = () => {
  if (data.user.role==='TEACHER') {
    const params = new URLSearchParams({
      cname: data.cname || '',  // 课程名称
      cno: data.cno || '',      // 课程编号
      tno: data.user.username || '',
    });

    window.open(`http://localhost:9090/studentCourse/export?${params.toString()}`);
  } else if (data.user.role==='STUDENT') {
    const params = new URLSearchParams({
      cname: data.cname || '',  // 课程名称
      cno: data.cno || '',      // 课程编号
      sno: data.user.username || '',
    });

    window.open(`http://localhost:9090/studentCourse/export?${params.toString()}`);
  } else {
    const params = new URLSearchParams({
      cname: data.cname || '',  // 课程名称
      cno: data.cno || '',      // 课程编号
    });

    // 打开导出链接，传递查询参数
    window.open(`http://localhost:9090/studentCourse/export?${params.toString()}`);
  }
}

const importSuccess = (res) => {
  if (res.code === '200') {
    ElMessage.success("导入成功")
    load()
  } else {
    ElMessage.error(res.msg)
  }
}

const goToCoursePage = (cno) => {
  router.push(`/course/${cno}`)
}
</script>