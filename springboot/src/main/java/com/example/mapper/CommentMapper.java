package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    @Select("select * from student.comment where cno like concat('%',#{cno},'%') order by id desc ")
    public List<Comment> selectAll(Comment comment);

    @Insert("insert into student.comment(cno, userid, username, content, time) values(#{cno},#{userId},#{userName},#{content},#{time})")
    void insertCourse(Comment comment);
}
