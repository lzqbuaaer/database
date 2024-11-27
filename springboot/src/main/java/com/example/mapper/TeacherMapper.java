package com.example.mapper;

import com.example.entity.Student;
import com.example.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TeacherMapper {
    @Results({
            @Result(column = "tno",property = "username"),
            @Result(column = "tpwd",property = "password"),
            @Result(column = "tname",property = "name")
    })
    @Select("select *from student.tbteacher where tno=#{username}")
    Teacher selectByTeacher(String username);

    @Results({
            @Result(column = "tno",property = "username"),
            @Result(column = "tpwd",property = "password"),
            @Result(column = "tname",property = "name")
    })
    @Select("select * from student.tbteacher where tno like concat('%',#{username},'%') and tname like concat('%',#{name},'%')  order by tno desc ")
    List<Teacher> selectAll(Teacher teacher);
    @Results({
            @Result(column = "tno",property = "username"),
            @Result(column = "tpwd",property = "password"),
            @Result(column = "tname",property = "name")
    })
    @Insert("insert into student.tbteacher(tno,tname,tsex,tdept,temail,tpwd,role) values(#{username},#{name},#{tsex},#{tdept},#{temail},#{password},#{role})")
    void insertTeacher(Teacher teacher);
    @Results({
            @Result(column = "tno",property = "username"),
            @Result(column = "tpwd",property = "password"),
            @Result(column = "tname",property = "name")
    })
    @Update("update student.tbteacher set tno=#{username} ,tname=#{name} ,tsex=#{tsex},tdept=#{tdept},temail=#{temail},tpwd=#{password} where tno=#{username}")
    void updateByTNO(Teacher teacher);
    @Results({
            @Result(column = "tno",property = "username"),
            @Result(column = "tpwd",property = "password"),
            @Result(column = "tname",property = "name")
    })
    @Delete("delete from student.tbteacher where tno=#{username}")
    void deleteByTNO(String username);
}
