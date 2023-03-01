package com.lee.service.impl;

import com.lee.dao.AccountDao;
import com.lee.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String out, String in, Double money) {
        accountDao.inMoney(in, money);
        // 造一个异常
        // int i = 1/0;
        accountDao.outMoney(out, money);
    }
}
