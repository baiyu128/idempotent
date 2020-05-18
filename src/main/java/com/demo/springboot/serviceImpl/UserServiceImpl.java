package com.demo.springboot.serviceImpl;

import com.demo.springboot.dao.UserMapper;
import com.demo.springboot.entity.User;
import com.demo.springboot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baiyu
 * @since 2020-05-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    public void test(){

    }
}
