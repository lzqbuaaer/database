package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminMapper {
    @Select("select *from student.tbadmin where username=#{username}")
    Admin selectByAdminname(String username);
    @Update("update tbadmin set password=#{password} where username=#{username}")
    void updateByAdmin(Admin admin);
    // 插入数据
    @Update("insert into tbadmin(username,password,name,role) values(#{username},#{password},#{name},#{role})")
    void insertAdmin(Admin admin);
}
