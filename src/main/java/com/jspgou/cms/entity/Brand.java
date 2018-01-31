/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseBrand;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Brand extends BaseBrand
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public String getText()
/*     */   {
/*  27 */     BrandText brandText = getBrandText();
/*  28 */     if (brandText != null) {
/*  29 */       return brandText.getText();
/*     */     }
/*  31 */     return null;
/*     */   }
/*     */ 
/*     */   public BrandText getBrandText() {
/*  35 */     Set set = getBrandTextSet();
/*  36 */     if (set != null) {
/*  37 */       Iterator it = set.iterator();
/*  38 */       if (it.hasNext()) {
/*  39 */         return (BrandText)it.next();
/*     */       }
/*     */     }
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   public void removeFromCategorys(Category category)
/*     */   {
/*  55 */     Set set = getCategorys();
/*  56 */     if (set != null)
/*  57 */       set.remove(category);
/*     */   }
/*     */ 
/*     */   public void addToCategory(Category category)
/*     */   {
/*  67 */     Set set = getCategorys();
/*  68 */     if (set == null) {
/*  69 */       set = new HashSet();
/*  70 */       setCategorys(set);
/*     */     }
/*  72 */     set.add(category);
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson() throws JSONException {
/*  76 */     JSONObject json = new JSONObject();
/*  77 */     json.put("id", CommonUtils.parseId(getId()));
/*  78 */     json.put("name", CommonUtils.parseStr(getName()));
/*  79 */     json.put("alias", CommonUtils.parseStr(getAlias()));
/*  80 */     json.put("categoryName", getCategory() == null ? "" : CommonUtils.parseStr(getCategory().getName()));
/*  81 */     json.put("categoryId", getCategory() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getCategory().getId())));
/*  82 */     json.put("webUrl", CommonUtils.parseStr(getWebUrl()));
/*  83 */     json.put("text", getBrandText() == null ? "" : CommonUtils.parseStr(getText()));
/*  84 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/*  85 */     json.put("sift", CommonUtils.parseBoolean(getSift()));
/*  86 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/*  87 */     json.put("logoPath", CommonUtils.parseStr(getLogoPath()));
/*  88 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson1() throws JSONException {
/*  92 */     JSONObject json = new JSONObject();
/*  93 */     json.put("brandId", CommonUtils.parseId(getId()));
/*  94 */     json.put("brandName", CommonUtils.parseStr(getName()));
/*  95 */     return json;
/*     */   }
/*     */ 
/*     */   public Brand()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Brand(Long id)
/*     */   {
/* 109 */     super(id);
/*     */   }
/*     */ 
/*     */   public Brand(Long id, Website website, Category category, String name, Integer priority)
/*     */   {
/* 127 */     super(id, 
/* 124 */       website, 
/* 125 */       category, 
/* 126 */       name, 
/* 127 */       priority);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Brand
 * JD-Core Version:    0.6.0
 */