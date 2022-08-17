package com.yw.user.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.yw.user.common.model.User;
import com.yw.user.mapper.UserMapper;
import com.yw.user.util.PageUtil;
import com.yyw.api.enums.EnableStatusEnum;
import com.yyw.api.vo.PageInfoVO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/15 18:17
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public PageInfoVO<User> list(Pageable pageable) {
        PageInfo<User> userPageInfo = PageMethod.startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPageInfo(() -> userMapper.list(EnableStatusEnum.ENABLE_STATUS.getCode()));
        return PageUtil.INSTANCE.convert(userPageInfo, pageable);
    }
}
