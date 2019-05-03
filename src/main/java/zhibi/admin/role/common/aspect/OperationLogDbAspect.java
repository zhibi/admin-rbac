package zhibi.admin.role.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import zhibi.admin.role.common.utils.ShiroUtils;
import zhibi.admin.role.service.LogService;
import zhibi.fast.commons.utils.IPUtils;
import zhibi.fast.commons.utils.JSONUtils;
import zhibi.fast.spring.boot.annotation.Operation;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 操作日志记录切面
 *
 * @author 执笔
 * @date 2019/3/28 11:13
 */
@Aspect
@Slf4j
@Component
@ConditionalOnClass(ProceedingJoinPoint.class)
public class OperationLogDbAspect {

    @Autowired
    private LogService         logService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 切面
     */
    @Pointcut("@annotation(zhibi.fast.spring.boot.annotation.Operation)")
    public void pointcut() {
    }

    /**
     * 通知
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method    method    = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Operation operation = method.getAnnotation(Operation.class);
        Object    proceed   = joinPoint.proceed();
        logService.insertLog(ShiroUtils.getUserInfo(), IPUtils.getLocalIp(request), operation.value(), getParamLog(joinPoint, method).toString());
        return proceed;
    }

    /**
     * 得到请求参数日志
     *
     * @param joinPoint
     * @param method
     * @return
     */
    private StringBuffer getParamLog(ProceedingJoinPoint joinPoint, Method method) {
        StringBuffer paramBuffer = new StringBuffer("{");
        Parameter[]  parameters  = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            String name = parameters[i].getName();
            paramBuffer
                    .append(name)
                    .append(":")
                    .append(JSONUtils.toJson(joinPoint.getArgs()[i]))
                    .append(",");
        }
        return paramBuffer.append("}");
    }


}
