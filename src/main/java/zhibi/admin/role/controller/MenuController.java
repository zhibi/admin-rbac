package zhibi.admin.role.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zhibi.admin.role.common.annotation.Operation;
import zhibi.admin.role.common.controller.BaseController;
import zhibi.admin.role.common.utils.ReturnUtils;
import zhibi.admin.role.domain.Menu;
import zhibi.admin.role.dto.MenuDTO;
import zhibi.admin.role.dto.MenuTree;
import zhibi.admin.role.mapper.MenuMapper;
import zhibi.admin.role.service.MenuService;
import zhibi.admin.role.service.RoleMenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("/menu")
@RequiresAuthentication
public class MenuController extends BaseController {


    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper  menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Operation("菜单列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        ArrayList<Menu> menuLists = new ArrayList<>();
        List<Menu>      lists     = menuService.getChildMenuList(menuLists, 0);
        model.addAttribute("menus", lists);
        return "menu/list";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list() {
        ModelMap      map          = new ModelMap();
        List<Menu>    list         = menuMapper.selectAllMenu();
        MenuTree      menuTreeUtil = new MenuTree(list, null);
        List<MenuDTO> treeGridList = menuTreeUtil.buildTreeGrid();
        map.put("treeList", treeGridList);
        map.put("total", list.size());
        return ReturnUtils.success("加载成功", map, null);
    }

   /* @RequiresPermissions("console:edit")
    @RequestMapping(value = "/from", method = {RequestMethod.GET})
    public String add(Menu menu, Model model) {
        if (StringUtils.isEmpty(menu.getParentId())) {
            menu.setParentId("0");
        }
        if (!StringUtils.isEmpty(menu.getMenuId())) {
            menu = menuService.getById(menu.getMenuId());
            if (!"null".equals(menu)) {
                menu.setUpdatedAt(DateUtil.getCurrentTime());
            }
        } else {
            menu.setChildNum(0);
            menu.setListorder(0);
            menu.setMenuType("menu");
            menu.setCreatedAt(DateUtil.getCurrentTime());
            menu.setUpdatedAt(DateUtil.getCurrentTime());
        }
        model.addAttribute("menu", menu);
        return "console/menu/from";
    }

    @RequiresPermissions("menu:save")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @Transactional
    @ResponseBody
    public ModelMap save(@Valid Menu menu, BindingResult result) {
        try {
            if (result.hasErrors()) {
                for (ObjectError er : result.getAllErrors()) {
                    return ReturnUtils.error(er.getDefaultMessage(), null, null);
                }
            }
            if (StringUtils.isEmpty(menu.getMenuId())) {
                String id = UuidUtil.getUUID();
                menu.setMenuId(id);
                menuService.insert(menu);
            } else {
                menuService.save(menu);
            }
            if (!"0".equals(menu.getParentId())) {
                //更新父类总数
                Example example = new Example(Menu.class);
                example.createCriteria().andCondition("parent_id = ", menu.getParentId());
                Integer parentCount = menuService.getCount(example);
                Menu    parentMenu  = menuService.getById(menu.getParentId());
                menuService.getById(menu.getParentId());
                parentMenu.setChildNum(parentCount);
                menuService.save(parentMenu);
            }
            return ReturnUtils.success("操作成功", null, "/console/menu/index");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtils.error("操作失败", null, null);
        }
    }

    @RequiresPermissions("menu:listorder")
    @RequestMapping(value = "/listorder", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap updateOrder(String id, Integer listorder) {
        if (StringUtils.isNotBlank(id)) {
            Menu menu = new Menu();
            menu.setListorder(listorder);
            Example example = new Example(Menu.class);
            example.createCriteria()
                    .andCondition("menu_id = ", id);
            menuService.update(menu, example);
            return ReturnUtils.success("success", null, null);
        } else {
            return ReturnUtils.error("error", null, null);
        }
    }

    @ResponseBody
    @RequiresPermissions("menu:delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public ModelMap delete(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtils.error("error", null, null);
            } else {
                for (String id : ids) {
                    roleMenuService.deleteMenuId(id);
                    menuService.deleteById(id);
                }
                return ReturnUtils.success("success", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtils.error("error", null, null);
        }
    }*/


}
