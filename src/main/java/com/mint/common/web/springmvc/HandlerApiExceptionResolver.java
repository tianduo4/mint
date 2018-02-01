package com.mint.common.web.springmvc;

import com.mint.cms.api.ApiResponse;
import com.mint.common.web.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class HandlerApiExceptionResolver
        implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        e.printStackTrace();
        int code = 100;
        String body = "\"\"";
        String message = "\"system exception\"";
        Class exceptionClass = e.getClass();
        if (exceptionClass.equals(BindException.class)) {
            code = 202;
            message = "\"param error\"";
        } else if (exceptionClass.equals(MissingServletRequestParameterException.class)) {
            code = 201;
            message = "\"param required\"";
        } else if (exceptionClass.equals(TypeMismatchException.class)) {
            code = 202;
            message = "\"param error\"";
        } else if (exceptionClass.equals(ServletRequestBindingException.class)) {
            code = 202;
            message = "\"param error\"";
        } else if (exceptionClass.equals(DataIntegrityViolationException.class)) {
            code = 202;
            message = "\"param error\"";
        } else if (exceptionClass.equals(BadSqlGrammarException.class)) {
            code = 202;
            message = "\"param error\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}

