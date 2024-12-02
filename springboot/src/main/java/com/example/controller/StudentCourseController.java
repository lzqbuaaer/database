package com.example.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.StudentCourse;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
}
