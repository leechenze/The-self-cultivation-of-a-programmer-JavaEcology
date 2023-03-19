package com.lee.dao;

import com.lee.domain.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO 添加@Mapper 以声明为数据代理类
@Mapper
public interface BookDao {

    // @Insert("insert into tb_book values(null,#{type},#{name},#{description})")
    @Insert("insert into tb_book (type,name,description) values(#{type}, #{name}, #{description})")
    public int save(Book book);

    @Update("update tb_book set type = #{type}, name=#{name}, description=#{description} where id = #{id}")
    public int update(Book book);

    @Delete("delete from tb_book where id = #{id}")
    public int delete(Integer id);

    @Select("select * from tb_book where id = #{id}")
    public Book getById(Integer id);

    @Select("select * from tb_book")
    public List<Book> getAll();

}
