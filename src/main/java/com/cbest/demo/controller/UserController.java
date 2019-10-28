package com.cbest.demo.controller;

import com.cbest.demo.comm.ResultModel;
import com.cbest.demo.dao.entity.User;
import com.cbest.demo.dto.UserRequest;
import com.cbest.demo.service.IUserService;
import com.github.pagehelper.Page;
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

    @PostMapping("/page/info")
    public ResultModel queryUserByPageInfo(@Valid @RequestBody UserRequest request) {
        PageInfo pageInfo = userService.queryUserByPageInfo(request);
        return ResultModel.ok().put("data", pageInfo);
    }

    @PostMapping("/page")
    public ResultModel queryUserByPage(@Valid @RequestBody UserRequest request) {
        Page page = userService.queryUserByPage(request);
        return ResultModel.ok().put("data", page);
    }

    @PutMapping("/add")
    public ResultModel addUser(@RequestBody UserRequest request) {
        userService.addUser(request);
        return ResultModel.ok();
    }


}
