package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.StudentCourse;
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
    @PostMapping("addSC")
    Result addSC(@RequestBody StudentCourse studentCourse){
        studentCourseService.addSC(studentCourse);
        return Result.success();
    }
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             StudentCourse studentCourse){
        PageInfo<StudentCourse> pageInfo=studentCourseService.selectPage(pageNum,pageSize,studentCourse);
        return Result.success(pageInfo);
    }
    @DeleteMapping("/deleteByScId/{scId}")
    public Result deleteByScId(@PathVariable Integer scId){
        studentCourseService.deleteByScId(scId);
        return Result.success();
    }
    @PutMapping("/addGrade")
    public Result addGrade(@RequestBody StudentCourse studentCourse){
        studentCourseService.addGrade(studentCourse);
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
