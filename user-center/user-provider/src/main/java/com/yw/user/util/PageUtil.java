package com.yw.user.util;

import com.github.pagehelper.PageInfo;
import com.yyw.api.vo.PageInfoVO;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/17 14:24
 */
public class PageUtil {

    public static final PageUtil INSTANCE = new PageUtil();

    public <T> PageInfoVO<T> convert(PageInfo<T> pageInfo, Pageable pageable) {
        if (Objects.isNull(pageInfo)) {
            return PageInfoVO.empty(pageable.getPageNumber(), pageable.getPageSize());
        }
        PageInfoVO<T> objectPageInfoVO = new PageInfoVO<>();
        objectPageInfoVO.setCurrentPage(pageInfo.getPageNum());
        objectPageInfoVO.setPageSize(pageInfo.getPageSize());
        objectPageInfoVO.setTotals(Long.bitCount(pageInfo.getTotal()));
        objectPageInfoVO.setTotalPages(pageInfo.getPages());
        objectPageInfoVO.setList(pageInfo.getList());
        return objectPageInfoVO;
    }

}
