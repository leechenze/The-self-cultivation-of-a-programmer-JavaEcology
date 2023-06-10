package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.vo.UserInfo;

@Mapper
public interface UserInfoMapper {
    public UserInfo selectByUsername(@Param("username") String username);
}
