package com.example.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Course course){
        PageInfo<Course> pageInfo=courseService.selectPage(pageNum,pageSize,course);
        return Result.success(pageInfo);
    }
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody Course course){
        courseService.addCourseInfo(course);
        return Result.success();
    }
    @PutMapping("/updateByCNO")
    public Result updateByCNO(@RequestBody Course course){
        courseService.updateByCNO(course);
        return Result.success();
    }
    @DeleteMapping("/deleteByCNO/{cno}")
    public Result deleteByCNO(@PathVariable String cno){
        courseService.deleteByCNO(cno);
        return Result.success();
    }

    @GetMapping("/export")
    public void export(Course course, HttpServletResponse response) throws IOException {
        List<Course> courseList = courseService.selectAll(course);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.setOnlyAlias(true);

        writer.addHeaderAlias("cno", "课程编号");
        writer.addHeaderAlias("cname", "课程名称");
        writer.addHeaderAlias("tno", "任课教师编号");
        writer.addHeaderAlias("ccredit", "课程学分");
        writer.addHeaderAlias("cdescribe", "课程描述");


        writer.write(courseList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("课程信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        writer.close();
    }
}

