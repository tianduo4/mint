package com.mint.cms.action.admin.main;

import com.mint.cms.manager.WebserviceCallRecordMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebserviceCallRecordAct {

    @Autowired
    private WebserviceCallRecordMng manager;

    @RequestMapping({"/webserviceCallRecord/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "webserviceCallRecord/list";
    }

    @RequestMapping({"/webserviceCallRecord/o_delete.do"})
    public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.manager.deleteByIds(ids);
        return list(pageNo, request, model);
    }
}

