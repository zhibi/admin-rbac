package com.fgt.common.core.mybatis.interceptor;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 自动添加时间
 * 添加的时候和更新的时候
 *
 * @author 执笔
 * @date 2018/12/5 11:08
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DateInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(DateInterceptor.class);

    /**
     * 插入的时候需要自动添加时间的字段
     */
    private List<String> insertDates;
    /**
     * 更新时候需要自动添加时间的字段
     */
    private List<String> updateDates;

    public DateInterceptor() {
        insertDates = new ArrayList<String>(2) {{
            add("createTime");
            add("updateTime");
        }};
        updateDates = new ArrayList<String>(1) {{
            add("updateTime");
        }};
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取参数
        Object parameter = invocation.getArgs()[1];

        if (sqlCommandType.equals(SqlCommandType.UPDATE)) {
            // 处理更新的时候
            setFieldDateTime(parameter, updateDates);
        } else if (sqlCommandType.equals(SqlCommandType.INSERT)) {
            // 处理插入的时候
            setFieldDateTime(parameter, insertDates);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 设置属性时间
     *
     * @param parameter
     * @param list
     */
    private void setFieldDateTime(Object parameter, List<String> list) {
        if (parameter != null) {
            // 获取所有插入的属性
            List<Field> fields = FieldUtils.getAllFieldsList(parameter.getClass());
            for (Field field : fields) {
                String name = field.getName();
                if (list.contains(name)) {
                    // 设置时间
                    setFieldValue(parameter, field);
                }
            }
        }
    }

    /**
     * 设置属性值
     */
    private void setFieldValue(Object obj, Field field) {
        try {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                return;
            }
            Class<?> type = field.getType();
            if (type.equals(Date.class)) {
                field.set(obj, new Date());
            } else if (type.equals(Long.class)) {
                field.set(obj, System.currentTimeMillis());
            } else if (type.equals(Timestamp.class)) {
                field.set(obj, new Timestamp(System.currentTimeMillis()));
            } else if (type.equals(LocalDate.class)) {
                field.set(obj, LocalDate.now());
            } else if (type.equals(LocalDateTime.class)) {
                field.set(obj, LocalDateTime.now());
            } else if (type.equals(Instant.class)) {
                field.set(obj, Instant.now());
            } else if (type.equals(LocalTime.class)) {
                field.set(obj, LocalTime.now());
            } else if (type.equals(String.class)) {
                field.set(obj, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            logger.debug("auto set field:{} value:{}", field.getName(), field.get(obj));
        } catch (IllegalAccessException ignored) {
        }
    }


    public void setInsertDates(List<String> insertDates) {
        this.insertDates = insertDates;
    }

    public void setUpdateDates(List<String> updateDates) {
        this.updateDates = updateDates;
    }
}
