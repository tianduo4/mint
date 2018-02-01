package com.mint.cms.action.member;

import com.mint.cms.web.ShopFrontHelper;
import com.mint.common.web.session.SessionProvider;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.EmailSender;
import com.mint.core.entity.MessageTemplate;
import com.mint.core.entity.User;
import com.mint.core.entity.Website;
import com.mint.core.manager.UserMng;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;
import com.mint.core.web.front.FrontHelper;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForgotPasswordAct {
    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordAct.class);
    private static final String FORGOTTEN_INPUT = "tpl.forgottenInput";
    private static final String FORGOTTEN_RESULT = "tpl.forgottenResult";
    private static final String RESET_PASSWORD_TPL = "tpl.resetPassword";

    @Autowired
    private UserMng userMng;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private SessionProvider session;

    @RequestMapping(value = {"/forgot_password.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String fogottenInput(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request,
                "tpl.forgottenInput", new Object[0]), request);
    }

    @RequestMapping(value = {"/forgot_password.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String fogottenSubmit(String checkcode, String username, String email, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateFogotten(checkcode, username, email,
                request, response);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }

        User user = this.userMng.getByUsername(username);
        MessageTemplate tpl = (MessageTemplate) web.getMessages().get(
                "resetPassword");
        EmailSender sender = web.getEmailSender();
        if (user == null) {
            model.addAttribute("status", Integer.valueOf(1));
        } else if (!user.getEmail().equalsIgnoreCase(email)) {
            model.addAttribute("status", Integer.valueOf(2));
        } else if (!user.getEmail().equals(email)) {
            model.addAttribute("status", Integer.valueOf(3));
        } else if (sender == null) {
            model.addAttribute("status", Integer.valueOf(4));
        } else if (tpl == null) {
            model.addAttribute("status", Integer.valueOf(5));
        } else {
            try {
                String base = new String(web.getUrlBuff(true));
                user = this.userMng.passwordForgotten(user.getId(), base, sender, tpl);

                String emailtype = email.substring(email.indexOf("@") + 1, email.indexOf("."));
                model.addAttribute("emailtype", emailtype);
                model.addAttribute("status", Integer.valueOf(0));
                model.addAttribute("user", user);
            } catch (Exception e) {
                model.addAttribute("status", Integer.valueOf(100));
                model.addAttribute("message", e.getMessage());
                log.error("send email exception.", e);
            }
        }
        model.addAttribute("user", user);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        log.info("find passsword, username={} email={}", username, email);
        return web.getTplSys("member", MessageResolver.getMessage(request,
                "tpl.forgottenResult", new Object[0]), request);
    }

    @RequestMapping({"/reset_password.jspx"})
    public String resetPwd(Long uid, String activationCode, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateReset(uid, activationCode, request);
        if (errors.hasErrors()) {
            return FrontHelper.showMessage((String) errors.getErrors().get(0), web,
                    model, request);
        }
        User user = this.userMng.findById(uid);
        boolean success;
        if (activationCode.equals(user.getResetKey())) {
            user = this.userMng.resetPassword(user.getId());
            success = true;
        } else {
            success = false;
        }
        model.addAttribute("user", user);
        model.addAttribute("success", Boolean.valueOf(success));
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request,
                "tpl.resetPassword", new Object[0]), request);
    }

    private WebErrors validateFogotten(String checkcode, String username, String email, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        String id = this.session.getSessionId(request, response);
        if (errors.ifOutOfLength(checkcode, "checkcode", 3, 10)) {
            return errors;
        }
        try {
            if (!this.captchaService.validateResponseForID(id, checkcode
                    .toUpperCase(Locale.ENGLISH)).booleanValue()) {
                errors.addErrorCode("error.checkcodeIncorrect");
                return errors;
            }
        } catch (CaptchaServiceException e) {
            errors.addErrorCode("error.checkcodeInvalid");
            errors.addErrorString(e.getMessage());
            return errors;
        }
        if (errors.ifNotEmail(email, "email", 100)) {
            return errors;
        }
        if (errors.ifNotUsername(username, "username", 3, 100)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateReset(Long uid, String resetKey, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(uid, "uid")) {
            return errors;
        }
        User user = this.userMng.findById(uid);
        if (errors.ifNotExist(user, User.class, uid)) {
            return errors;
        }
        if (errors.ifOutOfLength(resetKey, "resetKey", 32, 32)) {
            return errors;
        }
        return errors;
    }
}

