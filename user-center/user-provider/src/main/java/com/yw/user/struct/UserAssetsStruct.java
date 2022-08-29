package com.yw.user.struct;

import com.yw.user.common.model.UserAssets;
import com.yw.user.common.vo.UserAssetsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:18
 */
@Mapper
public interface UserAssetsStruct {

    UserAssetsStruct INSTANCE = Mappers.getMapper(UserAssetsStruct.class);

    UserAssetsVO convert(UserAssets userAssets);
}
