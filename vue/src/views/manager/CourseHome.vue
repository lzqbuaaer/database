<template>
  <el-container class="course-home">
    <el-header>
      <h2 class="course-title">课程主页</h2>
    </el-header>
    <el-main>
      <el-card class="course-card">
        <p><strong>课程编号：</strong>{{ data.course.cno }}</p>
        <p><strong>课程名称：</strong>{{ data.course.cname }}</p>
        <p><strong>任课教师编号：</strong>{{ data.course.tno }}</p>
        <p><strong>课程学分：</strong>{{ data.course.ccredit }}</p>
        <p><strong>上课时间：</strong>{{ data.weekDays[data.course.cday - 1] }} - 第{{ data.course.ctime }}大节</p>
        <p><strong>课程描述：</strong>{{ data.course.cdescribe }}</p>
      </el-card>

      <el-card class="discussion-card">
        <h3>课程讨论区</h3>
        <div v-if="data.comments.length > 0">
          <el-divider>讨论内容</el-divider>
          <div v-for="comment in data.comments" :key="comment.id" class="comment-item">
            <p><strong>{{ comment.userName }}:</strong> {{ comment.content }}</p>
            <p><small class="comment-time">发表于：{{ comment.time }}</small></p>
          </div>
        </div>
        <el-pagination background layout="prev, pager, next"
                       v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"
                       @current-change="handleCurrentChange"/>

        <!-- 发言框 -->
        <el-divider>发表新讨论</el-divider>
        <el-input
            v-model="data.newComment"
            placeholder="请输入你的讨论内容..."
            type="textarea"
            :rows="4"
            class="comment-input"
        ></el-input>

        <!-- 提交按钮 -->
        <el-button
            type="primary"
            @click="submitComment"
            :loading="data.loading"
        >
          提交
        </el-button>
      </el-card>
    </el-main>
  </el-container>
</template>

<script setup>
import {reactive, onMounted} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const route = useRoute();
const user = JSON.parse(localStorage.getItem('student-user') || '{}');
const data = reactive({
  course: [],
  weekDays: ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
  cno: '',
  comments: [], // 保存课程讨论
  newComment: '', // 新评论内容
  loading: false, // 控制提交按钮的加载状态
  pageNum: 1,
  pageSize: 5,
  total : 0,
});

onMounted(() => {
  data.cno = route.params.cno; // 从路由参数中获取课程编号
  load();
  loadComments();
});

const load = () => {
  axios.get(`http://localhost:9090/course/${data.cno}`).then(res => {
    ElMessage.success('查询完成！');
    data.course = res.data.data;
  });
};

const loadComments = () => {
  axios.get(`http://localhost:9090/course/selectComments`, {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      cno: data.cno,
    }
  }).then(res => {
    console.log(res.data);  // 打印返回的评论数据
    data.comments = res.data.data.list;
    data.total = res.data.data.total; // 如果返回了总页数，也可以更新
  }).catch(error => {
    ElMessage.error('加载评论失败！');
  });
};

const submitComment = () => {
  if (!data.newComment.trim()) {
    ElMessage.warning('评论内容不能为空');
    return;
  }

  data.loading = true;

  axios.post(`http://localhost:9090/course/addComment`, {
    cno: data.cno,
    userId: user.username, // 假设用户信息保存在 localStorage 中
    userName: user.name || '匿名用户',
    content: data.newComment,
    time: new Date().toLocaleString(),
  }).then(res => {
    ElMessage.success('评论提交成功！');
    console.log('评论提交成功：', res.data); // 打印响应数据，确认是否有返回
    loadComments()
    data.newComment = ''; // 清空输入框
    data.loading = false;
  }).catch(error => {
    ElMessage.error('评论提交失败！');
  }).finally(() => {
    data.loading = false;
  });
};

const handleCurrentChange = (pageNum) => {
  loadComments();//点击翻页时重新加载数据
}
</script>

<style scoped>
.course-home {
  padding: 30px;
  background-color: #f5f5f5;
}

.course-title {
  font-size: 24px;
  font-weight: 600;
  color: #333;
  text-align: center;
}

.course-card {
  margin-bottom: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.discussion-card {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.comment-item {
  background-color: #f9f9f9;
  border-left: 4px solid #42b983;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 4px;
}

.comment-time {
  color: #999;
}

.comment-input {
  margin-bottom: 10px;
  border-radius: 6px;
}
</style>
