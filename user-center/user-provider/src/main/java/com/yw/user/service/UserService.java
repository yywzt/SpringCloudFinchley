package com.yw.user.service;

import com.google.common.collect.Lists;
import com.yw.user.common.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 18:17
 */
@Service
public class UserService {
    public List<User> list(Pageable pageable) {
        User user = new User();
        user.setId(1L);
        return Lists.newArrayList(user);
    }
}
