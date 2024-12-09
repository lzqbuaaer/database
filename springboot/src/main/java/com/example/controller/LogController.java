package com.example.controller;

import com.example.common.Result;
import com.example.entity.Log;
import com.example.service.LogService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogService logService;

    @PostMapping("/add")
    public Result add(@RequestBody Log log) {
        logService.add(log);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Log log) {
        logService.updateById(log);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        logService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Log log) {
        System.out.println(log);
        PageInfo<Log> pageInfo = logService.selectPage(log, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        return Result.success(logService.selectAll());
    }
}
