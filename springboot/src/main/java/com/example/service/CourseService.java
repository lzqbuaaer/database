package com.example.service;

import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.entity.Course;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;
    //PageInfo框架的分页查询方法
    public PageInfo<Course> selectPage(Integer pageNum,Integer pageSize,Course course){  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum,pageSize);
        List<Course> courseList= courseMapper.selectAll(course);
        return PageInfo.of(courseList);
    }
    public void addCourseInfo(Course course){
        if (courseMapper.existsByCNO(course.getCno()) > 0) {
            throw new CustomException("课程编号冲突");
        } else {
            courseMapper.insertCourse(course);
        }
    }

    public void updateByCNO(Course course){
        courseMapper.updateByCNO(course);
    }
    public void deleteByCNO(String cno){
        courseMapper.deleteByCNO(cno);
    }

    public List<Course> selectAll(Course course) {
        return courseMapper.selectAll(course);
    }

    public Course selectByCNO(String cno) {
        return courseMapper.selectByCNO(cno).get(0);
    }
}
