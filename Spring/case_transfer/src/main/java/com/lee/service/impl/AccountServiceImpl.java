package com.lee.service.impl;

import com.lee.dao.AccountDao;
import com.lee.service.AccountService;
import com.lee.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private LogService logService;

    @Override
    public void transfer(String out, String in, Double money) {
        try{
            // 进账，出账方法
            accountDao.inMoney(in, money);
            // 造一个异常
            int i = 1/0;
            accountDao.outMoney(out, money);
        }finally {
            // 记录变动日志(无论是否异常都要运行日志追加)
            logService.log(out, in, money);
        }
    }
}
