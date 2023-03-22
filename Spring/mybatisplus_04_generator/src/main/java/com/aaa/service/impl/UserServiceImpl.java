package com.aaa.service.impl;

import com.aaa.domain.User;
import com.aaa.dao.UserDao;
import com.aaa.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leechenze
 * @since 2023-03-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}
