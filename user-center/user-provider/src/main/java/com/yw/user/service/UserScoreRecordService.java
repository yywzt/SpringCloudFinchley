package com.yw.user.service;

import com.yw.user.common.dto.score.UserScoreRecordDTO;
import com.yw.user.common.model.UserScoreRecord;
import com.yw.user.mapper.UserScoreRecordMapper;
import com.yw.user.struct.UserScoreRecordStruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:33
 */
@Service
public class UserScoreRecordService {

    @Resource
    private UserScoreRecordMapper userScoreRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<UserScoreRecordDTO> userScoreRecords) {
        userScoreRecords.forEach(this::add);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(UserScoreRecordDTO userScoreRecordDTO) {
        if (userScoreRecordDTO.getScore() <= 0) {
            return;
        }
        UserScoreRecord userScoreRecord = UserScoreRecordStruct.INSTANCE.convert(userScoreRecordDTO);
        save(userScoreRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(UserScoreRecord userScoreRecord) {
        userScoreRecordMapper.insertSelective(userScoreRecord);
    }

    public boolean exists(String transactionNo) {
        return userScoreRecordMapper.exists(transactionNo);
    }
}
