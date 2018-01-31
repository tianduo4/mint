/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ApiUserLoginDao;
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.manager.ApiUserLoginMng;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ApiUserLoginMngImpl
/*     */   implements ApiUserLoginMng
/*     */ {
/*     */   private ApiUserLoginDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng shopAdminMng;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  29 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  30 */     return page;
/*     */   }
/*     */   @Transactional(readOnly=true)
/*     */   public ApiUserLogin findById(Long id) {
/*  35 */     ApiUserLogin entity = this.dao.findById(id);
/*  36 */     return entity;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin save(ApiUserLogin bean) {
/*  40 */     this.dao.save(bean);
/*  41 */     return bean;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin update(ApiUserLogin bean) {
/*  45 */     Updater updater = new Updater(bean);
/*  46 */     ApiUserLogin entity = this.dao.updateByUpdater(updater);
/*  47 */     return entity;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin deleteById(Long id) {
/*  51 */     ApiUserLogin bean = this.dao.deleteById(id);
/*  52 */     return bean;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin[] deleteByIds(Long[] ids) {
/*  56 */     ApiUserLogin[] beans = new ApiUserLogin[ids.length];
/*  57 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  58 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  60 */     return beans;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin findBySessionKey(String sessionKey)
/*     */   {
/*  67 */     return this.dao.findBySessionKey(sessionKey);
/*     */   }
/*     */ 
/*     */   public ApiUserLogin findByUsername(String username)
/*     */   {
/*  72 */     return this.dao.findByUsername(username);
/*     */   }
/*     */ 
/*     */   public void updateLoginSuccess(String username, String sessionKey)
/*     */   {
/*  78 */     ApiUserLogin apiUserLogin = findByUsername(username);
/*  79 */     Date now = new Timestamp(System.currentTimeMillis());
/*  80 */     apiUserLogin.setLoginTime(now);
/*  81 */     apiUserLogin.setSessionKey(sessionKey);
/*  82 */     apiUserLogin.setLoginCount(Integer.valueOf(apiUserLogin.getLoginCount().intValue() + 1));
/*  83 */     update(apiUserLogin);
/*     */   }
/*     */ 
/*     */   public void saveLoginSuccess(String username, String sessionKey)
/*     */   {
/*  88 */     ApiUserLogin apiUserLogin = new ApiUserLogin();
/*  89 */     Date now = new Timestamp(System.currentTimeMillis());
/*  90 */     apiUserLogin.setLoginTime(now);
/*  91 */     apiUserLogin.setSessionKey(sessionKey);
/*  92 */     apiUserLogin.setLoginCount(Integer.valueOf(1));
/*  93 */     apiUserLogin.setUsername(username);
/*  94 */     save(apiUserLogin);
/*     */   }
/*     */   @Transactional(readOnly=true)
/*     */   public ApiUserLogin findUserLogin(String username, String sessionKey) {
/*  99 */     return this.dao.findUserLogin(username, sessionKey);
/*     */   }
/*     */ 
/*     */   public ShopAdmin findUser(String sessionKey, String aesKey, String ivKey) throws Exception {
/* 103 */     String decryptSessionKey = "";
/* 104 */     ShopAdmin user = null;
/* 105 */     if (StringUtils.isNotBlank(sessionKey))
/*     */     {
/* 107 */       decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, ivKey);
/* 108 */       ApiUserLogin apiUserLogin = findUserLogin(null, decryptSessionKey);
/* 109 */       if ((apiUserLogin != null) && (StringUtils.isNotBlank(decryptSessionKey))) {
/* 110 */         String username = apiUserLogin.getUsername();
/* 111 */         CmsThreadVariable.setApiUserLogin(apiUserLogin);
/* 112 */         if (StringUtils.isNotBlank(username)) {
/* 113 */           user = this.shopAdminMng.getByUsername(username);
/*     */         }
/*     */       }
/*     */     }
/* 117 */     return user;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Short getUserStatus(String sessionKey)
/*     */   {
/* 124 */     ApiUserLogin login = findUserLogin(null, sessionKey);
/* 125 */     if ((login != null) && (login.getActiveTime() != null) && (login.getSessionKey().equals(sessionKey))) {
/* 126 */       Date activeTime = login.getActiveTime();
/* 127 */       Date now = Calendar.getInstance().getTime();
/* 128 */       if (DateUtils.getDiffMinuteTwoDate(activeTime, now).doubleValue() <= 20.0D) {
/* 129 */         return ApiUserLogin.USER_STATUS_LOGIN;
/*     */       }
/* 131 */       return ApiUserLogin.USER_STATUS_LOGOVERTIME;
/*     */     }
/*     */ 
/* 134 */     return ApiUserLogin.USER_STATUS_LOGOUT;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin userActive(String sessionKey)
/*     */   {
/* 139 */     ApiUserLogin login = findUserLogin(null, sessionKey);
/* 140 */     if (login != null) {
/* 141 */       login.setActiveTime(Calendar.getInstance().getTime());
/*     */     }
/* 143 */     return login;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin userLogin(String username, String sessionKey) {
/* 147 */     ApiUserLogin login = findUserLogin(username, sessionKey);
/* 148 */     if (login == null) {
/* 149 */       login = new ApiUserLogin();
/* 150 */       login.setLoginTime(Calendar.getInstance().getTime());
/* 151 */       login.setActiveTime(Calendar.getInstance().getTime());
/* 152 */       login.setLoginCount(Integer.valueOf(1));
/* 153 */       login.setSessionKey(sessionKey);
/* 154 */       login.setUsername(username);
/* 155 */       login = save(login);
/*     */     } else {
/* 157 */       login.setLoginTime(Calendar.getInstance().getTime());
/* 158 */       login.setActiveTime(Calendar.getInstance().getTime());
/* 159 */       login.setLoginCount(Integer.valueOf(1 + login.getLoginCount().intValue()));
/* 160 */       login.setSessionKey(sessionKey);
/* 161 */       update(login);
/*     */     }
/* 163 */     return login;
/*     */   }
/*     */ 
/*     */   public ApiUserLogin userLogout(String username)
/*     */   {
/* 168 */     ApiUserLogin login = findUserLogin(username, null);
/* 169 */     if (login != null) {
/* 170 */       login.setSessionKey("");
/* 171 */       login.setActiveTime(null);
/* 172 */       update(login);
/*     */     }
/* 174 */     return login;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(ApiUserLoginDao dao)
/*     */   {
/* 184 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ApiUserLoginMngImpl
 * JD-Core Version:    0.6.0
 */