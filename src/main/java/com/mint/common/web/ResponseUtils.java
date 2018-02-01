package com.mint.common.web;

import com.mint.cms.api.ApiResponse;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResponseUtils {
    public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("access-control-allow-methods", "POST, GET,HEAD, OPTIONS,PATCH, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("access-control-allow-credentials", "true");
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void renderApiJson(HttpServletResponse response, HttpServletRequest request, ApiResponse apiResult) {
        response.setCharacterEncoding("UTF-8");
        renderJson(response, apiResult.toString());
    }
}

