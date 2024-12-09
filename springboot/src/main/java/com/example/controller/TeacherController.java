package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Teacher teacher) {
        PageInfo<Teacher> pageInfo = teacherService.selectPage(pageNum, pageSize, teacher);
        return Result.success(pageInfo);
    }

    @PostMapping("/addTeacherInfo")
    public Result addTeacherInfo(@RequestBody Teacher teacher) {
        teacher.setPassword("62bff9c5b8f865bdd70c588ce84158e0");
        teacher.setRole("TEACHER");
        teacherService.addTeacherInfo(teacher);
        return Result.success();
    }

    @PutMapping("/updateByTNO")
    public Result updateBySNO(@RequestBody Teacher teacher) {
        teacherService.updateByTNO(teacher);
        return Result.success();
    }

    @PutMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return Result.success();
    }

    @DeleteMapping("deleteByTNO/{username}")
    public Result deleteByTNO(@PathVariable String username) {
        teacherService.deleteByTNO(username);
        return Result.success();
    }

    @GetMapping("/checkPwd")
    public int checkPwd(@RequestBody Account account) {
        return teacherService.checkPwd(account);
    }

    @GetMapping("/export")
    public void export(Teacher teacher, HttpServletResponse response) throws IOException {
        List<Teacher> teacherList = teacherService.selectAll(teacher);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.setOnlyAlias(true);

        writer.addHeaderAlias("username", "编号");
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("tsex", "性别");
        writer.addHeaderAlias("tdept", "系别");
        writer.addHeaderAlias("temail", "邮箱");


        writer.write(teacherList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        writer.close();
    }

    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("编号", "username");
        reader.addHeaderAlias("姓名", "name");
        reader.addHeaderAlias("性别", "tsex");
        reader.addHeaderAlias("系别", "tdept");
        reader.addHeaderAlias("邮箱", "temail");
        List<Teacher> teacherList = reader.readAll(Teacher.class);
        for (Teacher teacher : teacherList) {
            addTeacherInfo(teacher);
        }
        return Result.success();
    }
}
