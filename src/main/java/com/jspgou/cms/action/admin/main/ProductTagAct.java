package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.ProductTag;
import com.jspgou.cms.manager.ProductTagMng;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductTagAct {
    private static final Logger log = LoggerFactory.getLogger(ProductTagAct.class);

    @Autowired
    private ProductTagMng manager;

    @RequestMapping({"/tag/v_list.do"})
    public String list(HttpServletRequest request, ModelMap model) {
        List list = this.manager.getList(SiteUtils.getWebId(request));
        model.addAttribute("list", list);
        return "tag/list";
    }

    @RequestMapping({"/tag/o_save.do"})
    public String save(ProductTag bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean);
        log.info("save ProductTag. id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/tag/o_update_tag_names.do"})
    public String updateTagName(Long[] wids, String[] tagNames, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdateTagNames(wids, tagNames, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ProductTag[] beans = this.manager.updateTagName(wids, tagNames);
        for (ProductTag bean : beans) {
            log.info("update ProductTag. id={},name={}", bean.getId(), bean
                    .getName());
        }
        return "redirect:v_list.do";
    }

    @RequestMapping({"/tag/o_delete.do"})
    public String delete(Long[] ids, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ProductTag[] beans = this.manager.deleteByIds(ids);
        for (ProductTag bean : beans) {
            log.info("delete ProductTag. id={},name={}", bean.getId(), bean
                    .getName());
        }
        return list(request, model);
    }

    private WebErrors validateSave(ProductTag bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        bean.setWebsite(SiteUtils.getWeb(request));
        return errors;
    }

    private WebErrors validateUpdateTagNames(Long[] wids, String[] tagNames, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(wids, "wids");
        errors.ifEmpty(tagNames, "tagNames");
        if (errors.hasErrors()) {
            return errors;
        }
        if (wids.length != tagNames.length) {
            errors.addErrorString("wids length must equals tagNames length");
            return errors;
        }
        int i = 0;
        for (int len = wids.length; i < len; i++) {
            vldExist(wids[i], errors);
            if (errors.hasErrors()) {
                return errors;
            }
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private void vldExist(Long id, WebErrors errors) {
        if (errors.hasErrors()) {
            return;
        }
        ProductTag entity = this.manager.findById(id);
        errors.ifNotExist(entity, ProductTag.class, id);
    }
}

