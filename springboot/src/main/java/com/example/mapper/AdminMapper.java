package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.Student;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminMapper {
    @Select("select *from student.tbadmin where username=#{username}")
    Admin selectByAdminname(String username);
    @Update("update tbadmin set password=#{password} where username=#{username}")
    void updateByAdmin(Admin admin);

}
