package com.jspgou.cms.action.member;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ApiAccountMng;
import com.jspgou.cms.manager.ApiUserLoginMng;
import com.jspgou.cms.service.LoginSvc;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.common.security.BadCredentialsException;
import com.jspgou.common.security.UserNotAcitveException;
import com.jspgou.common.security.UsernameNotFoundException;
import com.jspgou.common.web.session.SessionProvider;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Global;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.GlobalMng;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.security.UserNotInWebsiteException;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

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
public class LoginAct {
    private static final Logger log = LoggerFactory.getLogger(LoginAct.class);
    private static final String LOGIN_INPUT = "tpl.loginInput";
    public static final String TPL_INDEX = "tpl.index";

    @Autowired
    private LoginSvc loginSvc;

    @Autowired
    private UserMng userMng;

    @Autowired
    private GlobalMng globalMng;

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;

    @Autowired
    private SessionProvider session;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @RequestMapping(value = {"/login.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String loginInput(String returnUrl, String message, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        if (!StringUtils.isBlank(returnUrl)) {
            model.addAttribute("returnUrl", returnUrl);
            if (!StringUtils.isBlank(message)) {
                model.addAttribute("message", message);
            }
        }

        model.addAttribute("global", this.globalMng.get().getConfigAttr());
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request,
                "tpl.loginInput", new Object[0]), request);
    }

    @RequestMapping(value = {"/login.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String loginSubmit(String username, String password, String returnUrl, String redirectUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = WebErrors.create(request);
        try {
            ShopMember member = this.loginSvc.memberLogin(request, response, web, username, password);
            if (member == null) {
                return "redirect:/";
            }
            log.info("member '{}' login success.", username);
            if (!StringUtils.isBlank(returnUrl))
                return "redirect:" + returnUrl;
            if (!StringUtils.isBlank(redirectUrl)) {
                return "redirect:" + redirectUrl;
            }

            String sessionKey = this.session.getSessionId(request, response);
            model.addAttribute("member", member);
            ShopFrontHelper.setCommonData(request, model, web, 1);

            return web.getTemplate("index", MessageResolver.getMessage(request,
                    "tpl.index", new Object[0]), request);
        } catch (UsernameNotFoundException e) {
            errors.addErrorCode("error.usernameNotExist");
            log.info(e.getMessage());
        } catch (BadCredentialsException e) {
            errors.addErrorCode("error.passwordInvalid");
            log.info(e.getMessage());
        } catch (UserNotInWebsiteException e) {
            errors.addErrorCode("error.usernameNotInWebsite");
            log.info(e.getMessage());
        } catch (UserNotAcitveException e) {
            errors.addErrorCode("error.usernameNotActivated");
            log.info(e.getMessage());
        } catch (Exception e) {
            errors.addErrorCode("error.appIderror");
            log.info(e.getMessage());
        }
        errors.toModel(model);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.loginInput", new Object[0]), request);
    }

    public Integer errorRemaining(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        User user = this.userMng.getByUsername(username);
        if (user == null) {
            return null;
        }
        return null;
    }

    @RequestMapping({"/logout.jspx"})
    public String logout(String redirectUrl, String username, HttpServletRequest request, HttpServletResponse response) {
        this.apiUserLoginMng.userLogout(username);
        this.loginSvc.logout(request, response);
        if (!StringUtils.isBlank(redirectUrl)) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }
}

