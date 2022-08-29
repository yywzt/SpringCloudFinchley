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

    /**
     * 查询用户任务进度
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     * @return 用户任务进度
     */
    @Select(value = "select * from user_task where user_id = #{userId} and task_id = #{taskId} limit 1")
    UserTask get(@Param("userId") Long userId, @Param("taskId") Long taskId);

    /**
     * 新增、编辑用户任务进度
     *
     * @param userTask 用户任务进度
     */
    void addOrUpdate(@Param("userTask") UserTask userTask);
}
