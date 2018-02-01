package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ConsultMng;
import com.jspgou.cms.manager.DiscussMng;
import com.jspgou.cms.manager.OrderItemMng;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.security.annotation.Secured;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;

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

