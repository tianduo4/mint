package com.mint.plug.weixin.action.admin;

import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;
import com.mint.plug.weixin.entity.WeixinMenu;
import com.mint.plug.weixin.entity.WeixinMessage;
import com.mint.plug.weixin.manager.WeixinMessageMng;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeixinMessageAct {
    private static final Logger log = LoggerFactory.getLogger(WeixinMenuAct.class);

    @Autowired
    private WeixinMessageMng manager;

    @RequiresPermissions({"weixinMessage:v_list"})
    @RequestMapping({"/weixinMessage/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        Pagination p = this.manager.getPage(site.getId(), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));

        model.addAttribute("pagination", p);
        model.addAttribute("pageNo", pageNo);
        return "weixinMessage/list";
    }

    @RequiresPermissions({"weixinMessage:v_default_set"})
    @RequestMapping({"/weixinMessage/v_default_set.do"})
    public String setDefault(HttpServletRequest request, ModelMap model) {
        WeixinMessage defaultMsg = this.manager.getWelcome(SiteUtils.getWebId(request));
        model.addAttribute("sessionId", request.getSession().getId());
        if (defaultMsg == null) {
            return "weixinMessage/adddefault";
        }
        model.addAttribute("menu", defaultMsg);
        return "weixinMessage/editdefault";
    }

    @RequiresPermissions({"weixinMessage:o_default_save"})
    @RequestMapping({"/weixinMessage/o_default_save.do"})
    public String saveDefault(WeixinMessage bean, HttpServletRequest request, ModelMap model) {
        bean.setSite(SiteUtils.getWeb(request));
        bean.setWelcome(Boolean.valueOf(true));
        this.manager.save(bean);
        return setDefault(request, model);
    }

    @RequiresPermissions({"weixinMessage:o_default_update"})
    @RequestMapping({"/weixinMessage/o_default_update.do"})
    public String updateDefault(WeixinMessage bean, HttpServletRequest request, ModelMap model) {
        this.manager.update(bean);
        return setDefault(request, model);
    }

    @RequiresPermissions({"weixinMessage:v_add"})
    @RequestMapping({"/weixinMessage/v_add.do"})
    public String add(Integer pageNo, HttpServletRequest request, ModelMap model) {
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("pageNo", pageNo);
        return "weixinMessage/add";
    }

    @RequiresPermissions({"weixinMessage:v_edit"})
    @RequestMapping({"/weixinMessage/v_edit.do"})
    public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WeixinMessage entity = this.manager.findById(id);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("menu", entity);
        model.addAttribute("sessionId", request.getSession().getId());
        return "weixinMessage/edit";
    }

    @RequiresPermissions({"weixinMessage:o_save"})
    @RequestMapping({"/weixinMessage/o_save.do"})
    public String save(WeixinMessage bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        bean.setSite(site);
        bean.setWelcome(Boolean.valueOf(false));
        bean.setType(Integer.valueOf(0));
        this.manager.save(bean);
        return list(pageNo, request, model);
    }

    @RequiresPermissions({"weixinMessage:o_update"})
    @RequestMapping({"/weixinMessage/o_update.do"})
    public String update(WeixinMessage bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.manager.update(bean);
        return list(pageNo, request, model);
    }

    @RequiresPermissions({"weixinMessage:o_delete"})
    @RequestMapping({"/weixinMessage/o_delete.do"})
    public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        WeixinMessage[] beans = this.manager.deleteByIds(ids);
        for (WeixinMessage bean : beans) {
            log.info("delete WeixinMessage id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(ids, "ids")) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        WeixinMessage entity = this.manager.findById(id);

        return errors.ifNotExist(entity, WeixinMenu.class, id);
    }
}

