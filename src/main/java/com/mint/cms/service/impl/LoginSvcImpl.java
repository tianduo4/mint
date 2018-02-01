package com.mint.cms.service.impl;

import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopConfig;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ShopAdminMng;
import com.mint.cms.manager.ShopConfigMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.service.LoginSvc;
import com.mint.cms.service.ShoppingSvc;
import com.mint.common.security.BadCredentialsException;
import com.mint.common.security.UserNotAcitveException;
import com.mint.common.security.UsernameNotFoundException;
import com.mint.common.security.encoder.PwdEncoder;
import com.mint.common.security.rememberme.CookieTheftException;
import com.mint.common.security.rememberme.RememberMeService;
import com.mint.common.web.session.SessionProvider;
import com.mint.core.entity.Member;
import com.mint.core.entity.User;
import com.mint.core.entity.Website;
import com.mint.core.manager.MemberMng;
import com.mint.core.manager.UserMng;
import com.mint.core.security.UserNotInWebsiteException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginSvcImpl
        implements LoginSvc {
    private static final Logger log = LoggerFactory.getLogger(LoginSvcImpl.class);
    private ShopMemberMng shopMemberMng;
    private ShopAdminMng shopAdminMng;
    private UserMng userMng;
    private MemberMng memberMng;
    private ShopConfigMng shopConfigMng;
    private PwdEncoder pwdEncoder;
    private RememberMeService rememberMeService;
    private ShoppingSvc shoppingSvc;
    private SessionProvider session;

    public ShopMember memberLogin(HttpServletRequest request, HttpServletResponse response, Website web, String username, String password)
            throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException, UserNotAcitveException {
        Long webId = web.getId();

        logout(request, response);
        User user = login(username, password);
        ShopMember member = this.shopMemberMng.getByUserId(webId, user.getId());
        if (member == null) {
            ShopConfig config = this.shopConfigMng.findById(webId);
            if (config.getRegisterAuto().booleanValue()) {
                member = this.shopMemberMng.join(user, webId, config.getRegisterGroup());
            } else throw new UserNotInWebsiteException("user '" +
                    user.getUsername() + "' not in Website '" + webId +
                    "'");

        } else if (!member.getMember().getActive().booleanValue()) {
            throw new UserNotAcitveException("user '" +
                    user.getUsername() + "' not Active '" + webId +
                    "'");
        }

        ShopAdmin admin = this.shopAdminMng.getByUserId(user.getId(), webId);
        if (admin != null) {
            this.session.setAttribute(request, response, "_admin_id_key", admin.getId());
        }

        this.userMng.updateLoginInfo(user.getId(), request.getRemoteAddr());
        this.rememberMeService.loginSuccess(request, response, member.getMember());
        this.session.setAttribute(request, response, "_user_id_key", user.getId());
        this.session.setAttribute(request, response, "_member_id_key", member.getId());
        addUsernameCookie(member.getUsername(), null, null, request, response);
        this.shoppingSvc.addCookie(member, request, response);
        return member;
    }

    public ShopMember getMember(HttpServletRequest request, HttpServletResponse response, Website web) {
        ShopMember member = getMemberFromSession(request, response, web);
        return member != null ? member : getMemberFromCookie(request, response,
                web);
    }

    private ShopMember getMemberFromCookie(HttpServletRequest request, HttpServletResponse response, Website web) {
        Member coreMember;
        try {
            coreMember = (Member) this.rememberMeService
                    .autoLogin(request, response);
            if (coreMember == null)
                return null;
        } catch (CookieTheftException e) {
            log.warn("remember me cookie theft: {}", e.getMessage());
            return null;
        }
        if (coreMember == null) {
            return null;
        }
        Long webId = web.getId();
        Long userId = coreMember.getUser().getId();
        ShopMember member = null;

        boolean change = false;

        if (!coreMember.getWebsite().getId().equals(webId)) {
            coreMember = this.memberMng.getByUserId(webId, userId);
            change = true;
        }
        if (coreMember == null) {
            ShopConfig config = this.shopConfigMng.findById(webId);
            if (config.getRegisterAuto().booleanValue()) {
                member = this.shopMemberMng.join(userId, webId, config
                        .getRegisterGroup());
                log.debug("shop member auto login. username= {}", member
                        .getUsername());
            } else {
                log.debug("shop member not allow auto login.");
            }
        } else {
            member = this.shopMemberMng.findById(coreMember.getId());

            if (member == null) {
                throw new IllegalStateException(
                        "This is JspGou's BUG, ShopMember here should not be null.");
            }
        }
        if (member != null) {
            this.userMng.updateLoginInfo(userId, request.getRemoteAddr());
            this.session.setAttribute(request, response, "_user_id_key", member
                    .getMember().getUser().getId());
            this.session.setAttribute(request, response, "_member_id_key", member.getMember().getId());

            addUsernameCookie(member.getUsername(), null, null, request, response);
        }

        return member;
    }

    private ShopMember getMemberFromSession(HttpServletRequest request, HttpServletResponse response, Website web) {
        Long memberId = (Long) this.session.getAttribute(request, "_member_id_key");
        ShopMember member = null;
        Long webId = web.getId();
        if (memberId != null) {
            member = this.shopMemberMng.findById(memberId);

            if ((member != null) && (member.getWebsite().getId().equals(webId))) {
                return member;
            }
        }
        Long userId = (Long) this.session.getAttribute(request, "_user_id_key");
        if (userId != null) {
            member = this.shopMemberMng.getByUserId(webId, userId);
            if (member == null) {
                ShopConfig config = this.shopConfigMng.findById(webId);

                if (config.getRegisterAuto().booleanValue()) {
                    member = this.shopMemberMng.join(userId, webId, config
                            .getRegisterGroup());
                }
            }
            if (member != null) {
                this.session.setAttribute(request, response, "_member_id_key", member.getId());
            }
        }
        return member;
    }

    private void addUsernameCookie(String username, String firstname, String lastname, HttpServletRequest request, HttpServletResponse response) {
        StringBuilder un = new StringBuilder(
                new String(Base64.encodeBase64(username.getBytes())));
        while (un.charAt(un.length() - 1) == '=') {
            un.deleteCharAt(un.length() - 1);
        }
        response.addCookie(createCookie("username", un.toString(), request));

        StringBuilder fn = new StringBuilder();
        if (!StringUtils.isBlank(firstname)) {
            fn.append(firstname);
        }
        fn.append(":");
        if (!StringUtils.isBlank(lastname)) {
            fn.append(lastname);
        }
        String value = fn.toString();
        fn = new StringBuilder(
                new String(Base64.encodeBase64(value.getBytes())));
        while (fn.charAt(fn.length() - 1) == '=') {
            fn.deleteCharAt(fn.length() - 1);
        }
        Cookie c = createCookie("fullname", fn.toString(), request);
        c.setMaxAge(2147483647);
        response.addCookie(c);
    }

    private void deleteUsernameCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie username = createCookie("username", null, request);
        username.setMaxAge(0);
        Cookie fullname = createCookie("fullname", null, request);
        fullname.setMaxAge(0);
        response.addCookie(username);
        response.addCookie(fullname);
    }

    private Cookie createCookie(String name, String value, HttpServletRequest request) {
        Cookie cookie = new Cookie(name, value);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
        return cookie;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        this.rememberMeService.logout(request, response);
        this.session.logout(request, response);
        clearCookie(request, response);
    }

    public void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        this.shoppingSvc.clearCookie(request, response);
        deleteUsernameCookie(request, response);
    }

    public ShopAdmin adminLogin(HttpServletRequest request, HttpServletResponse response, Website web, String username, String password)
            throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException {
        Long webId = web.getId();

        logout(request, response);
        User user = login(username, password);
        ShopAdmin admin = this.shopAdminMng.getByUserId(user.getId(), webId);
        if (admin == null) {
            throw new UserNotInWebsiteException("user '" + user.getUsername() +
                    "' not in Website '" + webId + "'");
        }
        ShopMember member = this.shopMemberMng.getByUserId(webId, user.getId());
        if (member != null) {
            this.session.setAttribute(request, response, "_member_id_key", member.getId());
        }
        this.userMng.updateLoginInfo(user.getId(), request.getRemoteAddr());
        this.session.setAttribute(request, response, "_user_id_key", user
                .getId());
        this.session.setAttribute(request, response, "_admin_id_key", admin
                .getId());
        addUsernameCookie(admin.getUsername(), null, null, request, response);
        return admin;
    }

    public ShopAdmin getAdmin(HttpServletRequest request, HttpServletResponse response, Website web) {
        Long webId = web.getId();
        Long adminId = (Long) this.session.getAttribute(request,
                "_admin_id_key");

        if (adminId != null) {
            ShopAdmin admin = this.shopAdminMng.findById(adminId);
            if ((admin != null) && (admin.getWebsite().getId().equals(webId))) {
                return admin;
            }

            Long userId = admin.getAdmin().getUser().getId();
            admin = this.shopAdminMng.getByUserId(userId, webId);
            return admin;
        }

        return null;
    }

    private User login(String username, String password) throws UsernameNotFoundException, BadCredentialsException {
        User user = this.userMng.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found: " +
                    username);
        }
        if (!this.pwdEncoder.isPasswordValid(user.getPassword(), password)) {
            throw new BadCredentialsException("password invalid!");
        }
        return user;
    }

    @Autowired
    public void setShopMemberMng(ShopMemberMng shopMemberMng) {
        this.shopMemberMng = shopMemberMng;
    }

    @Autowired
    public void setShopConfigMng(ShopConfigMng shopConfigMng) {
        this.shopConfigMng = shopConfigMng;
    }

    @Autowired
    public void setShopAdminMng(ShopAdminMng shopAdminMng) {
        this.shopAdminMng = shopAdminMng;
    }

    @Autowired
    public void setUserMng(UserMng userMng) {
        this.userMng = userMng;
    }

    @Autowired
    public void setPwdEncoder(PwdEncoder pwdEncoder) {
        this.pwdEncoder = pwdEncoder;
    }

    @Autowired
    public void setRememberMeService(RememberMeService rememberMeService) {
        this.rememberMeService = rememberMeService;
    }

    @Autowired
    public void setMemberMng(MemberMng memberMng) {
        this.memberMng = memberMng;
    }

    @Autowired
    public void setSessionProvider(SessionProvider session) {
        this.session = session;
    }

    @Autowired
    public void setShoppingSvc(ShoppingSvc shoppingSvc) {
        this.shoppingSvc = shoppingSvc;
    }
}

