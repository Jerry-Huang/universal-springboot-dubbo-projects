package com.universal.provider.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.universal.api.user.bean.UserBean;
import com.universal.api.user.service.UserService;
import com.universal.provider.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(value = "TTL1D", key = "'USER#' + #userId")
    public UserBean findById(int userId) {

        final UserBean userBean = new UserBean();
        userBean.setId(userId);

        return userMapper.find(userBean);
    }

}
