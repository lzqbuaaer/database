package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Teacher;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Teacher teacher){
        PageInfo<Teacher> pageInfo=teacherService.selectPage(pageNum,pageSize,teacher);
        return Result.success(pageInfo);
    }
    @PostMapping("/addTeacherInfo")
    public Result addTeacherInfo(@RequestBody Teacher teacher){
        teacher.setPassword("206a97ee23118b6b34f00502ea434a74");
        teacher.setRole("TEACHER");
        teacherService.addTeacherInfo(teacher);
        return Result.success();
    }
    @PutMapping("/updateByTNO")
    public Result updateBySNO(@RequestBody Teacher teacher){
        teacherService.updateByTNO(teacher);
        return Result.success();
    }
    @DeleteMapping("deleteByTNO/{username}")
    public Result deleteByTNO(@PathVariable String username){
       teacherService.deleteByTNO(username);
        return Result.success();
    }
    @GetMapping("/checkPwd")
    public  int checkPwd(@RequestBody Account account){
        return teacherService.checkPwd(account);
    }
}
