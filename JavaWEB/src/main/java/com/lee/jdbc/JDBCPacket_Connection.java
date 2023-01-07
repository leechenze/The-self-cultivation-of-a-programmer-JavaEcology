
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

        try {
            // 开启事务
            conn.setAutoCommit(false);
            String sql1 = "update account set money = 1000 where id = 1;";
            int count1 = stmt.executeUpdate(sql1);
            System.out.println(count1);

            // 生成一条异常
            int i = 3 / 0;

            String sql2 = "update account set money = 1000 where id = 2;";
            int count2 = stmt.executeUpdate(sql2);
            System.out.println(count2);

            // 无异常时：提交事务
            conn.commit();
        } catch (Exception e) {
            // 出现异常时：回滚事务
            conn.rollback();
            e.printStackTrace();
        }



        stmt.close();
        conn.close();

    }
}
