/*     */ package com.jspgou.common.hibernate3;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Updater<T>
/*     */ {
/*     */   private T bean;
/* 107 */   private Set<String> includeProperties = new HashSet();
/*     */ 
/* 109 */   private Set<String> excludeProperties = new HashSet();
/*     */ 
/* 111 */   private UpdateMode mode = UpdateMode.MIDDLE;
/*     */ 
/*     */   public Updater(T bean)
/*     */   {
/*  29 */     this.bean = bean;
/*     */   }
/*     */ 
/*     */   public Updater(T bean, UpdateMode mode)
/*     */   {
/*  42 */     this.bean = bean;
/*  43 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */   public Updater<T> setUpdateMode(UpdateMode mode)
/*     */   {
/*  53 */     this.mode = mode;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public Updater<T> include(String property)
/*     */   {
/*  64 */     this.includeProperties.add(property);
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public Updater<T> exclude(String property)
/*     */   {
/*  75 */     this.excludeProperties.add(property);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isUpdate(String name, Object value)
/*     */   {
/*  89 */     if (this.mode == UpdateMode.MAX)
/*  90 */       return !this.excludeProperties.contains(name);
/*  91 */     if (this.mode == UpdateMode.MIN)
/*  92 */       return this.includeProperties.contains(name);
/*  93 */     if (this.mode == UpdateMode.MIDDLE) {
/*  94 */       if (value != null) {
/*  95 */         return !this.excludeProperties.contains(name);
/*     */       }
/*  97 */       return this.includeProperties.contains(name);
/*     */     }
/*     */ 
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   public T getBean()
/*     */   {
/* 120 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public Set<String> getExcludeProperties() {
/* 124 */     return this.excludeProperties;
/*     */   }
/*     */ 
/*     */   public Set<String> getIncludeProperties() {
/* 128 */     return this.includeProperties;
/*     */   }
/*     */ 
/*     */   public static enum UpdateMode
/*     */   {
/* 116 */     MAX, MIN, MIDDLE;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate3.Updater
 * JD-Core Version:    0.6.0
 */