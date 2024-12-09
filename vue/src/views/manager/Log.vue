<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" v-model="data.operatorName" placeholder="请输入操作者名字查询" :prefix-icon="Search"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData">
        <el-table-column label="操作者名字" prop="operatorName"></el-table-column>
        <el-table-column label="操作者角色" prop="operatorRole"></el-table-column>
        <el-table-column label="操作内容" prop="operation"></el-table-column>
        <el-table-column label="操作时间" prop="time"></el-table-column>
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
  username:'',
  operatorName:'',
  isUpdate:1,
  form: {},
  tableData: [],
  total:0
})
const load=()=>{
  axios.get('http://localhost:9090/log/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      operatorName:data.operatorName
    }
  }).then( res=>{
    console.log(res.data.data);
    ElMessage.success('查询完成！');
    data.tableData=res.data.data?.list||[]
    data.total=res.data.data?.total||0
  })
}
load();//调用函数获取后台数据
const handleCurrentChange=(pageNum)=>{
  load();//点击翻页时重新加载数据
}

const reset=()=>{
  data.username='';
  data.operatorName='';
  load();
}
</script>