package zhibi.admin.role.common.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 执笔
 * 异常处理类
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * 未授权异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public Object constraintViolationExceptionHandler(HttpServletRequest request, AuthorizationException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        ModelMap     modelMap     = modelAndView.getModelMap();
        modelMap.addAttribute("message", "未授权");
        modelMap.addAttribute("status", "401");
        modelMap.addAttribute("timestamp", new Date());
        return modelAndView;
    }
}
