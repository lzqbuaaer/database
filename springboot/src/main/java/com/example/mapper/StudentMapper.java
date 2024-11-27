package com.example.mapper;
import com.example.entity.Account;
import com.example.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {
    @Results({
            @Result(column = "sno",property = "username"),
            @Result(column = "spwd",property = "password"),
            @Result(column = "sname",property = "name")
    })
    @Select("select *from student.tbstudent where sno=#{username}")
    Student selectByStudent(String username);
    @Results({
            @Result(column = "sno",property = "username"),
            @Result(column = "spwd",property = "password"),
            @Result(column = "sname",property = "name")
    })
    @Select("select * from student.tbstudent where sno like concat('%',#{username},'%') and sname like concat('%',#{name},'%')  order by sno desc ")
    List<Student> selectAll(Student student);
    @Results({
            @Result(column = "sno",property = "username"),
            @Result(column = "spwd",property = "password"),
            @Result(column = "sname",property = "name")
    })
    @Insert("insert into student.tbstudent(sno,sname,ssex,sclass,sdept,spwd,role) values(#{username},#{name},#{ssex},#{sclass},#{sdept},#{password},#{role})")
    void insertStudent(Student student);
    @Results({
            @Result(column = "sno",property = "username"),
            @Result(column = "spwd",property = "password"),
            @Result(column = "sname",property = "name")
    })
    @Update("update student.tbstudent set sno=#{username} ,sname=#{name} ,ssex=#{ssex},sclass=#{sclass},sdept=#{sdept},spwd=#{password} where sno=#{username}")
    void updateBySNO(Student student);
    @Results({
            @Result(column = "sno",property = "username"),
            @Result(column = "spwd",property = "password"),
            @Result(column = "sname",property = "name")
    })
    @Delete("delete from student.tbstudent where sno=#{username}")
    void deleteBySNO(String username);
    @Results({
            @Result(column = "sno",property = "username"),
            @Result(column = "spwd",property = "password"),
            @Result(column = "sname",property = "name")
    })
    @Select("select *from student.tbstudent where sno=#{username}")
    Student checkPwd(String username);
}
