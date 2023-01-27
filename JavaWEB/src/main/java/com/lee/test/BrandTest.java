package com.lee.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.lee.pojo.Brand;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 品牌数据的增删改查操作
 */
public class BrandTest {
    /**
     * 查询所有
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


        Brand brand;
        List<Brand> brandList = new ArrayList<Brand>();
        // 处理结果
        while(rs.next()){
            // 获取数据
            Integer id = rs.getInt("id");
            String brandName = rs.getString("brand_name");
            String companyName = rs.getString("company_name");
            Integer ordered = rs.getInt("ordered");
            String description = rs.getString("description");
            Integer status = rs.getInt("status");

            // 封装的Brand对象
            brand = new Brand();
            brand.setId(id);
            brand.setBrandName(brandName);
            brand.setCompanyName(companyName);
            brand.setOrdered(ordered);
            brand.setDescription(description);
            brand.setStatus(status);

            // 装载集合
            brandList.add(brand);

        }

        System.out.println(brandList);

        rs.close();
        pstmt.close();
        conn.close();

    }




    /**
     * 添加数据
     */
    @Test
    public void testAdd() throws Exception {
        // 模拟接收页面提交的参数
        String brandName = "香飘飘";
        String companyName = "香飘飘";
        int ordered = 1;
        String description = "环绕地球一圈";
        int status = 1;


        // 获取connection连接对象
        Properties prop = new Properties();

        prop.load(new FileInputStream("src/druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);

        Connection conn = dataSource.getConnection();

        // 定义Sql
        String sql = "insert into tb_brand(brand_name, company_name, ordered, description, status) value(?,?,?,?,?);";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数
        pstmt.setString(1, brandName);
        pstmt.setString(2, companyName);
        pstmt.setInt(3, ordered);
        pstmt.setString(4, description);
        pstmt.setInt(5, status);

        // 执行sql
        int count = pstmt.executeUpdate();
        System.out.println(count > 0);

        pstmt.close();
        conn.close();

    }





    /**
     * 修改数据
     */
    @Test
    public void testUpdate() throws Exception {
        // 模拟接收页面提交的参数
        String brandName = "香飘飘";
        String companyName = "香飘飘";
        int ordered = 10000;
        String description = "环绕地球三圈";
        int status = 1;
        int id = 4;

        // 获取connection连接对象
        Properties prop = new Properties();

        prop.load(new FileInputStream("src/druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);

        Connection conn = dataSource.getConnection();

        // 定义Sql
        String sql = "" +
                "update tb_brand\n" +
                "set brand_name = ?,\n" +
                "company_name = ?,\n" +
                "ordered = ?,\n" +
                "description = ?,\n" +
                "status = ?\n" +
                "where id = ?";

        // 获取preparedStatement对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数
        pstmt.setString(1, brandName);
        pstmt.setString(2, companyName);
        pstmt.setInt(3, ordered);
        pstmt.setString(4, description);
        pstmt.setInt(5, status);
        pstmt.setInt(6, id);

        // 执行sql
        int count = pstmt.executeUpdate();
        System.out.println(count > 0);

        pstmt.close();
        conn.close();

    }







    /**
     * 删除数据
     */
    @Test
    public void testDelete() throws Exception {
        // 模拟接收页面提交的参数
        int id = 4;

        // 获取connection连接对象
        Properties prop = new Properties();

        prop.load(new FileInputStream("src/druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);

        Connection conn = dataSource.getConnection();

        // 定义Sql
        String sql = "delete from tb_brand where id = ?";

        // 获取preparedStatement对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数
        pstmt.setInt(1, id);

        // 执行sql
        int count = pstmt.executeUpdate();
        System.out.println(count > 0);

        pstmt.close();
        conn.close();
    }


}
