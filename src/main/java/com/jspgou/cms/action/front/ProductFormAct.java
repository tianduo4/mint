package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.Consult;
import com.jspgou.cms.entity.Discuss;
import com.jspgou.cms.entity.OrderItem;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ConsultMng;
import com.jspgou.cms.manager.DiscussMng;
import com.jspgou.cms.manager.OrderItemMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.front.FrontHelper;
import com.jspgou.core.web.front.URLHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductFormAct {

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ConsultMng consultMng;

    @Autowired
    private OrderItemMng orderItemMng;

    @Autowired
    private DiscussMng discussMng;

    @RequestMapping({"/searchDiscussPage*.jspx"})
    public String searchDiscussPage(Long productId, Integer pageNo, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }
        Product bean = this.productMng.findById(productId);
        model.addAttribute("product", bean);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "searchDiscussPage", ".jspx", SimplePage.cpn(pageNo));
        return web.getTemplate("shop", MessageResolver.getMessage(request, "tpl.discussContentPage", new Object[0]), request);
    }

    @RequestMapping({"/pingjia*.jspx"})
    public String pingjia(Long productId, Long orderId, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }
        Product bean = this.productMng.findById(productId);
        OrderItem orderItem = this.orderItemMng.findByMember(member.getId(), productId, orderId);
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("product", bean);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "discussContentPage", ".jspx", SimplePage.cpn(pageNo));
        return web.getTemplate("shop", MessageResolver.getMessage(request, "tpl.assess", new Object[0]), request);
    }

    @RequestMapping({"/haveDiscuss*.jspx"})
    public String haveDiscuss(Long productId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            ResponseUtils.renderJson(response, "denru");
            return null;
        }
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }
        OrderItem bean = this.orderItemMng.findByMember(member.getId(), productId, null);
        if (bean != null) {
            ResponseUtils.renderJson(response, "success");
            return null;
        }
        ResponseUtils.renderJson(response, "false");
        return null;
    }

    @RequestMapping({"/consultProduct*.jspx"})
    public String consultProduct(Long productId, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }

        int pageNo = URLHelper.getPageNo(request);
        ShopFrontHelper.setCommonData(request, model, web, SimplePage.cpn(Integer.valueOf(pageNo)));
        Product bean = this.productMng.findById(productId);
        Pagination page = this.consultMng.getPage(productId, null, null, null, null, SimplePage.cpn(Integer.valueOf(pageNo)), 5, Boolean.valueOf(true));
        model.addAttribute("product", bean);
        model.addAttribute("pagination", page);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "consultProduct", ".jspx", SimplePage.cpn(Integer.valueOf(pageNo)));
        return web.getTemplate("shop", MessageResolver.getMessage(request, "tpl.consultProduct", new Object[0]), request);
    }

    @RequestMapping({"/bargain*.jspx"})
    public String list(Long productId, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }
        int pageNo = URLHelper.getPageNo(request);
        Product bean = this.productMng.findById(productId);
        Pagination page = this.orderItemMng.getOrderItem(Integer.valueOf(SimplePage.cpn(Integer.valueOf(pageNo))), Integer.valueOf(4), productId);
        model.addAttribute("pagination", page);
        model.addAttribute("product", bean);
        ShopFrontHelper.setCommonData(request, model, web, pageNo);
        ShopFrontHelper.setDynamicPageData(request, model, web, RequestUtils.getLocation(request), "bargain", ".jspx", pageNo);
        return web.getTplSys("shop", MessageResolver.getMessage(request, "tpl.bargain", new Object[0]), request);
    }

    @RequestMapping({"/insertConsult.jspx"})
    public void insertConsult(Long productId, String content, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember user = MemberThread.get();
        if (user != null) {
            if (StringUtils.isEmpty(content)) {
                ResponseUtils.renderJson(response, "empty");
            } else {
                Consult bean = this.consultMng.saveOrUpdate(productId, content, user.getId());
                if (bean == null)
                    ResponseUtils.renderJson(response, "sameUsually");
                else
                    ResponseUtils.renderJson(response, "success");
            }
        } else
            ResponseUtils.renderJson(response, "false");
    }

    @RequestMapping({"showProblem.jspx"})
    public void showProblem(HttpServletResponse response, Long productId, HttpServletRequest request, ModelMap model) {
        ShopMember user = MemberThread.get();
        if (user != null)
            ResponseUtils.renderJson(response, "success");
        else
            ResponseUtils.renderJson(response, "false");
    }

    @RequestMapping({"/insertDiscuss.jspx "})
    public String insertDiscuss(Long productId, String disCon, String discussType, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            ResponseUtils.renderJson(response, "false");
            return null;
        }
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }
        Discuss bean = this.discussMng.saveOrUpdate(productId, disCon, member.getId(), discussType);
        if (bean == null) {
            ResponseUtils.renderJson(response, "sameUsually");
            return null;
        }
        ResponseUtils.renderJson(response, "success");
        return null;
    }

    @RequestMapping({"/historyRecord.jspx"})
    public String historyRecord(Long productId, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            ResponseUtils.renderJson(response, "false");
            return null;
        }
        if ((productId == null) || (this.productMng.findById(productId) == null)) {
            return FrontHelper.pageNotFound(web, model, request);
        }
        String str = "";
        Cookie[] cookeis = request.getCookies();
        int num = cookeis.length;
        for (int i = 0; i < num; i++) {
            if (cookeis[i].getName().equals("shop_record")) {
                str = ',' + cookeis[i].getValue();
                break;
            }
        }
        str = productId + str;
        int n = 0;
        int m = 0;
        int j = str.length();
        for (int i = 0; i < j; i++) {
            if (str.charAt(i) == ',') {
                n++;
            }
            if (n == 6) {
                break;
            }
            m = i + 1;
        }
        Cookie cook = new Cookie("shop_record", str.substring(0, m));
        String path = request.getContextPath();
        cook.setPath(StringUtils.isBlank(path) ? "/" : path);
        response.addCookie(cook);
        return null;
    }
}

