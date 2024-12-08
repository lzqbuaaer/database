package com.example.service;

import cn.hutool.core.date.DateUtil;
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

    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.insert(notice);
    }

    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {  //pageNum 当前的页码 pageSize 每页的个数
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> noticeList = noticeMapper.selectByTitle(notice.getTitle());
        return PageInfo.of(noticeList);
    }

    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }

    public List<Notice> selectAll() {
        return noticeMapper.selectAll();
    }
}
