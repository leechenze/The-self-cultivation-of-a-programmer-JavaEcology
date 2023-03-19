package com.lee.service;

import com.lee.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface BookService {

    /**
     * 保存
     * @param book
     * @return
     */
    public boolean save(Book book);

    /**
     *修改
     * @param book
     * @return
     */
    public boolean update(Book book);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    public boolean delete(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public Book getById(Integer id);

    /**
     * 查询全部
     * @return
     */
    public List<Book> getAll();
}
