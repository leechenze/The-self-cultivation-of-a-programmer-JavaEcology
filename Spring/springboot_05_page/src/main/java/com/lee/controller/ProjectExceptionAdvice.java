package com.lee.controller;

import com.lee.exception.BusinessException;
import com.lee.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 表现层的异常处理类
 */
@RestControllerAdvice
public class ProjectExceptionAdvice {
    /**
     * 处理系统异常
     */
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException systemException) {
        // 记录日志
        // 发送消息给运维
        // 发送邮件给开发
        return new Result(systemException.getCode(), null, systemException.getMessage());
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException businessException) {
        return new Result(businessException.getCode(), null, businessException.getMessage());
    }

    /**
     * 处理其他异常（其实走到这个异常中，作为开发者是理亏的）
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception) {
        // 记录日志
        // 发送消息给运维
        // 发送邮件给开发
        System.out.println(exception);
        String message = exception.toString();
        return new Result(Code.OTHER_ERR, null, "系统繁忙，请重试！");
    }
}
