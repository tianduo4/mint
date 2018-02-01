package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.Exended;
import com.jspgou.cms.entity.ExendedItem;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.manager.ExendedItemMng;
import com.jspgou.cms.manager.ExendedMng;
import com.jspgou.cms.manager.ProductTypeMng;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExendedAct {
    private static final Logger log = LoggerFactory.getLogger(ExendedAct.class);

    @Autowired
    private ExendedMng manager;

    @Autowired
    private ProductTypeMng productTypeMng;

    @Autowired
    private ExendedItemMng exendedItemMng;

    @RequestMapping({"/exended/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "exended/list";
    }

    @RequestMapping({"/exended/v_add.do"})
    public String add(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        List ptList = this.productTypeMng.getList(web.getId());
        model.addAttribute("ptList", ptList);
        return "exended/add";
    }

    @RequestMapping({"/exended/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Long[] typeIds = this.manager.findById(id).getProductTypeIds();
        List list = new ArrayList();
        list.addAll(this.manager.findById(id).getItems());
        List ptList = this.productTypeMng.getList(web.getId());
        model.addAttribute("ptList", ptList);
        model.addAttribute("list", list);
        model.addAttribute("typeIds", typeIds);
        model.addAttribute("exended", this.manager.findById(id));
        return "exended/edit";
    }

    @RequestMapping({"/exended/o_save.do"})
    public String save(Exended bean, Long[] typeIds, String[] itemName, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean, typeIds);
        addExendedItems(bean, itemName);
        log.info("save exended id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/exended/o_update.do"})
    public String update(Exended bean, Long[] typeIds, Long[] itemId, String[] itemName, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean, typeIds);
        updateExendedItems(bean, itemId, itemName);
        log.info("update Exended id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/exended/o_priority.do"})
    public String priority(Integer pageNo, Long[] wids, Integer[] priority, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validatePriority(wids, priority, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        this.manager.updatePriority(wids, priority);
        model.addAttribute("message", "global.success");
        return list(pageNo, request, model);
    }

    @RequestMapping({"/exended/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Exended[] beans = this.manager.deleteByIds(ids);
        for (Exended bean : beans) {
            log.info("delete Exended id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    private void addExendedItems(Exended bean, String[] itemName) {
        if (itemName != null) {
            int i = 0;
            for (int len = itemName.length; i < len; i++)
                if (!StringUtils.isBlank(itemName[i])) {
                    ExendedItem item = new ExendedItem();
                    item.setName(itemName[i]);
                    item.setExended(bean);
                    this.exendedItemMng.save(item);
                }
        }
    }

    private void updateExendedItems(Exended bean, Long[] itemId, String[] itemName) {
        Set<ExendedItem> set = bean.getItems();
        if (itemId != null) {
            for (ExendedItem e : set) {
                if (!Arrays.asList(itemId).contains(e.getId()))
                    this.exendedItemMng.deleteById(e.getId());
            }
        } else {
            for (ExendedItem e : set) {
                this.exendedItemMng.deleteById(e.getId());
            }
        }

        if (itemName != null) {
            int i = 0;
            for (int len = itemName.length; i < len; i++)
                if (!StringUtils.isBlank(itemName[i]))
                    if ((itemId != null) && (i < itemId.length)) {
                        ExendedItem item = this.exendedItemMng.findById(itemId[i]);
                        item.setName(itemName[i]);
                        item.setExended(bean);
                        this.exendedItemMng.update(item);
                    } else {
                        ExendedItem item = new ExendedItem();
                        item.setName(itemName[i]);
                        item.setExended(bean);
                        this.exendedItemMng.save(item);
                    }
        }
    }

    public static Long[] fetchProductTypeIds(Collection<ProductType> productTypes) {
        Long[] ids = new Long[productTypes.size()];
        int i = 0;
        for (ProductType sdt : productTypes) {
            ids[(i++)] = sdt.getId();
        }
        return ids;
    }

    private WebErrors validateSave(Exended bean, HttpServletRequest request) {
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
        Exended entity = this.manager.findById(id);

        return errors.ifNotExist(entity, Exended.class, id);
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

