package com.universal.provider.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.universal.api.user.bean.UserBean;
import com.universal.spring.boot.starter.mybatis.DataSource;

@Mapper
@DataSource("onl")
public interface UserMapper {

    UserBean find(final UserBean userBean);
}
