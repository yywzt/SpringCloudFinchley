package com.yw.user.mapper;

import com.yw.user.common.model.UserGoldCoinRecord;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 18:02
 */
public interface UserGoldCoinRecordMapper extends Mapper<UserGoldCoinRecord> {

    @Select(value = "select exists(select id from user_gold_coin_record where transaction_no = #{transactionNo})")
    boolean exists(String transactionNo);
}
