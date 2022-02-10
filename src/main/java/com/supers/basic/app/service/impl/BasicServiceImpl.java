package com.supers.basic.app.service.impl;

import com.supers.basic.app.service.BasicService;
import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.infra.mapper.UserMapper;
import com.supers.common.util.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class BasicServiceImpl implements BasicService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String loginName, String password){
        Example example = new Example(IamUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(IamUser.FIELD_LOGIN, loginName);
        /**
         * 缺少密码校验，代补充
         */
        IamUser user = userMapper.selectByExample(example).get(0);
        return JwtUtils.createToken(user);
    }
}
