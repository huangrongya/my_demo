package com.bifrost.demo.service.impl;

import com.bifrost.demo.dao.entity.User;
import com.bifrost.demo.dao.mapper.UserMapper;
import com.bifrost.demo.dto.UserRequest;
import com.bifrost.demo.service.IUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserById(Integer id) {

        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo queryUserByPageInfo(UserRequest request) {
        PageInfo<User> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize()).doSelectPageInfo(()->userMapper.selectAll());
        return pageInfo;
    }

    @Override
    public void addUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        userMapper.insert(user);
    }

    @Override
    public Page queryUserByPage(UserRequest request) {
        Page<User> page = PageHelper.startPage(request.getPageNum(), request.getPageSize()).doSelectPage(()->userMapper.selectAll());
        return page;
    }
}
