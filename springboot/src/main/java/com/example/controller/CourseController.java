package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.Log;
import com.example.service.CommentService;
import com.example.service.CourseService;
import com.example.service.LogService;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private LogService logService;
    @Resource
    private CommentService commentService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             @RequestParam(defaultValue = "") String username,
                             @RequestParam(defaultValue = "") String userRole,
                             Course course) {
        PageInfo<Course> pageInfo;
        if ("TEACHER".equals(userRole)) {
            pageInfo = courseService.selectPageByTno(pageNum, pageSize, course);
        } else {
            pageInfo = courseService.selectPage(pageNum, pageSize, course);
        }
        System.out.println("userRole:" + userRole);
        //添加日志
        Log log = new Log(username, userRole, "select * from student.tbcourse where cname like concat('%'," + course.getCname() + ",'%') and cno like concat('%'," + course.getCno() + ",'%') and tno like concat('%'," + course.getTno() + ",'%') order by id desc");
        logService.add(log);
        return Result.success(pageInfo);
    }

    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody Course course,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String userRole) {
        courseService.addCourseInfo(course);
        //添加日志
        Log log = new Log(username, userRole, "insert into student.tbcourse(cno, tno, cname, ccredit, cdescribe, cday, ctime) values(" + course.getCno() + "," + course.getTno() + "," + course.getCname() + "," + course.getCcredit() + "," + course.getCdescribe() + "," + course.getCday() + "," + course.getCtime() + ")");
        logService.add(log);
        return Result.success();
    }

    @PutMapping("/updateByCNO")
    public Result updateByCNO(@RequestBody Course course
            , @RequestParam(defaultValue = "") String username
            , @RequestParam(defaultValue = "") String userRole) {
        courseService.updateByCNO(course);
        //添加日志
        Log log = new Log(username, userRole, "update student.tbcourse set cno=" + course.getCno() + " ,tno=" + course.getTno() + " ,cname=" + course.getCname() + ",ccredit=" + course.getCcredit() + ",cdescribe=" + course.getCdescribe() + ",cday=" + course.getCday() + ",ctime=" + course.getCtime() + " where cno=" + course.getCno());
        logService.add(log);
        return Result.success();
    }

    @DeleteMapping("/deleteByCNO/{cno}")
    public Result deleteByCNO(@PathVariable String cno,
                              @RequestParam(defaultValue = "") String username,
                              @RequestParam(defaultValue = "") String userRole) {
        courseService.deleteByCNO(cno);
        //添加日志
        Log log = new Log(username, userRole, "delete from student.tbcourse where cno=" + cno);
        logService.add(log);
        return Result.success();
    }

    @GetMapping("/export")
    public void export(Course course, HttpServletResponse response) throws IOException {
        List<Course> courseList = courseService.selectAll(course);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.setOnlyAlias(true);

        writer.addHeaderAlias("cno", "课程编号");
        writer.addHeaderAlias("cname", "课程名称");
        writer.addHeaderAlias("tno", "任课教师编号");
        writer.addHeaderAlias("ccredit", "课程学分");
        writer.addHeaderAlias("cdescribe", "课程描述");
        writer.addHeaderAlias("cday", "星期");
        writer.addHeaderAlias("ctime", "节数");


        writer.write(courseList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("课程信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        writer.close();
    }

    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("课程编号", "cno");
        reader.addHeaderAlias("课程名称", "cname");
        reader.addHeaderAlias("任课教师编号", "tno");
        reader.addHeaderAlias("课程学分", "ccredit");
        reader.addHeaderAlias("课程描述", "cdescribe");
        reader.addHeaderAlias("星期", "cday");
        reader.addHeaderAlias("节数", "ctime");
        List<Course> courseList = reader.readAll(Course.class);
        for (Course course : courseList) {
            courseService.addCourseInfo(course);
        }
        return Result.success();
    }

    @GetMapping("/{cno}")
    public Result getCourseByCNO(@PathVariable String cno) {
        System.out.println("cno:" + cno);
        Course course = courseService.selectByCNO(cno);
        return Result.success(course);
    }

    @GetMapping("/selectComments")
    public Result getComments(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              Comment comment) {
        PageInfo<Comment> comments = commentService.selectPage(pageNum, pageSize, comment);
        return Result.success(comments);
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment) {
        commentService.insert(comment);
        return Result.success();
    }
}

