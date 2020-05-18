package com.demo.springboot.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baiyu
 * @data 2020-05-18 15:25
 */
public interface TokenService {

    /**
     * 创建token
     * @return
     */
    public  String createToken();

    /**
     * 检验token
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request) throws Exception;
}
