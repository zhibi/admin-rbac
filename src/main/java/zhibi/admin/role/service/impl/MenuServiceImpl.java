package zhibi.admin.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import zhibi.admin.role.domain.Menu;
import zhibi.admin.role.mapper.MenuMapper;
import zhibi.admin.role.service.MenuService;
import zhibi.fast.mybatis.example.MybatisExample;
import zhibi.fast.mybatis.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getChildMenuList(ArrayList<Menu> menuList, Long parentId) {
        MybatisExample condition = new MybatisExample()
                .eq("parent_id", parentId)
                .order("sort", false);
        List<Menu> list = menuMapper.selectByExample(condition);
        for (Menu menu : list) {
            menuList.add(menu);
            getChildMenuList(menuList, menu.getId());
        }
        return menuList;
    }

}
