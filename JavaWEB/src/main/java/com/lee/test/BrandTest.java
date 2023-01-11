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
}
