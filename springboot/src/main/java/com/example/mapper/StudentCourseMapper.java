package com.example.mapper;

import com.example.entity.StudentCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface StudentCourseMapper {
    //@Select("select * from student.studentcourse where sno=#{sno} order by scId desc ")
    List<StudentCourse> selectAll(StudentCourse studentCourse);
    @Insert("insert into student.studentcourse(sno,cno,grade) values (#{sno},#{cno},#{grade})")
    void addSC(StudentCourse studentCourse);
    @Select("select *from studentcourse where sno=#{sno} and cno=#{cno}")
    StudentCourse selectBySnoAndCno(StudentCourse studentCourse);
    @Delete("delete from studentcourse where scId=#{scId}")
    void deleteByScId(Integer scId);
    @Update("update studentcourse set grade=#{grade} where scId=#{scId}")
    void addGrade(StudentCourse studentCourse);
}
