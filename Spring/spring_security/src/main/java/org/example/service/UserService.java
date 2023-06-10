package org.example.service;

import org.example.mapper.UserInfoMapper;
import org.example.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = this.userInfoMapper.selectByUsername(username);
        return User.withUsername(userInfo.getUsername()).password(userInfo.getPassword()).roles(userInfo.getRole().split(",")).build();
    }
}
