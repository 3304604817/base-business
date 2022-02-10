package com.supers.basic.app.service;

import com.github.pagehelper.PageInfo;
import com.supers.basic.domain.entity.IamUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> currentUser(HttpServletRequest request);

    PageInfo<IamUser> list(IamUser dto, int page, int size);

    /**
     * 创建
     * @param iamUser
     * @return
     */
    IamUser insert(IamUser iamUser);

    /**
     * 批量创建用户
     * @param iamUsers
     * @return
     */
    List<IamUser> batchInsert(List<IamUser> iamUsers);

    /**
     * 更新用户信息
     * @param iamUser
     * @return
     */
    IamUser update(IamUser iamUser);

    /**
     * 批量删除用户
     * @param iamUsers
     */
    void batchDelete(List<IamUser> iamUsers);
}
