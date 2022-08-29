package com.yw.user.service;

import com.yw.user.common.dto.UserGoldCoinRecordDTO;
import com.yw.user.common.model.UserGoldCoinRecord;
import com.yw.user.mapper.UserGoldCoinRecordMapper;
import com.yw.user.struct.UserGoldCoinRecordStruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:33
 */
@Service
public class UserGoldCoinRecordService {

    @Resource
    private UserGoldCoinRecordMapper userGoldCoinRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<UserGoldCoinRecordDTO> userGoldCoinRecordS) {
        userGoldCoinRecordS.forEach(this::add);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(UserGoldCoinRecordDTO userGoldCoinRecordDTO) {
        UserGoldCoinRecord userGoldCoinRecord = UserGoldCoinRecordStruct.INSTANCE.convert(userGoldCoinRecordDTO);
        save(userGoldCoinRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(UserGoldCoinRecord userGoldCoinRecord) {
        userGoldCoinRecordMapper.insertSelective(userGoldCoinRecord);
    }

}
