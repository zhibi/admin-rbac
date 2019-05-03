package zhibi.admin.role.controller.console;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zhibi.admin.role.domain.Role;
import zhibi.admin.role.domain.User;
import zhibi.admin.role.domain.UserRole;
import zhibi.admin.role.mapper.RoleMapper;
import zhibi.admin.role.mapper.UserMapper;
import zhibi.admin.role.mapper.UserRoleMapper;
import zhibi.admin.role.service.RoleService;
import zhibi.admin.role.service.UserService;
import zhibi.fast.commons.exception.MessageException;
import zhibi.fast.commons.response.JsonResponse;
import zhibi.fast.spring.boot.annotation.Operation;
import zhibi.fast.spring.boot.controller.BaseController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console/user")
public class UserController extends BaseController {


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
        } else {
            user = new User();
        }
        model.addAttribute("checkRoleId", checkRoleId);
        model.addAttribute("roleList", roleService.getAllEnable());
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
    public JsonResponse list(User user) {
        PageInfo<User> pageInfo = userService.selectPage(user);
        for (User list : pageInfo.getList()) {
            List<Role> roleList = roleMapper.selectRoleListByUserId(list.getId());
            list.setRoleList(roleList);
        }
        return JsonResponse.success("加载成功", pageInfo);
    }

    @Operation("更新用户信息")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String merge(@Valid User user, BindingResult result, @RequestParam(defaultValue = "") String roleIds, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
            }
            userService.updateOrSaveUser(user, roleIds.split(","));
            return redirect("/console/user/index", "操作成功", attributes);
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

    @Operation("删除用户")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResponse delete(Long id) {
        userMapper.deleteById(id);
        return JsonResponse.success("删除成功");
    }

}
