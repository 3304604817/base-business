package com.supers.basic.api.controller;

import com.supers.basic.app.service.BasicService;
import com.supers.basic.app.service.SeataService;
import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.domain.repository.UserRepository;
import com.supers.common.util.jwt.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="Seata")
@RestController
@RequestMapping("/seata")
public class SeataController {
    @Autowired
    private SeataService seataService;
    @Autowired
    private UserRepository userRepository;

    @Access(accessNoToken = true)
    @ApiOperation(value = "分布式事务测试")
    @PostMapping("/transaction-test")
    public ResponseEntity transactionTest() {
        seataService.transactionTest();
        return new ResponseEntity(HttpStatus.OK);
    }
}
