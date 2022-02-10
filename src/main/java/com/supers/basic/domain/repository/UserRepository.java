package com.supers.basic.domain.repository;

import com.supers.basic.domain.entity.IamUser;

import java.util.List;

public interface UserRepository {
    List<IamUser> list(IamUser dto);

    IamUser detail(Long userId);

    IamUser insert(IamUser iamUser);

    List<IamUser> batchInsert(List<IamUser> iamUsers);

    IamUser update(IamUser iamUser);

    void batchDelete(List<IamUser> iamUsers);
}
