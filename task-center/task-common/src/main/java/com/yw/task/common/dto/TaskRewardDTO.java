package com.yw.task.common.dto;

import com.yw.task.common.model.TaskLevelReward;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 任务等级奖励
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/25 15:04
 */
@Data
@AllArgsConstructor
public class TaskRewardDTO {

    /**
     * 任务等级
     */
    private Integer level;

    private List<TaskLevelRewardDTO> rewards;

    @Data
    public static class TaskLevelRewardDTO {
        /**
         * 奖励类型
         */
        private Integer type;

        /**
         * 值
         */
        private Integer value;

        public TaskLevelRewardDTO(Integer type, Integer value) {
            this.type = type;
            this.value = value;
        }
    }

    /**
     * @param level               任务等级
     * @param taskLevelRewardList 对应的奖励
     */
    public static TaskRewardDTO convert(Integer level, List<TaskLevelReward> taskLevelRewardList) {
        List<TaskLevelRewardDTO> rewards = taskLevelRewardList.stream()
                .map(taskLevelReward -> new TaskLevelRewardDTO(taskLevelReward.getType(), taskLevelReward.getValue()))
                .collect(Collectors.toList());
        return new TaskRewardDTO(level, rewards);
    }

    /**
     * @param taskLevelRewardList 多个任务等级对应的奖励
     */
    public static List<TaskRewardDTO> convert(List<TaskLevelReward> taskLevelRewardList) {
        Map<Integer, List<TaskLevelReward>> collect = taskLevelRewardList.stream()
                .collect(Collectors.groupingBy(TaskLevelReward::getLevel));
        if (Objects.isNull(collect)) {
            return Collections.emptyList();
        }
        List<TaskRewardDTO> taskRewards = new ArrayList<>();
        collect.forEach((level, taskLevelRewards) -> {
            TaskRewardDTO taskRewardDTO = convert(level, taskLevelRewards);
            taskRewards.add(taskRewardDTO);
        });
        return taskRewards;
    }

}
