package com.fgt.common.core.mybatis.condition;

import java.util.List;

/**
 * 查询条件
 *
 * @author 执笔
 * @date 2018/12/5 10:35
 */
public class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    Criterion(String condition) {
        this.condition = condition;
        this.noValue = true;
    }

    Criterion(String condition, Object value) {
        this.condition = condition;
        this.value = value;
        if (value instanceof List<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    Criterion(String condition, Object value, Object secondValue) {
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.betweenValue = true;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }

    public boolean isNoValue() {
        return noValue;
    }

    public void setNoValue(boolean noValue) {
        this.noValue = noValue;
    }

    public boolean isSingleValue() {
        return singleValue;
    }

    public void setSingleValue(boolean singleValue) {
        this.singleValue = singleValue;
    }

    public boolean isBetweenValue() {
        return betweenValue;
    }

    public void setBetweenValue(boolean betweenValue) {
        this.betweenValue = betweenValue;
    }

    public boolean isListValue() {
        return listValue;
    }

    public void setListValue(boolean listValue) {
        this.listValue = listValue;
    }
}