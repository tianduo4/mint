package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Consult;
import com.mint.cms.manager.ConsultMng;
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
public class ConsultAct {
    private static final Logger log = LoggerFactory.getLogger(ConsultAct.class);
    private String product_name = "";

    @Autowired
    private ConsultMng manager;

    @RequestMapping({"/consult/v_list.do"})
    public String list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String userName = RequestUtils.getQueryParam(request, "userName");
        userName = StringUtils.trim(userName);
        String productName = RequestUtils.getQueryParam(request, "productName");
        productName = StringUtils.trim(productName);
        Pagination pagination = this.manager.getPage(null, userName, productName, startTime, endTime,
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request), Boolean.valueOf(true));

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
        return "consult/list";
    }

    @RequestMapping({"/consult/v_visualize_list.do"})
    public String get_visualize_list(Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String userName = RequestUtils.getQueryParam(request, "userName");
        userName = StringUtils.trim(userName);
        String productName = RequestUtils.getQueryParam(request, "productName");
        productName = StringUtils.trim(productName);

        if (StringUtils.isNotBlank(productName)) {
            this.product_name = productName;
        } else {
            productName = this.product_name;
        }
        Pagination pagination = this.manager.getVisiblePage(userName, productName, startTime, endTime,
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        if (!StringUtils.isBlank(userName)) {
            model.addAttribute("userName", userName);
        }
        if (!StringUtils.isBlank(productName)) {
            model.addAttribute("productName", productName);
        }
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));

        return "consult/visualize_list";
    }

    @RequestMapping({"/consult/v_add.do"})
    public String add(ModelMap model) {
        return "consult/add";
    }

    @RequestMapping({"/consult/v_edit.do"})
    public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("consult", this.manager.findById(id));
        model.addAttribute("pageNo", pageNo);
        return "consult/edit";
    }

    @RequestMapping({"/consult/o_save.do"})
    public String save(Consult bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        log.info("save Consult id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/consult/o_update.do"})
    public String update(Long id, String adminReplay, HttpServletRequest request, ModelMap model, Integer pageNo) {
        Consult bean = this.manager.findById(id);
        bean.setAdminReplay(adminReplay);
        this.manager.update(bean);
        model.addAttribute("pageNo", pageNo);
        log.info("update Consult id={}.", bean.getId());
        return list(null, null, pageNo, request, model);
    }

    @RequestMapping({"/consult/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Consult[] beans = this.manager.deleteByIds(ids);
        for (Consult bean : beans) {
            log.info("delete Consult id={}", bean.getId());
        }
        return list(null, null, Integer.valueOf(SimplePage.cpn(pageNo)), request, model);
    }

    @RequestMapping({"/consult/o_visual_delete.do"})
    public String visual_delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Consult[] beans = this.manager.deleteByIds(ids);
        for (Consult bean : beans) {
            log.info("delete Consult id={}", bean.getId());
        }
        return get_visualize_list(null, null, Integer.valueOf(SimplePage.cpn(pageNo)), request, model);
    }

    @RequestMapping({"/consult/o_visualize_update.do"})
    public String updateVisual(String id, HttpServletRequest request, ModelMap model, String pageNo) {
        Consult bean = this.manager.findById(Long.valueOf(Long.parseLong(id)));
        String adminReplay = request.getParameter("adminReplay" + id);
        if (StringUtils.isNotBlank(adminReplay))
            bean.setAdminReplay(adminReplay);
        else {
            bean.setAdminReplay(null);
        }
        this.manager.update(bean);
        model.addAttribute("pageNo", Integer.valueOf(SimplePage.cpn(Integer.valueOf(Integer.parseInt(pageNo)))));
        log.info("update Consult id={}.", bean.getId());
        return get_visualize_list(null, null, Integer.valueOf(SimplePage.cpn(Integer.valueOf(Integer.parseInt(pageNo)))), request, model);
    }

    private WebErrors validateSave(Consult bean, HttpServletRequest request) {
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
        Consult entity = this.manager.findById(id);

        return errors.ifNotExist(entity, Consult.class, id);
    }
}

