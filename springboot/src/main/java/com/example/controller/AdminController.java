package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.service.AdminService;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @GetMapping("/checkPwd")
    public  int checkPwd(@RequestBody Account account){
        return adminService.checkPwd(account);
    }

    @PutMapping("/updateByAdmin")
    public Result updateByAdmin(@RequestBody Admin admin){
        adminService.updateAdmin(admin);
        return Result.success();
    }
}
