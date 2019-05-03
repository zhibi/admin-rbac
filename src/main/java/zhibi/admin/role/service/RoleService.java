package zhibi.admin.role.service;


import zhibi.admin.role.domain.Menu;
import zhibi.admin.role.domain.Role;
import zhibi.fast.mybatis.service.BaseService;

import java.util.List;

/**
 * @author 执笔
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 所有可用角色
     *
     * @return
     */
    List<Role> getAllEnable();
}
