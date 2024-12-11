package com.example.service;

import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    public PageInfo<Comment> selectPage(Integer pageNum, Integer pageSize, Comment comment) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(commentMapper.selectAll(comment));
    }

    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    public void insert(Comment comment) {
        commentMapper.insertCourse(comment);
    }
}
