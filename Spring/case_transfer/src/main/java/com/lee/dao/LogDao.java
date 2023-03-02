package com.lee.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao {
    @Insert("insert into transfer_log (info,createDate) values(#{info},now())")
    void log(String info);

}
