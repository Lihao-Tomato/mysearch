package org.example.util;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:33
 */
public class DBUtil {
    private static volatile DataSource dataSource = null;

    private static String getDatabasePath() {
        try {
            String classesPath = DBUtil.class.getProtectionDomain()
                    .getCodeSource().getLocation().getFile();
            File classesDir = new File(URLDecoder.decode(classesPath, "UTF-8"));
            String path = classesDir.getParent() + File.separator + "searcher.db";
            LogUtil.log("数据库文件路径为: %s", path);
            return path;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

   private static void initDataSource() {
       SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
       String url = "jdbc:sqlite://" + getDatabasePath();
       sqLiteDataSource.setUrl(url);

       dataSource = sqLiteDataSource;
   }

    // 单例的二次判断模式
    private static Connection initConnection() {
        if (dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }

        // Connection 对象是线程不安全 —— 每个线程都必须有自己的 Connection 对象
        // 一个线程只有一个 Connection 对象没有问题
        // 使用 ThreadLocal 实现，每个线程有自己的 Connection 对象
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ThreadLocal<Connection> connectionThreadLocal = ThreadLocal.withInitial(DBUtil::initConnection);

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }
}
