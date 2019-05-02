package zhibi.admin.role.service;

import zhibi.admin.role.common.base.service.BaseService;
import zhibi.admin.role.domain.Menu;

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
    List<Menu> getChildMenuList(ArrayList<Menu> menuList, Integer parentId);
}
