package org.example.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:33
 */
public class LogUtil {
    // 类型... 可变参数列表
    // Object... 代表，任意类型，任意长度都可以传入
    public static void log(String format, Object... args) {
        String message = String.format(format, args);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String now = dateFormat.format(date);
        System.out.printf("%s: %s\n", now, message);
    }

    public static void main(String[] args) {
        log("你好");
        log("今天有 %d 个同学来听课", 18);
    }
}
