package com.lee.jdbc;

import com.lee.pojo.Account;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

        String sql = "select * from account";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            // int id = rs.getInt(1);
            // String name = rs.getString(2);
            // double money = rs.getDouble(3);
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double money = rs.getDouble("money");
            System.out.println(id);
            System.out.println(name);
            System.out.println(money);
            System.out.println("=========");
        }

        // 释放资源
        rs.close();
        stmt.close();
        conn.close();
    }




    /**
     * 查询account账户表数据，封装为Account对象中，并存储到ArrayList集合中
     * 1.定义实体类Account
     * 2.查询数据，封装到Account中
     * 3.将Account对象存入ArrayList集合中
     */
    @Test
    public void testResultSetToAccount() throws Exception{

        String url = "jdbc:mysql:///jdbc_packer";
        String userName = "root";
        String password = "lcz19930316";
        Connection conn = DriverManager.getConnection(url, userName, password);

        String sql = "select * from account";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        // 创建一个集合
        List<Account> list = new ArrayList<Account>();

        while(rs.next()){
            // 对象创建
            Account account = new Account();
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double money = rs.getDouble("money");
            account.setId(id);
            account.setName(name);
            account.setMoney(money);

            // 存入集合
            list.add(account);

        }

        System.out.println(list);


        // 释放资源
        rs.close();
        stmt.close();
        conn.close();
    }



}
