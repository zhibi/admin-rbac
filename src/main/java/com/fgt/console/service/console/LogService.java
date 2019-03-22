package com.fgt.console.service.console;

import com.fgt.console.mapper.LogMapper;
import com.fgt.console.domain.Log;
import com.fgt.common.util.CamelCaseUtil;
import com.fgt.common.util.DateUtil;
import com.fgt.common.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 执笔
 */
@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    public List<Log> getPageList(Log log) {
        PageHelper.offsetPage(log.getOffset(), log.getLimit());
        PageHelper.orderBy(CamelCaseUtil.toUnderlineName(log.getSort())+" "+log.getOrder());
        return logMapper.selectAll();
    }

    public void insert(Log log){
        logMapper.insert(log);
    }

    public void insertLoginLog(String username, String ip, String action){
        Log  log = new Log();
        log.setLogId(UuidUtil.getUUID());
        log.setLogUser(username);
        log.setLogTime(DateUtil.getCurrentTime());
        log.setLogIp(ip);
        log.setLogAction(action);
        this.insert(log);
    }


}
