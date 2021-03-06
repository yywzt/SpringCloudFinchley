package com.example.yywauth.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/10 14:18
 * @description
 */
@Data
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected Long id;
    protected String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Timestamp creationDate;
    protected String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    protected Timestamp updationDate;
    protected Long enabledFlag = 1L;
    private String roleName;
    private String roleCode;
}
