package com.lee.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 品牌数据的增删改查操作
 */
public class BrandTest {
    /**
     * 查询所有
     *
     */
    @Test
    public void testSelectAll() throws Exception {
        // 获取connection连接对象
        Properties prop = new Properties();

        prop.load(new FileInputStream("src/druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);

        Connection conn = dataSource.getConnection();

        // 定义Sql
        String sql = "select * from tb_brand";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        // 处理结果
        while(rs.next()){
            // 获取数据
            
            // 封装Brand对象

            // 装载集合
        }

        rs.close();
        pstmt.close();
        conn.close();





    }
}
