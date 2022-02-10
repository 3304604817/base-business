package com.supers.basic.feign;

import com.supers.basic.feign.fallback.BasicFeignClientFallBack;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "base-basic",
        fallback = BasicFeignClientFallBack.class
)
public interface BasicFeignClient {

    @GetMapping("/test/temple")
    public String temple();
}
