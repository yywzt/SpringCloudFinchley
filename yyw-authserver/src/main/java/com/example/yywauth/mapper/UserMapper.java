package com.example.yywauth.mapper;

import com.example.yywauth.modal.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/12 12:03
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findUserInfByUname(String username);
}
