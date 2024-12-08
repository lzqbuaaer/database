<template>
  <div>

    <div class="card" style="line-height: 30px; margin-bottom: 5px">
      <div>欢迎您，<span style="color: #6E77F2">{{ user.name }}</span> 祝您今天过得开心！</div>
    </div>

    <div style="display: flex">
      <div class="card" style="flex: 50%">
        <div style="font-size: 17px; font-weight: bold; padding: 10px 10px 20px 10px">系统公告</div>
        <el-timeline>
          <el-timeline-item
              v-for="(item, index) in data.noticeData"
              :key="index"
              :timestamp="item.time"
          >
            {{ item.title }} : {{ item.content }}
          </el-timeline-item>
        </el-timeline>
      </div>
      <div style="flex: 50%"></div>
    </div>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {reactive} from "vue";

const user = JSON.parse(localStorage.getItem('student-user') || '{}')

const data = reactive({
  noticeData: []
})

const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

loadNotice()
</script>