<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" v-model="data.cname" placeholder="请输入课程名查询" :prefix-icon="Search"/>
      <el-input style="width: 300px; margin-right: 10px" v-model="data.cno" placeholder="请输入课程号查询" :prefix-icon="Search"/>
      <el-input style="width: 300px; margin-right: 10px" v-model="data.tno" placeholder="请输入教师编号查询" :prefix-icon="Search"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData">
        <el-table-column label="课程编号" prop="cno"></el-table-column>
        <el-table-column label="课程名称" prop="cname"></el-table-column>
        <el-table-column label="任课教师编号" prop="tno"></el-table-column>
        <el-table-column label="课程学分" prop="ccredit"></el-table-column>
        <el-table-column label="课程描述" prop="cdescribe"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template v-slot="scope">
            <el-button type="primary" @click="handleSelect(scope.row)">选课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"
                     @current-change="handleCurrentChange"/>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import axios from "axios";
import {Search} from "@element-plus/icons-vue";

const data = reactive({
  pageNum: 1,
  pageSize:10,
  cname:'',
  cno:'',
  ccredit:'',
  cdescribe:'',
  tno:'',
  user:JSON.parse(localStorage.getItem('student-user') || '{}'),
  tableData: [],
  total:0
})
const load=()=>{
  axios.get('http://localhost:9090/course/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      cname:data.cname,
      cno:data.cno,
      tno:data.tno,

    }
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
const handleSelect = (row) => {
  axios.post('http://localhost:9090/studentCourse/addSC',{sno:data.user.username,cno:row.cno}).then(res=>{
      if(res.data.code==='200'){
        ElMessage.success('选课成功！');
      }else{
        ElMessage.error(res.data.msg);
      }
  })

}
const reset=()=>{
  data.cname='';
  data.cno='';
  data.tno='';
  load();
}
</script>