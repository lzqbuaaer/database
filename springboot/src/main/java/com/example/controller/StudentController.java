package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Student;
import com.example.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Student student){
        PageInfo<Student> pageInfo=studentService.selectPage(pageNum,pageSize,student);
        return Result.success(pageInfo);
    }
    @PostMapping("/addStudentInfo")
    public Result addStudentInfo(@RequestBody Student student){
        student.setPassword("62bff9c5b8f865bdd70c588ce84158e0");
        student.setRole("STUDENT");
        studentService.addStudentInfo(student);
        return Result.success();
    }
    @PutMapping("/updateBySNO")
    public Result updateBySNO(@RequestBody Student student){
        studentService.updateBySNO(student);
        return Result.success();
    }
    @DeleteMapping("deleteBySNO/{username}")
    public Result deleteBySNO(@PathVariable String username){
        studentService.deleteBySNO(username);
        return Result.success();
    }
    @GetMapping("/checkPwd")
    public  int checkPwd(@RequestBody Account account){
        return studentService.checkPwd(account);
    }

    @GetMapping("/export")
    public void export(Student student, HttpServletResponse response) throws IOException {
        List<Student> studentList = studentService.selectAll(student);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.setOnlyAlias(true);

        writer.addHeaderAlias("username", "学号");
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("ssex", "性别");
        writer.addHeaderAlias("sclass", "班级");
        writer.addHeaderAlias("sdept", "系别");


        writer.write(studentList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        writer.close();
    }

    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("学号", "username");
        reader.addHeaderAlias("姓名", "name");
        reader.addHeaderAlias("性别", "ssex");
        reader.addHeaderAlias("班级", "sclass");
        reader.addHeaderAlias("系别", "sdept");
        List<Student> studentList = reader.readAll(Student.class);
        for (Student student : studentList) {
            addStudentInfo(student);
        }
        return Result.success();
    }
}
