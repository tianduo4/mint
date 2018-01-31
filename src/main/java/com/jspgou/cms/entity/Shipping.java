/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseShipping;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.Collection;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Shipping extends BaseShipping
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Double price;
/*     */   public static final int UNIFORM = 1;
/*     */   public static final int BY_WEIGHT = 2;
/*     */   public static final int BY_COUNTRY = 3;
/*     */ 
/*     */   public Double calPrice(Double weight)
/*     */   {
/*  40 */     int m = getMethod().intValue();
/*     */     Double p;
/*  41 */     if (m == 1) {
/*  42 */       p = getUniformPrice();
/*     */     }
/*     */     else
/*     */     {
/*     */       Double p;
/*  43 */       if (m == 2)
/*     */       {
/*     */         Double p;
/*  45 */         if (weight.doubleValue() <= 0.0D)
/*  46 */           p = Double.valueOf(0.0D);
/*     */         else
/*  48 */           p = byWeight(weight);
/*     */       }
/*     */       else {
/*  51 */         throw new RuntimeException("Shipping method not supported: " + m);
/*     */       }
/*     */     }
/*     */     Double p;
/*  53 */     return p;
/*     */   }
/*     */ 
/*     */   public static Long[] fetchIds(Collection<Shipping> shippings)
/*     */   {
/*  58 */     if (shippings == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     Long[] ids = new Long[shippings.size()];
/*  62 */     int i = 0;
/*  63 */     for (Shipping s : shippings) {
/*  64 */       ids[(i++)] = s.getId();
/*     */     }
/*  66 */     return ids;
/*     */   }
/*     */ 
/*     */   public Double byWeight(Double weight)
/*     */   {
/*  76 */     Double price = getFirstPrice();
/*  77 */     Double ap = getAdditionalPrice();
/*  78 */     int fw = getFirstWeight().intValue();
/*  79 */     int aw = getAdditionalWeight().intValue();
/*  80 */     weight = Double.valueOf(weight.doubleValue() - fw);
/*  81 */     while (weight.doubleValue() > 0.0D) {
/*  82 */       weight = Double.valueOf(weight.doubleValue() - aw);
/*  83 */       price = Double.valueOf(price.doubleValue() + ap.doubleValue());
/*     */     }
/*  85 */     return price;
/*     */   }
/*     */ 
/*     */   public JSONObject converToJson() throws JSONException {
/*  89 */     JSONObject json = new JSONObject();
/*  90 */     json.put("id", CommonUtils.parseId(getId()));
/*  91 */     json.put("name", CommonUtils.parseStr(getName()));
/*  92 */     json.put("description", CommonUtils.parseStr(getDescription()));
/*  93 */     json.put("uniformPrice", CommonUtils.parseDouble(getUniformPrice()));
/*  94 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/*  95 */     json.put("isDefault", CommonUtils.parseBoolean(getIsDefault()));
/*  96 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/*  97 */     json.put("firstWeight", CommonUtils.parseInteger(getFirstWeight()));
/*  98 */     json.put("firstPrice", CommonUtils.parseDouble(getFirstPrice()));
/*  99 */     json.put("additionalWeight", CommonUtils.parseInteger(getAdditionalWeight()));
/* 100 */     json.put("additionalPrice", CommonUtils.parseDouble(getAdditionalPrice()));
/* 101 */     json.put("logisticsType", CommonUtils.parseStr(getLogisticsType()));
/* 102 */     json.put("logisticsId", getLogistics() == null ? "" : CommonUtils.parseLong(getLogistics().getId()));
/* 103 */     json.put("method", CommonUtils.parseInteger(getMethod()));
/* 104 */     return json;
/*     */   }
/*     */ 
/*     */   public Shipping()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Shipping(Long id)
/*     */   {
/* 118 */     super(id);
/*     */   }
/*     */ 
/*     */   public Shipping(Long id, Website website, String name, Integer method, Integer priority, Boolean disabled)
/*     */   {
/* 128 */     super(id, website, name, method, priority, disabled);
/*     */   }
/*     */ 
/*     */   public Double getPrice()
/*     */   {
/* 134 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(Double price) {
/* 138 */     this.price = price;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Shipping
 * JD-Core Version:    0.6.0
 */