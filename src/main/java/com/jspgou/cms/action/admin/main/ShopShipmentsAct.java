package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.ShopShipments;
import com.jspgou.cms.manager.ShopShipmentsMng;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopShipmentsAct {
    private static final Logger log = LoggerFactory.getLogger(ShopShipmentsAct.class);

    @Autowired
    private ShopShipmentsMng manager;

    @RequestMapping({"/shopShipments/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "shopShipments/list";
    }

    @RequestMapping({"/shopShipments/v_add.do"})
    public String add(ModelMap model) {
        return "shopShipments/add";
    }

    @RequestMapping({"/shopShipments/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("shopShipments", this.manager.findById(id));
        return "shopShipments/edit";
    }

    @RequestMapping({"/shopShipments/o_save.do"})
    public String save(ShopShipments bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        bean = this.manager.save(bean);
        log.info("save ShopShipments id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/shopShipments/o_update.do"})
    public String update(ShopShipments bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean);
        log.info("update ShopShipments id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/shopShipments/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopShipments[] beans = this.manager.deleteByIds(ids);
        for (ShopShipments bean : beans) {
            log.info("delete ShopShipments id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    private WebErrors validateSave(ShopShipments bean, HttpServletRequest request) {
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
        ShopShipments entity = this.manager.findById(id);

        return errors.ifNotExist(entity, ShopShipments.class, id);
    }
}

