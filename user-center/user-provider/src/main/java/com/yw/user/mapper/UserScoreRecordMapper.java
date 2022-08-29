package com.yw.user.mapper;

import com.yw.user.common.model.UserScoreRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:34
 */
public interface UserScoreRecordMapper extends Mapper<UserScoreRecord> {

    /**
     * 根据流水号判断是否存在
     *
     * @param transactionNo 流水号
     * @return true:存在 false:不存在
     */
    @Select(value = "select exists(select id from user_score_record where transaction_no = #{transactionNo})")
    boolean exists(@Param("transactionNo") String transactionNo);
}
