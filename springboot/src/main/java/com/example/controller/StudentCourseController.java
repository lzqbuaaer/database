package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Log;
import com.example.entity.StudentCourse;
import com.example.service.LogService;
import com.example.service.StudentCourseService;
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

@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {
    @Resource
    private StudentCourseService studentCourseService;
    @Resource
    private LogService logService;

    @PostMapping("addSC")
    Result addSC(@RequestBody StudentCourse studentCourse,
                 @RequestParam(defaultValue = "") String username,
                 @RequestParam(defaultValue = "") String userRole) {
        studentCourseService.addSC(studentCourse);
        // 添加日志
        Log log1 = new Log(username, userRole, "select * from student.studentcourse where sno=" + studentCourse.getSno() + " and cno=" + studentCourse.getCno());
        Log log2 = new Log(username, userRole, "insert into student.studentcourse(sno,cno,grade) values(" + studentCourse.getSno() + "," + studentCourse.getCno() + "," + studentCourse.getGrade() + ")");
        logService.add(log1);
        logService.add(log2);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             @RequestParam(defaultValue = "") String username,
                             @RequestParam(defaultValue = "") String userRole,
                             StudentCourse studentCourse) {
        PageInfo<StudentCourse> pageInfo = studentCourseService.selectPage(pageNum, pageSize, studentCourse);
        // 添加日志
        Log log = new Log(username, userRole, "select * from student.studentcourse where sno=" + studentCourse.getSno()+" order by scId desc");
        logService.add(log);
        return Result.success(pageInfo);
    }

    @DeleteMapping("/deleteByScId/{scId}")
    public Result deleteByScId(@PathVariable Integer scId,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String userRole) {
        studentCourseService.deleteByScId(scId);
        // 添加日志
        Log log = new Log(username, userRole, "delete from student.studentcourse where scId=" + scId);
        logService.add(log);
        return Result.success();
    }

    @PutMapping("/addGrade")
    public Result addGrade(@RequestBody StudentCourse studentCourse,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String userRole) {
        studentCourseService.addGrade(studentCourse);
        // 添加日志
        Log log = new Log(username, userRole, "update student.studentcourse set grade=" + studentCourse.getGrade() + " where scId=" + studentCourse.getScId());
        logService.add(log);
        return Result.success();
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
