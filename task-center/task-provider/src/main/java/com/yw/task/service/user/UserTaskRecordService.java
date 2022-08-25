package com.yw.task.service.user;

import com.yw.task.common.model.user.UserTaskRecord;
import com.yw.task.mapper.UserTaskRecordMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/22 10:20
 */
@Service
public class UserTaskRecordService {

    @Resource
    private UserTaskRecordMapper userTaskRecordMapper;

    public void batchAdd(List<UserTaskRecord> userTaskRecords) {
        if(CollectionUtils.isEmpty(userTaskRecords)) {
            return;
        }
        userTaskRecordMapper.batchInsert(userTaskRecords);
    }

//    private List<UserTaskRecord> buildUserTaskRecords(UserTaskDTO userTask) {
//        Integer finishedLevel = getTargetFinishedTaskLevel().getLevel();
//        List<UserTaskRecord> userTaskRecords = Lists.newArrayList();
//        int level = currentLevel;
//        do {
//            UserTaskRecord userTaskRecord = new UserTaskRecord();
//            userTaskRecord.setTaskId(userTask.getTaskId());
//            userTaskRecord.setUserId(userTask.getUserId());
//            userTaskRecord.setLevel(level);
//            userTaskRecord.setFinishedDate(currentDate);
//            userTaskRecords.add(userTaskRecord);
//        } while (++level <= finishedLevel);
//
//        return userTaskRecords;
//    }
}
