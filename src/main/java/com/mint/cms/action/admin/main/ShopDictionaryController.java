package com.mint.cms.action.admin.main;

import com.mint.cms.entity.ShopDictionary;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.cms.manager.ShopDictionaryTypeMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.RequestUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopDictionaryController {
    private static final Logger log = LoggerFactory.getLogger(ShopDictionaryController.class);

    @Autowired
    private ShopDictionaryMng manager;

    @Autowired
    private ShopDictionaryTypeMng shopDictionaryTypeMng;

    @RequestMapping({"/shopDictionary/v_list.do"})
    public String list(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String name = RequestUtils.getQueryParam(request, "name");
        List typelist = this.shopDictionaryTypeMng.findAll();
        Pagination pagination = this.manager.getPage(name, typeId, SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("name", name);
        model.addAttribute("typeId", typeId);
        model.addAttribute("typelist", typelist);
        model.addAttribute("pagination", pagination);
        return "shopDictionary/list";
    }

    @RequestMapping({"/shopDictionary/v_add.do"})
    public String add(ModelMap model) {
        List sdtList = this.shopDictionaryTypeMng.findAll();
        model.addAttribute("sdtList", sdtList);
        return "shopDictionary/add";
    }

    @RequestMapping({"/shopDictionary/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        List sdtList = this.shopDictionaryTypeMng.findAll();
        model.addAttribute("sdtList", sdtList);
        model.addAttribute("shopDictionary", this.manager.findById(id));
        return "shopDictionary/edit";
    }

    @RequestMapping({"/shopDictionary/o_save.do"})
    public String save(ShopDictionary bean, Long shopDictionaryTypeId, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean.setShopDictionaryType(this.shopDictionaryTypeMng.findById(shopDictionaryTypeId));
        bean = this.manager.save(bean);
        log.info("save ShopDictionary id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/shopDictionary/o_update.do"})
    public String update(ShopDictionary bean, Long shopDictionaryTypeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean.setShopDictionaryType(this.shopDictionaryTypeMng.findById(shopDictionaryTypeId));
        bean = this.manager.update(bean);
        log.info("update ShopDictionary id={}.", bean.getId());
        return list(null, pageNo, request, model);
    }

    @RequestMapping({"/shopDictionary/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopDictionary[] beans = this.manager.deleteByIds(ids);
        for (ShopDictionary bean : beans) {
            log.info("delete ShopDictionary id={}", bean.getId());
        }
        return list(null, pageNo, request, model);
    }

    @RequestMapping({"/shopDictionary/o_priority.do"})
    public String priority(Integer pageNo, Long[] wids, Integer[] priority, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validatePriority(wids, priority, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        this.manager.updatePriority(wids, priority);
        model.addAttribute("message", "global.success");
        return list(null, pageNo, request, model);
    }

    private WebErrors validateSave(ShopDictionary bean, HttpServletRequest request) {
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
        ShopDictionary entity = this.manager.findById(id);

        return errors.ifNotExist(entity, ShopDictionary.class, id);
    }

    private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(wids, "ids")) {
            return errors;
        }
        if (errors.ifEmpty(priority, "priority")) {
            return errors;
        }
        if (wids.length != priority.length) {
            errors.addErrorString("ids length not equals priority length");
            return errors;
        }
        int i = 0;
        for (int len = wids.length; i < len; i++) {
            if (vldExist(wids[i], web.getId(), errors)) {
                return errors;
            }
            if (priority[i] == null) {
                priority[i] = Integer.valueOf(0);
            }
        }
        return errors;
    }
}

