package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.entity.Student;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        noticeService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Notice notice) {
        System.out.println(notice);
        PageInfo<Notice> pageInfo = noticeService.selectPage(notice, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Notice> list = noticeService.selectAll();
        return Result.success(list);
    }
}
