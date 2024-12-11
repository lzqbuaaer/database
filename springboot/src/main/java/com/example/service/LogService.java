package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Log;
import com.example.mapper.LogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogService {
    @Resource
    private LogMapper logMapper;

    public void add(Log log) {
        log.setTime(DateUtil.now());
        logMapper.insert(log);
    }

    public PageInfo selectPage(Log log, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Log> logList = logMapper.selectByOperatorName(log.getOperatorName());
        return PageInfo.of(logList);
    }

    public void updateById(Log log) {
        logMapper.updateById(log);
    }

    public void deleteById(Integer id) {
        logMapper.deleteById(id);
    }

    public List<Log> selectAll() {
        return logMapper.selectAll();
    }
}
