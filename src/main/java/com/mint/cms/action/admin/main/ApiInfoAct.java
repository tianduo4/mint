package com.mint.cms.action.admin.main;

import com.mint.cms.entity.ApiInfo;
import com.mint.cms.manager.ApiInfoMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiInfoAct {
    private static final Logger log = LoggerFactory.getLogger(ApiInfoAct.class);

    @Autowired
    private ApiInfoMng manager;

    @RequestMapping({"/apiInfo/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "apiInfo/list";
    }

    @RequestMapping({"/apiInfo/v_add.do"})
    public String add(ModelMap model) {
        return "apiInfo/add";
    }

    @RequestMapping({"/apiInfo/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("apiInfo", this.manager.findById(id));
        return "apiInfo/edit";
    }

    @RequestMapping({"/apiInfo/o_save.do"})
    public String save(ApiInfo bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean);
        log.info("save ApiInfo id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/apiInfo/o_update.do"})
    public String update(ApiInfo bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean);
        log.info("update ApiInfo id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/apiInfo/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ApiInfo[] beans = this.manager.deleteByIds(ids);
        for (ApiInfo bean : beans) {
            log.info("delete ApiInfo id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    private WebErrors validateSave(ApiInfo bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);

        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, web.getId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Long id, Long webId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ApiInfo entity = this.manager.findById(id);

        return errors.ifNotExist(entity, ApiInfo.class, id);
    }
}

