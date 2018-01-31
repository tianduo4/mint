/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.entity.base.BaseProductFashion;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ProductFashion extends BaseProductFashion
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public ProductFashion()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ProductFashion(Long id)
/*     */   {
/*  33 */     super(id);
/*     */   }
/*     */ 
/*     */   public ProductFashion(Long id, Boolean m_default)
/*     */   {
/*  45 */     super(id, 
/*  45 */       m_default);
/*     */   }
/*     */ 
/*     */   public void init() {
/*  49 */     if (getSaleCount() == null) {
/*  50 */       setSaleCount(Integer.valueOf(0));
/*     */     }
/*  52 */     if (getStockCount() == null) {
/*  53 */       setStockCount(Integer.valueOf(0));
/*     */     }
/*  55 */     if (getMarketPrice() == null) {
/*  56 */       setMarketPrice(Double.valueOf(0.0D));
/*     */     }
/*  58 */     if (getSalePrice() == null) {
/*  59 */       setSalePrice(Double.valueOf(0.0D));
/*     */     }
/*  61 */     if (getCostPrice() == null) {
/*  62 */       setCostPrice(Double.valueOf(0.0D));
/*     */     }
/*     */ 
/*  65 */     setCreateTime(new Timestamp(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public List<String> getPropertysName()
/*     */   {
/*  73 */     String propertys = getFashionStyle();
/*  74 */     List t = new ArrayList();
/*  75 */     String[] c = propertys.split("@");
/*  76 */     for (int i = 0; i < c.length; i++) {
/*  77 */       if (c[i].indexOf("??") != -1) {
/*  78 */         t.add(c[i].substring(0, c[i].indexOf("??")));
/*     */       }
/*     */     }
/*  81 */     return t;
/*     */   }
/*     */   public List<String> getPropertysValue() {
/*  84 */     String propertys = getFashionStyle();
/*  85 */     List u = new ArrayList();
/*  86 */     String[] c = propertys.split("@");
/*  87 */     for (int i = 0; i < c.length; i++) {
/*  88 */       if (c[i].indexOf("??") != -1) {
/*  89 */         u.add(c[i].substring(c[i].indexOf("??") + 2));
/*     */       }
/*     */     }
/*  92 */     return u;
/*     */   }
/*     */ 
/*     */   public String getFashPic() {
/*  96 */     return getImageUrl(getFashionPic());
/*     */   }
/*     */ 
/*     */   private String getImageUrl(String img) {
/* 100 */     if (StringUtils.isBlank(img)) {
/* 101 */       return null;
/*     */     }
/* 103 */     return getProductId().getWebsite().getUploadUrl(img);
/*     */   }
/*     */ 
/*     */   public void addTostandards(Standard standard) {
/* 107 */     Set set = getStandards();
/* 108 */     if (set == null) {
/* 109 */       set = new HashSet();
/* 110 */       setStandards(set);
/*     */     }
/* 112 */     set.add(standard);
/* 113 */     standard.addToProductFashions(this);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ProductFashion
 * JD-Core Version:    0.6.0
 */