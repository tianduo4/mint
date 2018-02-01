package com.mint.cms.action.front;

import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ConsultMng;
import com.mint.cms.manager.DiscussMng;
import com.mint.cms.manager.OrderItemMng;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.security.annotation.Secured;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured
@Controller
public class MyInfomationAct {

    @Autowired
    private OrderItemMng orderItemMng;

    @Autowired
    private ConsultMng consultMng;

    @Autowired
    private DiscussMng discussMng;

    @RequestMapping({"/my_discuss*.jspx"})
    public String getMyDiscuss(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        Pagination pagination = this.discussMng.getPageByMember(member.getId(), SimplePage.cpn(pageNo), 10, true);
        model.addAttribute("pagination", pagination);
        model.addAttribute("member", member);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "my_discuss", ".jspx", SimplePage.cpn(pageNo));
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.mydiscuss", new Object[0]), request);
    }

    @RequestMapping({"/my_cousult*.jspx"})
    public String getMyCousult(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        Pagination pagination = this.consultMng.getPageByMember(member.getId(), SimplePage.cpn(pageNo), 1, true);
        model.addAttribute("pagination", pagination);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "my_cousult", ".jspx", SimplePage.cpn(pageNo));
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myconsult", new Object[0]), request);
    }

    @RequestMapping({"/buyRecord*.jspx"})
    public String getBuyRecord(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        Pagination pagination = this.orderItemMng.getPageByMember(Integer.valueOf(4), member.getId(), SimplePage.cpn(pageNo), 2);
        model.addAttribute("pagination", pagination);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "buyRecord", ".jspx", SimplePage.cpn(pageNo));
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.buyRecord", new Object[0]), request);
    }

    public String getHistoryProductIds(HttpServletRequest request) {
        String str = null;
        Cookie[] cookie = request.getCookies();
        int num = cookie.length;
        for (int i = 0; i < num; i++) {
            if (cookie[i].getName().equals("shop_record")) {
                str = cookie[i].getValue();
                break;
            }
        }
        return str;
    }
}

