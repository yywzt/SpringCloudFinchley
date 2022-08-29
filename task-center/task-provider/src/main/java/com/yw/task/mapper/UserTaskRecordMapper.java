package com.yw.task.mapper;

import com.yw.task.common.model.user.UserTaskRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 11:16
 */
public interface UserTaskRecordMapper extends Mapper<UserTaskRecord> {
    /**
     * 批量保存用户任务完成记录
     *
     * @param userTaskRecords 用户任务完成记录
     */
    void batchInsert(@Param("userTaskRecords") List<UserTaskRecord> userTaskRecords);
}
