package com.example.yywauth.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/11/12 18:44
 * @description
 */
@Data
public class UserInf implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    protected Long id;

    /**
     * 创建人
     */
    protected String createdBy;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Timestamp creationDate;

    /**
     * 修改人
     */
    protected String updatedBy;

    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    protected Timestamp updationDate;

    /**
     * 是否可用
     */
    protected Long enabledFlag = 1L;

    /**
     *  字段名称：
     *
     * 数据库字段信息：uname VARCHAR(255)
     */
    @NotBlank(message = "用户名不能为空")
    private String uname;

    /**
     *  字段名称：
     *
     * 数据库字段信息：passwd VARCHAR(255)
     */
    @NotBlank(message = "密码不能为空")
    private String passwd;

    /**
     *  字段名称：
     *
     * 数据库字段信息：gentel VARCHAR(255)
     */
    private String gentel;

    /**
     *  字段名称：
     *
     * 数据库字段信息：email VARCHAR(255)
     */
    private String email;

    /**
     *  字段名称：
     *
     * 数据库字段信息：city VARCHAR(255)
     */
    private String city;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.passwd;
    }

    @Override
    public String getUsername() {
        return this.uname;
    }

    //账户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //认证是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是不是激活的
    @Override
    public boolean isEnabled() {
        return true;
    }
}
