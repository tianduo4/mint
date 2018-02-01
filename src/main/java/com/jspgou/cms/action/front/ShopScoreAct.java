package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.GiftExchange;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.GiftExchangeMng;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;
import com.jspgou.core.web.front.URLHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopScoreAct {
    public static final String MEMBER_SCORE = "tpl.myscore";

    @Autowired
    private GiftExchangeMng giftExchangeMng;

    @RequestMapping({"/shopScore/myscore*.jspx"})
    public String getMyScore(Integer status, Integer useStatus, String startTime, String endTime, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        model.addAttribute("status", status);
        model.addAttribute("useStatus", useStatus);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("historyProductIds", "");
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "myscore", ".jspx", pageNo.intValue());
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myscore", new Object[0]), request);
    }

    @RequestMapping({"/shopScore/exchange.jspx"})
    public String exchange(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        model.addAttribute("list", this.giftExchangeMng.getlist(member.getId()));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.exchange", new Object[0]), request);
    }

    @RequestMapping({"/shopScore/exchange_accomplish.jspx"})
    public String exchangeaccomplish(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateGiftExchangeView(id, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        GiftExchange giftExchange = this.giftExchangeMng.findById(id);
        if ((giftExchange.getMember().equals(member)) && (giftExchange.getStatus().intValue() == 2)) {
            giftExchange.setStatus(Integer.valueOf(3));
            this.giftExchangeMng.update(giftExchange);
        }
        return exchange(request, model);
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

    private WebErrors validateGiftExchangeView(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(id, "id")) {
            return errors;
        }
        GiftExchange giftExchange = this.giftExchangeMng.findById(id);
        if (errors.ifNotExist(giftExchange, GiftExchange.class, id)) {
            return errors;
        }
        return errors;
    }
}

