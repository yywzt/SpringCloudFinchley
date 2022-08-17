package com.yw.user.common.api;

import com.yw.user.common.model.User;
import com.yyw.api.vo.PageInfoVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户API
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 17:41
 */
public interface UserApi {

    /**
     * 获取用户
     *
     * @param pageable 分页参数
     * @return 用户集合
     */
    @GetMapping(value = "/user/list")
    PageInfoVO<User> user(@PageableDefault(page = 1) Pageable pageable);
}
