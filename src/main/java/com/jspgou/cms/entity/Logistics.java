/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseLogistics;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Logistics extends BaseLogistics
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public Logistics()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Logistics(Long id)
/*     */   {
/*  30 */     super(id);
/*     */   }
/*     */ 
/*     */   public Logistics(Long id, String name, Integer priority)
/*     */   {
/*  44 */     super(id, 
/*  43 */       name, 
/*  44 */       priority);
/*     */   }
/*     */ 
/*     */   public LogisticsText getLogisticsText()
/*     */   {
/*  51 */     Set set = getLogisticsTextSet();
/*  52 */     if (set != null) {
/*  53 */       Iterator it = set.iterator();
/*  54 */       if (it.hasNext()) {
/*  55 */         return (LogisticsText)it.next();
/*     */       }
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/*  67 */     LogisticsText logisticsText = getLogisticsText();
/*  68 */     if (logisticsText != null) {
/*  69 */       return logisticsText.getText();
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson() throws JSONException {
/*  75 */     JSONObject json = new JSONObject();
/*  76 */     json.put("id", CommonUtils.parseId(getId()));
/*  77 */     json.put("name", CommonUtils.parseStr(getName()));
/*  78 */     json.put("webUrl", CommonUtils.parseStr(getWebUrl()));
/*  79 */     json.put("logoPath", CommonUtils.parseStr(getLogoPath()));
/*  80 */     json.put("priority", CommonUtils.parseInteger(getPriority()));
/*  81 */     json.put("isCourier", CommonUtils.parseBoolean(getCourier()));
/*  82 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson1() throws JSONException {
/*  86 */     JSONObject json = new JSONObject();
/*  87 */     json.put("id", CommonUtils.parseId(getId()));
/*  88 */     json.put("courier", CommonUtils.parseBoolean(getCourier()));
/*  89 */     json.put("imgUrl", CommonUtils.parseStr(getImgUrl()));
/*  90 */     json.put("evenSpacing", CommonUtils.parseInteger(getEvenSpacing()));
/*     */ 
/*  92 */     json.put("fnt", CommonUtils.parseDouble(getFnt()));
/*  93 */     json.put("fnz", CommonUtils.parseDouble(getFaz()));
/*  94 */     json.put("fnw", CommonUtils.parseDouble(getFnw()));
/*  95 */     json.put("fnh", CommonUtils.parseDouble(getFnh()));
/*  96 */     json.put("fnwh", CommonUtils.parseStr(getFnwh()));
/*     */ 
/*  98 */     json.put("fat", CommonUtils.parseDouble(getFat()));
/*  99 */     json.put("faz", CommonUtils.parseDouble(getFaz()));
/* 100 */     json.put("faw", CommonUtils.parseDouble(getFaw()));
/* 101 */     json.put("fah", CommonUtils.parseDouble(getFah()));
/* 102 */     json.put("fawh", CommonUtils.parseStr(getFawh()));
/*     */ 
/* 104 */     json.put("fpt", CommonUtils.parseDouble(getFat()));
/* 105 */     json.put("fpz", CommonUtils.parseDouble(getFaz()));
/* 106 */     json.put("fpw", CommonUtils.parseDouble(getFaw()));
/* 107 */     json.put("fph", CommonUtils.parseDouble(getFah()));
/* 108 */     json.put("fpwh", CommonUtils.parseStr(getFawh()));
/*     */ 
/* 110 */     json.put("snt", CommonUtils.parseDouble(getFat()));
/* 111 */     json.put("snz", CommonUtils.parseDouble(getFaz()));
/* 112 */     json.put("snw", CommonUtils.parseDouble(getFaw()));
/* 113 */     json.put("snh", CommonUtils.parseDouble(getFah()));
/* 114 */     json.put("snwh", CommonUtils.parseStr(getFawh()));
/*     */ 
/* 116 */     json.put("sat", CommonUtils.parseDouble(getFat()));
/* 117 */     json.put("saz", CommonUtils.parseDouble(getFaz()));
/* 118 */     json.put("saw", CommonUtils.parseDouble(getFaw()));
/* 119 */     json.put("sah", CommonUtils.parseDouble(getFah()));
/* 120 */     json.put("sawh", CommonUtils.parseStr(getFawh()));
/*     */ 
/* 122 */     json.put("spt", CommonUtils.parseDouble(getFat()));
/* 123 */     json.put("spz", CommonUtils.parseDouble(getFaz()));
/* 124 */     json.put("spw", CommonUtils.parseDouble(getFaw()));
/* 125 */     json.put("sph", CommonUtils.parseDouble(getFah()));
/* 126 */     json.put("spwh", CommonUtils.parseStr(getFawh()));
/*     */ 
/* 128 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Logistics
 * JD-Core Version:    0.6.0
 */