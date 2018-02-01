package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.Gift;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.cms.manager.AddressMng;
import com.jspgou.cms.manager.GiftExchangeMng;
import com.jspgou.cms.manager.GiftMng;
import com.jspgou.cms.manager.ShopMemberAddressMng;
import com.jspgou.cms.web.FrontUtils;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;
import com.jspgou.core.web.front.URLHelper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GiftAct {

    @Autowired
    private GiftMng manager;

    @Autowired
    private ShopMemberAddressMng shopMemberAddressMng;

    @Autowired
    private AddressMng addressMng;

    @Autowired
    private GiftExchangeMng giftExchangeMng;

    @RequestMapping(value = {"/gift*.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String list(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        int pageNo = URLHelper.getPageNo(request);
        ShopFrontHelper.setCommonData(request, model, web, pageNo);
        ShopFrontHelper.setDynamicPageData(request, model, web, RequestUtils.getLocation(request), "gift", ".jspx", pageNo);
        return web.getTplSys("gift", MessageResolver.getMessage(request, "tpl.gift", new Object[0]), request);
    }

    @RequestMapping(value = {"/present.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String present(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        if ((id != null) && (this.manager.findById(id) != null))
            model.addAttribute("gift", this.manager.findById(id));
        else {
            return FrontHelper.pageNotFound(web, model, request);
        }
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("gift", MessageResolver.getMessage(request, "tpl.present", new Object[0]), request);
    }

    @RequestMapping({"/fetchGift.jspx"})
    public void fetchGift(Long giftId, Integer giftNumb, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        JSONObject json = new JSONObject();
        if (member == null) {
            json.put("status", 0);
        } else {
            Gift gift = this.manager.findById(giftId);
            if (giftNumb.intValue() > gift.getGiftStock().intValue()) {
                json.put("status", 2);
                json.put("error", "库存不足");
            }
            if (gift.getGiftScore().intValue() * Long.parseLong(giftNumb.toString()) > member.getScore().intValue()) {
                json.put("status", 2);
                json.put("error", "积分不足");
            } else {
                json.put("status", 1);
            }
        }
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/exchange.jspx"})
    public String shippingInput(Long id, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:login.jspx";
        }
        WebErrors errors = validateGiftView(id, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        Gift gift = this.manager.findById(id);
        if (count.intValue() > gift.getGiftStock().intValue()) {
            return FrontUtils.showMessage(request, model, "库存不足");
        }
        if (gift.getGiftScore().intValue() * Long.parseLong(count.toString()) > member.getScore().intValue()) {
            return FrontUtils.showMessage(request, model, "积分不足");
        }
        model.addAttribute("gift", gift);
        model.addAttribute("count", count);
        model.addAttribute("totalScore", Long.valueOf(gift.getGiftScore().intValue() * Long.parseLong(count.toString())));

        List smalist = this.shopMemberAddressMng.getList(member.getId());
        model.addAttribute("smalist", smalist);

        List plist = this.addressMng.getListById(null);
        model.addAttribute("plist", plist);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("gift", MessageResolver.getMessage(request,
                "tpl.exchange", new Object[0]), request);
    }

    @RequestMapping(value = {"/create_exchange.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String createExchange(Long deliveryInfo, Long id, Integer count, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:login.jspx";
        }
        WebErrors errors = validateGiftView(id, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        Gift gift = this.manager.findById(id);
        if (count.intValue() > gift.getGiftStock().intValue()) {
            return FrontUtils.showMessage(request, model, "库存不足");
        }
        if (gift.getGiftScore().intValue() * Long.parseLong(count.toString()) > member.getScore().intValue()) {
            return FrontUtils.showMessage(request, model, "积分不足");
        }
        ShopMemberAddress shopMemberAddress = this.shopMemberAddressMng.findById(deliveryInfo);
        this.giftExchangeMng.save(gift, shopMemberAddress, member, count);
        return FrontUtils.showMessage(request, model, "兑换成功");
    }

    private WebErrors validateGiftView(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(id, "id")) {
            return errors;
        }
        Gift gift = this.manager.findById(id);
        if (errors.ifNotExist(gift, Gift.class, id)) {
            return errors;
        }
        return errors;
    }
}

