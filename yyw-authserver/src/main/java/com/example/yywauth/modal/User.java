package com.example.yywauth.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/12/10 11:38
 * @description
 */
@Data
@Table(name = "user_inf")
public class User implements Serializable {

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
    private String uname;
    private String passwd;
    private String gentel;
    private String email;
    private String city;

    private Set<Roles> roles;
}
