package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Webservice;
import com.mint.cms.manager.WebserviceMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebserviceController {
    private static final Logger log = LoggerFactory.getLogger(WebserviceController.class);

    @Autowired
    private WebserviceMng manager;

    @RequestMapping({"/webservice/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "webservice/list";
    }

    @RequestMapping({"/webservice/v_add.do"})
    public String add(ModelMap model) {
        return "webservice/add";
    }

    @RequestMapping({"/webservice/v_edit.do"})
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        model.addAttribute("Webservice", this.manager.findById(id));
        return "webservice/edit";
    }

    @RequestMapping({"/webservice/o_save.do"})
    public String save(Webservice bean, String[] paramName, String[] defaultValue, HttpServletRequest request, ModelMap model) {
        bean = this.manager.save(bean, paramName, defaultValue);
        log.info("save Webservice id={}", bean);
        return "redirect:v_list.do";
    }

    @RequestMapping({"/webservice/o_update.do"})
    public String update(Webservice bean, String[] paramName, String[] defaultValue, Integer pageNo, HttpServletRequest request, ModelMap model) {
        bean = this.manager.update(bean, paramName, defaultValue);
        log.info("update Webservice id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/webservice/o_delete.do"})
    public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.manager.deleteByIds(ids);
        return list(pageNo, request, model);
    }
}

