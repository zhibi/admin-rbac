package com.fgt.common.core.aspect;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * controller切面
 * 打印所有的请求日志
 *
 * @author 执笔
 */
@Aspect
@Component
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private static Pattern PATTERN = Pattern.compile("[ \t\n\r]");

    @Autowired
    private HttpServletRequest request;

    /**
     * 拦截所有的Controller
     */
    @Pointcut("execution(public * *..controller..*Controller.*(..))")
    public void controller() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("controller()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (isRequest(method)) {
            logger.info("**** 请求：{} ", buildAccessLog(this.request));
            logger.info("**** 参数：{} ", getMethodInfo(joinPoint));
        }
        return joinPoint.proceed();
    }

    /**
     * 是否是http请求
     *
     * @param method
     * @return
     */
    private boolean isRequest(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)
                || method.isAnnotationPresent(PostMapping.class)
                || method.isAnnotationPresent(GetMapping.class)
                || method.isAnnotationPresent(DeleteMapping.class)
                || method.isAnnotationPresent(PutMapping.class)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 方法参数
     *
     * @param point
     * @return
     */
    private String getMethodInfo(JoinPoint point) {
        String className = point.getSignature().getDeclaringType().getSimpleName();
        String methodName = point.getSignature().getName();
        //所有的参数名
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(parameterNames)) {
            for (int i = 0; i < parameterNames.length; i++) {
                Object arg = point.getArgs()[i];
                //处理参数值
                String value = "null";
                if (arg != null) {
                    if (isPrimitive(arg.getClass())) {
                        value = String.valueOf(arg);
                    } else if (arg instanceof MultipartFile) {
                        value = "FILE#" + ((MultipartFile) arg).getOriginalFilename() + "#" + ((MultipartFile) arg).getSize() / 1024 + "KB";
                    } else {
                        value = JSONUtils.toJSONString(value);
                    }
                }
                sb.append(parameterNames[i]).append(":").append(value).append(",");
            }
        }
        return String.format("%s.%s {%s}", className, methodName, sb.toString());
    }


    /**
     * 判断是否为基本类型
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */

    private boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive();
    }


    /**
     * 请求需要打印的日志
     *
     * @param request
     * @return
     */
    private String buildAccessLog(HttpServletRequest request) {
        StringBuilder accessLog = new StringBuilder();
        build(accessLog, request.getRemoteHost());
        build(accessLog, request.getMethod());
        build(accessLog, buildRequestPath(request));
        build(accessLog, buildRequestParam(request));
        build(accessLog, buildRequestHeard(request));
        return accessLog.toString();
    }

    private void build(StringBuilder sb, String str) {
        sb.append(" ");
        if (StringUtils.isEmpty(str)) {
            sb.append("-");
        } else if (PATTERN.matcher(str).find()) {
            sb.append("\"");
            sb.append(str.replace("\"", "\"\""));
            sb.append("\"");
        } else {
            sb.append(str);
        }
    }

    /**
     * 请求路径
     *
     * @return
     */
    private String buildRequestPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 请求参数
     *
     * @param request
     * @return
     */
    private String buildRequestParam(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder("param:{");
        Enumeration<String> enumeration = request.getParameterNames();
        return getRequestString(request, buffer, enumeration);
    }

    /**
     * 请求头
     *
     * @param request
     * @return
     */
    private String buildRequestHeard(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder("heard:{");
        Enumeration<String> enumeration = request.getHeaderNames();
        return getHeardString(request, buffer, enumeration);
    }

    /**
     * 请求里面的数据
     *
     * @param request
     * @param buffer
     * @param enumeration
     * @return
     */
    private String getRequestString(HttpServletRequest request, StringBuilder buffer, Enumeration<String> enumeration) {
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            buffer.append(key).append(":").append(request.getParameter(key)).append(",");
        }
        buffer.append("}");
        return buffer.toString();
    }

    /**
     * 请求头
     *
     * @param request
     * @param buffer
     * @param enumeration
     * @return
     */
    private String getHeardString(HttpServletRequest request, StringBuilder buffer, Enumeration<String> enumeration) {
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            buffer.append(key).append(":").append(request.getHeader(key)).append(",");
        }
        buffer.append("}");
        return buffer.toString();
    }

}
