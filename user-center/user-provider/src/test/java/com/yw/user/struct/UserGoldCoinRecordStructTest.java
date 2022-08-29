package com.yw.user.struct;

import com.yw.user.common.dto.UserGoldCoinRecordDTO;
import com.yw.user.common.enums.AssetsChangeTypeEnum;
import com.yw.user.common.model.UserGoldCoinRecord;
import com.yw.user.common.request.GrantAssetsRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/29 11:44
 */
class UserGoldCoinRecordStructTest {

    @Test
    void user_gold_coin_record_dto_to_user_gold_coin_record() {
        UserGoldCoinRecordDTO userGoldCoinRecordDTO = UserGoldCoinRecordDTO.builder()
                .userId(1L)
                .balance(100L)
                .goldCoin(10L)
                .memo("赠予10金币")
                .transactionNo("10000001")
                .type(AssetsChangeTypeEnum.IN.getType())
                .build();
        UserGoldCoinRecord userGoldCoinRecord = UserGoldCoinRecordStruct.INSTANCE.convert(userGoldCoinRecordDTO);

        assertNotNull(userGoldCoinRecord);
        assertEquals(userGoldCoinRecordDTO.getUserId(), userGoldCoinRecord.getUserId());
        assertEquals(userGoldCoinRecordDTO.getBalance(), userGoldCoinRecord.getBalance());
        assertEquals(userGoldCoinRecordDTO.getGoldCoin(), userGoldCoinRecord.getGoldCoin());
        assertEquals(userGoldCoinRecordDTO.getMemo(), userGoldCoinRecord.getMemo());
        assertEquals(userGoldCoinRecordDTO.getTransactionNo(), userGoldCoinRecord.getTransactionNo());
        assertEquals(userGoldCoinRecordDTO.getType(), userGoldCoinRecord.getType());
    }

    @Test
    void grant_assets_request_to_user_gold_coin_record_dto() {
        GrantAssetsRequest grantAssetsRequest = new GrantAssetsRequest();
        grantAssetsRequest.setUserId(1L);
        grantAssetsRequest.setScore(10L);
        grantAssetsRequest.setGoldCoin(10L);
        grantAssetsRequest.setSerialNumber("10000001");
        grantAssetsRequest.setMemo("赠予10积分,10金币");

        Long newGoldCoin = 100L;
        AssetsChangeTypeEnum assetsChangeTypeEnum = AssetsChangeTypeEnum.IN;
        UserGoldCoinRecordDTO userGoldCoinRecordDTO = UserGoldCoinRecordStruct.INSTANCE.convert(grantAssetsRequest, newGoldCoin, assetsChangeTypeEnum);

        assertNotNull(userGoldCoinRecordDTO);
        assertEquals(grantAssetsRequest.getUserId(), userGoldCoinRecordDTO.getUserId());
        assertEquals(newGoldCoin, userGoldCoinRecordDTO.getBalance());
        assertEquals(grantAssetsRequest.getGoldCoin(), userGoldCoinRecordDTO.getGoldCoin());
        assertEquals(grantAssetsRequest.getMemo(), userGoldCoinRecordDTO.getMemo());
        assertEquals(grantAssetsRequest.getSerialNumber(), userGoldCoinRecordDTO.getTransactionNo());
        assertEquals(assetsChangeTypeEnum.getType(), userGoldCoinRecordDTO.getType());
    }
}