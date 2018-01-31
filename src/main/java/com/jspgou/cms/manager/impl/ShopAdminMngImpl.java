/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopAdminDao;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.AdminMng;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ShopAdminMngImpl
/*     */   implements ShopAdminMng
/*     */ {
/*     */   private UserMng userMng;
/*     */   private WebsiteMng websiteMng;
/*     */   private AdminMng adminMng;
/*     */   private ShopAdminDao dao;
/*     */   private WebserviceMng webserviceMng;
/*     */   private MemberMng memberMng;
/*     */ 
/*     */   public ShopAdmin getByUserId(Long userId, Long webId)
/*     */   {
/*  35 */     Admin admin = this.adminMng.getByUserId(userId, webId);
/*  36 */     if (admin == null) {
/*  37 */       return null;
/*     */     }
/*  39 */     return findById(admin.getId());
/*     */   }
/*     */ 
/*     */   public void register(String username, String password, Boolean viewonlyAdmin, String email, String ip, Boolean disabled, Long webId, Integer[] reoles, ShopAdmin shopAdmin)
/*     */   {
/*  45 */     shopAdmin = register(username, password, viewonlyAdmin, email, ip, disabled.booleanValue(), webId, shopAdmin);
/*     */ 
/*  47 */     this.webserviceMng.callWebService("true", username, password, email, null, "addUser");
/*  48 */     this.adminMng.addRole(shopAdmin.getAdmin(), reoles);
/*     */   }
/*     */ 
/*     */   public ShopAdmin register(String username, String password, Boolean viewonlyAdmin, String email, String ip, boolean disabled, Long webId, ShopAdmin shopAdmin)
/*     */   {
/*  55 */     Website web = this.websiteMng.findById(webId);
/*  56 */     Admin admin = this.adminMng.register(username, password, email, ip, Boolean.valueOf(disabled), web, viewonlyAdmin);
/*  57 */     shopAdmin.setAdmin(admin);
/*  58 */     shopAdmin.setWebsite(web);
/*  59 */     return save(shopAdmin);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long webId, int pageNo, int pageSize) {
/*  65 */     Pagination page = this.dao.getPage(webId, pageNo, pageSize);
/*  66 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public ShopAdmin findById(Long id) {
/*  72 */     ShopAdmin entity = this.dao.findById(id);
/*  73 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopAdmin save(ShopAdmin bean)
/*     */   {
/*  78 */     this.dao.save(bean);
/*  79 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopAdmin update(ShopAdmin bean, String password, Boolean disabled, String email, Boolean viewonlyAdmin)
/*     */   {
/*  85 */     ShopAdmin entity = findById(bean.getId());
/*  86 */     Admin admin = entity.getAdmin();
/*  87 */     this.userMng.updateUser(admin.getUser().getId(), password, email);
/*  88 */     if (disabled != null) {
/*  89 */       admin.setDisabled(disabled);
/*     */     }
/*     */ 
/*  92 */     Updater updater = new Updater(bean);
/*  93 */     entity = this.dao.updateByUpdater(updater);
/*  94 */     return entity;
/*     */   }
/*     */ 
/*     */   public void update(String password, Boolean disabled, String email, Boolean viewonlyAdmin, ShopAdmin shopAdmin, Integer[] roleIds)
/*     */   {
/*  99 */     shopAdmin = update(shopAdmin, password, disabled, email, viewonlyAdmin);
/*     */ 
/* 101 */     ShopMember member = new ShopMember();
/* 102 */     member.setRealName(shopAdmin.getFirstname());
/* 103 */     this.webserviceMng.callWebService("true", shopAdmin.getUsername(), password, email, member, "updateUser");
/* 104 */     this.adminMng.updateRole(shopAdmin.getAdmin(), roleIds);
/*     */   }
/*     */ 
/*     */   public void delete(Long[] ids)
/*     */   {
/* 109 */     ShopAdmin[] beans = deleteByIds(ids);
/* 110 */     for (ShopAdmin bean : beans)
/*     */     {
/* 112 */       Map paramsValues = new HashMap();
/* 113 */       paramsValues.put("username", bean.getUsername());
/* 114 */       paramsValues.put("admin", "true");
/* 115 */       this.webserviceMng.callWebService("deleteUser", paramsValues);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ShopAdmin deleteById(Long id)
/*     */   {
/* 121 */     ShopAdmin bean = this.dao.deleteById(id);
/*     */ 
/* 125 */     if (this.memberMng.getByUsername(bean.getUsername()) == null) {
/* 126 */       this.userMng.deleteById(bean.getAdmin().getUser().getId());
/*     */     }
/* 128 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopAdmin[] deleteByIds(Long[] ids)
/*     */   {
/* 133 */     ShopAdmin[] beans = new ShopAdmin[ids.length];
/* 134 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 135 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 137 */     return beans;
/*     */   }
/*     */ 
/*     */   public ShopAdmin findByName(String name)
/*     */   {
/* 144 */     return this.dao.findByName(name);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(ShopAdminDao dao)
/*     */   {
/* 158 */     this.dao = dao;
/*     */   }
/*     */   @Autowired
/*     */   public void setAdminMng(AdminMng adminMng) {
/* 163 */     this.adminMng = adminMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 168 */     this.websiteMng = websiteMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setUserMng(UserMng userMng) {
/* 173 */     this.userMng = userMng;
/*     */   }
/*     */ 
/*     */   public ShopAdmin getByUsername(String username)
/*     */   {
/* 178 */     return this.dao.getByUsername(username);
/*     */   }
/*     */   @Autowired
/*     */   public void setWebserviceMng(WebserviceMng webserviceMng) {
/* 183 */     this.webserviceMng = webserviceMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setMemberMng(MemberMng memberMng) {
/* 188 */     this.memberMng = memberMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopAdminMngImpl
 * JD-Core Version:    0.6.0
 */