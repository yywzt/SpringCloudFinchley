package com.yw.task.mapper;

import com.yw.task.common.model.TaskLevel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:59
 */
public interface TaskLevelMapper extends Mapper<TaskLevel> {

    /**
     * 查询任务等级
     *
     * @param taskId 任务ID
     * @return 任务等级
     */
    @Select(value = "select * from task_level where task_id = #{taskId}")
    List<TaskLevel> list(@Param("taskId") Long taskId);
}
