package com.lee1.pojo;

import com.lee1.mapper.BrandMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

    /**
     * 查询所有
     * @throws IOException
     */
    @Test
    public void testSelectAll() throws IOException {
        // 1.获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取Mapper接口代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        // 4.执行方法
        List<Brand> brands = brandMapper.selectAll();
        System.out.println(brands);
        // 5.释放资源
        sqlSession.close();
    }

    /**
     * 查询详情
     * @throws IOException
     */
    @Test
    public void testSelectById() throws IOException {
        // 接收的参数
        int id = 1;

        // 1.获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取Mapper接口代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        // 4.执行方法
        Brand brand = brandMapper.selectById(id);
        System.out.println(brand);
        // 5.释放资源
        sqlSession.close();
    }

    /**
     * 条件查询
     * @throws IOException
     */
    @Test
    public void testSelectByCondition() throws IOException {
        // 假设这是接收的参数
        int status = 1;
        String companyName = "华为";
        String brandName = "华为";

        // 处理参数
        companyName = "%" + companyName + "%";
        brandName = "%" + brandName + "%";

        // 封装对象
        Brand brand = new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);

        // 封装map对象
        Map map = new HashMap();
        // map.put("status", status);
        map.put("companyName", companyName);
        // map.put("brandName", brandName);


        // 封装单条件查询的Brand对象
        Brand brandSingle = new Brand();
        // brandSingle.setStatus(status);
        brandSingle.setCompanyName(companyName);
        // brandSingle.setBrandName(brandName);

        // 1.获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取Mapper接口代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        // 4.执行方法
        // List<Brand> brands = brandMapper.selectByCondition(status, companyName, brandName);
        List<Brand> brands = brandMapper.selectByConditionSingle(brandSingle);
        System.out.println(brands);
        // 5.释放资源
        sqlSession.close();
    }

    /**
     * 添加
     */
    @Test
    public void testAdd() throws IOException {
        // 假设这是接收的参数
        int status = 1;
        String companyName = "波导手机";
        String brandName = "波导";
        String description = "波导手机，手机中的战斗机";
        int ordered = 100;

        // 封装对象
        Brand brand = new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);
        brand.setDescription(description);
        brand.setOrdered(ordered);


        // 1.获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取SqlSession对象（默认是false，即开启事务，传入true，以设置自动提交事务, 关闭事务）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 3.获取Mapper接口代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        // 4.执行方法
        brandMapper.add(brand);
        Integer id = brand.getId();
        System.out.println(id);
        // 5.手动设置提交事务
        // sqlSession.commit();
        // 6.释放资源
        sqlSession.close();
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() throws IOException {
        // 假设这是接收的参数
        int status = 0;
        String companyName = "波导手机";
        String brandName = "波导";
        String description = "波导手机，波导手机，手机中的战斗机";
        int ordered = 200;
        int id = 10;

        // 封装对象
        Brand brand = new Brand();
        brand.setStatus(status);
        // brand.setCompanyName(companyName);
        // brand.setBrandName(brandName);
        // brand.setDescription(description);
        // brand.setOrdered(ordered);
        brand.setId(id);

        // 1.获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        // 2.获取SqlSession对象（默认是false，即开启事务，传入true，以设置自动提交事务, 关闭事务）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 3.获取Mapper接口代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        // 4.执行方法
        int count = brandMapper.update(brand);
        System.out.println(count);
        // 5.释放资源
        sqlSession.close();
    }












}
