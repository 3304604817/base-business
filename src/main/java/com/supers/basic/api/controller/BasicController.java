package com.supers.basic.api.controller;

import com.supers.basic.app.service.BasicService;
import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.domain.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Api(tags="基础")
@RestController
@RequestMapping("")
public class BasicController {
    @Autowired
    private BasicService basicService;
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody IamUser user,
                        @RequestParam(value = "loginName", required = true) String loginName,
                        @RequestParam(value = "password", required = true) String password) {
        String token = basicService.login(loginName, password);
        return new ResponseEntity(token, HttpStatus.OK);
    }
}
