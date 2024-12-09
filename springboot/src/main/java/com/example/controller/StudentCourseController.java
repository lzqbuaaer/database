package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Course;
import com.example.entity.StudentCourse;
import com.example.entity.Teacher;
import com.example.service.CourseService;
import com.example.service.StudentCourseService;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {
    @Resource
    private StudentCourseService studentCourseService;

    @Resource
    private CourseService courseService;

    @Resource
    private TeacherService teacherService;

    @PostMapping("addSC")
    Result addSC(@RequestBody StudentCourse studentCourse) {
        studentCourseService.addSC(studentCourse);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             StudentCourse studentCourse) {
        PageInfo<StudentCourse> pageInfo = studentCourseService.selectPage(pageNum, pageSize, studentCourse);
        return Result.success(pageInfo);
    }

    @DeleteMapping("/deleteByScId/{scId}")
    public Result deleteByScId(@PathVariable Integer scId) {
        studentCourseService.deleteByScId(scId);
        return Result.success();
    }

    @PutMapping("/addGrade")
    public Result addGrade(@RequestBody StudentCourse studentCourse) {
        studentCourseService.addGrade(studentCourse);
        return Result.success();
    }

    @GetMapping("/courseTable")
    public Result getCourseTable(StudentCourse studentCourse) {
        List<StudentCourse> scs = studentCourseService.selectAll(studentCourse);
        List<JSONObject> list = CollUtil.newArrayList();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        JSONObject jsonObject4 = new JSONObject();
        JSONObject jsonObject5 = new JSONObject();
        jsonObject1.set("section", JSONUtil.parseObj("{\"num\": 1, \"time\": \"08:00-09:35\"}"));
        jsonObject2.set("section", JSONUtil.parseObj("{\"num\": 2, \"time\": \"09:50-12:15\"}"));
        jsonObject3.set("section", JSONUtil.parseObj("{\"num\": 3, \"time\": \"14:00-15:35\"}"));
        jsonObject4.set("section", JSONUtil.parseObj("{\"num\": 4, \"time\": \"15:50-18:15\"}"));
        jsonObject5.set("section", JSONUtil.parseObj("{\"num\": 5, \"time\": \"19:00-20:35\"}"));
        list.add(jsonObject1);
        list.add(jsonObject2);
        list.add(jsonObject3);
        list.add(jsonObject4);
        list.add(jsonObject5);
        scs.forEach(sc -> {
            String cno = sc.getCno();
            String tno = sc.getTno();
            Course course = courseService.selectByCNO(cno);
            Teacher teacher = teacherService.selectByTNO(tno);
            int cday = course.getCday();
            int ctime = course.getCtime();
            setJson(cday, course, teacher, list.get(ctime - 1));
        });
        System.out.println(list);
        return Result.success(list);
    }

    private void setJson(int cday, Course course, Teacher teacher, JSONObject jsonObject) {
        JSONObject comb = new JSONObject();
        comb.put("course", course);
        comb.put("teacher", teacher);
        switch (cday) {
            case 1:
                jsonObject.set("mon", comb);
                break;
            case 2:
                jsonObject.set("tue", comb);
                break;
            case 3:
                jsonObject.set("wed", comb);
                break;
            case 4:
                jsonObject.set("thu", comb);
                break;
            case 5:
                jsonObject.set("fri", comb);
                break;
            case 6:
                jsonObject.set("sat", comb);
                break;
            case 7:
                jsonObject.set("sun", comb);
                break;
            default:
                break;
        }
    }

    @GetMapping("/export")
    public void export(StudentCourse studentCourse, HttpServletResponse response) throws IOException {
        List<StudentCourse> courseList = studentCourseService.selectAll(studentCourse);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.setOnlyAlias(true);

        writer.addHeaderAlias("scId", "选课编号");
        writer.addHeaderAlias("cno", "课程编号");
        writer.addHeaderAlias("cname", "课程名称");
        writer.addHeaderAlias("tno", "任课教师编号");
        writer.addHeaderAlias("ccredit", "课程学分");
        writer.addHeaderAlias("sno", "学号");
        writer.addHeaderAlias("sname", "学生名称");
        writer.addHeaderAlias("grade", "成绩");


        writer.write(courseList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("选课记录", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        writer.close();
    }

    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("选课编号", "scId");
        reader.addHeaderAlias("课程编号", "cno");
        reader.addHeaderAlias("课程名称", "cname");
        reader.addHeaderAlias("任课教师编号", "tno");
        reader.addHeaderAlias("课程学分", "ccredit");
        reader.addHeaderAlias("学号", "sno");
        reader.addHeaderAlias("学生名称", "sname");
        reader.addHeaderAlias("成绩", "grade");
        List<StudentCourse> studentCourseList = reader.readAll(StudentCourse.class);
        for (StudentCourse studentCourse : studentCourseList) {
            studentCourseService.addSC(studentCourse);
        }
        return Result.success();
    }
}
