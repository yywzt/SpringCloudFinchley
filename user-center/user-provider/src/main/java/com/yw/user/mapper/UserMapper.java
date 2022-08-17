package com.yw.user.mapper;

import com.yw.user.common.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 11:42
 */
public interface UserMapper extends Mapper<User> {

    @Select(value = "select * from user where enable_status = #{enableStatus}")
    List<User> list(@Param("enableStatus") Integer enableStatus);
}
