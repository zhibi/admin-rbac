package zhibi.admin.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhibi.admin.role.domain.Log;
import zhibi.admin.role.domain.User;
import zhibi.admin.role.mapper.LogMapper;
import zhibi.admin.role.service.LogService;
import zhibi.fast.mybatis.service.impl.BaseServiceImpl;

/**
 * @author 执笔
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public void insertLog(User user, String ip, String action, String data) {
        Log log = new Log();
        if (null != user) {
            log.setUser(user.getUsername());
            log.setUserId(user.getId());
        }
        log.setIp(ip);
        log.setAction(action);
        log.setData(data);
        logMapper.insertSelective(log);
    }
}
