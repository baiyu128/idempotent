package com.demo.springboot.constant;

/**
 * @author baiyu
 * @data 2020-05-18 15:55
 */
public class Constant {

    public static final String TOKEN_NAME = "BY_Idempotent";

    public static class Redis {
        public static final String  TOKEN_PREFIX = "Idempotent_token_";
    }
}
