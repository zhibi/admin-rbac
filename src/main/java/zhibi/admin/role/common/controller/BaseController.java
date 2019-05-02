package zhibi.admin.role.common.controller;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import zhibi.admin.role.common.context.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 执笔
 * @date 2019/4/9 21:48
 */
@Slf4j
public abstract class BaseController implements Constant {
    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);


    @Autowired
    protected HttpSession        session;
    @Autowired
    protected   HttpServletRequest request;

    /**
     * 刷新页面
     *
     * @return
     * @Date 2016年2月24日 上午11:16:22
     */
    protected String refresh() {
        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * 重定向
     *
     * @param viewName
     * @return
     */
    protected String redirect(String viewName) {
        return "redirect:" + viewName;
    }

}
