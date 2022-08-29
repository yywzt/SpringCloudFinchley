package com.yw.user.mapper;

import com.yw.user.common.model.UserAssets;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:14
 */
public interface UserAssetsMapper extends Mapper<UserAssets> {

    @Select(value = "select * from user_assets where user_id = #{userId} limit 1")
    UserAssets findByUserId(@Param("userId") Long userId);

    void changeScoreAndGoldCoin(@Param("userId") Long userId, @Param("newScore") Long newScore, @Param("newGoldCoins") Long newGoldCoins, @Param("modifyDate") LocalDateTime modifyDate);

}
