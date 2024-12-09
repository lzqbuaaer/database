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
    @Insert("insert into student.tbcourse(cno, tno, cname, ccredit, cdescribe, cday, ctime) values(#{cno},#{tno},#{cname},#{ccredit},#{cdescribe},#{cday},#{ctime})")
    void insertCourse(Course course);
    @Update("update student.tbcourse set cno=#{cno} ,tno=#{tno} ,cname=#{cname},ccredit=#{ccredit},cdescribe=#{cdescribe},cday=#{cday},ctime=#{ctime} where cno=#{cno}")
    void updateByCNO(Course course);
    @Delete("delete from student.tbcourse where cno=#{cno}")
    void deleteByCNO(String cno);

    @Select("select count(*) from student.tbcourse where cno = #{cno}")
    int existsByCNO(String cno);

    @Select("select * from student.tbcourse where cno = #{cno}")
    public List<Course> selectByCNO(String cno);
}
