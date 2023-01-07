package com.lee.jdbc;



import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCPacket_Statement {
    public static void main(String[] args) {

    }

    /**
     * 执行对应的DML语句:对数据的增删改
     * @throws Exception
     */
    @Test
    public void testDML() throws Exception{
        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement stmt = conn.createStatement();

        String sql = "update account set money = 1000 where id = 1;";
        // 返回执行完DML语句后受影响的行数
        int count = stmt.executeUpdate(sql);
        if (count > 0) {
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }

        stmt.close();
        conn.close();
    }

    /**
     * 执行对应的DDL语句:对表和库的增删改查操作
     * @throws Exception
     */
    @Test
    public void testDDL() throws Exception {
        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement stmt = conn.createStatement();

        // String sql = "create database jdbc_packer_db";
        String sql = "drop database jdbc_packer_db";
        int count = stmt.executeUpdate(sql);
        System.out.println(count);

        stmt.close();
        conn.close();
    }


}
