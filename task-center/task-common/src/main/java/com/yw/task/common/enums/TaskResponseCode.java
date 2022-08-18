package com.yw.task.common.enums;

import com.yyw.api.model.AppCode;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 16:42
 */
public enum TaskResponseCode implements AppCode {
    /**
     *
     */
    RESOURCE_NOT_FOUND("404", "资源不存在"),
    UNKNOWN_EXCEPTION("-1", "系统压力山大,请稍后重试！"),
    SUCCESS("0", "OK"),
    INSERT_EXCEPTION("10", "数据新增失败！"),
    INSERT_BATCH_EXCEPTION("11", "数据新增失败！"),
    UPDATE_EXCEPTION("20", "数据更新失败！"),
    DELETE_EXCEPTION("30", "数据删除失败！"),
    DISABLE_EXCEPTION("31", "使数据无效失败！"),
    SELECT_ONE_EXCEPTION("40", "单条数据获取失败！"),
    SELECT_EXCEPTION("41", "数据获取失败！"),
    SELECT_PAGINATION_EXCEPTION("42", "分页数据获取失败！"),
    INVALID_SYSTEM_CLOCK("10101", "系统时间回调到当前时间之前的时间点，拒绝产生ID%d毫秒"),
    UNKNOWN_WORKER_ID("10102", "无法获取IdWorker标识"),
    INVALID_WORKER_ID("10103", "无效IdWorker标识，%d > %d"),
    PARAM_IS_NULL("task-10000", "参数为空"),
    PARAM_CHECK_FAILURE("task-10001", "参数校验未通过: {0}"),
    TASK_NOT_EXIST("task-20000", "任务不存在");

    private String code;
    private String message;

    private TaskResponseCode(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    @Override
    public String toString() {
        return "TaskResponseCode{code='" + this.code + '\'' + ", message='" + this.message + '\'' + "} " + super.toString();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
