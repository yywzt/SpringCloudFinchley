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

    /**
     * 查询任务列表
     *
     * @param classificationId 分类ID
     * @param enableStatus     {@link com.yyw.api.enums.EnableStatusEnum}
     * @return 任务列表
     */
    @Select(value = "select * from task where classification_id = #{classificationId} and enable_status = #{enableStatus}")
    List<Task> list(@Param("classificationId") Long classificationId, @Param("enableStatus") Integer enableStatus);

    /**
     * 获取任务信息
     *
     * @param id           任务ID
     * @param enableStatus {@link com.yyw.api.enums.EnableStatusEnum}
     * @return 任务信息
     */
    @Select(value = "select * from task where id = #{id} and enable_status = #{enableStatus}")
    Optional<Task> get(@Param("id") Long id, @Param("enableStatus") Integer enableStatus);

    /**
     * 根据事件ID获取任务信息
     *
     * @param eventId      事件ID
     * @param enableStatus {@link com.yyw.api.enums.EnableStatusEnum}
     * @return 任务信息
     */
    @Select(value = "select * from task where event_id = #{eventId} and enable_status = #{enableStatus}")
    Optional<Task> findByEventId(@Param("eventId") String eventId, @Param("enableStatus") Integer enableStatus);
}
