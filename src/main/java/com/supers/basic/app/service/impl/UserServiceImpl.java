package com.supers.basic.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supers.basic.app.service.UserService;
import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object>  currentUser(HttpServletRequest request){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", request.getAttribute("id").toString());
        userMap.put("loginName", request.getAttribute("loginName").toString());
        userMap.put("realName", request.getAttribute("realName").toString());
        userMap.put("email", request.getAttribute("email").toString());
        userMap.put("organizationId", request.getAttribute("organizationId").toString());
        userMap.put("phone", request.getAttribute("phone").toString());
        return userMap;
    }

    @Override
    public PageInfo<IamUser> list(IamUser dto, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(userRepository.list(dto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IamUser insert(IamUser iamUser){
        iamUser.setOrganizationId(Objects.isNull(iamUser.getOrganizationId()) ? 1L : iamUser.getOrganizationId());
        return userRepository.insert(iamUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<IamUser> batchInsert(List<IamUser> iamUsers){
        return userRepository.batchInsert(iamUsers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IamUser update(IamUser iamUser){
        return userRepository.update(iamUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<IamUser> iamUsers){
        userRepository.batchDelete(iamUsers);
    }
}
