package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Student;
import com.example.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Student student){
        PageInfo<Student> pageInfo=studentService.selectPage(pageNum,pageSize,student);
        return Result.success(pageInfo);
    }
    @PostMapping("/addStudentInfo")
    public Result addStudentInfo(@RequestBody Student student){
        student.setPassword("206a97ee23118b6b34f00502ea434a74");
        student.setRole("STUDENT");
        studentService.addStudentInfo(student);
        return Result.success();
    }
    @PutMapping("/updateBySNO")
    public Result updateBySNO(@RequestBody Student student){
        studentService.updateBySNO(student);
        return Result.success();
    }
    @DeleteMapping("deleteBySNO/{username}")
    public Result deleteBySNO(@PathVariable String username){
        studentService.deleteBySNO(username);
        return Result.success();
    }
    @GetMapping("/checkPwd")
    public  int checkPwd(@RequestBody Account account){
        return studentService.checkPwd(account);
    }
}
