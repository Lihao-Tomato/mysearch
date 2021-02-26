package org.example.dao;

import org.example.util.DBUtil;
import org.example.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:31
 */
public class DeleteDAO {
    public void delete(List<Integer> idList) {
        try {
            Connection c = DBUtil.getConnection();
            // JDK8 中支持的 stream 用法
            List<String> placeholderList = idList.stream()
                    .map(id -> "?")
                    .collect(Collectors.toList());
            //        List<String> placeholderList = new ArrayList<>();
            //        for (Integer id : idList) {
            //            placeholderList.add("?");
            //        }

            String placeHolder = String.join(",", placeholderList);
            String sql = String.format("DELETE FROM file_meta WHERE id IN (%s)", placeHolder);

            try (PreparedStatement s = c.prepareStatement(sql)) {
                for (int i = 0; i < idList.size(); i++) {
                    int id = idList.get(i);
                    s.setInt(i + 1, id);
                }
                int i = s.executeUpdate();
                LogUtil.log("执行 SQL: %s, %s, 收到影响的行数是: %d", sql, idList, i);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        List<Integer> idList = Arrays.asList(4,5,6,7,8);

        DeleteDAO dao = new DeleteDAO();
        dao.delete(idList);
    }
}
