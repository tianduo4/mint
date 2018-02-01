package com.mint.cms.action.front;

import com.mint.cms.entity.Collect;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.CollectMng;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.front.URLHelper;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CollectAct {
    private static final String MY_COLLECT = "tpl.mycollect";

    @Autowired
    private CollectMng manager;

    @RequestMapping({"/collect/add_to_collect.jspx"})
    public String addToCollect(Long productId, Long productFashId, Boolean isAdd, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JSONException {
        ShopMember member = MemberThread.get();
        if (member == null) {
            ResponseUtils.renderJson(response, new JSONObject().put("status", 0).toString());
            return null;
        }
        Collect collect = null;
        if (productFashId == null) {
            List clist = this.manager.findByProductId(productId);
            if (clist.size() > 0) {
                ResponseUtils.renderJson(response, new JSONObject().put("status", 2).toString());
                return null;
            }
            collect = this.manager.save(member, productId, null);
        } else {
            collect = this.manager.findByProductFashionId(productFashId);
            if (collect != null) {
                ResponseUtils.renderJson(response, new JSONObject().put("status", 2).toString());
                return null;
            }
            collect = this.manager.save(member, productId, productFashId);
        }
        ResponseUtils.renderJson(response, new JSONObject().put("status", 1).toString());
        return null;
    }

    @RequestMapping({"/collect/mycollect*.jspx"})
    public String myCollect(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
        model.addAttribute("historyProductIds", "");
        Cookie[] cookie = request.getCookies();
        for (int i = 0; i < cookie.length; i++) {
            if (cookie[i].getName().equals("shop_record")) {
                String str = cookie[i].getValue();
                model.addAttribute("historyProductIds", str);
                break;
            }
        }
        ShopFrontHelper.setCommonData(request, model, web, 1);
        ShopFrontHelper.setDynamicPageData(request, model, web, "", "mycollect", ".jspx", pageNo.intValue());
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.mycollect", new Object[0]), request);
    }

    @RequestMapping({"/collect/delCollect.jspx"})
    public String delCollect(Integer[] collectIds, HttpServletResponse response, ModelMap model) {
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "";
        }
        this.manager.deleteByIds(collectIds);
        ResponseUtils.renderJson(response, "删除成功!");
        return null;
    }
}

