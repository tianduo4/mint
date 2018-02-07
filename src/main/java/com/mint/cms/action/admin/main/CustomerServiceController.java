package com.mint.cms.action.admin.main;

import com.mint.cms.entity.CustomerService;
import com.mint.cms.manager.CustomerServiceMng;
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
public class CustomerServiceController {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceController.class);

    @Autowired
    private CustomerServiceMng manager;

    @RequestMapping({"/customerService/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPagination(null, SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pageNo);
        return "customerService/list";
    }

    @RequestMapping({"/customerService/v_add.do"})
    public String add(HttpServletRequest request, ModelMap model) {
        return "customerService/add";
    }

    @RequestMapping({"/customerService/v_edit.do"})
    public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("customerService", this.manager.findById(id));
        model.addAttribute("pageNo", pageNo);
        return "customerService/edit";
    }

    @RequestMapping({"/customerService/o_save.do"})
    public String save(CustomerService bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean);
        log.info("save CustomerService id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/customerService/o_update.do"})
    public String qqUpdate(CustomerService bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean);
        log.info("update CustomerService id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/customerService/o_delete.do"})
    public String qqDelete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        CustomerService[] beans = this.manager.deleteByIds(ids);
        for (CustomerService bean : beans) {
            log.info("delete CustomerService id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    @RequestMapping({"/customerService/o_priority.do"})
    public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.manager.updatePriority(wids, priority);
        model.addAttribute("message", "global.success");
        return list(pageNo, request, model);
    }

    private WebErrors validateSave(CustomerService bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
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
        CustomerService entity = this.manager.findById(id);

        return errors.ifNotExist(entity, CustomerService.class, id);
    }
}

