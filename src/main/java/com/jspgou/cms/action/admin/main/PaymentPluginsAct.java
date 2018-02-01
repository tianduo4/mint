package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.entity.PaymentPlugins;
import com.jspgou.cms.manager.PaymentPluginsMng;
import com.jspgou.core.web.WebErrors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaymentPluginsAct {
    private static final Logger log = LoggerFactory.getLogger(PaymentPluginsAct.class);

    @Autowired
    private PaymentPluginsMng manager;

    @RequestMapping({"/plugins/v_list.do"})
    public String list(HttpServletRequest request, ModelMap model) {
        List<PaymentPlugins> list = this.manager.getList();
        Set codeSet = new HashSet();
        for (PaymentPlugins p : list) {
            codeSet.add(p.getCode());
        }
        model.addAttribute("list", list);
        model.addAttribute("codeSet", codeSet);
        return "plugins/list";
    }

    @RequestMapping({"/plugins/v_add.do"})
    public String add(String code, HttpServletRequest request, ModelMap model) {
        model.addAttribute("code", code);
        return "plugins/add_" + code;
    }

    @RequestMapping({"/plugins/v_edit.do"})
    public String edit(Long id, String code, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        PaymentPlugins paymentPlugins = this.manager.findById(id);
        model.addAttribute("paymentPlugins", paymentPlugins);
        return "plugins/edit_" + code;
    }

    @RequestMapping({"/plugins/o_save.do"})
    public String save(PaymentPlugins bean, String platform, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean.setPlatform(platform);
        bean = this.manager.save(bean);
        log.info("save Payment, id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/plugins/o_update.do"})
    public String update(PaymentPlugins bean, String platform, long[] shippingIds, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean.setPlatform(platform);
        bean = this.manager.update(bean);
        log.info("update Payment, id={}.", bean.getId());
        return list(request, model);
    }

    @RequestMapping({"/plugins/o_delete.do"})
    public String delete(Long[] ids, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        PaymentPlugins[] beans = this.manager.deleteByIds(ids);
        for (PaymentPlugins bean : beans) {
            log.info("delete Payment, id={}", bean.getId());
        }
        return list(request, model);
    }

    @RequestMapping({"/plugins/o_priority.do"})
    public String priority(Long[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.manager.updatePriority(wids, priority);
        model.addAttribute("message", "global.success");
        return list(request, model);
    }

    private WebErrors validateSave(PaymentPlugins bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
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

    private boolean vldExist(Long id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        PaymentPlugins entity = this.manager.findById(id);

        return errors.ifNotExist(entity, Payment.class, id);
    }
}

