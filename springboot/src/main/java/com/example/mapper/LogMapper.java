package com.example.mapper;

import com.example.entity.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LogMapper {
    @Insert("insert into student.log (id, operatorName, operatorRole,operation, time) values (#{id}, #{operatorName}, #{operatorRole}, #{operation}, #{time})")
    void insert(Log log);

    @Select("select * from log")
    List<Log> selectAll();

    @Select("select * from log where operatorName like concat('%', #{operatorName}, '%')")
    List<Log> selectByOperatorName(String operatorName);

    @Update("update student.log set id=#{id}, operatorName=#{opeatorName}, operatorRole=#{operatorRole}, operation=#{operation}, time=#{time} where id=#{id}")
    void updateById(Log log);

    @Delete("delete from log where id = #{id}")
    void deleteById(Integer id);
}
