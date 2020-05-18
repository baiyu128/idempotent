package com.demo.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.demo.springboot.annotation.AutoIdempotent;
import com.demo.springboot.entity.Result;
import com.demo.springboot.service.TokenService;
import com.demo.springboot.serviceImpl.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author baiyu
 * @data 2020-05-18 16:10
 */
@RestController
public class BusinessController {
    @Resource
    private TokenService tokenService;

    @Resource
    private TestService testService;


    @PostMapping("/get/token")
    public Result  getToken(){
        String token = tokenService.createToken();
        if (!StringUtils.isBlank(token)) {
            return Result.ok(token);
        }
        return Result.error("获取token失败");
    }


    @AutoIdempotent
    @PostMapping("/test/Idempotence")
    public Result testIdempotence() {
        String businessResult = testService.testIdempotence();
        if (!StringUtils.isBlank(businessResult)) {
            return Result.ok(businessResult);
        }
        return Result.error("测试token失败");
    }
}
