package com.bypx.synthesis.service;

import com.bypx.synthesis.dao.LogDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LogSerive {
    @Resource
    LogDao logDao;

    public void add(String name ,String id){
        logDao.add(name, id);
    }
}
