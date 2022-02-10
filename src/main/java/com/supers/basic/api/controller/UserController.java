package com.supers.basic.api.controller;

import com.github.pagehelper.PageInfo;
import com.supers.basic.domain.entity.IamUser;
import com.supers.basic.app.service.UserService;
import com.supers.common.util.jwt.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags="用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * @CrossOrigin 让接口支持跨域请求
     */
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping("/current-user")
    @CrossOrigin
    public ResponseEntity currentUser(HttpServletRequest request) {
        return new ResponseEntity(userService.currentUser(request), HttpStatus.OK);
    }

    @ApiOperation(value = "员工列表")
    @GetMapping("/list")
    @CrossOrigin
    @Access(accessNoToken = true)
    public ResponseEntity list(@RequestParam(defaultValue = "0", required = false) int page,
                               @RequestParam(defaultValue = "10", required = false) int size,
                               @RequestParam(value = "loginName", required = false) String loginName,
                               @RequestParam(value = "realName", required = false) String realName) {
        IamUser iamUser = new IamUser();
        iamUser.setLoginName(loginName);
        iamUser.setRealName(realName);
        PageInfo<IamUser> pageInfo = userService.list(iamUser, page, size);
        return new ResponseEntity(pageInfo, HttpStatus.OK);
    }

    @ApiOperation(value = "创建员工")
    @PostMapping("/insert")
    @CrossOrigin
    @Access(accessNoToken = true)
    public ResponseEntity insert(@RequestBody IamUser iamUser) {
        iamUser = userService.insert(iamUser);
        return new ResponseEntity(iamUser, HttpStatus.OK);
    }

    @Access(accessNoToken = true)
    @ApiOperation(value = "批量创建员工")
    @PostMapping("/batchInsert")
    public ResponseEntity batchInsert(@RequestBody List<IamUser> iamUsers) {
        iamUsers = userService.batchInsert(iamUsers);
        return new ResponseEntity(iamUsers, HttpStatus.OK);
    }

    @Access(accessNoToken = true)
    @ApiOperation(value = "更新员工信息")
    @PutMapping("/update")
    public ResponseEntity<IamUser> update(@RequestBody IamUser iamUser) {
        iamUser = userService.update(iamUser);
        return new ResponseEntity(iamUser, HttpStatus.OK);
    }

    @ApiOperation(value = "批量删除员工")
    @DeleteMapping("/batchDelete")
    @CrossOrigin
    public ResponseEntity batchDelete(@RequestBody List<IamUser> iamUsers) {
        userService.batchDelete(iamUsers);
        return new ResponseEntity(HttpStatus.OK);
    }
}
