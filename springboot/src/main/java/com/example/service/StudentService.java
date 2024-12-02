package com.example.service;

import com.example.entity.Account;
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
    public Account login(Account account){
        Account db_Student= studentMapper.selectByStudent(account.getUsername());
        if(db_Student.getName()==null){
            throw new CustomException("账号或密码错误!");
        }
        // 启用MD5加密
        if(!MD5Utils.getMD5(account.getPassword()).equals(db_Student.getPassword())){
            throw  new CustomException("账号或密码错误!");
         }
        return db_Student;
    }

    public PageInfo<Student> selectPage(Integer pageNum, Integer pageSize, Student student){  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum,pageSize);
        List<Student> studentList= studentMapper.selectAll(student);
        return PageInfo.of(studentList);
    }
    public void addStudentInfo(Student student){studentMapper.insertStudent(student);}

    public void updateBySNO(Student student){
        String MD5Pwd=MD5Utils.getMD5(student.getPassword());
        student.setPassword(MD5Pwd);
        studentMapper.updateBySNO(student);
    }
    public void deleteBySNO(String username){studentMapper.deleteBySNO(username);}
    public int checkPwd(Account account){
        Account check= studentMapper.checkPwd(account.getUsername());
        if(!MD5Utils.getMD5(account.getPassword()).equals(check.getPassword())) return 0;
        return 1;
    }

    public List<Student> selectAll(Student student){
        return studentMapper.selectAll(student);
    }
}
