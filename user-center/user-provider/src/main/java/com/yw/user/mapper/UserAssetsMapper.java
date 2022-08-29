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

    /**
     * 查询用户资产信息
     *
     * @param userId 用户ID
     * @return 用户资产类
     */
    @Select(value = "select * from user_assets where user_id = #{userId} limit 1")
    UserAssets findByUserId(@Param("userId") Long userId);

    /**
     * 更新用户积分、金币
     *
     * @param userId       用户ID
     * @param newScore     更新后的积分
     * @param newGoldCoins 更新后的金币
     * @param modifyDate   上一次的更新时间(乐观锁)
     */
    void changeScoreAndGoldCoin(@Param("userId") Long userId, @Param("newScore") Long newScore, @Param("newGoldCoins") Long newGoldCoins, @Param("modifyDate") LocalDateTime modifyDate);

}
