package zhibi.admin.role.service;

import zhibi.admin.role.common.base.service.BaseService;
import zhibi.admin.role.domain.User;

/**
 * @author 执笔
 */
public interface UserService extends BaseService<User> {


    /**
     * 更新或者保存用户
     *
     * @param user
     * @param split
     */
    void updateOrSaveUser(User user, String[] split);
}
