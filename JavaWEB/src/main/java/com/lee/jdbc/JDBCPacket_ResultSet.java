package com.lee.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCPacket_ResultSet {
    /**
     * 执行DQL查询语句
     */
    @Test
    public void testResultSet() throws Exception{

        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);




    }
}
