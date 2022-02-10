package com.supers.basic.infra.mapper;

import com.supers.basic.domain.entity.IamUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<IamUser> {
    List<IamUser> list(IamUser dto);

    IamUser detail(@Param("userId") Long userId);
}
