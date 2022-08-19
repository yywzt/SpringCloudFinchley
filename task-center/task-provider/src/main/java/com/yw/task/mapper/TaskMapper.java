package com.yw.task.mapper;

import com.yw.task.common.model.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/18 15:34
 */
public interface TaskMapper extends Mapper<Task> {

    @Select(value = "select * from task where classification_id = #{classificationId} and enable_status = #{enableStatus}")
    List<Task> list(@Param("classificationId") Long classificationId, @Param("enableStatus") Integer enableStatus);

    @Select(value = "select * from task where id = #{id} and enable_status = #{enableStatus}")
    Optional<Task> get(@Param("id") Long id, @Param("enableStatus") Integer enableStatus);
}
