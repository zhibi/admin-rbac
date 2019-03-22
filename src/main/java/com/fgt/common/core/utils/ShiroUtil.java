package com.fgt.common.core.utils;

import com.fgt.console.domain.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro工具类
 *
 * @author 执笔
 */
public class ShiroUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public static Admin getUserInfo() {
        try {
            if (getSession() != null) {
                return (Admin) getSubject().getPrincipals().getPrimaryPrincipal();
            } else {
                return null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
