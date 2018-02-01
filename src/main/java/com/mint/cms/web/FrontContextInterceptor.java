package com.mint.cms.web;

import com.mint.cms.entity.ApiAccount;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopConfig;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ApiAccountMng;
import com.mint.cms.manager.ApiUserLoginMng;
import com.mint.cms.manager.ShopAdminMng;
import com.mint.cms.manager.ShopConfigMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.service.LoginSvc;
import com.mint.cms.web.threadvariable.AdminThread;
import com.mint.cms.web.threadvariable.GroupThread;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.util.AES128Util;
import com.mint.common.util.CheckMobile;
import com.mint.common.web.session.SessionProvider;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FrontContextInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ShopConfigMng shopConfigMng;

    @Autowired
    private LoginSvc loginSvc;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ShopAdminMng shopAdminMng;

    @Autowired
    private SessionProvider session;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Website web = SiteUtils.getWeb(request);
        ShopConfig config = this.shopConfigMng.findById(web.getId());
        if (config == null) {
            throw new IllegalStateException(
                    "no ShopConfig found in Website id=" + web.getId());
        }
        request.setAttribute("_shop_config_key", config);

        ShopMember member = null;
        ShopAdmin admin = null;
        Subject subject = SecurityUtils.getSubject();
        String username = "";
        if (subject.isAuthenticated()) {
            username = (String) subject.getPrincipal();
            member = this.shopMemberMng.getByUsername(web.getId(), username);
            admin = this.shopAdminMng.getByUsername(username);
        }
        if (admin != null) {
            Long userId = admin.getAdmin().getUser().getId();
            member = this.shopMemberMng.getByUserId(web.getId(), userId);
            if (member == null) {
                if (config.getRegisterAuto().booleanValue()) {
                    member = this.shopMemberMng.join(userId, web.getId(), config.getRegisterGroup());
                }
            }
            AdminThread.set(admin);
        }
        checkEquipment(request, response);
        if (member != null) {
            List list = this.apiAccountMng.getPage(1, 1).getList();
            String sessionId = this.session.getSessionId(request, response);
            MemberThread.setSessionKey(AES128Util.encrypt(sessionId, ((ApiAccount) list.get(0)).getAesKey(), ((ApiAccount) list.get(0)).getIvKey()));
            MemberThread.setUserName(username);
            this.apiUserLoginMng.userLogin(username, sessionId);
            MemberThread.set(member);
            GroupThread.set(member.getGroup());
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MemberThread.remove();
        GroupThread.remove();
    }

    public void checkEquipment(HttpServletRequest request, HttpServletResponse response) {
        String ua = (String) this.session.getAttribute(request, "ua");
        if (ua == null)
            try {
                String userAgent = request.getHeader("USER-AGENT").toLowerCase();
                if (userAgent == null) {
                    userAgent = "";
                }
                if (CheckMobile.check(userAgent))
                    ua = "mobile";
                else {
                    ua = "pc";
                }

                this.session.setAttribute(request, response, "ua", ua);
            } catch (Exception localException) {
            }
        if (StringUtils.isNotBlank(ua))
            request.setAttribute("ua", ua);
    }
}

