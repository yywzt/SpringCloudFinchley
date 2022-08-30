package com.yw.task.common.model.user;

import com.yw.task.common.enums.GrantStatusEnum;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户任务完成奖励记录
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 11:46
 */
@Data
@Table(name = "user_task_reward")
public class UserTaskReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private Long userId;

    /**
     * 用户任务等级
     */
    private Integer level;

    /**
     * 奖励数据
     * {@link List <com.yw.task.common.dto.user.UserTaskRewardContent>}
     */
    private String rewardContent;

    /**
     * 发放状态
     * {@link GrantStatusEnum}
     */
    private Integer grantStatus;

    /**
     * 流水号
     */
    private String transactionNo;
    /**
     * 备注
     */
    private String memo;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

}