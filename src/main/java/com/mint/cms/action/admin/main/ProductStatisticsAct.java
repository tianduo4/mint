package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Product;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.manager.ProductMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.core.entity.Global;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductStatisticsAct {

    @Autowired
    private ProductMng productMng;

    @Autowired
    private CategoryMng categoryMng;

    @RequestMapping({"/productStatistics/v_productLack.do"})
    public String productLack(Integer count, Boolean status, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        Global global = web.getGlobal();
        if (count == null) {
            count = global.getStockWarning();
        }
        Pagination pagination = this.productMng.getPageByStockWarning(web.getId(), count, status, SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("count", count);
        model.addAttribute("status", status);
        return "sale/productLack";
    }

    @RequestMapping({"/productStatistics/o_updateRemind.do"})
    public String updateRemind(Boolean status, Integer count, Integer pageNo, Long productId, HttpServletRequest request, ModelMap model) {
        Product product = this.productMng.findById(productId);
        product.setLackRemind(status);
        this.productMng.updateByUpdater(product);
        if (status.booleanValue())
            status = Boolean.valueOf(false);
        else {
            status = Boolean.valueOf(true);
        }
        model.addAttribute("status", status);
        model.addAttribute("count", count);
        return "redirect:v_productLack.do";
    }

    @RequestMapping({"/productStatistics/o_resetSaleTop.do"})
    public String resetSaleTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.productMng.resetSaleTop();
        return productSaleTop(typeId, pageNo, request, model);
    }

    @RequestMapping({"/productStatistics/v_productSaleTop.do"})
    public String productSaleTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        List list = this.productMng.findAll();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (((Product) list.get(j)).getCategory().getName().equals(((Product) list.get(i)).getCategory().getName())) {
                    list.remove(j);
                }
            }
        }
        Pagination pagination = this.productMng.getPage1(typeId, 4, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("list", list);
        model.addAttribute("typeId", typeId);
        return "sale/productSaleTop";
    }

    @RequestMapping({"/productStatistics/o_resetProfitTop.do"})
    public String resetProfitTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.productMng.resetProfitTop();
        return productProfitTop(typeId, pageNo, request, model);
    }

    @RequestMapping({"/productStatistics/v_productProfitTop.do"})
    public String productProfitTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);

        List list = this.productMng.findAll();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (((Product) list.get(j)).getCategory().getName().equals(((Product) list.get(i)).getCategory().getName())) {
                    list.remove(j);
                }
            }
        }
        Pagination pagination = this.productMng.getPage1(typeId, 8, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("list", list);
        model.addAttribute("typeId", typeId);
        return "sale/productProfitTop";
    }
}

