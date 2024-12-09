import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Manager',
            component: () => import('@/views/Manager.vue'),
            redirect: '/login',
            children: [
                {path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
                {path: 'course', name: 'Course', component: () => import('@/views/manager/Course.vue')},
                {path: 'student', name: 'Student', component: () => import('@/views/manager/Student.vue')},
                {path: 'teacher', name: 'Teacher', component: () => import('@/views/manager/Teacher.vue')},
                {path: 'person', name: 'Person', component: () => import('@/views/manager/Person.vue')},
                {path: 'notice', name: 'Notice', component: () => import('@/views/manager/Notice.vue')},
                {path: 'log', name: 'Log', component: () => import('@/views/manager/Log.vue')},
                {path: 'courselist', name: 'CourseList', component: () => import('@/views/manager/CourseList.vue')},
                {path: 'coursetable', name: 'CourseTable', component: () => import('@/views/manager/CourseTable.vue')},
                {
                    path: 'studentcourseinfo',
                    name: 'StudentCourseInfo',
                    component: () => import('@/views/manager/StudentCourseInfo.vue')
                },
            ]
        },
        {
            path: '/login',
            name: 'Login',
            component: () => import('@/views/Login.vue'),
        },
        {
            path: '/register',
            name: 'Register',
            component: () => import('@/views/Register.vue'),
        }
    ]
})

export default router
