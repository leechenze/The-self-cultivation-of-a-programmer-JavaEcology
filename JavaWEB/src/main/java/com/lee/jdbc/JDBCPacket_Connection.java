
package com.lee.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCPacket_Connection {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement stmt = conn.createStatement();

        // 开启事务


        String sql1 = "update account set money = 9999 where id = 1;";
        int count1 = stmt.executeUpdate(sql1);
        System.out.println(count1);
        String sql2 = "update account set money = 9999 where id = 2;";
        int count2 = stmt.executeUpdate(sql2);
        System.out.println(count2);
        // 回滚事务

        // 提交事务

        stmt.close();
        conn.close();

    }
}
