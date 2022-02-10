package com.supers.basic.feign;

import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.feign.fallback.BasicFeignClientFallBack;
import com.supers.basic.feign.fallback.BusinessFeignClientFallBack;
import com.supers.common.util.jwt.annotation.Access;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "base-business",
        fallback = BusinessFeignClientFallBack.class
)
public interface BusinessFeignClient {

    @ApiOperation(value = "更新员工信息")
    @PutMapping("/user/update")
    public ResponseEntity<IamUser> update(@RequestBody IamUser iamUser);
}
