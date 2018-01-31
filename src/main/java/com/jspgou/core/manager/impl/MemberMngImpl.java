/*     */ package com.jspgou.core.manager.impl;
/*     */ 
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.security.UsernameNotFoundException;
/*     */ import com.jspgou.common.security.userdetails.UserDetails;
/*     */ import com.jspgou.common.security.userdetails.UserDetailsService;
/*     */ import com.jspgou.core.dao.MemberDao;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.MemberMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import java.sql.Timestamp;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class MemberMngImpl
/*     */   implements MemberMng, UserDetailsService
/*     */ {
/*     */   private UserMng userMng;
/*     */   private MemberDao dao;
/*     */ 
/*     */   public UserDetails loadUser(Long id, String s)
/*     */     throws UsernameNotFoundException
/*     */   {
/*  28 */     Member member = findById(id);
/*  29 */     if (member == null) {
/*  30 */       throw new UsernameNotFoundException("member id not found '" + id + "'");
/*     */     }
/*  32 */     return member;
/*     */   }
/*     */ 
/*     */   public Member getByUsername(Long webId, String username)
/*     */   {
/*  38 */     User entity = this.userMng.getByUsername(username);
/*  39 */     if (entity == null) {
/*  40 */       return null;
/*     */     }
/*  42 */     return getByUserId(webId, entity.getId());
/*     */   }
/*     */ 
/*     */   public Member getByUsername(String username)
/*     */   {
/*  48 */     return this.dao.getByUsername(username);
/*     */   }
/*     */ 
/*     */   public Member getByUserIdAndActive(String activationCode, Long userId)
/*     */   {
/*  53 */     return this.dao.getByUserIdAndActive(activationCode, userId);
/*     */   }
/*     */ 
/*     */   public Member getByUserId(Long webId, Long userId)
/*     */   {
/*  58 */     return this.dao.getByUserId(webId, userId);
/*     */   }
/*     */ 
/*     */   public Member register(String username, String password, String email, Boolean active, String activationCode, String ip, Boolean disabled, Website website)
/*     */   {
/*  64 */     Member member = new Member();
/*  65 */     Timestamp createtime = new Timestamp(System.currentTimeMillis());
/*  66 */     User user = this.userMng.register(username, password, email, ip, createtime);
/*  67 */     member.setCreateTime(createtime);
/*  68 */     member.setUser(user);
/*  69 */     member.setWebsite(website);
/*  70 */     member.setDisabled(disabled);
/*  71 */     member.setActive(active);
/*  72 */     member.setActivationCode(activationCode);
/*  73 */     return save(member);
/*     */   }
/*     */ 
/*     */   public Member join(String username, Website website)
/*     */   {
/*  78 */     User entity = this.userMng.getByUsername(username);
/*  79 */     if (entity == null) {
/*  80 */       throw new IllegalStateException("User not found: " + username);
/*     */     }
/*  82 */     return join(entity, website);
/*     */   }
/*     */ 
/*     */   public Member join(User user, Website website)
/*     */   {
/*  88 */     Member entity = getByUserId(website.getId(), user.getId());
/*  89 */     if (entity != null) {
/*  90 */       return entity;
/*     */     }
/*  92 */     Member member = new Member();
/*  93 */     member.setUser(user);
/*  94 */     member.setWebsite(website);
/*  95 */     return save(member);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/* 101 */     return this.dao.getPage(pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Member findById(Long id)
/*     */   {
/* 106 */     return this.dao.findById(id);
/*     */   }
/*     */ 
/*     */   public Member save(Member bean)
/*     */   {
/* 111 */     bean.init();
/* 112 */     return this.dao.save(bean);
/*     */   }
/*     */ 
/*     */   public Member update(Member bean)
/*     */   {
/* 118 */     return this.dao.updateByUpdater(new Updater(bean));
/*     */   }
/*     */ 
/*     */   public Member deleteById(Long id)
/*     */   {
/* 123 */     return this.dao.deleteById(id);
/*     */   }
/*     */ 
/*     */   public Member[] deleteByIds(Long[] ids)
/*     */   {
/* 128 */     Member[] beans = new Member[ids.length];
/* 129 */     for (int i = 0; i < ids.length; i++) {
/* 130 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 132 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(MemberDao dao)
/*     */   {
/* 140 */     this.dao = dao;
/*     */   }
/*     */   @Autowired
/*     */   public void setUserMng(UserMng userMng) {
/* 145 */     this.userMng = userMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.MemberMngImpl
 * JD-Core Version:    0.6.0
 */