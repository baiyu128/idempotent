package com.demo.springboot.util;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dorspire on 2016/8/19.
 */
public class UuidUtil {
    private static final Random random = new Random();
    private static final AtomicInteger number = new AtomicInteger(nextInt(10000, 20000));

    /**
     * 获取uuid
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        return uuidString.replaceAll("-", "");
    }

    private static int nextInt(int min, int max) {
        return random.nextInt(max) % (max - min) + min;
    }

    public static long generate() {
        LocalDate date = LocalDate.now();
        long now = (date.getYear() * 10L + date.getMonthValue()) * 100000000 + System.currentTimeMillis() >> 1;
        int get = number.getAndAdd(nextInt(1, 100));
        if (get >= 100000) {
            get = nextInt(10000, 20000);
            number.getAndSet(get);
        }
        return now * 100000 + get;
    }

    public static long ungenerate(long no) {
        LocalDate date = LocalDate.now();
        no -= (date.getYear() * 10L + date.getMonthValue()) * 100000000;
        System.out.println(no);
        return no / 100000 << 1;
    }

    public static String generate(String con) {
        //1.13位时间戳
        String hs = String.valueOf(System.currentTimeMillis());
        //2.5位排序数
        int cs = new Random().nextInt(10000) + 10000;
        //操作缓存
        //3.10位摘要
        long sn = simple(con);
        //System.out.println("sn: " + sn);
        long ts = binlog(sn);
        //System.out.println("ts: " + ts);
        //10位时 max = 33, min = 30
        int max = 33;
        int min = 30;
        if (ts >= min && ts < max) {
            ts = sn;
        } else if (ts < min) {
            ts = sn << (min - ts);
        } else if (ts >= max) {
            ts = sn >>> (ts - max + 1);
        }
        //System.out.println("ts: " + ts);

        return hs + cs + ts;
    }

    private static long simple(String ss) {
        byte[] bs = ss.getBytes();
        long ln = 0L;
        for (byte b : bs) {
            int i = b % 10;
            //ln += bs[i] << (bs[i] % 10);
            //ls = (bs[i] + bs[bs.length - i - 1]) * i;
            ln += b << i + b + i * i;
        }
        return Math.abs(ln);
    }

    private static long binlog(long bits) {
        if (bits < 0) bits = Math.abs(bits);
        int log = 0;
        if (bits > 0x7fffffff) {
            bits >>>= 32;
            log += 32;
        }
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log += 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }
}