package com.yw.user.struct;

import com.yw.user.common.dto.UserGoldCoinRecordDTO;
import com.yw.user.common.enums.AssetsChangeTypeEnum;
import com.yw.user.common.model.UserGoldCoinRecord;
import com.yw.user.common.request.GrantAssetsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 18:03
 */
@Mapper
public interface UserGoldCoinRecordStruct {

    UserGoldCoinRecordStruct INSTANCE = Mappers.getMapper(UserGoldCoinRecordStruct.class);

    UserGoldCoinRecord convert(UserGoldCoinRecordDTO userGoldCoinRecordDTO);

    @Mapping(source = "newGoldCoins", target = "balance")
    @Mapping(source = "grantAssetsRequest.goldCoins", target = "goldCoins")
    @Mapping(source = "grantAssetsRequest.serialNumber", target = "transactionNo")
    @Mapping(source = "assetsChangeTypeEnum.type", target = "type")
    UserGoldCoinRecordDTO convert(GrantAssetsRequest grantAssetsRequest, long newGoldCoins, AssetsChangeTypeEnum assetsChangeTypeEnum);
}