/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.WebserviceAuthDao;
/*     */ import com.jspgou.cms.entity.WebserviceAuth;
/*     */ import com.jspgou.cms.manager.WebserviceAuthMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class WebserviceAuthMngImpl
/*     */   implements WebserviceAuthMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PwdEncoder pwdEncoder;
/*     */   private WebserviceAuthDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  21 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  22 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public boolean isPasswordValid(String username, String password) {
/*  28 */     WebserviceAuth auth = findByUsername(username);
/*  29 */     if ((auth != null) && (auth.getEnable())) {
/*  30 */       return this.pwdEncoder.isPasswordValid(auth.getPassword(), password);
/*     */     }
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public WebserviceAuth findByUsername(String username)
/*     */   {
/*  39 */     WebserviceAuth entity = this.dao.findByUsername(username);
/*  40 */     return entity;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public WebserviceAuth findById(Integer id) {
/*  46 */     WebserviceAuth entity = this.dao.findById(id);
/*  47 */     return entity;
/*     */   }
/*     */ 
/*     */   public WebserviceAuth save(WebserviceAuth bean)
/*     */   {
/*  52 */     this.dao.save(bean);
/*  53 */     return bean;
/*     */   }
/*     */ 
/*     */   public WebserviceAuth update(WebserviceAuth bean)
/*     */   {
/*  58 */     Updater updater = new Updater(bean);
/*  59 */     WebserviceAuth entity = this.dao.updateByUpdater(updater);
/*  60 */     return entity;
/*     */   }
/*     */ 
/*     */   public WebserviceAuth update(Integer id, String username, String password, String system, Boolean enable)
/*     */   {
/*  65 */     WebserviceAuth entity = findById(id);
/*  66 */     if (StringUtils.isNotBlank(username)) {
/*  67 */       entity.setUsername(username);
/*     */     }
/*  69 */     if (StringUtils.isNotBlank(password)) {
/*  70 */       entity.setPassword(this.pwdEncoder.encodePassword(password));
/*     */     }
/*  72 */     if (StringUtils.isNotBlank(system)) {
/*  73 */       entity.setSystem(system);
/*     */     }
/*  75 */     if (enable != null) {
/*  76 */       entity.setEnable(enable.booleanValue());
/*     */     }
/*  78 */     return entity;
/*     */   }
/*     */ 
/*     */   public WebserviceAuth deleteById(Integer id)
/*     */   {
/*  83 */     WebserviceAuth bean = this.dao.deleteById(id);
/*  84 */     return bean;
/*     */   }
/*     */ 
/*     */   public WebserviceAuth[] deleteByIds(Integer[] ids)
/*     */   {
/*  89 */     WebserviceAuth[] beans = new WebserviceAuth[ids.length];
/*  90 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  91 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  93 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(WebserviceAuthDao dao)
/*     */   {
/* 102 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.WebserviceAuthMngImpl
 * JD-Core Version:    0.6.0
 */