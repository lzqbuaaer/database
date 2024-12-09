package com.example.service;

import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Log;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service
public class AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private LogService logService;

    public Account login(Account account) {
        Account db_Admin = adminMapper.selectByAdminname(account.getUsername());
        // 添加操作日志
        Log log = new Log("管理员","ADMIN", "select * from student.tbadmin where username=" + account.getUsername());
        logService.add(log);

        if (db_Admin.getName() == null) {
            throw new CustomException("账号或密码错误!");
        }
        // 启用MD5加密
        if (!MD5Utils.getMD5(account.getPassword()).equals(db_Admin.getPassword())) {
            throw new CustomException("账号或密码错误!");
        }

        /*
        if(!account.getPassword().equals(db_Admin.getPassword())){
            throw  new CustomException("账号或密码错误!");
        }
         */
        return db_Admin;
    }

    public int checkPwd(Account account) {
        Account check = adminMapper.selectByAdminname(account.getUsername());
        // 添加操作日志
        Log log = new Log( "管理员","ADMIN", "select * from student.tbadmin where username=" + account.getUsername());
        logService.add(log);
        if (!MD5Utils.getMD5(account.getPassword()).equals(check.getPassword())) return 0;
        return 1;
    }

    public void updateAdmin(Admin admin) {
        String MD5Pwd = MD5Utils.getMD5(admin.getPassword());
        admin.setPassword(MD5Pwd);
        adminMapper.updateByAdmin(admin);
        // 添加操作日志
        Log log = new Log("管理员","ADMIN","update tbadmin set password=" + admin.getPassword() + " where username=" + admin.getUsername());
        logService.add(log);
    }

    public void register(Account account) {
        Admin db_Admin = new Admin();
        if (adminMapper.selectByAdminname(account.getUsername()) != null) {
            throw new CustomException("用户名已存在!");
        }
        db_Admin.setUsername(account.getUsername());
        String MD5Pwd = MD5Utils.getMD5(account.getPassword());
        db_Admin.setPassword(MD5Pwd);
        db_Admin.setName("管理员");
        db_Admin.setRole("ADMIN");
        adminMapper.insertAdmin(db_Admin);
    }
}
