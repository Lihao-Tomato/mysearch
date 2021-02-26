package org.example.service;

import org.example.dao.InitDAO;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:33
 */
public class DBService {
    private final InitDAO initDAO = new InitDAO();

    public void init(){
        initDAO.init();
    }
}
