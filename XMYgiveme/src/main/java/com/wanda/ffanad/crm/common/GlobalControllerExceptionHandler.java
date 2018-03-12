package com.wanda.ffanad.crm.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.core.common.RestResult;

/**
 * @author huangzhiqiang19
 * @date 2016年5月20日 下午8:09:02
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * 用户hibernate validator绑定数据时抛出的异常
     * 
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<RestResult> validationErrorHandler(HttpServletRequest request, BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ObjectError> objectErrors = e.getBindingResult().getGlobalErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            sb.append("字段" + fieldError.getField() + fieldError.getDefaultMessage() + ";");
        }
        for (ObjectError objectError : objectErrors) {
            sb.append("字段" + objectError.getObjectName() + objectError.getDefaultMessage() + ";");
        }

        if (sb.toString().endsWith(";")) {
            sb.deleteCharAt(sb.lastIndexOf(";"));
        }

        RestResult restResult = new RestResult();
        restResult.setStatus(HttpStatus.BAD_REQUEST.value());
        restResult.setMessage(sb.toString());
        return new ResponseEntity<RestResult>(restResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * RequestParam 跑出的为空异常
     * 
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<RestResult> paramErrorHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        RestResult restResult = new RestResult();
        restResult.setStatus(HttpStatus.BAD_REQUEST.value());
        restResult.setMessage(e.getMessage());
        return new ResponseEntity<RestResult>(restResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FfanadException.class)
    public ResponseEntity<RestResult> exceptionHandler(HttpServletRequest request, FfanadException e) {
        //        LOGGER.error("FfanadException", e);
        RestResult restResult = new RestResult(e.getStatus(), e.getMessage());
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (responseStatus == null) {
            return new ResponseEntity<RestResult>(restResult, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<RestResult>(restResult, responseStatus.value());
        }
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResult> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        LOGGER.error("Controller Exception", e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        RestResult restResult = new RestResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        return new ResponseEntity<RestResult>(restResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
