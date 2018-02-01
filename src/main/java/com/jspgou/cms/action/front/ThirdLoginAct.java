package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.service.WeixinSvc;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.security.encoder.PwdEncoder;
import com.jspgou.common.web.HttpRequestUtil;
import com.jspgou.common.web.LoginUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.session.SessionProvider;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.Member;
import com.jspgou.core.entity.ThirdAccount;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.GlobalMng;
import com.jspgou.core.manager.ThirdAccountMng;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubject.Builder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThirdLoginAct {
    private static final Logger log = LoggerFactory.getLogger(ThirdLoginAct.class);
    public static final String TPL_AUTH = "tpl.member.auth";
    public static final String TPL_BIND = "tpl.member.bind";
    public static final String UNIFORM_SINGLE_INTERFACE = "https://open.weixin.qq.com/connect/qrconnect";
    public static final String characterEncodingUTF8 = "UTF-8";

    @Autowired
    private UserMng userMng;

    @Autowired
    private SessionProvider session;

    @Autowired
    private PwdEncoder pwdEncoder;

    @Autowired
    private ThirdAccountMng accountMng;

    @Autowired
    private GlobalMng globalMng;

    @Autowired
    private WeixinSvc WeixinSvc;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @RequestMapping({"/public_auth.jspx"})
    public String auth(String openId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.auth", new Object[0]), request);
    }

    @RequestMapping({"/public_wechat_login.jspx"})
    public String weChatLogin(String code, String state, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException, JSONException {
        Website web = SiteUtils.getWeb(request);
        JSONObject json = new JSONObject();

        if (StringUtils.isBlank(state)) {
            return FrontHelper.pageNotFound(web, model, request);
        }

        String AccessToken = this.WeixinSvc.getAccesstoken(code);
        json = new JSONObject(AccessToken);
        String oppenId = json.getString("openid");
        WebErrors error = validateKey(oppenId, request);
        if (error.hasErrors()) {
            return FrontHelper.showError(error, web, model, request);
        }
        if ("WECHAT".equals("WECHAT")) {
            this.session.setAttribute(request, response, "weChatId", oppenId);
        }
        if (StringUtils.isNotBlank(oppenId)) {
            oppenId = this.pwdEncoder.encodePassword(oppenId);
        }
        String token = json.getString("access_token");

        String TestToken = this.WeixinSvc.testToken(token, oppenId);
        json = new JSONObject(TestToken);

        String errcode = json.getString("errcode");
        String errmsg = json.getString("errmsg");
        String nickname = null;

        if ((errcode.equals("0")) && (errmsg.equals("ok"))) {
            String getUserinfo = this.WeixinSvc.getUserInfo(token, oppenId);
            json = new JSONObject(getUserinfo);
            nickname = json.getString("nickname");
        }
        ThirdAccount account = this.accountMng.findByKey(oppenId);
        model.addAttribute("WECHAT", "WECHAT");
        if (account != null) {
            if (!account.getThirdLoginName().equals(nickname)) {
                account.setThirdLoginName(nickname);
                this.accountMng.update(account);
            }
            model.addAttribute("succ", Boolean.valueOf(true));

            loginByKey(oppenId, request, response, model);
        } else {
            model.addAttribute("nickname", nickname);
            model.addAttribute("succ", Boolean.valueOf(false));
        }

        ShopFrontHelper.setCommonData(request, model, web, 1);

        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.auth", new Object[0]), request);
    }

    @RequestMapping({"/public_bind_username.jspx"})
    public String bind_username_post(String username, String password, String nickname, Boolean sina, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = WebErrors.create(request);
        String source = "";
        if (StringUtils.isBlank(username)) {
            errors.addErrorCode("error.usernameRequired");
        } else {
            boolean usernameExist = this.userMng.usernameExist(username);
            if (usernameExist) {
                errors.addErrorCode("error.usernameExist");
            } else {
                String openId = (String) this.session.getAttribute(request, "openId");
                String uid = (String) this.session.getAttribute(request, "uid");
                String wechatOpenId = (String) this.session.getAttribute(request, "weChatId");

                if ((StringUtils.isNotBlank(openId)) || (StringUtils.isNotBlank(uid)) || (StringUtils.isNotBlank(wechatOpenId))) {
                    this.shopMemberMng.register(username, password, null, Boolean.valueOf(true), null,
                            null, null, web.getId(), Long.valueOf(1L));
                }

                if (StringUtils.isNotBlank(openId))
                    source = "QQ";
                else if (StringUtils.isNotBlank(uid))
                    source = "SINA";
                else if (StringUtils.isNotBlank(wechatOpenId)) {
                    source = "WECHAT";
                }

                loginByUsername(username, nickname, request, response, model);
            }
        }
        if (errors.hasErrors()) {
            errors.toModel(model);
            model.addAttribute("success", Boolean.valueOf(false));
        } else {
            model.addAttribute("success", Boolean.valueOf(true));
        }
        model.addAttribute("source", source);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.bind", new Object[0]), request);
    }

    @RequestMapping({"/public_auth_login.jspx"})
    public void authLogin(String key, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        if (StringUtils.isNotBlank(source)) {
            if (source.equals("QQ"))
                this.session.setAttribute(request, response, "openId", key);
            else if (source.equals("QQWEBO"))
                this.session.setAttribute(request, response, "weboOpenId", key);
            else if (source.equals("SINA")) {
                this.session.setAttribute(request, response, "uid", key);
            }
        }
        JSONObject json = new JSONObject();

        if (StringUtils.isNotBlank(key)) {
            key = this.pwdEncoder.encodePassword(key);
        }
        ThirdAccount account = this.accountMng.findByKey(key);
        if (account != null) {
            json.put("succ", true);

            loginByKey(key, request, response, model);
        } else {
            json.put("succ", false);
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping(value = {"/public_bind.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String bind_get(String nickname, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        model.addAttribute("nickname", nickname);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.member.bind", new Object[0]), request);
    }

    @RequestMapping({"/sso/authenticate.jspx"})
    public void authenticate(String username, String sessionId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        User user = this.userMng.getByUsername(username);
        if ((user != null) && (sessionId != null)) {
            String userSessionId = user.getSessionId();
            if (StringUtils.isNotBlank(userSessionId)) {
                if (userSessionId.equals(sessionId))
                    ResponseUtils.renderJson(response, "true");
            } else
                ResponseUtils.renderJson(response, "false");
        }
    }

    @RequestMapping({"/sso/login.jspx"})
    public void loginSso(String username, String sessionId, String ssoLogout, HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        ShopAdmin admin = AdminThread.get();
        if (admin != null) {
            user = admin.getAdmin().getUser();
        }
        ShopMember member = MemberThread.get();
        if (member != null) {
            user = member.getMember().getUser();
        }
        Website site = SiteUtils.getWeb(request);
        if (StringUtils.isNotBlank(username)) {
            JSONObject object = new JSONObject();
            try {
                if (user == null) {
                    List authenticateUrls = site.getSsoAuthenticateUrls();
                    String success = authenticate(username, sessionId, authenticateUrls);
                    if (success.equals("true")) {
                        LoginUtils.loginShiro(request, response, username);
                        user = this.userMng.getByUsername(username);
                        if (user != null) {
                            this.userMng.updateLoginInfo(user.getId(), null, null, sessionId);
                        }
                        object.put("result", "login");
                    }
                } else if ((StringUtils.isNotBlank(ssoLogout)) && (ssoLogout.equals("true"))) {
                    LoginUtils.logout();
                    object.put("result", "logout");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResponseUtils.renderJson(response, object.toString());
        }
    }

    private void loginByKey(String key, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        ThirdAccount account = this.accountMng.findByKey(key);
        if ((StringUtils.isNotBlank(key)) && (account != null)) {
            String username = account.getUsername();
            loginShiro(request, response, username);
        }
    }

    private void loginByUsername(String username, String nickname, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String openId = (String) this.session.getAttribute(request, "openId");
        String uid = (String) this.session.getAttribute(request, "uid");
        String weChatOpenId = (String) this.session.getAttribute(request, "weChatId");
        if (StringUtils.isNotBlank(openId)) {
            loginShiro(request, response, username);

            bind(username, nickname, openId, "QQ");
        }
        if (StringUtils.isNotBlank(uid)) {
            loginShiro(request, response, username);

            bind(username, nickname, uid, "SINA");
        }
        if (StringUtils.isNotBlank(weChatOpenId)) {
            loginShiro(request, response, username);

            bind(username, nickname, weChatOpenId, "WECHAT");
        }
    }

    private void bind(String username, String nickname, String openId, String source) {
        ThirdAccount account = this.accountMng.findByKey(openId);
        if (account == null) {
            account = new ThirdAccount();
            account.setUsername(username);
            account.setThirdLoginName(nickname);

            openId = this.pwdEncoder.encodePassword(openId);
            account.setAccountKey(openId);
            account.setSource(source);
            this.accountMng.save(account);
        }
    }

    private WebErrors validateKey(String key, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isBlank(key)) {
            errors.addErrorString("网站系统后台参数错误");
            return errors;
        }
        return errors;
    }

    private void loginShiro(HttpServletRequest request, HttpServletResponse response, String username) {
        PrincipalCollection principals = new SimplePrincipalCollection(username, username);
        WebSubject.Builder builder = new WebSubject.Builder(request, response);
        builder.principals(principals);
        builder.authenticated(true);
        WebSubject subject = builder.buildWebSubject();
        ThreadContext.bind(subject);
    }

    private String authenticate(String username, String sessionId, List<String> authenticateUrls) {
        String result = "false";
        for (String url : authenticateUrls) {
            result = authenticate(username, sessionId, url);
            if (result.equals("true")) {
                break;
            }
        }
        return result;
    }

    private String authenticate(String username, String sessionId, String authenticateUrl) {
        Map params = new HashMap();
        params.put("username", username);
        params.put("sessionId", sessionId);
        String success = "false";
        try {
            success = HttpRequestUtil.request(authenticateUrl, params, "post", "utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }
}

