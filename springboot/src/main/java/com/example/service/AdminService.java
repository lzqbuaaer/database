package com.example.service;

import com.example.entity.Account;
import com.example.entity.Admin;
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
    public Account login(Account account){
       Account db_Admin= adminMapper.selectByAdminname(account.getUsername());
       if(db_Admin.getName()==null){
           throw new CustomException("账号或密码错误!");
       }
       // 启用MD5加密
       if(!MD5Utils.getMD5(account.getPassword()).equals(db_Admin.getPassword())){
           throw  new CustomException("账号或密码错误!");
       }

        /*
        if(!account.getPassword().equals(db_Admin.getPassword())){
            throw  new CustomException("账号或密码错误!");
        }
         */
       return db_Admin;
    }
    public int checkPwd(Account account){
        Account check= adminMapper.selectByAdminname(account.getUsername());
        if(!MD5Utils.getMD5(account.getPassword()).equals(check.getPassword())) return 0;
        return 1;
    }
    public void updateAdmin(Admin admin){
        String MD5Pwd=MD5Utils.getMD5(admin.getPassword());
        admin.setPassword(MD5Pwd);
        adminMapper.updateByAdmin(admin);
    }
}
