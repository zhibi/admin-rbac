package zhibi.admin.role.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import zhibi.admin.role.domain.User;
import zhibi.admin.role.mapper.MenuMapper;
import zhibi.admin.role.mapper.UserMapper;

import java.util.Set;

/**
 * 后台身份校验核心类
 *
 * @author 执笔
 */
@Slf4j
public class AdminShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;


    /**
     * 认证信息.(身份验证)
     * :
     * Authentication 是用来验证用户身份
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();

        //通过username从数据库中查找 User对象
        User userInfo = userMapper.selectByUsername(username);
        if (userInfo == null) {
            throw new UnknownAccountException();
        }
        if (userInfo.getState() == User.UserStateEnum.LOCKING) {
            //帐号锁定
            throw new LockedAccountException();
        }
        //加密方式;
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现

        return new SimpleAuthenticationInfo(
                //用户
                userInfo,
                //密码
                userInfo.getPassword(),
                //salt=username+salt
                ByteSource.Util.bytes(userInfo.getSalt()),
                //realm name
                userInfo.getUsername()
        );
    }


    /**
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        log.info("后台权限校验");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User                    user              = (User) principals.getPrimaryPrincipal();
        Set<String>             menus;
        if (user.getType() == User.UserTypeEnum.ADMIN) {
            menus = menuMapper.selectALLMenuCode();
        } else {
            menus = menuMapper.selectMenuCodeByUserId(user.getId());
        }
        authorizationInfo.setStringPermissions(menus);
        return authorizationInfo;
    }
}
