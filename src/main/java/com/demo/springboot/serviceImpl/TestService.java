package com.demo.springboot.serviceImpl;

import com.demo.springboot.annotation.AutoIdempotent;
import org.springframework.stereotype.Service;

/**
 * @author baiyu
 * @data 2020-05-18 16:14
 */
@Service
public class TestService {


    @AutoIdempotent
    public String testIdempotence() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
