package com.yw.user.controller;

import com.yw.user.common.api.UserApi;
import com.yw.user.common.model.User;
import com.yw.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 18:16
 */
@RestController
public class UserController implements UserApi {

    @Resource
    private UserService userService;

    @Override
    public List<User> user(Pageable pageable) {
        return userService.list(pageable);
    }
}
