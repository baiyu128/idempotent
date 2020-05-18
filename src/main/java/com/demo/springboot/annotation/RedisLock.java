package com.demo.springboot.annotation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author baiyu
 * @data 2020-05-15 15:22
 */
// 表示可以注解在方法上
@Target({ElementType.METHOD})
// 注解保留时长，运行时保留
@Retention(RetentionPolicy.RUNTIME)
// 自动继承
@Inherited
@Documented
public @interface RedisLock {
    /** 锁的 key 值，必须为非空字符串 */
    @NotNull
    @NotEmpty
    String key();
    /** 锁的 value 值 */
    String value() default "";
    /** 默认锁有效期 默认 15 */
    long expire() default 15;
    /** 锁有效期期的时间单位，默认 秒 */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
