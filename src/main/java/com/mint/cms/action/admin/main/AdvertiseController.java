package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Advertise;
import com.mint.cms.manager.AdspaceMng;
import com.mint.cms.manager.AdvertiseMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.web.RequestUtils1;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.core.web.WebErrors;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class AdvertiseController {
    private static final Logger log = LoggerFactory.getLogger(AdvertiseController.class);

    @Autowired
    private ProductMng productMng;

    @Autowired
    private AdspaceMng adspaceMng;

    @Autowired
    private AdvertiseMng manager;

    @RequestMapping({"/advertise/v_list.do"})
    public String list(Integer queryAdspaceId, Boolean queryEnabled, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(queryAdspaceId,
                queryEnabled, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));
        if (queryAdspaceId != null) {
            model.addAttribute("queryAdspaceId", queryAdspaceId);
        }
        if (queryEnabled != null) {
            model.addAttribute("queryEnabled", queryEnabled);
        }
        return "advertise/list";
    }

    @RequestMapping({"/advertise/v_add.do"})
    public String add(HttpServletRequest request, ModelMap model) {
        List adspaceList = this.adspaceMng.getList();
        model.addAttribute("adspaceList", adspaceList);
        return "advertise/add";
    }

    @RequestMapping({"/advertise/v_edit.do"})
    public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Advertise advertise = this.manager.findById(id);
        model.addAttribute("advertise", advertise);
        model.addAttribute("attr", advertise.getAttr());
        model.addAttribute("adspaceList", this.adspaceMng.getList());
        model.addAttribute("pageNo", pageNo);
        return "advertise/edit";
    }

    @RequestMapping({"/advertise/o_save.do"})
    public String save(Advertise bean, Integer adspaceId, HttpServletRequest request, ModelMap model) {
        Map<String, String> attr = RequestUtils1.getRequestMap(request, "attr_");

        Set<String> toRemove = new HashSet();
        for (Map.Entry entry : attr.entrySet()) {
            if (StringUtils.isBlank((String) entry.getValue())) {
                toRemove.add((String) entry.getKey());
            }
        }
        for (String key : toRemove) {
            attr.remove(key);
        }
        bean = this.manager.save(bean, adspaceId, attr);
        log.info("save advertise id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/advertise/o_update.do"})
    public String update(Integer queryAdspaceId, Boolean queryEnabled, Advertise bean, Integer adspaceId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Map<String, String> attr = RequestUtils1.getRequestMap(request, "attr_");

        Set<String> toRemove = new HashSet();
        for (Map.Entry entry : attr.entrySet()) {
            if (StringUtils.isBlank((String) entry.getValue())) {
                toRemove.add((String) entry.getKey());
            }
        }
        for (String key : toRemove) {
            attr.remove(key);
        }
        bean = this.manager.update(bean, adspaceId, attr);
        log.info("update advertise id={}.", bean.getId());
        return list(queryAdspaceId, queryEnabled, pageNo, request, model);
    }

    @RequestMapping({"/advertise/o_delete.do"})
    public String delete(Integer[] ids, Integer queryAdspaceId, Boolean queryEnabled, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        try {
            Advertise[] beans = this.manager.deleteByIds(ids);
            for (Advertise bean : beans)
                log.info("delete advertise. id={}", bean.getId());
        } catch (Exception e) {
            errors.addErrorString(this.productMng.getTipFile("Please.delete.the.data.to.change.the.advertising.management"));
            return errors.showErrorPage(model);
        }
        Advertise[] beans;
        return list(queryAdspaceId, queryEnabled, pageNo, request, model);
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids");
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private void vldExist(Integer id, WebErrors errors) {
        if (errors.hasErrors()) {
            return;
        }
        Advertise entity = this.manager.findById(id);
        errors.ifNotExist(entity, Advertise.class, id);
    }
}

