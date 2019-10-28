package com.cbest.demo.service;

import com.cbest.demo.dao.entity.User;
import com.cbest.demo.dto.UserRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public interface IUserService {
    User queryUserById(Integer id);

    PageInfo queryUserByPageInfo(UserRequest request);

    void addUser(UserRequest request);

    Page queryUserByPage(UserRequest request);
}
