package zhibi.admin.role.service;


import zhibi.admin.role.domain.Log;
import zhibi.admin.role.domain.User;
import zhibi.fast.mybatis.service.BaseService;

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
