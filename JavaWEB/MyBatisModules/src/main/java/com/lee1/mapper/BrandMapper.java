package com.lee1.mapper;

import com.lee1.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {


    /**
     * 查询所有
     */
    List<Brand> selectAll();


    /**
     * 查询详情
     */
    Brand selectById(int id);


    /**
     * 条件查询
     *   参数接收
     *      1。散装参数
     *      2。对象参数
     *      3。map集合参数
     */
    /**
     * 1。散装参数（如果多个参数，需要使用@Param("xml中对应的sql语句中的占位符名称")）
     */
    // List<Brand> selectByCondition(@Param("status") int status, @Param("companyName") String companyName, @Param("brandName") String brandName);
    /**
     * 2。对象参数
     */
    // List<Brand> selectByCondition(Brand brand);
    /**
     * 3。map集合参数
     */
    // List<Brand> selectByCondition(Map map);
    /**
     * 4。单条件动态查询
     */
    List<Brand> selectByConditionSingle(Brand brand);


    /**
     * 添加
     * @return
     */
    void add(Brand brand);


    /**
     * 修改
     */
    int update(Brand brand);

    /**
     * 删除
     */
    void deleteById(int id);

    /**
     * 批量删除
     */
    void deleteByIds(@Param("ids") int[] ids);


}
