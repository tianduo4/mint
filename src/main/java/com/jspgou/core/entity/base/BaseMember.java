/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseMember
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   public static String REF = "Member";
/*  25 */   public static String PROP_WEBSITE = "website";
/*  26 */   public static String PROP_CREATE_TIME = "createTime";
/*  27 */   public static String PROP_DISABLED = "disabled";
/*  28 */   public static String PROP_USER = "user";
/*  29 */   public static String PROP_ID = "id";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Date createTime;
/*     */   private Boolean disabled;
/*     */   private Boolean active;
/*     */   private String activationCode;
/*     */   private User user;
/*     */   private Website website;
/*     */ 
/*     */   public BaseMember()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseMember(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseMember(Long id, User user, Website website, Date createTime, Boolean disabled, Boolean active, String activationCode)
/*     */   {
/*  54 */     setId(id);
/*  55 */     setUser(user);
/*  56 */     setWebsite(website);
/*  57 */     setCreateTime(createTime);
/*  58 */     setDisabled(disabled);
/*  59 */     setActive(active);
/*  60 */     setActivationCode(activationCode);
/*  61 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  82 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id) {
/*  86 */     this.id = id;
/*  87 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime() {
/*  91 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime) {
/*  95 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled() {
/*  99 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled) {
/* 103 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Boolean getActive()
/*     */   {
/* 112 */     return this.active;
/*     */   }
/*     */ 
/*     */   public void setActive(Boolean active)
/*     */   {
/* 120 */     this.active = active;
/*     */   }
/*     */ 
/*     */   public String getActivationCode()
/*     */   {
/* 127 */     return this.activationCode;
/*     */   }
/*     */ 
/*     */   public void setActivationCode(String activationCode)
/*     */   {
/* 135 */     this.activationCode = activationCode;
/*     */   }
/*     */ 
/*     */   public User getUser() {
/* 139 */     return this.user;
/*     */   }
/*     */ 
/*     */   public void setUser(User user) {
/* 143 */     this.user = user;
/*     */   }
/*     */ 
/*     */   public Website getWebsite() {
/* 147 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website) {
/* 151 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 156 */     if (obj == null) return false;
/* 157 */     if (!(obj instanceof Member)) return false;
/*     */ 
/* 159 */     Member member = (Member)obj;
/* 160 */     if ((getId() == null) || (member.getId() == null)) return false;
/* 161 */     return getId().equals(member.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 167 */     if (-2147483648 == this.hashCode) {
/* 168 */       if (getId() == null) return super.hashCode();
/* 169 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 170 */       this.hashCode = hashStr.hashCode();
/*     */     }
/* 172 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseMember
 * JD-Core Version:    0.6.0
 */