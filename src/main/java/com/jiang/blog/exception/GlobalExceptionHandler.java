package com.jiang.blog.exception;

import com.jiang.blog.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/*
  统一处理异常 主要作用于处理 系统异常 将 系统异常 转成api的输出
 */
// 拦截异常的
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //    @ExceptionHandler 表示处理那种类型的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception:", e);
        return ApiRestResponse.error(BlogExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(BlogException.class)
    @ResponseBody
    public Object handleMallException(BlogException e) {
        log.error("BlogException Exception:", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    // MethodArgumentNotValidException处理Controller抛出的校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: ", e);
        return handleBindingResult(e.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ApiRestResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException: ", e);
        return ApiRestResponse.error(400, e.getMessage());
    }

    private ApiRestResponse handleBindingResult(BindingResult result) {
        // 把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (int i = 0; i < allErrors.size(); i++) {
                ObjectError objectError = allErrors.get(i);
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.error(BlogExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(BlogExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }

}
