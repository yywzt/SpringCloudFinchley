package com.example.apolloconfig.bootexample.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/10/11 16:14
 */
@Data
public class Qun implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段名称：群组名称
     * <p>
     * 数据库字段信息：qun_name VARCHAR(255)
     */
    @NotBlank(message = "群组名称不能为空")
    private String qunName;

    protected Long id;

    /**
     * 创建人
     */
    protected String createdBy;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected LocalDateTime creationDate;

    /**
     * 修改人
     */
    protected String updatedBy;

    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    protected LocalDateTime updationDate;

    /**
     * 是否可用
     */
    protected Long enabledFlag = 1L;
}
