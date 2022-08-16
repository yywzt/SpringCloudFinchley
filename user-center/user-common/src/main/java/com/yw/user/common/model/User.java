package com.yw.user.common.model;

import com.yyw.api.enums.EnableStatusEnum;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 17:22
 */
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    private String password;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    /**
     * {@link EnableStatusEnum}
     */
    private Integer enableStatus;
}
