package com.yw.task.service.user;

import com.google.common.collect.Lists;
import com.yw.task.common.model.user.UserTaskRecord;
import com.yw.task.mapper.UserTaskRecordMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 10:20
 */
@Service
public class UserTaskRecordService {

    @Resource
    private UserTaskRecordMapper userTaskRecordMapper;

    /**
     * @param finishedTaskLevels 完成的任务等级信息
     * @param userId             用户ID
     * @param taskId             任务ID
     * @param finishedDate       任务完成时间
     */
    public void batchRecord(Set<Integer> finishedTaskLevels, Long userId, Long taskId, LocalDateTime finishedDate) {
        if (CollectionUtils.isEmpty(finishedTaskLevels)) {
            return;
        }
        List<UserTaskRecord> userTaskRecords = Lists.newArrayList();
        for (Integer level : finishedTaskLevels) {
            UserTaskRecord userTaskRecord = new UserTaskRecord();
            userTaskRecord.setTaskId(taskId);
            userTaskRecord.setUserId(userId);
            userTaskRecord.setLevel(level);
            userTaskRecord.setFinishedDate(finishedDate);
            userTaskRecords.add(userTaskRecord);
        }
        userTaskRecordMapper.batchInsert(userTaskRecords);
    }
}
