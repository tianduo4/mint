package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Discuss;
import com.mint.cms.manager.DiscussMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.RequestUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DiscussAct {
    private static final Logger log = LoggerFactory.getLogger(DiscussAct.class);
    private String product_name = "";

    @Autowired
    private DiscussMng manager;

    @RequestMapping({"/discuss/v_list.do"})
    public String list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String userName = RequestUtils.getQueryParam(request, "userName");
        userName = StringUtils.trim(userName);
        String productName = RequestUtils.getQueryParam(request, "productName");
        productName = StringUtils.trim(productName);
        Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime,
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), true);
        if (!StringUtils.isBlank(userName)) {
            model.addAttribute("userName", userName);
        }
        if (!StringUtils.isBlank(productName)) {
            model.addAttribute("productName", productName);
        }

        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pageNo);
        return "discuss/list";
    }

    @RequestMapping({"/discuss/v_add.do"})
    public String add(ModelMap model) {
        return "discuss/add";
    }

    @RequestMapping({"/discuss/v_edit.do"})
    public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("discuss", this.manager.findById(id));
        model.addAttribute("pageNo", pageNo);
        return "discuss/edit";
    }

    @RequestMapping({"/discuss/o_save.do"})
    public String save(Discuss bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        log.info("save Discuss id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/discuss/o_update.do"})
    public String update(Discuss bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean);
        log.info("update Discuss id={}.", bean.getId());
        return list(null, null, pageNo, request, model);
    }

    @RequestMapping({"/discuss/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Discuss[] beans = this.manager.deleteByIds(ids);
        for (Discuss bean : beans) {
            log.info("delete Discuss id={}", bean.getId());
        }
        return list(null, null, pageNo, request, model);
    }

    @RequestMapping({"/discuss/o_visual_delete.do"})
    public String visual_delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Discuss[] beans = this.manager.deleteByIds(ids);
        for (Discuss bean : beans) {
            log.info("delete Discuss id={}", bean.getId());
        }
        return get_visual_list(null, null, pageNo, request, model);
    }

    @RequestMapping({"/discuss/v_visual_list.do"})
    public String get_visual_list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String userName = RequestUtils.getQueryParam(request, "userName");
        userName = StringUtils.trim(userName);
        String productName = RequestUtils.getQueryParam(request, "productName");
        productName = StringUtils.trim(productName);

        if (StringUtils.isNotBlank(productName)) {
            this.product_name = productName;
        } else {
            productName = this.product_name;
        }
        Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime,
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), true);
        if (!StringUtils.isBlank(userName)) {
            model.addAttribute("userName", userName);
        }
        if (!StringUtils.isBlank(productName)) {
            model.addAttribute("productName", productName);
        }
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pageNo);
        return "discuss/visual_list";
    }

    private WebErrors validateSave(Discuss bean, HttpServletRequest request) {
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
        Discuss entity = this.manager.findById(id);

        return errors.ifNotExist(entity, Discuss.class, id);
    }
}

