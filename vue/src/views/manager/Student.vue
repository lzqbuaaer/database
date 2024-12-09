<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" v-model="data.name" placeholder="请输入姓名查询" :prefix-icon="Search"/>
      <el-input style="width: 300px; margin-right: 10px" v-model="data.username" placeholder="请输入学号查询" :prefix-icon="Search"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="exportData">导出</el-button>
        <el-upload style="display: inline-block; margin-left:10px" action="http://localhost:9090/student/import" :show-file-list="false" :on-success="importSuccess">
          <el-button type="info">导入</el-button>
        </el-upload>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="学号" prop="username"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="ssex"></el-table-column>
        <el-table-column label="班级" prop="sclass"></el-table-column>
        <el-table-column label="系别" prop="sdept"></el-table-column>
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
        <el-form-item label="学号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别" prop="ssex">
          <el-select v-model="data.form.ssex" >
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="sclass">
          <el-input v-model="data.form.sclass" autocomplete="off" />
        </el-form-item>
        <el-form-item label="系别" prop="sdept">
          <el-input v-model="data.form.sdept" autocomplete="off" />
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
  name:'',
  ssex:'',
  sclass:'',
  sdept:'',
  password:'123456',
  role:'STUDENT',
  isUpdate:1,
  formVisible: false,
  form: {},
  tableData: [],
  total:0
})
const load=()=>{
  axios.get('http://localhost:9090/student/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      username:data.username,
      name:data.name
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
const handleAdd = () => {
  data.form = {}
  data.isAdd=0;
  data.formVisible = true
}

const exportData = () => {
  // 构建查询字符串
  const params = new URLSearchParams({
    username: data.username || '',
    name: data.name || '',
  });

  // 打开导出链接，传递查询参数
  window.open(`http://localhost:9090/student/export?${params.toString()}`);
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
  data.isAdd=1;
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true

}
const save=()=>{
  console.log(data.isAdd);
  axios.request({
    url:data.isAdd? 'http://localhost:9090/student/updateBySNO':'http://localhost:9090/student/addStudentInfo',
    method:data.isAdd? 'PUT':'POST',
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
    axios.delete('http://localhost:9090/student/deleteBySNO/'+username).then(res=>{
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
  data.name='';
  load();
}
</script>