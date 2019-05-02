package zhibi.admin.role.service;

import zhibi.admin.role.common.base.service.BaseService;
import zhibi.admin.role.domain.Log;
import zhibi.admin.role.domain.User;

/**
 * @author 执笔
 */
public interface LogService extends BaseService<Log> {


    /**
     * 保存操作日志
     *
     * @param user
     * @param ip
     * @param action
     */
    void insertLog(User user, String ip, String action, String data);


}
