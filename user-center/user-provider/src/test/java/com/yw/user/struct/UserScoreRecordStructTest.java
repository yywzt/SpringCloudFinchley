package com.yw.user.struct;

import com.yw.user.common.dto.score.UserScoreRecordDTO;
import com.yw.user.common.enums.AssetsChangeTypeEnum;
import com.yw.user.common.model.UserScoreRecord;
import com.yw.user.common.request.GrantAssetsRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/29 11:35
 */
class UserScoreRecordStructTest {

    @Test
    void user_score_record_dto_to_user_score_record() {
        UserScoreRecordDTO userScoreRecordDTO = UserScoreRecordDTO.builder()
                .userId(1L)
                .balance(100L)
                .score(10L)
                .memo("赠予10积分")
                .transactionNo("10000001")
                .type(AssetsChangeTypeEnum.IN.getType())
                .build();
        UserScoreRecord userScoreRecord = UserScoreRecordStruct.INSTANCE.convert(userScoreRecordDTO);

        assertNotNull(userScoreRecord);
        assertEquals(userScoreRecordDTO.getUserId(), userScoreRecord.getUserId());
        assertEquals(userScoreRecordDTO.getBalance(), userScoreRecord.getBalance());
        assertEquals(userScoreRecordDTO.getScore(), userScoreRecord.getScore());
        assertEquals(userScoreRecordDTO.getMemo(), userScoreRecord.getMemo());
        assertEquals(userScoreRecordDTO.getTransactionNo(), userScoreRecord.getTransactionNo());
        assertEquals(userScoreRecordDTO.getType(), userScoreRecord.getType());
    }

    @Test
    void grant_assets_request_to_user_score_record_dto() {
        GrantAssetsRequest grantAssetsRequest = new GrantAssetsRequest();
        grantAssetsRequest.setUserId(1L);
        grantAssetsRequest.setScore(10L);
        grantAssetsRequest.setGoldCoins(10L);
        grantAssetsRequest.setSerialNumber("10000001");
        grantAssetsRequest.setMemo("赠予10积分,10金币");

        Long newScore = 100L;
        AssetsChangeTypeEnum assetsChangeTypeEnum = AssetsChangeTypeEnum.IN;
        UserScoreRecordDTO userScoreRecordDTO = UserScoreRecordStruct.INSTANCE.convert(grantAssetsRequest, newScore, assetsChangeTypeEnum);

        assertNotNull(userScoreRecordDTO);
        assertEquals(grantAssetsRequest.getUserId(), userScoreRecordDTO.getUserId());
        assertEquals(newScore, userScoreRecordDTO.getBalance());
        assertEquals(grantAssetsRequest.getScore(), userScoreRecordDTO.getScore());
        assertEquals(grantAssetsRequest.getMemo(), userScoreRecordDTO.getMemo());
        assertEquals(grantAssetsRequest.getSerialNumber(), userScoreRecordDTO.getTransactionNo());
        assertEquals(assetsChangeTypeEnum.getType(), userScoreRecordDTO.getType());
    }
}