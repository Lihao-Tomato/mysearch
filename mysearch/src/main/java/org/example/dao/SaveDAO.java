package org.example.dao;

import org.example.model.FileMeta;
import org.example.util.DBUtil;
import org.example.util.LogUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:32
 */
public class SaveDAO {
    public void save(List<FileMeta> fileList) {
        try {
            Connection c = DBUtil.getConnection();
            String sql = "INSERT INTO file_meta "+
                    "(name, path, is_directory, pinyin, pinyin_first, size, last_modified) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement s = c.prepareStatement(sql)) {
                for (FileMeta file : fileList) {
                    s.setString(1,file.getName());
                    s.setString(2, file.getPath());
                    s.setBoolean(3, file.isDirectory());
                    s.setString(4, file.getPinyin());
                    s.setString(5, file.getPinyinFirst());
                    s.setLong(6, file.getLength());
                    s.setLong(7, file.getLastModifiedTimestamp());

                    int i = s.executeUpdate();
                    LogUtil.log("执行 SQL: %s, %s, 受到影响的行数是: %d", sql, file, i);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<FileMeta> fileList = Arrays.asList(
                new FileMeta(new File("C:\\Users\\李浩\\Pictures\\Saved Pictures\\打卡图1.png")),
            new FileMeta(new File("C:\\Users\\李浩\\Pictures\\Saved Pictures\\打卡图.jpg")),
            new FileMeta(new File("C:\\Users\\李浩\\Pictures\\Saved Pictures\\冲.png")),
            new FileMeta(new File("C:\\Users\\李浩\\Pictures\\Saved Pictures\\喝茶.png"))
        );

        SaveDAO saveDAO = new SaveDAO();
        saveDAO.save(fileList);
    }
}
