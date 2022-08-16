package com.yw.user.sdk.fallback;

import com.yw.user.common.model.User;
import com.yw.user.sdk.client.UserClient;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 17:58
 */
public class UserClientFallback implements UserClient {

    @Override
    public List<User> user(Pageable pageable) {
        return new ArrayList<>();
    }
}
