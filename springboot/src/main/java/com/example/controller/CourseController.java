package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}

