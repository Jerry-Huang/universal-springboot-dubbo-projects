package com.universal.provider.user.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.universal.api.user.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("master")
public interface UserMapper {

    UserBean find(final UserBean userBean);
}
