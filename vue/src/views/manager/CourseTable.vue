<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <div
          style="font-size: 25px; font-weight: bold; padding: 10px 10px 20px 10px;text-align: center;margin:auto;width: 100px;">
        课程表
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="时间/课程">
          <template v-slot="scope">
            <div  v-if="scope.row.section">
              <div style="margin: 10px 0">第{{ scope.row.section.num }}大节</div>
              <div style="margin: 10px 0">{{ scope.row.section.time }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周一">
          <template v-slot="scope">
            <div  v-if="scope.row.mon">
              <div style="margin: 10px 0">{{ scope.row.mon.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.mon.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.mon.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周二">
          <template v-slot="scope">
            <div  v-if="scope.row.tue">
              <div style="margin: 10px 0">{{ scope.row.tue.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.tue.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.tue.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周三">
          <template v-slot="scope">
            <div  v-if="scope.row.wed">
              <div style="margin: 10px 0">{{ scope.row.wed.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.wed.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.wed.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周四">
          <template v-slot="scope">
            <div  v-if="scope.row.thu">
              <div style="margin: 10px 0">{{ scope.row.thu.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.thu.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.thu.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周五">
          <template v-slot="scope">
            <div  v-if="scope.row.fri">
              <div style="margin: 10px 0">{{ scope.row.fri.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.fri.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.fri.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周六">
          <template v-slot="scope">
            <div  v-if="scope.row.sat">
              <div style="margin: 10px 0">{{ scope.row.sat.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.sat.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.sat.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="周日">
          <template v-slot="scope">
            <div  v-if="scope.row.sun">
              <div style="margin: 10px 0">{{ scope.row.sun.course.cname }}</div>
              <div style="margin: 10px 0">{{ scope.row.sun.teacher.tname }}</div>
              <div style="margin: 10px 0">{{ scope.row.sun.course.cclassroom }}</div>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import axios from "axios";

const data = reactive({
  pageNum: 1,
  pageSize: 10,
  scId: '',
  cname: '',
  cno: '',
  ccredit: '',
  cdescribe: '',
  tno: '',
  grade: '',
  sname: '',
  user: JSON.parse(localStorage.getItem('student-user') || '{}'),
  tableData: [],
  form: {},
  total: 0
})
const load = () => {
  let params={}
  if(data.user.role==='STUDENT'){
    params.sno=data.user.username;
  }else if(data.user.role==='TEACHER'){
    params.tno=data.user.username;
  }
  axios.get('http://localhost:9090/studentCourse/courseTable',{
    params:params
  }).then( res=>{
    console.log(res.data.data);
    data.tableData=res.data.data
  })
}
load();//调用函数获取后台数据

</script>