package com.example.service;

import com.example.entity.Account;
import com.example.entity.Log;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import com.example.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private LogService logService;

    public Account login(Account account) {
        Account db_Student = studentMapper.selectByStudent(account.getUsername());
        //添加日志
        Log log = new Log(account.getName(), "STUDENT", "select * from student.tbstudent where sno=" + account.getUsername());
        logService.add(log);
        if (db_Student.getName() == null) {
            throw new CustomException("账号或密码错误!");
        }
        // 启用MD5加密
        if (!MD5Utils.getMD5(account.getPassword()).equals(db_Student.getPassword())) {
            throw new CustomException("账号或密码错误!");
        }
        return db_Student;
    }

    public PageInfo<Student> selectPage(Integer pageNum, Integer pageSize, Student student) {  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum, pageSize);
        List<Student> studentList = studentMapper.selectAll(student);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "select * from student.tbstudent where sno like concat('%'," + student.getUsername() + ",'%') and sname like concat('%'," + student.getName() + ",'%')  order by sno desc ");
        logService.add(log);
        return PageInfo.of(studentList);
    }

    public void addStudentInfo(Student student) {
        studentMapper.insertStudent(student);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "insert into student.tbstudent(sno,sname,ssex,sclass,sdept,spwd,role) values(" + student.getUsername() + "," + student.getName() + "," + student.getSsex() + "," + student.getSclass() + "," + student.getSdept() + "," + student.getPassword() + "," + student.getRole() + ")");
        logService.add(log);
    }

    public void updateBySNO(Student student) {
        String MD5Pwd = MD5Utils.getMD5(student.getPassword());
        student.setPassword(MD5Pwd);
        studentMapper.updateBySNO(student);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "update student.tbstudent set sno=" + student.getUsername() + " ,sname=" + student.getName() + " ,ssex=" + student.getSsex() + ",sclass=" + student
                .getSclass() + ",sdept =" + student.getSdept() + ",spwd=" + student.getPassword() + " where sno=" + student.getUsername());
        logService.add(log);
    }

    public void deleteBySNO(String username) {
        studentMapper.deleteBySNO(username);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "delete from student.tbstudent where sno=" + username);
        logService.add(log);
    }

    public int checkPwd(Account account) {
        Account check = studentMapper.checkPwd(account.getUsername());
        if (!MD5Utils.getMD5(account.getPassword()).equals(check.getPassword())) return 0;
        return 1;
    }

    public List<Student> selectAll(Student student) {
        //添加日志
        Log log = new Log("管理员", "ADMIN", "select * from student.tbstudent where sno like concat('%'," + student.getUsername() + ",'%') and sname like concat('%'," + student.getName() + ",'%')  order by sno desc ");
        logService.add(log);
        return studentMapper.selectAll(student);
    }

    public void register(Account account) {
        Student db_Student = new Student();
        if (studentMapper.selectByStudent(account.getUsername()) != null) {
            throw new CustomException("用户名已存在!");
        }
        db_Student.setUsername(account.getUsername());
        String MD5Pwd = MD5Utils.getMD5(account.getPassword());
        db_Student.setPassword(MD5Pwd);
        db_Student.setName(account.getName());
        db_Student.setRole("STUDENT");
        studentMapper.insertStudent(db_Student);
        //添加日志
        Log log = new Log(db_Student.getName(), "STUDENT", "insert into student.tbstudent(sno,sname,ssex,sclass,sdept,spwd,role) values(" + db_Student.getUsername() + "," + db_Student.getName() + "," + db_Student.getSsex() + "," + db_Student.getSclass() + "," + db_Student.getSdept() + "," + db_Student.getPassword() + "," + db_Student.getRole() + ")");
        logService.add(log);
    }
}
