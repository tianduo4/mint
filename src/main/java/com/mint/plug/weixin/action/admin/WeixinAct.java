package com.mint.plug.weixin.action.admin;

import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;
import com.mint.plug.weixin.entity.Weixin;
import com.mint.plug.weixin.manager.WeixinMng;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeixinAct {

    @Autowired
    private WeixinMng manager;

    @Autowired
    private WebsiteMng siteMng;

    @RequiresPermissions({"weixin:v_edit"})
    public String add(HttpServletRequest request, ModelMap model) {
        return "weixin/add";
    }

    @RequiresPermissions({"weixin:v_edit"})
    @RequestMapping({"/weixin/v_edit.do"})
    public String edit(HttpServletRequest request, ModelMap model) {
        Weixin entity = this.manager.find(SiteUtils.getWebId(request));
        if (entity != null) {
            model.addAttribute("site", SiteUtils.getWeb(request));
            model.addAttribute("weixin", entity);
            return "weixin/edit";
        }
        return add(request, model);
    }

    @RequiresPermissions({"weixin:o_update"})
    @RequestMapping({"/weixin/o_save.do"})
    public String save(Weixin bean, String appKey, String appSecret, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        bean.setSite(site);
        bean.setAppKey(appKey);
        bean.setAppSecret(appSecret);
        this.manager.save(bean);
        return edit(request, model);
    }

    @RequiresPermissions({"weixin:o_update"})
    @RequestMapping({"/weixin/o_update.do"})
    public String update(Weixin bean, String appKey, String appSecret, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        bean.setAppKey(appKey);
        bean.setAppSecret(appSecret);
        this.manager.update(bean);
        return edit(request, model);
    }

    private WebErrors validateCheck(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);

        Website site = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");

        return errors;
    }
}

