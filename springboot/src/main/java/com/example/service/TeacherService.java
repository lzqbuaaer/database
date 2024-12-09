package com.example.service;

import com.example.entity.Account;
import com.example.entity.Log;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.mapper.TeacherMapper;
import com.example.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private LogService logService;

    public Account login(Account account) {
        Account db_Teacher = teacherMapper.selectByTeacher(account.getUsername());
        // 添加日志
        Log log = new Log(account.getName(), "TEACHER", "select * from student.tbteacher where tno=" + account.getUsername());
        logService.add(log);
        if (db_Teacher.getName() == null) {
            throw new CustomException("账号或密码错误!");
        }
        // 启用MD5加密
        if (!MD5Utils.getMD5(account.getPassword()).equals(db_Teacher.getPassword())) {
            throw new CustomException("账号或密码错误!");
        }

        /*
        if(!account.getPassword().equals(db_Teacher.getPassword())){
            throw  new CustomException("账号或密码错误!");
        }
        */
        return db_Teacher;
    }

    public PageInfo<Teacher> selectPage(Integer pageNum, Integer pageSize, Teacher teacher) {  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teacherList = teacherMapper.selectAll(teacher);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "select * from student.tbteacher where tno like concat('%'," + teacher.getUsername() + ",'%') and tname like concat('%'," + teacher.getName() + ",'%')  order by tno desc ");
        logService.add(log);
        return com.github.pagehelper.PageInfo.of(teacherList);
    }

    public void addTeacherInfo(Teacher teacher) {
        teacherMapper.insertTeacher(teacher);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "insert into student.tbteacher(tno,tname,tsex,tdept,temail,tpwd,role) values(" + teacher.getUsername() + "," + teacher.getName() + "," + teacher.getTsex() + "," + teacher.getTdept() + "," + teacher.getTemail() + "," + teacher.getPassword() + "," + teacher.getRole() + ")");
        logService.add(log);
    }

    public void updateByTNO(Teacher teacher) {
        String MD5Pwd = MD5Utils.getMD5(teacher.getPassword());
        teacher.setPassword(MD5Pwd);
        teacherMapper.updateByTNO(teacher);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "update student.tbteacher set tno=" + teacher.getUsername() + " ,tname=" + teacher.getName() + " ,tsex=" + teacher
                .getTsex() + ",tdept =" + teacher.getTdept() + ",temail=" + teacher.getTemail() + ",tpwd=" + teacher.getPassword() + " where tno=" + teacher.getUsername());
        logService.add(log);
    }

    public void deleteByTNO(String username) {
        teacherMapper.deleteByTNO(username);
        //添加日志
        Log log = new Log("管理员", "ADMIN", "delete from student.tbteacher where tno=" + username);
        logService.add(log);
    }

    public int checkPwd(Account account) {
        Account check = teacherMapper.selectByTeacher(account.getUsername());
        if (!MD5Utils.getMD5(account.getPassword()).equals(check.getPassword())) return 0;
        return 1;
    }

    public List<Teacher> selectAll(Teacher teacher) {
        //添加日志
        Log log = new Log("管理员", "ADMIN", "select * from student.tbteacher where tno like concat('%'," + teacher.getUsername() + ",'%') and tname like concat('%'," + teacher.getName() + ",'%')  order by tno desc ");
        logService.add(log);
        return teacherMapper.selectAll(teacher);
    }

    public void register(Account account) {
        Teacher db_Teacher = new Teacher();
        if (teacherMapper.selectByTeacher(account.getUsername()) != null) {
            throw new CustomException("用户名已存在!");
        }
        db_Teacher.setUsername(account.getUsername());
        String MD5Pwd = MD5Utils.getMD5(account.getPassword());
        db_Teacher.setPassword(MD5Pwd);
        db_Teacher.setName(account.getName());
        db_Teacher.setRole("TEACHER");
        teacherMapper.insertTeacher(db_Teacher);
        //添加日志
        Log log = new Log(db_Teacher.getName(), "TEACHER", "insert into student.tbteacher(tno,tname,tsex,tdept,temail,tpwd,role) values(" + db_Teacher.getUsername() + "," + db_Teacher.getName() + "," + db_Teacher.getTsex() + "," + db_Teacher.getTdept() + "," + db_Teacher.getTemail() + "," + db_Teacher.getPassword() + "," + db_Teacher.getRole() + ")");
        logService.add(log);
    }
}
