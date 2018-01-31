/*     */ package com.jspgou.common.hibernate4;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Updater<T>
/*     */ {
/*     */   private T bean;
/* 105 */   private Set<String> includeProperties = new HashSet();
/*     */ 
/* 107 */   private Set<String> excludeProperties = new HashSet();
/*     */ 
/* 109 */   private UpdateMode mode = UpdateMode.MIDDLE;
/*     */ 
/*     */   public Updater(T bean)
/*     */   {
/*  27 */     this.bean = bean;
/*     */   }
/*     */ 
/*     */   public Updater(T bean, UpdateMode mode)
/*     */   {
/*  40 */     this.bean = bean;
/*  41 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */   public Updater<T> setUpdateMode(UpdateMode mode)
/*     */   {
/*  51 */     this.mode = mode;
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   public Updater<T> include(String property)
/*     */   {
/*  62 */     this.includeProperties.add(property);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   public Updater<T> exclude(String property)
/*     */   {
/*  73 */     this.excludeProperties.add(property);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isUpdate(String name, Object value)
/*     */   {
/*  87 */     if (this.mode == UpdateMode.MAX)
/*  88 */       return !this.excludeProperties.contains(name);
/*  89 */     if (this.mode == UpdateMode.MIN)
/*  90 */       return this.includeProperties.contains(name);
/*  91 */     if (this.mode == UpdateMode.MIDDLE) {
/*  92 */       if (value != null) {
/*  93 */         return !this.excludeProperties.contains(name);
/*     */       }
/*  95 */       return this.includeProperties.contains(name);
/*     */     }
/*     */ 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   public T getBean()
/*     */   {
/* 118 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public Set<String> getExcludeProperties() {
/* 122 */     return this.excludeProperties;
/*     */   }
/*     */ 
/*     */   public Set<String> getIncludeProperties() {
/* 126 */     return this.includeProperties;
/*     */   }
/*     */ 
/*     */   public static enum UpdateMode
/*     */   {
/* 114 */     MAX, MIN, MIDDLE;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.Updater
 * JD-Core Version:    0.6.0
 */