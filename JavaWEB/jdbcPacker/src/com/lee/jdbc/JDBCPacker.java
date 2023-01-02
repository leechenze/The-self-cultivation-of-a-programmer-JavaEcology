package com.lee.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCPacker {
    public static void main(String[] args) throws Exception {
        // 1。注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2。获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);
        // 3。定义sql语句
        String sql = "update account set money = 5000 where id = 1;";
        // 4。获取执行sql的对象
        Statement stmt = conn.createStatement();
        // 5。执行sql(受影响的行数 Affected rows: 1)
        int count = stmt.executeUpdate(sql);
        // 6.打印处理结果
        System.out.println(count);
        // 7.释放资源
        stmt.close();
        conn.close();

    }
}
