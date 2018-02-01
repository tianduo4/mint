package com.mint.cms.action.front;

import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.CouponMng;
import com.mint.cms.manager.MemberCouponMng;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberCouponAct {

    @Autowired
    private MemberCouponMng manage;
    private CouponMng couponMng;

    @RequestMapping(value = {"/myCoupon.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String pay1(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:login.jspx";
        }
        List list = this.manage.getList(member.getId());
        model.addAttribute("couList", list);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myCoupon", new Object[0]), request);
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

