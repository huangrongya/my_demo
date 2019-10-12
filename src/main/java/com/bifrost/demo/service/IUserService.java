package com.bifrost.demo.service;

import com.bifrost.demo.dao.entity.User;
import com.bifrost.demo.dto.UserRequest;
import com.github.pagehelper.PageInfo;

public interface IUserService {
    User queryUserById(Integer id);

    PageInfo queryUserByPage(UserRequest request);

    void addUser(UserRequest request);
}
