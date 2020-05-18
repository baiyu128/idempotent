package com.demo.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.springboot.annotation.AutoIdempotent;
import com.demo.springboot.entity.User;
import com.demo.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baiyu
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/cloud/user")
public class UserController {

    @Resource
    private UserService userService;

    public List<User> test(){
        return userService.list();
    }

    @GetMapping("/one")
    public User getOne(@RequestParam String name) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("name", name);
        return userService.getOne(qw);
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.list();
    }

    @PostMapping("/save")
    @AutoIdempotent
    public String sa(@RequestBody User u){
        boolean save = userService.save(u);
        if (save) return "success";
        return "fail";
    }

}
