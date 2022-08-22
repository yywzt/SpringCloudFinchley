package com.yw.task.mapper.user;

import com.yw.task.common.model.user.UserTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 16:30
 */
public interface UserTaskMapper extends Mapper<UserTask> {

    @Select(value = "select * from user_task where user_id = #{userId} and task_id = #{taskId} limit 1")
    UserTask get(@Param("userId") Long userId, @Param("taskId") Long taskId);

    void addOrUpdate(@Param("userTask") UserTask userTask);
}
