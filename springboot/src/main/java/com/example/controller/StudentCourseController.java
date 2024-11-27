package com.example.controller;

import com.example.common.Result;
import com.example.entity.StudentCourse;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}
