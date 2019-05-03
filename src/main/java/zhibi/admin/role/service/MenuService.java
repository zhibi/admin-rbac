package zhibi.admin.role.service;


import zhibi.admin.role.domain.Menu;
import zhibi.fast.mybatis.service.BaseService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */

public interface MenuService extends BaseService<Menu> {


    /**
     *
     * @param menuList
     * @param parentId
     * @return
     */
    List<Menu> getChildMenuList(ArrayList<Menu> menuList, Long parentId);
}
