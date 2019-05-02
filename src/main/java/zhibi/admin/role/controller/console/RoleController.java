package zhibi.admin.role.controller.console;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zhibi.admin.role.common.annotation.Operation;
import zhibi.admin.role.common.controller.BaseController;
import zhibi.admin.role.common.exception.MessageException;
import zhibi.admin.role.common.utils.ReturnUtils;
import zhibi.admin.role.domain.Menu;
import zhibi.admin.role.domain.Role;
import zhibi.admin.role.domain.RoleMenu;
import zhibi.admin.role.dto.MenuTree;
import zhibi.admin.role.mapper.MenuMapper;
import zhibi.admin.role.mapper.RoleMapper;
import zhibi.admin.role.mapper.RoleMenuMapper;
import zhibi.admin.role.mapper.UserRoleMapper;
import zhibi.admin.role.service.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("/console/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService    roleService;
    @Autowired
    private RoleMapper     roleMapper;
    @Autowired
    private MenuMapper     menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;


    @Operation("查看角色")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/role/index";
    }

    /**
     * 异步查看角色列表
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Role role) {
        ModelMap       map      = new ModelMap();
        PageInfo<Role> pageInfo = roleService.selectPage(role);
        for (Role item : pageInfo.getList()) {
            List<Menu> menuList = menuMapper.selectMenuByRoleId(item.getId());
            item.setMenuList(menuList);
        }
        map.put("pageInfo", pageInfo);
        map.put("queryParam", role);
        return ReturnUtils.success("加载成功", map, null);
    }

    /**
     * 角色列表
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "detail/{id}", method = {RequestMethod.GET})
    public String from(@PathVariable Integer id, Model model) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if (role == null) {
            role = new Role();
        }
        model.addAttribute("role", role);
        return "console/role/detail";
    }

    @Operation("添加更新角色")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String save(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
        }
        roleService.merge(role);
        return redirect("/console/role/index", "操作成功", attributes);
    }

    @Operation("删除角色")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(Integer id) {
        roleMapper.deleteById(id);
        return ReturnUtils.success("操作成功", null, null);
    }

    /**
     * 授权页面
     *
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/grant", method = {RequestMethod.GET})
    public String grantForm(String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "console/role/grant";
    }

    @Operation("角色授权")
    @RequestMapping(value = "/grant", method = {RequestMethod.POST})
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ModelMap grant(Integer roleId, String[] menuIds) {
        try {
            roleMenuMapper.delete(new RoleMenu().setRoleId(roleId));
            if (menuIds != null && roleId != null) {
                for (String menuId : menuIds) {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(Integer.parseInt(menuId));
                    roleMenu.setRoleId(roleId);
                    roleMenuMapper.insertSelective(roleMenu);
                }
            }
            return ReturnUtils.success("操作成功", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtils.error("操作失败", null, null);
        }
    }

    /**
     * 分配权限树
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/menutree", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelMap comboTree(Integer roleId) {
        List<Menu> menuLists = menuMapper.selectAll();
        RoleMenu   roleMenu  = new RoleMenu();
        roleMenu.setRoleId(roleId);
        List<RoleMenu>            roleMenuLists = roleMenuMapper.select(roleMenu);
        MenuTree                  menuTreeUtil  = new MenuTree(menuLists, roleMenuLists);
        List<Map<String, Object>> mapList       = menuTreeUtil.buildTree();
        return ReturnUtils.success(null, mapList, null);
    }


   /* @RequestMapping(value = "/combobox", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelMap comboBox() {
        ModelMap   map      = new ModelMap();
        List<Role> roleList = roleService.getFromAll();
        map.put("roleList", roleList);
        return ReturnUtils.success(null, map, null);
    }*/

   /*




    @RequestMapping(value = "/menulist", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap menulist(String id) {
        ModelMap map      = new ModelMap();
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(id);
        List<RoleMenu>    roleMenuLists = roleMenuService.getRoleList(roleMenu);
        ArrayList<String> roleList      = new ArrayList<>();
        for (RoleMenu roleMenuList : roleMenuLists) {
            roleList.add(roleMenuList.getMenuId());
        }
        map.put("id", id);
        map.put("roleList", roleList);
        return ReturnUtils.success("操作成功", map, null);
    }*/
}
