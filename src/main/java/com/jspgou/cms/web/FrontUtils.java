package com.jspgou.cms.web;

import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.freemarker.DirectiveUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import freemarker.core.Environment;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;

public class FrontUtils {
    public static final String SITE = "site";
    public static final String PARAM_SYS_PAGE = "sysPage";
    public static final String PARAM_USER_PAGE = "userPage";
    public static final String COUNT = "count";
    public static final String FIRST = "first";
    public static final String PAGE_NO = "pageNo";
    public static final String MESSAGE = "message";
    public static final String ARGS = "args";
    public static final String RETURN_URL = "returnUrl";
    public static final String RES_EXP = "${res}";
    public static final String MOBILE_RES_TPL = "mobileRes";

    public static Website getSite(Environment env)
            throws TemplateModelException {
        TemplateModel model = env.getGlobalVariable("site");
        if ((model instanceof AdapterTemplateModel)) {
            return (Website) ((AdapterTemplateModel) model)
                    .getAdaptedObject(Website.class);
        }
        throw new TemplateModelException("'site' not found in DataModel");
    }

    public static String getTplPath(HttpServletRequest request, String solution, String dir, String name) {
        String equipment = (String) request.getAttribute("ua");

        Website site = SiteUtils.getWeb(request);
        if ((StringUtils.isNotBlank(equipment)) && (equipment.equals("mobile"))) {
            solution = site.getMobileSolutionPath();
        }
        return solution + "/" + dir + "/" + MessageResolver.getMessage(request, name, new Object[0]) + ".html";
    }

    public static int getCount(Map<String, TemplateModel> params)
            throws TemplateException {
        Integer count = DirectiveUtils.getInt("count", params);
        if ((count == null) || (count.intValue() <= 0) || (count.intValue() >= 5000)) {
            return 5000;
        }
        return count.intValue();
    }

    public static int getFirst(Map<String, TemplateModel> params)
            throws TemplateException {
        Integer first = DirectiveUtils.getInt("first", params);
        if ((first == null) || (first.intValue() <= 0)) {
            return 0;
        }
        return first.intValue() - 1;
    }

    public static int getPageNo(Environment env)
            throws TemplateException {
        TemplateModel pageNo = env.getGlobalVariable("pageNo");
        if ((pageNo instanceof TemplateNumberModel)) {
            return ((TemplateNumberModel) pageNo).getAsNumber().intValue();
        }
        throw new TemplateModelException("'pageNo' not found in DataModel.");
    }

    public static void includePagination(Map<String, TemplateModel> params, Environment env)
            throws TemplateException, IOException {
        String sysPage = DirectiveUtils.getString("sysPage", params);
        String userPage = DirectiveUtils.getString("userPage", params);
        if (!StringUtils.isBlank(sysPage)) {
            String tpl = "/WEB-INF/t/gou_sys_defined/style_page/channel_" + sysPage + ".html";
            env.include(tpl, "UTF-8", true);
        } else {
            StringUtils.isBlank(userPage);
        }
    }

    public static String showMessage(HttpServletRequest request, ModelMap model, String message) {
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        model.put("message", message);
        return web.getTplSys("common", MessageResolver.getMessage(request, "tpl.messagePage", new Object[0]), request);
    }

    public static String showLogin(HttpServletRequest request, ModelMap model) {
        StringBuilder buff = new StringBuilder("redirect:");
        buff.append("/login.jspx").append("?");
        buff.append("returnUrl").append("=");
        buff.append(RequestUtils.getLocation(request));
        return buff.toString();
    }
}

