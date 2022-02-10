package com.supers.basic.feign.fallback;

import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.feign.BusinessFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class BusinessFeignClientFallBack implements BusinessFeignClient {

    private static final Logger logger = LoggerFactory.getLogger(BusinessFeignClientFallBack.class);

    @Override
    public ResponseEntity<IamUser> update(@RequestBody IamUser iamUser){
        logger.error("feign call failed");
        return new ResponseEntity(iamUser, HttpStatus.OK);
    }
}
