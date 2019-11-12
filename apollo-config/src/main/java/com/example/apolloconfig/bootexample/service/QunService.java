package com.example.apolloconfig.bootexample.service;

import com.example.apolloconfig.bootexample.mapper.QunMapper;
import com.example.apolloconfig.bootexample.modal.Qun;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2019/10/11 16:15
 */
@Service
public class QunService {

    private QunMapper qunMapper;

    public QunMapper getQunMapper() {
        return qunMapper;
    }

    @Autowired
    public void setQunMapper(QunMapper qunMapper) {
        this.qunMapper = qunMapper;
    }

    public List<Qun> listQun(){
        return qunMapper.selectAll();
    }

    public PageInfo<Qun> pageQun(int page, int size){
        PageInfo<Qun> qunPageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> qunMapper.selectAll());
        return qunPageInfo;
    }
}
