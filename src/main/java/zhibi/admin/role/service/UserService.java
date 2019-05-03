package zhibi.admin.role.service;


import zhibi.admin.role.domain.User;
import zhibi.fast.mybatis.service.BaseService;

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
