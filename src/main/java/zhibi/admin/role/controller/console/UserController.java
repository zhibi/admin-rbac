package zhibi.admin.role.controller.console;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zhibi.admin.role.common.annotation.Operation;
import zhibi.admin.role.common.utils.PasswordUtils;
import zhibi.admin.role.common.utils.ReturnUtils;
import zhibi.admin.role.domain.Role;
import zhibi.admin.role.domain.User;
import zhibi.admin.role.domain.UserRole;
import zhibi.admin.role.mapper.RoleMapper;
import zhibi.admin.role.mapper.UserMapper;
import zhibi.admin.role.mapper.UserRoleMapper;
import zhibi.admin.role.service.RoleService;
import zhibi.admin.role.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console/user")
public class UserController {


    @Autowired
    private UserService    userService;
    @Autowired
    private UserMapper     userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper     roleMapper;
    @Autowired
    private RoleService    roleService;

    @Operation("查看用户列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/user/index";
    }


    @Operation("用户详情")
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET})
    public String detail(@PathVariable Integer id, Model model) {
        String checkRoleId = "";
        User   user        = userMapper.selectByPrimaryKey(id);
        if (null != user) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            List<UserRole>    userRoleLists = userRoleMapper.select(userRole);
            ArrayList<String> checkRoleIds  = new ArrayList<>();
            for (UserRole userRoleList : userRoleLists) {
                checkRoleIds.add(userRoleList.getRoleId() + "");
            }
            checkRoleId = String.join(",", checkRoleIds);
        }
        model.addAttribute("checkRoleId", checkRoleId);
        model.addAttribute("roleLists", roleService.getAllEnable());
        model.addAttribute("user", user);
        return "console/user/detail";
    }

    /**
     * 异步加载用户列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(User user) {
        ModelMap       map      = new ModelMap();
        PageInfo<User> pageInfo = userService.selectPage(user);
        for (User list : pageInfo.getList()) {
            List<Role> roleList = roleMapper.selectRoleListByUserId(list.getId());
            list.setRoleList(roleList);
        }
        map.put("pageInfo", pageInfo);
        map.put("queryParam", user);
        return ReturnUtils.success("加载成功", map, null);
    }

    @Transactional
    @Operation("更新用户信息")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap merge(@Valid User user, BindingResult result, @RequestParam(defaultValue = "") String roleIds) {
        try {
            if (result.hasErrors()) {
                return ReturnUtils.error(result.getAllErrors().get(0).getDefaultMessage(), null, null);
            }
            userService.updateOrSaveUser(user, roleIds.split(","));
            return ReturnUtils.success("操作成功", null, "/console/user/index");
        } catch (Exception e) {
            return ReturnUtils.error(e.getMessage(), null, null);
        }
    }

    @Operation("删除用户")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(String[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    userMapper.deleteById(id);
                }
                return ReturnUtils.success("删除成功", null, null);
            } else {
                return ReturnUtils.error("删除失败", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtils.error("删除失败", null, null);
        }
    }

    @Operation("修改用户密码")
    @RequestMapping(value = "/modifyPwd", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap modifyPwd(Integer id, String password) {
        try {
            if (id != null && StringUtils.isNotEmpty(password)) {
                User user = userMapper.selectByPrimaryKey(id);
                if (null != user) {
                    String newPassword = PasswordUtils.createPwd(password, user.getSalt());
                    user.setPassword(newPassword);
                    userMapper.updateByPrimaryKeySelective(user);
                    return ReturnUtils.success("操作成功", null, null);
                } else {
                    return ReturnUtils.error("对像不存在，修改失败", null, null);
                }
            } else {
                return ReturnUtils.error("参数错误，修改失败", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtils.error("修改失败", null, null);
        }
    }

}
