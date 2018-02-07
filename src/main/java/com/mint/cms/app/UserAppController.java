package com.mint.cms.app;

import com.mint.cms.entity.ApiAccount;
import com.mint.cms.entity.ApiUserLogin;
import com.mint.cms.entity.Collect;
import com.mint.cms.entity.Coupon;
import com.mint.cms.entity.MemberCoupon;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ApiAccountMng;
import com.mint.cms.manager.ApiUserLoginMng;
import com.mint.cms.manager.ApiUtilMng;
import com.mint.cms.manager.CollectMng;
import com.mint.cms.manager.CouponMng;
import com.mint.cms.manager.MemberCouponMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.common.util.AES128Util;
import com.mint.common.util.Apputil;
import com.mint.common.util.ConvertMapToJson;
import com.mint.common.web.RequestUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.session.SessionProvider;
import com.mint.core.entity.User;
import com.mint.core.manager.UserMng;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAppController {

    @Autowired
    private ApiUtilMng apiUtilMng;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;

    @Autowired
    private SessionProvider session;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private CollectMng collectMng;

    @Autowired
    private CouponMng couponMng;

    @Autowired
    private MemberCouponMng memberCouponMng;

    @RequestMapping({"/api/user/login.jspx"})
    public void userLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        JSONObject o = new JSONObject();
        String username = request.getParameter("username");
        String appId = request.getParameter("appId");
        String encryptPass = null;
        Boolean decryptionFailure = Boolean.valueOf(true);
        encryptPass = this.apiUtilMng.getEncryptpass(request);
        if ((StringUtils.isNotBlank(username)) && (StringUtils.isNotBlank(appId)) &&
                (StringUtils.isNotBlank(encryptPass))) {
            User user = this.userMng.register(username, null, null, null);
            ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
            if (user != null) {
                if (this.userMng.isPasswordValid(user.getId(), encryptPass)) {
                    String sessionKey = this.session.getSessionId(request, response);
                    ApiUserLogin apiUserLogin = this.apiUserLoginMng
                            .findByUsername(username);
                    if (apiUserLogin != null)
                        this.apiUserLoginMng
                                .updateLoginSuccess(username, sessionKey);
                    else {
                        this.apiUserLoginMng.saveLoginSuccess(username, sessionKey);
                    }
                    String aesKey = apiAccount.getAesKey();
                    String ivVal = apiAccount.getIvKey();
                    o.put("message", "03");
                    o.put("sessionKey",
                            AES128Util.encrypt(sessionKey, aesKey, ivVal));
                } else {
                    o.put("mesage", "02");
                }
            } else
                o.put("message", "01");
        } else {
            o.put("message", "00");
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                o.toString(), "/api/user/login.jspx", decryptionFailure,
                request));
    }

    @RequestMapping({"/api/user/username_unique.jspx"})
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String username = request.getParameter("username");
        String callback = request.getParameter("callback");
        String result = "00";
        if (StringUtils.isBlank(username)) {
            result = "02";
        } else {
            result = "01";
            if (this.userMng.usernameExist(username))
                map.put("pd", "false");
            else {
                map.put("pd", "true");
            }
        }

        map.put("result", result);
        if (!StringUtils.isBlank(callback))
            ResponseUtils.renderJson(response, callback + "(" +
                    ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
        else
            ResponseUtils.renderJson(response,
                    ConvertMapToJson.buildJsonBody(map, 0, false));
    }

    @RequestMapping({"/api/user/register.jspx"})
    public void register(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONObject o = new JSONObject();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (this.apiUtilMng.verify(request).booleanValue()) {
            if ((StringUtils.isNotBlank(username)) &&
                    (StringUtils.isNotBlank(password)) &&
                    (StringUtils.isNotBlank(email))) {
                if ((!this.userMng.usernameExist(username)) &&
                        (!this.userMng.emailExist(email))) {
                    User user = this.userMng.register(username, password, email,
                            RequestUtils.getIpAddr(request));
                    o.put("message", "02");
                    o.put("userId", user.getId());
                } else {
                    o.put("message", "01");
                }
            } else o.put("message", "00");
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                o.toString(), "/api/user/register.jspx", request));
    }

    @RequestMapping({"/api/user/logout.jspx"})
    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONObject o = new JSONObject();
        String sessionKey = request.getParameter("sessionKey");
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        if (user != null) {
            ApiUserLogin apiUserLogin = this.apiUserLoginMng
                    .findBySessionKey(sessionKey);
            if (apiUserLogin != null) {
                this.apiUserLoginMng.deleteById(apiUserLogin.getId());
                o.put("message", "01");
            } else {
                o.put("message", "00");
            }
        } else {
            o.put("message", "00");
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                o.toString(), "/api/user/logout.jspx", request));
    }

    @RequestMapping({"/api/user/profile.jspx"})
    public void profile(HttpServletRequest request, HttpServletResponse response, ModelMap madel)
            throws JSONException {
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        JSONObject o = new JSONObject();
        if (user != null) {
            Boolean protocol = Apputil.getRequestBoolean(request
                    .getParameter("protocol"));
            o.put("id", user.getId());

            if (user.getGroup() != null) {
                o.put("groupId", user.getGroup().getId());
                o.put("groupName", user.getGroup().getName());
            }
            o.put("username", user.getUsername());
            o.put("email", user.getEmail());
            o.put("realName", user.getRealName());
            o.put("gender", user.getGender());
            o.put("birthday", user.getBirthday());
            o.put("score", user.getScore());
            o.put("money", user.getMoney());
            o.put("company", user.getCompany());
            o.put("marriage", user.getMarriage());
            o.put("position", user.getPosition());
            o.put("address", user.getAddress());
            o.put("mobile", user.getMobile());
            o.put("tel", user.getTel());
            o.put("avatar", user.getAvatar());
            o.put("lastLoginTime", user.getLastLoginTime());
            o.put("LoginCount", user.getLoginCount());
            o.put("schoolTag", user.getSchoolTag());
            o.put("schoolTagDate", user.getSchoolTagDate());
            o.put("favoriteBrand", user.getFavoriteBrand());
            o.put("favoriteStar", user.getFavoriteStar());
            o.put("favoriteMovie", user.getFavoriteMovie());
        }
        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                o.toString(), "/api/user/profile.jspx", request));
    }

    @RequestMapping({"/api/user/profileupdate.jspx"})
    public void profileupdate(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Map map = new HashMap();
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        String message = "00";
        if (user != null) {
            String realName = request.getParameter("realName");
            String mobile = request.getParameter("mobile");
            String tel = request.getParameter("tel");
            String gender = request.getParameter("gender");
            Date birthday = Apputil.getRequestDate(request
                    .getParameter("birthday"));
            String address = request.getParameter("address");
            String marriage = request.getParameter("marriage");
            String company = request.getParameter("company");
            String position = request.getParameter("position");
            String avatar = request.getParameter("avater");
            String schoolTag = request.getParameter("schoolTag");
            Date schoolTagDate = Apputil.getRequestDate(request
                    .getParameter("schoolTagDate"));
            String favoriteBrand = request.getParameter("favoriteBrand");
            String favoriteStar = request.getParameter("favoriteStar");
            String favoriteMovie = request.getParameter("favoriteMovie");
            if (StringUtils.isNotBlank(realName)) {
                user.setRealName(realName);
            }
            if (StringUtils.isNotBlank(mobile)) {
                user.setMobile(mobile);
            }
            if (StringUtils.isNotBlank(tel)) {
                user.setTel(tel);
            }
            if (StringUtils.isNotBlank(address)) {
                user.setAddress(address);
            }
            if (StringUtils.isNotBlank(marriage)) {
                user.setMarriage(Boolean.valueOf(Boolean.parseBoolean(marriage)));
            }
            if (StringUtils.isNotBlank(gender)) {
                user.setGender(Boolean.valueOf(Boolean.parseBoolean(gender)));
            }
            if (birthday != null) {
                user.setBirthday(birthday);
            }
            if (StringUtils.isNotBlank(company)) {
                user.setCompany(company);
            }
            if (StringUtils.isNotBlank(position)) {
                user.setPosition(position);
            }
            if (StringUtils.isNotBlank(avatar)) {
                user.setAvatar(avatar);
            }
            if (StringUtils.isNotBlank(schoolTag)) {
                user.setSchoolTag(schoolTag);
            }
            if (schoolTagDate != null) {
                user.setSchoolTagDate(schoolTagDate);
            }
            if (StringUtils.isNotBlank(favoriteBrand)) {
                user.setFavoriteBrand(favoriteBrand);
            }
            if (StringUtils.isNotBlank(favoriteStar)) {
                user.setFavoriteStar(favoriteStar);
            }
            if (StringUtils.isNotBlank(favoriteMovie)) {
                user.setFavoriteMovie(favoriteMovie);
            }
            this.shopMemberMng.update(user);
            message = "01";
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(message,
                "/api/user/profileupdate.jspx", request));
    }

    @RequestMapping({"/api/user/pwd.jspx"})
    public void pwd(HttpServletRequest request, HttpServletResponse response) {
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        String result = "00";
        if (user != null) {
            String origPwd = request.getParameter("origPwd");
            String newPwd = request.getParameter("newPwd");
            String email = request.getParameter("email");
            if ((StringUtils.isNotBlank(newPwd)) && (StringUtils.isNotBlank(email))) {
                this.userMng.updateUser(user.getId(), newPwd, email);
                result = "01";
            }
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(result,
                "/api/user/profileupdate.jspx", request));
    }

    @RequestMapping({"/api/user/collectList.jspx"})
    public void collectList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONArray arr = new JSONArray();
        JSONObject o = new JSONObject();
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        Integer firstResult = Apputil.getfirstResult(request
                .getParameter("firstResult"));
        Integer maxResults = Apputil.getmaxResults(request
                .getParameter("maxResults"));
        if (user != null) {
            List<Collect> list = this.collectMng.getList(user.getId(), firstResult.intValue(),
                    maxResults.intValue());
            for (Collect collect : list) {
                o.put("id", collect.getId());
                if (collect.getFashion() != null) {
                    o.put("fashionId", collect.getFashion().getId());
                }
                o.put("productCoverImg", collect.getProduct().getProductExt()
                        .getCode());
                o.put("productName", collect.getProduct().getName());
                o.put("productSalePrice", collect.getProduct().getSalePrice());
                o.put("productId", collect.getProduct().getId());
                o.put("time", collect.getTime());
                o.put("userId", user.getId());
                arr.put(o);
            }
        }
        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                arr.toString(), "/api/user/collectList.jspx", request));
    }

    @RequestMapping({"/api/user/add_to_collect.jspx"})
    public void addToCollect(Long productId, Long productFashId, HttpServletRequest request, HttpServletResponse response) {
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        Integer pd = Integer.valueOf(0);
        if ((user != null) && (productId != null)) {
            Collect collect = null;
            if (productFashId == null) {
                List clist = this.collectMng.getList(productId,
                        user.getId());
                if (clist.size() > 0)
                    collect = this.collectMng.save(user, productId, null);
            } else {
                collect = this.collectMng.findByProductFashionId(productFashId);
                if (collect == null) {
                    collect = this.collectMng.save(user, productId, productFashId);
                }
            }
            pd = Integer.valueOf(1);
        } else {
            pd = Integer.valueOf(0);
        }
        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                pd.toString(), "api/user/add_to_collect.jspx", request));
    }

    @RequestMapping({"/api/user/getcoupon.jspx"})
    public void coupon(Long id, HttpServletRequest request, HttpServletResponse response) {
        JSONObject o = new JSONObject();
        Date newdate = new Date();
        Integer pd = Integer.valueOf(0);
        if (id != null) {
            Coupon coupon = this.couponMng.findById(id);
            ShopMember user = this.apiUtilMng.findbysessionKey(request);
            if ((coupon != null) && (user != null)) {
                if (this.memberCouponMng.findByCoupon(user.getId(), id) != null) {
                    if (newdate.before(this.couponMng.findById(id)
                            .getCouponEndTime())) {
                        MemberCoupon memberCoupon = new MemberCoupon();
                        memberCoupon.setCoupon(coupon);
                        memberCoupon.setMember(user);
                        memberCoupon.setIsuse(Boolean.valueOf(false));
                        coupon.setCouponCount(Integer.valueOf(coupon.getCouponCount().intValue() - 1));
                        this.memberCouponMng.save(memberCoupon);
                        this.couponMng.update(coupon);

                        pd = Integer.valueOf(1);

//             break label208; //TODO
                    }
                }
                pd = Integer.valueOf(2);
            } else {
                pd = Integer.valueOf(0);
            }
        } else {
            pd = Integer.valueOf(0);
        }

        label208:
        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                pd.toString(), "/api/user/getcoupon.jspx", request));
    }

    @RequestMapping({"api/user/myCouponList.jspx"})
    public void myCouponList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        String result = "00";
        Integer pd = Integer.valueOf(0);
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        if (user != null) {
            List list = this.memberCouponMng.getList(user.getId());
            if ((list != null) &&
                    (list.size() > 0)) {
                map.put("pd", getMyCouponJson(list));
            }
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                pd.toString(), "/api/user/myCouponList.jspx", request));
    }

    private String getMyCouponJson(List<MemberCoupon> beanlist) throws JSONException {
        JSONObject o = new JSONObject();
        JSONArray arr = new JSONArray();
        for (MemberCoupon coupon : beanlist) {
            o.put("id", coupon.getId());
            o.put("name", coupon.getCoupon().getCouponName());
            o.put("couponPrice", coupon.getCoupon().getCouponPrice());
            o.put("leastPrice", coupon.getCoupon().getLeastPrice());
            o.put("couponBeginTime", coupon.getCoupon().getCouponTime());
            o.put("couponEndTime", coupon.getCoupon().getCouponEndTime());
            arr.put(o);
        }

        return arr.toString();
    }
}

