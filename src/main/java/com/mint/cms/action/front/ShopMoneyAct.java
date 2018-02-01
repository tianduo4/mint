package com.mint.cms.action.front;

import com.mint.cms.entity.ShopMember;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.front.URLHelper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopMoneyAct {
    public static final String MEMBER_MONEY = "tpl.mymoney";

    @RequestMapping({"/shopMoney/mymoney*.jspx"})
    public String getMyScore(Integer status, Integer useStatus, String startTime, String endTime, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
        model.addAttribute("status", status);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "mymoney", ".jspx", pageNo.intValue());
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.mymoney", new Object[0]), request);
    }
}

