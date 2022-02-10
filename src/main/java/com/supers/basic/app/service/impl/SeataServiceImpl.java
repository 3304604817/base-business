package com.supers.basic.app.service.impl;

import com.supers.basic.app.service.SeataService;
import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.feign.BusinessFeignClient;
import com.supers.basic.infra.mapper.UserMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeataServiceImpl implements SeataService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    @SuppressWarnings("all")
    private BusinessFeignClient businessFeignClient;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void transactionTest(){
        IamUser user = userMapper.selectByPrimaryKey(1L);
        user.setEmail("db_basic@qq.com");
        userMapper.updateByPrimaryKey(user);

        user.setEmail("db_business@qq.com");
        businessFeignClient.update(user);
    }
}
