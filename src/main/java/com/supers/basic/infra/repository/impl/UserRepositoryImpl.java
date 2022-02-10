package com.supers.basic.infra.repository.impl;

import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.domain.repository.UserRepository;
import com.supers.basic.infra.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<IamUser> list(IamUser dto){
        return userMapper.list(dto);
    }

    @Override
    public IamUser detail(Long userId){
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IamUser insert(IamUser iamUser){
        userMapper.insertSelective(iamUser);
        return iamUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<IamUser> batchInsert(List<IamUser> iamUsers){
        for(IamUser user:iamUsers){
            userMapper.insertSelective(user);
        }
        return iamUsers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IamUser update(IamUser iamUser){
        userMapper.updateByPrimaryKeySelective(iamUser);
        return iamUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<IamUser> iamUsers){
        iamUsers.stream().forEach(p -> {
            userMapper.deleteByPrimaryKey(p.getId());
        });
    }
}
