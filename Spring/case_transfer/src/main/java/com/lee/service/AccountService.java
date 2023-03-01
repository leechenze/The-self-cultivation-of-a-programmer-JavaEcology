package com.lee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AccountService {
    @Transactional
    public void transfer(String out, String in, Double money);
}
