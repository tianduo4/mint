/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import com.jspgou.cms.entity.Collect;
/*     */ import com.jspgou.cms.entity.Coupon;
/*     */ import com.jspgou.cms.entity.MemberCoupon;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.cms.manager.ApiUtilMng;
/*     */ import com.jspgou.cms.manager.CollectMng;
/*     */ import com.jspgou.cms.manager.CouponMng;
/*     */ import com.jspgou.cms.manager.MemberCouponMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.util.Apputil;
/*     */ import com.jspgou.common.util.ConvertMapToJson;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class UserAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiUtilMng apiUtilMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng apiAccountMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiUserLoginMng apiUserLoginMng;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private CollectMng collectMng;
/*     */ 
/*     */   @Autowired
/*     */   private CouponMng couponMng;
/*     */ 
/*     */   @Autowired
/*     */   private MemberCouponMng memberCouponMng;
/*     */ 
/*     */   @RequestMapping({"/api/user/login.jspx"})
/*     */   public void userLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws Exception
/*     */   {
/*  51 */     JSONObject o = new JSONObject();
/*  52 */     String username = request.getParameter("username");
/*  53 */     String appId = request.getParameter("appId");
/*  54 */     String encryptPass = null;
/*  55 */     Boolean decryptionFailure = Boolean.valueOf(true);
/*  56 */     encryptPass = this.apiUtilMng.getEncryptpass(request);
/*  57 */     if ((StringUtils.isNotBlank(username)) && (StringUtils.isNotBlank(appId)) && 
/*  58 */       (StringUtils.isNotBlank(encryptPass))) {
/*  59 */       User user = this.userMng.register(username, null, null, null);
/*  60 */       ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
/*  61 */       if (user != null)
/*     */       {
/*  63 */         if (this.userMng.isPasswordValid(user.getId(), encryptPass)) {
/*  64 */           String sessionKey = this.session.getSessionId(request, response);
/*  65 */           ApiUserLogin apiUserLogin = this.apiUserLoginMng
/*  66 */             .findByUsername(username);
/*  67 */           if (apiUserLogin != null)
/*  68 */             this.apiUserLoginMng
/*  69 */               .updateLoginSuccess(username, sessionKey);
/*     */           else {
/*  71 */             this.apiUserLoginMng.saveLoginSuccess(username, sessionKey);
/*     */           }
/*  73 */           String aesKey = apiAccount.getAesKey();
/*  74 */           String ivVal = apiAccount.getIvKey();
/*  75 */           o.put("message", "03");
/*  76 */           o.put("sessionKey", 
/*  77 */             AES128Util.encrypt(sessionKey, aesKey, ivVal));
/*     */         }
/*     */         else {
/*  80 */           o.put("mesage", "02");
/*     */         }
/*     */       }
/*     */       else
/*  84 */         o.put("message", "01");
/*     */     }
/*     */     else {
/*  87 */       o.put("message", "00");
/*     */     }
/*     */ 
/*  90 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/*  91 */       o.toString(), "/api/user/login.jspx", decryptionFailure, 
/*  92 */       request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/username_unique.jspx"})
/*     */   public void checkUsername(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 102 */     Map map = new HashMap();
/* 103 */     String username = request.getParameter("username");
/* 104 */     String callback = request.getParameter("callback");
/* 105 */     String result = "00";
/* 106 */     if (StringUtils.isBlank(username)) {
/* 107 */       result = "02";
/*     */     } else {
/* 109 */       result = "01";
/* 110 */       if (this.userMng.usernameExist(username))
/* 111 */         map.put("pd", "false");
/*     */       else {
/* 113 */         map.put("pd", "true");
/*     */       }
/*     */     }
/*     */ 
/* 117 */     map.put("result", result);
/* 118 */     if (!StringUtils.isBlank(callback))
/* 119 */       ResponseUtils.renderJson(response, callback + "(" + 
/* 120 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     else
/* 122 */       ResponseUtils.renderJson(response, 
/* 123 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/register.jspx"})
/*     */   public void register(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 138 */     JSONObject o = new JSONObject();
/* 139 */     String username = request.getParameter("username");
/* 140 */     String email = request.getParameter("email");
/* 141 */     String password = request.getParameter("password");
/* 142 */     if (this.apiUtilMng.verify(request).booleanValue()) {
/* 143 */       if ((StringUtils.isNotBlank(username)) && 
/* 144 */         (StringUtils.isNotBlank(password)) && 
/* 145 */         (StringUtils.isNotBlank(email))) {
/* 146 */         if ((!this.userMng.usernameExist(username)) && 
/* 147 */           (!this.userMng.emailExist(email))) {
/* 148 */           User user = this.userMng.register(username, password, email, 
/* 149 */             RequestUtils.getIpAddr(request));
/* 150 */           o.put("message", "02");
/* 151 */           o.put("userId", user.getId());
/*     */         } else {
/* 153 */           o.put("message", "01");
/*     */         }
/*     */       }
/* 156 */       else o.put("message", "00");
/*     */     }
/*     */ 
/* 159 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 160 */       o.toString(), "/api/user/register.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/logout.jspx"})
/*     */   public void logout(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 172 */     JSONObject o = new JSONObject();
/* 173 */     String sessionKey = request.getParameter("sessionKey");
/* 174 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 175 */     if (user != null) {
/* 176 */       ApiUserLogin apiUserLogin = this.apiUserLoginMng
/* 177 */         .findBySessionKey(sessionKey);
/* 178 */       if (apiUserLogin != null) {
/* 179 */         this.apiUserLoginMng.deleteById(apiUserLogin.getId());
/* 180 */         o.put("message", "01");
/*     */       } else {
/* 182 */         o.put("message", "00");
/*     */       }
/*     */     } else {
/* 185 */       o.put("message", "00");
/*     */     }
/*     */ 
/* 188 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 189 */       o.toString(), "/api/user/logout.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/profile.jspx"})
/*     */   public void profile(HttpServletRequest request, HttpServletResponse response, ModelMap madel)
/*     */     throws JSONException
/*     */   {
/* 203 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 204 */     JSONObject o = new JSONObject();
/* 205 */     if (user != null) {
/* 206 */       Boolean protocol = Apputil.getRequestBoolean(request
/* 207 */         .getParameter("protocol"));
/* 208 */       o.put("id", user.getId());
/*     */ 
/* 210 */       if (user.getGroup() != null) {
/* 211 */         o.put("groupId", user.getGroup().getId());
/* 212 */         o.put("groupName", user.getGroup().getName());
/*     */       }
/* 214 */       o.put("username", user.getUsername());
/* 215 */       o.put("email", user.getEmail());
/* 216 */       o.put("realName", user.getRealName());
/* 217 */       o.put("gender", user.getGender());
/* 218 */       o.put("birthday", user.getBirthday());
/* 219 */       o.put("score", user.getScore());
/* 220 */       o.put("money", user.getMoney());
/* 221 */       o.put("company", user.getCompany());
/* 222 */       o.put("marriage", user.getMarriage());
/* 223 */       o.put("position", user.getPosition());
/* 224 */       o.put("address", user.getAddress());
/* 225 */       o.put("mobile", user.getMobile());
/* 226 */       o.put("tel", user.getTel());
/* 227 */       o.put("avatar", user.getAvatar());
/* 228 */       o.put("lastLoginTime", user.getLastLoginTime());
/* 229 */       o.put("LoginCount", user.getLoginCount());
/* 230 */       o.put("schoolTag", user.getSchoolTag());
/* 231 */       o.put("schoolTagDate", user.getSchoolTagDate());
/* 232 */       o.put("favoriteBrand", user.getFavoriteBrand());
/* 233 */       o.put("favoriteStar", user.getFavoriteStar());
/* 234 */       o.put("favoriteMovie", user.getFavoriteMovie());
/*     */     }
/* 236 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 237 */       o.toString(), "/api/user/profile.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/profileupdate.jspx"})
/*     */   public void profileupdate(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 251 */     Map map = new HashMap();
/* 252 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 253 */     String message = "00";
/* 254 */     if (user != null) {
/* 255 */       String realName = request.getParameter("realName");
/* 256 */       String mobile = request.getParameter("mobile");
/* 257 */       String tel = request.getParameter("tel");
/* 258 */       String gender = request.getParameter("gender");
/* 259 */       Date birthday = Apputil.getRequestDate(request
/* 260 */         .getParameter("birthday"));
/* 261 */       String address = request.getParameter("address");
/* 262 */       String marriage = request.getParameter("marriage");
/* 263 */       String company = request.getParameter("company");
/* 264 */       String position = request.getParameter("position");
/* 265 */       String avatar = request.getParameter("avater");
/* 266 */       String schoolTag = request.getParameter("schoolTag");
/* 267 */       Date schoolTagDate = Apputil.getRequestDate(request
/* 268 */         .getParameter("schoolTagDate"));
/* 269 */       String favoriteBrand = request.getParameter("favoriteBrand");
/* 270 */       String favoriteStar = request.getParameter("favoriteStar");
/* 271 */       String favoriteMovie = request.getParameter("favoriteMovie");
/* 272 */       if (StringUtils.isNotBlank(realName)) {
/* 273 */         user.setRealName(realName);
/*     */       }
/* 275 */       if (StringUtils.isNotBlank(mobile)) {
/* 276 */         user.setMobile(mobile);
/*     */       }
/* 278 */       if (StringUtils.isNotBlank(tel)) {
/* 279 */         user.setTel(tel);
/*     */       }
/* 281 */       if (StringUtils.isNotBlank(address)) {
/* 282 */         user.setAddress(address);
/*     */       }
/* 284 */       if (StringUtils.isNotBlank(marriage)) {
/* 285 */         user.setMarriage(Boolean.valueOf(Boolean.parseBoolean(marriage)));
/*     */       }
/* 287 */       if (StringUtils.isNotBlank(gender)) {
/* 288 */         user.setGender(Boolean.valueOf(Boolean.parseBoolean(gender)));
/*     */       }
/* 290 */       if (birthday != null) {
/* 291 */         user.setBirthday(birthday);
/*     */       }
/* 293 */       if (StringUtils.isNotBlank(company)) {
/* 294 */         user.setCompany(company);
/*     */       }
/* 296 */       if (StringUtils.isNotBlank(position)) {
/* 297 */         user.setPosition(position);
/*     */       }
/* 299 */       if (StringUtils.isNotBlank(avatar)) {
/* 300 */         user.setAvatar(avatar);
/*     */       }
/* 302 */       if (StringUtils.isNotBlank(schoolTag)) {
/* 303 */         user.setSchoolTag(schoolTag);
/*     */       }
/* 305 */       if (schoolTagDate != null) {
/* 306 */         user.setSchoolTagDate(schoolTagDate);
/*     */       }
/* 308 */       if (StringUtils.isNotBlank(favoriteBrand)) {
/* 309 */         user.setFavoriteBrand(favoriteBrand);
/*     */       }
/* 311 */       if (StringUtils.isNotBlank(favoriteStar)) {
/* 312 */         user.setFavoriteStar(favoriteStar);
/*     */       }
/* 314 */       if (StringUtils.isNotBlank(favoriteMovie)) {
/* 315 */         user.setFavoriteMovie(favoriteMovie);
/*     */       }
/* 317 */       this.shopMemberMng.update(user);
/* 318 */       message = "01";
/*     */     }
/*     */ 
/* 321 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(message, 
/* 322 */       "/api/user/profileupdate.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/pwd.jspx"})
/*     */   public void pwd(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 333 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 334 */     String result = "00";
/* 335 */     if (user != null) {
/* 336 */       String origPwd = request.getParameter("origPwd");
/* 337 */       String newPwd = request.getParameter("newPwd");
/* 338 */       String email = request.getParameter("email");
/* 339 */       if ((StringUtils.isNotBlank(newPwd)) && (StringUtils.isNotBlank(email))) {
/* 340 */         this.userMng.updateUser(user.getId(), newPwd, email);
/* 341 */         result = "01";
/*     */       }
/*     */     }
/*     */ 
/* 345 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(result, 
/* 346 */       "/api/user/profileupdate.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/collectList.jspx"})
/*     */   public void collectList(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 358 */     JSONArray arr = new JSONArray();
/* 359 */     JSONObject o = new JSONObject();
/* 360 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 361 */     Integer firstResult = Apputil.getfirstResult(request
/* 362 */       .getParameter("firstResult"));
/* 363 */     Integer maxResults = Apputil.getmaxResults(request
/* 364 */       .getParameter("maxResults"));
/* 365 */     if (user != null) {
/* 366 */       List list = this.collectMng.getList(user.getId(), firstResult.intValue(), 
/* 367 */         maxResults.intValue());
/* 368 */       for (Collect collect : list) {
/* 369 */         o.put("id", collect.getId());
/* 370 */         if (collect.getFashion() != null) {
/* 371 */           o.put("fashionId", collect.getFashion().getId());
/*     */         }
/* 373 */         o.put("productCoverImg", collect.getProduct().getProductExt()
/* 374 */           .getCode());
/* 375 */         o.put("productName", collect.getProduct().getName());
/* 376 */         o.put("productSalePrice", collect.getProduct().getSalePrice());
/* 377 */         o.put("productId", collect.getProduct().getId());
/* 378 */         o.put("time", collect.getTime());
/* 379 */         o.put("userId", user.getId());
/* 380 */         arr.put(o);
/*     */       }
/*     */     }
/* 383 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 384 */       arr.toString(), "/api/user/collectList.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/add_to_collect.jspx"})
/*     */   public void addToCollect(Long productId, Long productFashId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 395 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 396 */     Integer pd = Integer.valueOf(0);
/* 397 */     if ((user != null) && (productId != null)) {
/* 398 */       Collect collect = null;
/* 399 */       if (productFashId == null) {
/* 400 */         List clist = this.collectMng.getList(productId, 
/* 401 */           user.getId());
/* 402 */         if (clist.size() > 0)
/* 403 */           collect = this.collectMng.save(user, productId, null);
/*     */       }
/*     */       else {
/* 406 */         collect = this.collectMng.findByProductFashionId(productFashId);
/* 407 */         if (collect == null) {
/* 408 */           collect = this.collectMng.save(user, productId, productFashId);
/*     */         }
/*     */       }
/* 411 */       pd = Integer.valueOf(1);
/*     */     } else {
/* 413 */       pd = Integer.valueOf(0);
/*     */     }
/* 415 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 416 */       pd.toString(), "api/user/add_to_collect.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/getcoupon.jspx"})
/*     */   public void coupon(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 425 */     JSONObject o = new JSONObject();
/* 426 */     Date newdate = new Date();
/* 427 */     Integer pd = Integer.valueOf(0);
/* 428 */     if (id != null) {
/* 429 */       Coupon coupon = this.couponMng.findById(id);
/* 430 */       ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 431 */       if ((coupon != null) && (user != null)) {
/* 432 */         if (this.memberCouponMng.findByCoupon(user.getId(), id) != null)
/*     */         {
/* 434 */           if (newdate.before(this.couponMng.findById(id)
/* 434 */             .getCouponEndTime())) {
/* 435 */             MemberCoupon memberCoupon = new MemberCoupon();
/* 436 */             memberCoupon.setCoupon(coupon);
/* 437 */             memberCoupon.setMember(user);
/* 438 */             memberCoupon.setIsuse(Boolean.valueOf(false));
/* 439 */             coupon.setCouponCount(Integer.valueOf(coupon.getCouponCount().intValue() - 1));
/* 440 */             this.memberCouponMng.save(memberCoupon);
/* 441 */             this.couponMng.update(coupon);
/*     */ 
/* 443 */             pd = Integer.valueOf(1);
/*     */ 
/* 445 */             break label208;
/*     */           }
/*     */         }
/* 447 */         pd = Integer.valueOf(2);
/*     */       }
/*     */       else
/*     */       {
/* 451 */         pd = Integer.valueOf(0);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 456 */       pd = Integer.valueOf(0);
/*     */     }
/*     */ 
/* 459 */     label208: ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 460 */       pd.toString(), "/api/user/getcoupon.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"api/user/myCouponList.jspx"})
/*     */   public void myCouponList(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 472 */     Map map = new HashMap();
/* 473 */     String result = "00";
/* 474 */     Integer pd = Integer.valueOf(0);
/* 475 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 476 */     if (user != null) {
/* 477 */       List list = this.memberCouponMng.getList(user.getId());
/* 478 */       if ((list != null) && 
/* 479 */         (list.size() > 0))
/*     */       {
/* 481 */         map.put("pd", getMyCouponJson(list));
/*     */       }
/*     */     }
/*     */ 
/* 485 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 486 */       pd.toString(), "/api/user/myCouponList.jspx", request));
/*     */   }
/*     */ 
/*     */   private String getMyCouponJson(List<MemberCoupon> beanlist) throws JSONException
/*     */   {
/* 491 */     JSONObject o = new JSONObject();
/* 492 */     JSONArray arr = new JSONArray();
/* 493 */     for (MemberCoupon coupon : beanlist) {
/* 494 */       o.put("id", coupon.getId());
/* 495 */       o.put("name", coupon.getCoupon().getCouponName());
/* 496 */       o.put("couponPrice", coupon.getCoupon().getCouponPrice());
/* 497 */       o.put("leastPrice", coupon.getCoupon().getLeastPrice());
/* 498 */       o.put("couponBeginTime", coupon.getCoupon().getCouponTime());
/* 499 */       o.put("couponEndTime", coupon.getCoupon().getCouponEndTime());
/* 500 */       arr.put(o);
/*     */     }
/*     */ 
/* 503 */     return arr.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.UserAppAct
 * JD-Core Version:    0.6.0
 */