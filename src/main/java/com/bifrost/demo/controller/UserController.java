package com.bifrost.demo.controller;

import com.bifrost.demo.comm.ResultModel;
import com.bifrost.demo.dao.entity.User;
import com.bifrost.demo.dto.UserRequest;
import com.bifrost.demo.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;


    @PostMapping("/one")
    public ResultModel queryUserById(@RequestBody UserRequest request) {
        User user = userService.queryUserById(request.getId());
        return ResultModel.ok().put("data", user);
    }

    @PostMapping("/page")
    public ResultModel queryUserByPage(@Valid @RequestBody UserRequest request) {
        PageInfo pageInfo = userService.queryUserByPage(request);
        return ResultModel.ok().put("data", pageInfo);
    }

    @PutMapping("/add")
    public ResultModel addUser(@RequestBody UserRequest request) {
        userService.addUser(request);
        return ResultModel.ok();
    }


}
