package zhibi.admin.role.controller.console;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zhibi.admin.role.common.annotation.Operation;
import zhibi.admin.role.common.controller.BaseController;
import zhibi.admin.role.common.utils.PasswordUtils;
import zhibi.admin.role.common.utils.ShiroUtils;
import zhibi.admin.role.domain.Menu;
import zhibi.admin.role.domain.Role;
import zhibi.admin.role.domain.User;
import zhibi.admin.role.dto.MenuDTO;
import zhibi.admin.role.dto.MenuTree;
import zhibi.admin.role.mapper.MenuMapper;
import zhibi.admin.role.mapper.RoleMapper;
import zhibi.admin.role.mapper.UserMapper;
import zhibi.admin.role.valid.ValidUser;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console")
@Slf4j
public class HomeController extends BaseController {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/index";
    }


    /**
     * 首页展示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "main", method = {RequestMethod.GET})
    public String right(Model model) {
        model.addAllAttributes(this.getTotal());
        return "console/right";
    }

    /**
     * 首页展示数据
     *
     * @return
     */
    private Map<String, Object> getTotal() {
        Integer             userCount = userMapper.selectCount(new User());
        Integer             roleCount = roleMapper.selectCount(new Role());
        Integer             menuCount = menuMapper.selectCount(new Menu());
        Map<String, Object> mp        = new HashMap<>(4);
        mp.put("userCount", userCount);
        mp.put("roleCount", roleCount);
        mp.put("menuCount", menuCount);
        return mp;
    }

    /**
     * 获取菜单
     *
     * @param user
     * @return
     */
    private List<MenuDTO> getMenu(User user) {
        List<Menu> menuLists = null;
        if (user.getType() == User.UserTypeEnum.ADMIN) {
            menuLists = menuMapper.selectAllMenu();
        } else {
            menuLists = menuMapper.selectMenuByUserId(user.getId());
        }
        MenuTree menuTreeUtil = new MenuTree(menuLists, null);
        return menuTreeUtil.buildTreeGrid();
    }

    /**
     * 登录页
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        if (ShiroUtils.isLogin()) {
            return "redirect:index";
        }
        return "console/login";
    }

    @Operation("登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(@Valid ValidUser validUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:login";
        }
        String                username = validUser.getUsername();
        UsernamePasswordToken token    = new UsernamePasswordToken(validUser.getUsername(), validUser.getPassword());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + username + "]登录认证通过");
            List<MenuDTO> treeGridList = this.getMenu(ShiroUtils.getUserInfo());
            session.setAttribute("menuList", treeGridList);
            session.setAttribute(SESSION_USER, ShiroUtils.getUserInfo());
            return "redirect:index";
        } else {
            token.clear();
            return "redirect:login";
        }
    }

    /**
     * 退出登录
     *
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        log.info("【退出登录】 {}", ShiroUtils.getUserInfo().getUsername());
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "您已安全退出");
        return "redirect:login";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/modifyPwd", method = {RequestMethod.GET})
    public String modifyPwd() {
        return "console/modify-pwd";
    }

    @Operation("修改用户密码")
    @RequestMapping(value = "/modifyPwd", method = {RequestMethod.POST})
    public String modifyPwd(String pwd, String password, String password2, RedirectAttributes attributes) {
        if (!password.equals(password2)) {
            return redirect("/console/modifyPwd", "两次密码不一样", attributes);
        }
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getUserInfo().getId());
        if (null != user) {
            if (!PasswordUtils.createPwd(pwd, user.getSalt()).equalsIgnoreCase(user.getPassword())) {
                return redirect("/console/modifyPwd", "原密码错误", attributes);
            }
            String newPassword = PasswordUtils.createPwd(password, user.getSalt());
            user.setPassword(newPassword);
            userMapper.updateByPrimaryKeySelective(user);
            return redirect("/console/modifyPwd", "修改成功", attributes);
        } else {
            return redirect("/console/modifyPwd", "对像不存在，修改失败", attributes);
        }
    }
}
