package com.example.service;

import com.example.entity.StudentCourse;
import com.example.exception.CustomException;
import com.example.mapper.StudentCourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class StudentCourseService {
    @Resource
    private StudentCourseMapper studentCourseMapper;

    public PageInfo<StudentCourse> selectPage(Integer pageNum, Integer pageSize, StudentCourse studentCourse){  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum,pageSize);
        List<StudentCourse> studentCourseList=studentCourseMapper.selectAll(studentCourse);
        return PageInfo.of(studentCourseList);
    }
    public void addSC(StudentCourse studentCourse){
        StudentCourse course=studentCourseMapper.selectBySnoAndCno(studentCourse);
        if(course==null){
            studentCourseMapper.addSC(studentCourse);
        }else{
            throw  new CustomException("已选过该课程!");
        }
    }
    public void deleteByScId(Integer scId){studentCourseMapper.deleteByScId(scId);}
    public void addGrade(StudentCourse studentCourse){studentCourseMapper.addGrade(studentCourse);};

    public List<StudentCourse> selectAll(StudentCourse studentCourse) {
        return studentCourseMapper.selectAll(studentCourse);
    }
}
