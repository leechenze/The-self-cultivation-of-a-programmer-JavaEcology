package com.lee.jdbc;

import org.junit.Test;

import java.sql.*;

public class JDBCPacket_PreparedStatement {
    public static void main(String[] args) {

    }

    /**
     * 模拟用户登录时的Sql注入
     */
    @Test
    public void testLoginInject() throws Exception {

        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);

        // 接收用户输入：用户名和密码
        String sysName = "george";
        String sysPswd = "' or '1' = '1";
        String sql = "select * from tb_user where name = '" + sysName + "' and password = '" + sysPswd + "'";

        System.out.println(sql);

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        // 判断登录是否成功
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

        conn.close();
        stmt.close();
        rs.close();
    }

    /**
     * 模拟用户登录时的Sql注入
     */
    @Test
    public void testPreparedStatement() throws Exception {

        String url = "jdbc:mysql:///jdbc_packer?useServerPrepStmts=true&&useSSL=false";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);

        // 接收用户输入：用户名和密码
        String sysName = "george";
        // String sysPswd = "' or '1' = '1";
         String sysPswd = "123";

        // 定义sql
        String sql = "select * from tb_user where name = ? and password = ?";

        // 获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置？的值
        pstmt.setString(1, sysName);
        pstmt.setString(2, sysPswd);

        // 执行sql
        ResultSet rs = pstmt.executeQuery();

        // 判断登录是否成功
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

        conn.close();
        pstmt.close();
        rs.close();
    }

}
