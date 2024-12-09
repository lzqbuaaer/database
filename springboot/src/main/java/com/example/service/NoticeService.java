package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Log;
import com.example.entity.Notice;
import com.example.entity.Student;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private LogService logService;

    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.insert(notice);
        // 添加日志
        Log log = new Log("管理员", "ADMIN", "insert into student.notice(id,title,content,time) values(" + notice.getId() + "," + notice.getTitle() + "," + notice.getContent() + "," + notice.getTime() + ")");
        logService.add(log);
    }

    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> noticeList = noticeMapper.selectByTitle(notice.getTitle());
        // 添加日志
        Log log = new Log("管理员", "ADMIN", "select * from notice where title like concat('%', " + notice.getTitle() + ", '%')");
        logService.add(log);
        return PageInfo.of(noticeList);
    }

    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
        // 添加日志
        Log log = new Log("管理员", "ADMIN", "update student.notice set id=" + notice.getId() + " ,title=" + notice.getTitle() + " ,content=" + notice.getContent() + ",time=" + notice.getTime() + " where id=" + notice.getId());
        logService.add(log);
    }

    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
        // 添加日志
        Log log = new Log("管理员", "ADMIN", "delete from notice where id = " + id);
        logService.add(log);
    }

    public List<Notice> selectAll() {
        // 添加日志
        Log log = new Log("管理员", "ADMIN", "select * from notice");
        logService.add(log);
        return noticeMapper.selectAll();
    }
}
