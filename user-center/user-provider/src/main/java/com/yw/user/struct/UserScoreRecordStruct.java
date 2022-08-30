package com.yw.user.struct;

import com.yw.user.common.dto.score.UserScoreRecordDTO;
import com.yw.user.common.enums.AssetsChangeTypeEnum;
import com.yw.user.common.model.UserScoreRecord;
import com.yw.user.common.request.GrantAssetsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:48
 */
@Mapper
public interface UserScoreRecordStruct {

    UserScoreRecordStruct INSTANCE = Mappers.getMapper(UserScoreRecordStruct.class);

    /**
     * UserScoreRecordDTO -> UserScoreRecord
     *
     * @param userScoreRecord {@link UserScoreRecordDTO}
     * @return {@link UserScoreRecord}
     */
    UserScoreRecord convert(UserScoreRecordDTO userScoreRecord);

    /**
     * GrantAssetsRequest -> UserScoreRecordDTO
     *
     * @param grantAssetsRequest   {@link GrantAssetsRequest}
     * @param newScore             变更后的积分数
     * @param assetsChangeTypeEnum {@link AssetsChangeTypeEnum}
     * @return {@link UserScoreRecordDTO}
     */
    @Mapping(source = "newScore", target = "balance")
    @Mapping(source = "grantAssetsRequest.score", target = "score")
    @Mapping(source = "grantAssetsRequest.transactionNo", target = "transactionNo")
    @Mapping(source = "assetsChangeTypeEnum.type", target = "type")
    UserScoreRecordDTO convert(GrantAssetsRequest grantAssetsRequest, Long newScore, AssetsChangeTypeEnum assetsChangeTypeEnum);
}
