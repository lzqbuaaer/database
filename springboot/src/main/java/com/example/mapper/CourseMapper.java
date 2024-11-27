package com.example.mapper;

import com.example.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CourseMapper {
    @Select("select * from student.tbcourse where cname like concat('%',#{cname},'%') and cno like concat('%',#{cno},'%') and tno like concat('%',#{tno},'%') order by id desc ")
    public List<Course> selectAll(Course course);
    @Insert("insert into student.tbcourse(cno, tno, cname, ccredit, cdescribe) values(#{cno},#{tno},#{cname},#{ccredit},#{cdescribe})")
    void insertCourse(Course course);
    @Update("update student.tbcourse set cno=#{cno} ,tno=#{tno} ,cname=#{cname},ccredit=#{ccredit},cdescribe=#{cdescribe} where cno=#{cno}")
    void updateByCNO(Course course);
    @Delete("delete from student.tbcourse where cno=#{cno}")
    void deleteByCNO(String cno);
}