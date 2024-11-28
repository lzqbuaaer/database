package com.example.controller;

import com.example.common.Result;
import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;
    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account db_Account;
        if(RoleEnum.ADMIN.name().equals(account.getRole())){
             db_Account=adminService.login(account);

        } else if (RoleEnum.STUDENT.name().equals(account.getRole())) {
             db_Account=studentService.login(account);
        } else if (RoleEnum.TEACHER.name().equals(account.getRole())) {
            db_Account=teacherService.login(account);
        }else{
            return  Result.error("角色错误！");
        }

        return Result.success(db_Account);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
             adminService.register(account);
        } else {
            return Result.error("角色错误！只能注册为管理员");
        }
        return Result.success();
    }
}
