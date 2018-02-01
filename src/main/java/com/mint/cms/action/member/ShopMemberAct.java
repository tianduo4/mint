package com.mint.cms.action.member;

import com.mint.cms.dao.OrderDao;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.manager.UserMng;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;
import com.mint.core.web.front.FrontHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopMemberAct {
    private static final Logger log = LoggerFactory.getLogger(ShopMemberAct.class);
    public static final String MEMBER_CENTER = "tpl.memberCenter";
    public static final String MEMBER_PORTRAIT = "tpl.memberPortrait";
    public static final String MEMBER_PROFILE = "tpl.memberProfile";
    public static final String MEMBER_PASSWORD = "tpl.memberPassword";

    @Autowired
    private OrderDao dao;

    @Autowired
    private UserMng userMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ShopMemberMng manager;

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @RequestMapping(value = {"/shopMember/index.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        BigDecimal money = this.dao.getMemberMoneyByYear(member.getId());
        Integer[] orders = this.dao.getOrderByMember(member.getId());
        Integer[] products = this.productMng.getProductByTag(web.getId());
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        model.addAttribute("money", money);
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request,
                "tpl.memberCenter", new Object[0]), request);
    }

    @RequestMapping(value = {"/shopMember/profile.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }

        List userDegreeList = this.shopDictionaryMng.getListByType(Long.valueOf(1L));

        List familyMembersList = this.shopDictionaryMng.getListByType(Long.valueOf(2L));

        List incomeDescList = this.shopDictionaryMng.getListByType(Long.valueOf(3L));

        List workSeniorityList = this.shopDictionaryMng.getListByType(Long.valueOf(4L));

        List degreeList = this.shopDictionaryMng.getListByType(Long.valueOf(5L));
        model.addAttribute("member", member);
        model.addAttribute("userDegreeList", userDegreeList);
        model.addAttribute("familyMembersList", familyMembersList);
        model.addAttribute("incomeDescList", incomeDescList);
        model.addAttribute("workSeniorityList", workSeniorityList);
        model.addAttribute("degreeList", degreeList);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberProfile", new Object[0]), request);
    }

    @RequestMapping(value = {"/shopMember/profile.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String profileSubmit(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        bean = this.manager.update(bean, groupId, userDegreeId, degreeId, incomeDescId, workSeniorityId, familyMembersId);
        log.info("ShopMember update infomation: {}", bean.getUsername());
        return index(request, response, model);
    }

    @RequestMapping(value = {"/shopMember/pwd.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String passwordInput(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        model.addAttribute("username", member.getUsername());
        ShopFrontHelper.setCommonData(request, model, web, 1);
        model.addAttribute("email", MemberThread.get().getEmail());
        model.addAttribute("historyProductIds", getHistoryProductIds(request));
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberPassword", new Object[0]), request);
    }

    @RequestMapping(value = {"/shopMember/portrait.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String portrait(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberPortrait", new Object[0]), request);
    }

    @RequestMapping(value = {"/shopMember/updateAvatar.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String updateAvatar(String picPaths, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        member.setAvatar(picPaths);
        this.manager.update(member);

        return "redirect: profile.jspx";
    }

    @RequestMapping(value = {"/shopMember/pwd.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String passwordSubmit(String origPwd, String newPwd, String email, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        if (member == null) {
            return "redirect:../login.jspx";
        }
        Long userId = member.getMember().getUser().getId();
        WebErrors errors = validatePassword(userId, email, newPwd, member.getEmail(), origPwd, request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        this.userMng.updateUser(userId, newPwd, email);
        log.info("ShopMember update password: {}", member.getUsername());
        return FrontHelper.showSuccess("global.success", nextUrl, web, model, request);
    }

    @RequestMapping({"/shopMember/checkPwd.jspx"})
    public void checkPwd(String origPwd, HttpServletRequest request, HttpServletResponse response) {
        ShopMember member = MemberThread.get();
        Long userId = member.getMember().getUser().getId();
        boolean pass = this.userMng.isPasswordValid(userId, origPwd);
        ResponseUtils.renderJson(response, pass ? "true" : "false");
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

    private WebErrors validatePassword(Long userId, String email, String newPwd, String origEmail, String origPwd, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if ((!StringUtils.isBlank(newPwd)) &&
                (errors.ifOutOfLength(newPwd, "password", 3, 32))) {
            return errors;
        }
        if (!this.userMng.isPasswordValid(userId, origPwd)) {
            errors.addErrorCode("error.passwordInvalid");
        }
        if (errors.ifNotEmail(email, "email", 100)) {
            return errors;
        }
        if ((!email.equals(origEmail)) && (this.userMng.emailExist(email))) {
            errors.addErrorCode("error.emailExist");
            return errors;
        }
        return errors;
    }
}

