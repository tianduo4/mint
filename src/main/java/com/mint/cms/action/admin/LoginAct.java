package com.mint.cms.action.admin;

import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.service.LoginSvc;
import com.mint.cms.web.SiteUtils;
import com.mint.cms.web.threadvariable.AdminThread;
import com.mint.common.security.BadCredentialsException;
import com.mint.common.security.UsernameNotFoundException;
import com.mint.core.entity.Website;
import com.mint.core.security.UserNotInWebsiteException;
import com.mint.core.web.WebErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginAct {
    private static final Logger log = LoggerFactory.getLogger(LoginAct.class);

    @Autowired
    private LoginSvc loginSvc;

    @RequestMapping(value = {"/index.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(ModelMap model) {
        ShopAdmin admin = AdminThread.get();
        if (admin != null) {
            model.addAttribute("admin", admin);
            return "index";
        }
        return "login";
    }

    @RequestMapping(value = {"/login.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String input(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "login";
    }

    @RequestMapping(value = {"/login.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String submit(String username, String password, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebErrors errors = validateSubmit(username, request, response);
        if (!errors.hasErrors()) {
            Website web = SiteUtils.getWeb(request);
            try {
                this.loginSvc.adminLogin(request, response, web, username, password);
                log.info("admin '{}' login success.", username);
                return "redirect:index.do";
            } catch (UsernameNotFoundException e) {
                errors.addError(e.getMessage());
                log.info(e.getMessage());
            } catch (BadCredentialsException e) {
                errors.addError(e.getMessage());
                log.info(e.getMessage());
            } catch (UserNotInWebsiteException e) {
                errors.addError(e.getMessage());
                log.info(e.getMessage());
            }

        }

        errors.toModel(model);

        return "login";
    }

    @RequestMapping({"/logout.do"})
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        this.loginSvc.logout(request, response);
        return "redirect:index.do";
    }

    private WebErrors validateSubmit(String username, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);

        return errors;
    }
}

