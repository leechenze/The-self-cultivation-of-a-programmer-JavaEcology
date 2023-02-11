package com.lee1.mapper;

import com.lee1.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;

public interface UserMapper1 {
    /**
     * mybatis参数封装：
     *  单个参数：
     *      POJO类型：可以直接使用，要保证属性名 和 sql语句的参数占位符名称一致
     *      Map集合：直接使用，要保证键名 和 sql语句的参数占位符名称一致
     *      Collection：封装为Map集合
     *          map.put("arg0",collection集合);
     *          map.put("collection",collection集合);
     *      List：封装为Map集合
     *          map.put("arg0",list集合);
     *          map.put("collection",list集合);
     *          map.put("list",list集合);
     *      Array：封装为Map集合
     *          map.put("arg0",数组);
     *          map.put("array",数组);
     *      其他类型：单个参数的其他类型，写什么都能够接收到，比如之前使用的 id，但还是推荐要用@Param指定参数名。
     *  多个参数：
     *      当有多个参数时 mybatis首先会封装为Map集合
     *      map.put("arg0")
     *      map.put("arg1")
     *      或者
     *      map.put("param1")
     *      map.put("param2")
     *      所以当没有指定@Param时 是可以通过arg0，arg1 或 param1，param2的方式对形参进行获取的
     *      但是开发中没有人这么写，知道有这么个办法即可;
     */
    /** 多个参数 */
    // User select(@Param("username") String username, @Param("password") String password);
    // User select(String username, String password);

    /**
     * 单个参数
     */
    // Collection
    User select(Collection collection);

    /**
     * sql注解
     */
    @Select("select * from mybatis.tb_user where id = #{id}")
    User selectById(int id);


}
