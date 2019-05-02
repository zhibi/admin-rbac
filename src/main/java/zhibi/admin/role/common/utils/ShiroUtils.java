package zhibi.admin.role.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import zhibi.admin.role.domain.User;

/**
 * shiro工具类
 *
 * @author 执笔
 */
public class ShiroUtils {

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Boolean isLogin() {
        return getSubject().isAuthenticated();
    }

    /**
     * 获取session信息
     *
     * @return
     */
    public static Session getSession() {
        try {
            Session session = getSubject().getSession();
            if (session == null) {
                session = getSubject().getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static User getUserInfo() {
        try {
            if (getSession() != null) {
                return (User) getSubject().getPrincipals().getPrimaryPrincipal();
            }
        } catch (Exception ignored) {

        }
        throw new RuntimeException("用户未登录");
    }
}
