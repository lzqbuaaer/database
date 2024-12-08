<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" v-model="data.title" placeholder="请输入标题查询" :prefix-icon="Search"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="公告标题" prop="title"></el-table-column>
        <el-table-column label="公告内容" prop="content"></el-table-column>
        <el-table-column label="发布时间" prop="time"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.username)">删除</el-button>
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
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="data.form.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input type="textarea" :rows="4" v-model="data.form.content" autocomplete="off" />
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



const data = reactive({
  pageNum: 1,
  pageSize:10,
  username:'',
  title:'',
  isUpdate:1,
  formVisible: false,
  form: {},
  tableData: [],
  total:0
})
const load=()=>{
  axios.get('http://localhost:9090/notice/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      title:data.title
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
const handleAdd = () => {
  data.form = {}
  data.isUpdate=0;
  data.formVisible = true
}

const handleEdit = (row) => {
  data.isUpdate=1;
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true

}
const save=()=>{
  console.log(data.isUpdate);
  axios.request({
    url:data.isUpdate? 'http://localhost:9090/notice/updateById':'http://localhost:9090/notice/add',
    method:data.isUpdate? 'PUT':'POST',
    data:data.form
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
const handleDelete = (username) => {
  console.log(username);
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    axios.delete('http://localhost:9090/notice/deleteById/'+username).then(res=>{
      if(res.data.code=== '200'){
        load();
        ElMessage.success('操作成功!');
      }else {
        ElMessage.error("操作失败!");
      }
    })
  }).catch(err => {})
}
const reset=()=>{
  data.username='';
  data.title='';
  load();
}
</script>