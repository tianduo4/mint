/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Role;
/*     */ import com.jspgou.core.entity.User;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseRole
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Role";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_SUPER = "super";
/*  23 */   public static String PROP_PRIORITY = "priority";
/*     */ 
/*  59 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private Integer priority;
/*     */   private Boolean m_super;
/*     */   private Set<String> perms;
/*     */   private Set<User> users;
/*     */ 
/*     */   public BaseRole()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseRole(Integer id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseRole(Integer id, String name, Integer priority, Boolean m_super)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setName(name);
/*  50 */     setPriority(priority);
/*  51 */     setSuper(m_super);
/*  52 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  81 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  89 */     this.id = id;
/*  90 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 100 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 108 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 116 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 124 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getSuper()
/*     */   {
/* 132 */     return this.m_super;
/*     */   }
/*     */ 
/*     */   public void setSuper(Boolean m_super)
/*     */   {
/* 140 */     this.m_super = m_super;
/*     */   }
/*     */ 
/*     */   public Set<String> getPerms()
/*     */   {
/* 148 */     return this.perms;
/*     */   }
/*     */ 
/*     */   public void setPerms(Set<String> perms)
/*     */   {
/* 156 */     this.perms = perms;
/*     */   }
/*     */ 
/*     */   public Set<User> getUsers() {
/* 160 */     return this.users;
/*     */   }
/*     */ 
/*     */   public void setUsers(Set<User> users) {
/* 164 */     this.users = users;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 169 */     if (obj == null) return false;
/* 170 */     if (!(obj instanceof Role)) return false;
/*     */ 
/* 172 */     Role role = (Role)obj;
/* 173 */     if ((getId() == null) || (role.getId() == null)) return false;
/* 174 */     return getId().equals(role.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 180 */     if (-2147483648 == this.hashCode) {
/* 181 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 183 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 184 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 187 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 193 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseRole
 * JD-Core Version:    0.6.0
 */