package org.example.service;

import org.example.dao.DeleteDAO;
import org.example.dao.QueryDAO;
import org.example.dao.SaveDAO;
import org.example.model.FileMeta;
import org.example.util.ListUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:33
 */
public class FileService {
    private final SaveDAO saveDAO = new SaveDAO();
    private final DeleteDAO deleteDAO = new DeleteDAO();
    private final QueryDAO queryDAO = new QueryDAO();

    private void saveFileList(List<FileMeta> fileList) {
        saveDAO.save(fileList);
    }

    public void deleteFileList(List<FileMeta> fileList){
        List<Integer> idList = fileList.stream().map(FileMeta::getId).collect(Collectors.toList());
        deleteDAO.delete(idList);
    }

    public void service(String path, List<FileMeta> scanResultList) {
        List<FileMeta> queryResultList = queryDAO.queryByPath(path);

        // 1. queryResultList(数据库查询出来的) - scanResultList(文件扫描出来的结果)
        List<FileMeta> ds1 = ListUtil.differenceSet(queryResultList,scanResultList);
        deleteFileList(ds1);

        // 2. scanResultList - queryResultList
        List<FileMeta> ds2 = ListUtil.differenceSet(scanResultList,queryResultList);
        saveFileList(ds2);
    }

    // 根据搜索框内容查询
    public List<FileMeta> query(String keyword) {
        return queryDAO.query(keyword);
    }
}
