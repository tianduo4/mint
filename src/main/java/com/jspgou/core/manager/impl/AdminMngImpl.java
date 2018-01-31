/*     */ package com.jspgou.core.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.core.dao.AdminDao;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.AdminMng;
/*     */ import com.jspgou.core.manager.RoleMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class AdminMngImpl
/*     */   implements AdminMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceMng webserviceMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberGroupMng shopmemberGroupMng;
/*     */   private UserMng userMng;
/*     */   private AdminDao dao;
/*     */   protected RoleMng roleMng;
/*     */ 
/*     */   public Admin getByUsername(String username)
/*     */   {
/*  27 */     return this.dao.getByUsername(username);
/*     */   }
/*     */ 
/*     */   public Admin getByUserId(Long userId, Long webId)
/*     */   {
/*  32 */     return this.dao.getByUserId(userId, webId);
/*     */   }
/*     */ 
/*     */   public Admin register(String username, String password, String email, String ip, Boolean disabled, Website website, Boolean viewonlyAdmin)
/*     */   {
/*  38 */     Admin entity = new Admin();
/*  39 */     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
/*  40 */     User user = this.userMng.register(username, password, email, ip, timestamp);
/*  41 */     entity.setCreateTime(timestamp);
/*  42 */     entity.setUser(user);
/*  43 */     entity.setWebsite(website);
/*  44 */     entity.setDisabled(disabled);
/*  45 */     entity.setViewonlyAdmin(viewonlyAdmin);
/*  46 */     return save(entity);
/*     */   }
/*     */ 
/*     */   public Admin findById(Long id)
/*     */   {
/*  51 */     return this.dao.findById(id);
/*     */   }
/*     */ 
/*     */   public Admin save(Admin bean)
/*     */   {
/*  56 */     bean.init();
/*  57 */     return this.dao.save(bean);
/*     */   }
/*     */ 
/*     */   public Admin update(Admin bean)
/*     */   {
/*  62 */     return this.dao.updateByUpdater(new Updater(bean));
/*     */   }
/*     */ 
/*     */   public Admin deleteById(Long id)
/*     */   {
/*  67 */     return this.dao.deleteById(id);
/*     */   }
/*     */ 
/*     */   public Admin[] deleteByIds(Long[] ids)
/*     */   {
/*  72 */     Admin[] beans = new Admin[ids.length];
/*  73 */     for (int i = 0; i < ids.length; i++) {
/*  74 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  76 */     return beans;
/*     */   }
/*     */ 
/*     */   public void updateRole(Admin admin, Integer[] roleIds)
/*     */   {
/*  82 */     admin.getRoles().clear();
/*  83 */     if (roleIds != null)
/*  84 */       for (Integer rid : roleIds)
/*  85 */         admin.addToRoles(this.roleMng.findById(rid));
/*     */   }
/*     */ 
/*     */   public void addRole(Admin admin, Integer[] roleIds)
/*     */   {
/*  92 */     if (roleIds != null)
/*  93 */       for (Integer rid : roleIds)
/*  94 */         admin.addToRoles(this.roleMng.findById(rid));
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(AdminDao dao)
/*     */   {
/* 110 */     this.dao = dao;
/*     */   }
/*     */   @Autowired
/*     */   public void setUserMng(UserMng userMng) {
/* 115 */     this.userMng = userMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setRoleMng(RoleMng roleMng) {
/* 120 */     this.roleMng = roleMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.AdminMngImpl
 * JD-Core Version:    0.6.0
 */