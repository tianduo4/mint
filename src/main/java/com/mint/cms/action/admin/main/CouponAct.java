package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Coupon;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.manager.CouponMng;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CouponAct {

    @Autowired
    private CouponMng manager;

    @Autowired
    private CategoryMng categoryMng;

    @RequestMapping({"/coupon/v_add.do"})
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List parentList = this.categoryMng.getListForParent(SiteUtils.getWebId(request), null);
        model.addAttribute("parentList", parentList);
        return "coupon/add";
    }

    @RequestMapping({"/coupon/o_save.do"})
    public String save(Coupon bean, Integer categoryId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        this.manager.save(bean, site, categoryId);
        return list(request, response, model);
    }

    @RequestMapping({"/coupon/v_list.do"})
    public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List cList = this.manager.getList();
        model.addAttribute("list", cList);
        return "coupon/list";
    }

    @RequestMapping({"/coupon/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.addAttribute("coupon", this.manager.findById(id));
        return "coupon/edit";
    }

    @RequestMapping({"/coupon/o_update.do"})
    public String update(Coupon bean, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        this.manager.update(bean);
        return list(request, response, model);
    }

    @RequestMapping({"/coupon/o_delete.do"})
    public String delete(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        String url = site.getUploadUrl();
        this.manager.deleteByIds(ids, url);
        return list(request, response, model);
    }
}

