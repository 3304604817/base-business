package com.supers.basic.feign.fallback;

import com.supers.basic.feign.BasicFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BasicFeignClientFallBack implements BasicFeignClient {

    private static final Logger logger = LoggerFactory.getLogger(BasicFeignClientFallBack.class);

    @Override
    public String temple(){
        logger.error("feign call failed");
        return "调用失败";
    }
}
