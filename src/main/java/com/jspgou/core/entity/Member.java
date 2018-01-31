/*     */ package com.jspgou.core.entity;
/*     */ 
/*     */ import com.jspgou.common.security.userdetails.UserDetails;
/*     */ import com.jspgou.core.entity.base.BaseMember;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Member extends BaseMember
/*     */   implements UserDetails
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public void init()
/*     */   {
/*  15 */     if (getActive() == null) {
/*  16 */       setActive(Boolean.valueOf(true));
/*     */     }
/*  18 */     if (getCreateTime() == null)
/*  19 */       setCreateTime(new Date());
/*  20 */     if (getDisabled() == null)
/*  21 */       setDisabled(Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   public Member()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Member(Long id)
/*     */   {
/*  30 */     super(id);
/*     */   }
/*     */ 
/*     */   public Member(Long id, User user, Website website, Date createTime, Boolean disabled, Boolean active, String activationCode)
/*     */   {
/*  47 */     super(id, 
/*  42 */       user, 
/*  43 */       website, 
/*  44 */       createTime, 
/*  45 */       disabled, 
/*  46 */       active, 
/*  47 */       activationCode);
/*     */   }
/*     */ 
/*     */   public boolean isAccountNonExpired()
/*     */   {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isAccountNonLocked()
/*     */   {
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/*  63 */     return !getDisabled().booleanValue();
/*     */   }
/*     */ 
/*     */   public boolean isCredentialsNonExpired()
/*     */   {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  73 */     return getUser().getUsername();
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  77 */     return getUser().getEmail();
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/*  82 */     return getUser().getPassword();
/*     */   }
/*     */ 
/*     */   public Long getLoginCount() {
/*  86 */     return getUser().getLoginCount();
/*     */   }
/*     */ 
/*     */   public String getRegisterIp() {
/*  90 */     return getUser().getRegisterIp();
/*     */   }
/*     */ 
/*     */   public Date getLastLoginTime() {
/*  94 */     return getUser().getLastLoginTime();
/*     */   }
/*     */ 
/*     */   public String getLastLoginIp() {
/*  98 */     return getUser().getLastLoginIp();
/*     */   }
/*     */ 
/*     */   public Date getCurrentLoginTime() {
/* 102 */     return getUser().getCurrentLoginTime();
/*     */   }
/*     */ 
/*     */   public String getCurrentLoginIp() {
/* 106 */     return getUser().getCurrentLoginIp();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Member
 * JD-Core Version:    0.6.0
 */