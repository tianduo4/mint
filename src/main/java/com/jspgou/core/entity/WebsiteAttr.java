/*     */ package com.jspgou.core.entity;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class WebsiteAttr
/*     */ {
/*     */   private Map<String, String> attr;
/*     */   public static final String PICTURENEW = "new_picture";
/*     */   public static final String DAYNEW = "day";
/*     */   public static final String PREVIEW = "preview";
/*     */   public static final String QQ_ENABLE = "qqEnable";
/*     */   public static final String QQ_ID = "qqID";
/*     */   public static final String QQ_KEY = "qqKey";
/*     */   public static final String SINA_ENABLE = "sinaEnable";
/*     */   public static final String SINA_ID = "sinaID";
/*     */   public static final String SINA_KEY = "sinaKey";
/*     */   public static final String QQWEBO_ENABLE = "qqWeboEnable";
/*     */   public static final String QQWEBO_ID = "qqWeboID";
/*     */   public static final String QQWEBO_KEY = "qqWeboKey";
/*     */   public static final String WEIXIN_ENABLE = "weixinEnable";
/*     */   public static final String WEIXIN_ID = "weixinID";
/*     */   public static final String WEIXIN_KEY = "weixinKey";
/*     */   public static final String SSO_ENABLE = "ssoEnable";
/*     */ 
/*     */   public WebsiteAttr()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WebsiteAttr(Map<String, String> attr)
/*     */   {
/*  13 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr()
/*     */   {
/*  19 */     if (this.attr == null) {
/*  20 */       this.attr = new HashMap();
/*     */     }
/*  22 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr) {
/*  26 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public String getPictureNew()
/*     */   {
/*  48 */     return (String)getAttr().get("new_picture");
/*     */   }
/*     */ 
/*     */   public int getDayNew() {
/*  52 */     String day = (String)getAttr().get("day");
/*  53 */     if (StringUtils.isNotBlank(day)) {
/*  54 */       return Integer.parseInt(day);
/*     */     }
/*  56 */     return 0;
/*     */   }
/*     */ 
/*     */   public Boolean getSsoEnable()
/*     */   {
/*  61 */     String enable = (String)getAttr().get("ssoEnable");
/*  62 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public void setPictureNew(String path) {
/*  66 */     getAttr().put("new_picture", path);
/*     */   }
/*     */ 
/*     */   public void setDayNew(Integer day) {
/*  70 */     getAttr().put("day", day.toString());
/*     */   }
/*     */ 
/*     */   public Boolean getPreview() {
/*  74 */     String preview = (String)getAttr().get("preview");
/*  75 */     return Boolean.valueOf(!"false".equals(preview));
/*     */   }
/*     */ 
/*     */   public void setPreview(boolean preview)
/*     */   {
/*  84 */     getAttr().put("preview", String.valueOf(preview));
/*     */   }
/*     */ 
/*     */   public Boolean getQqEnable() {
/*  88 */     String enable = (String)getAttr().get("qqEnable");
/*  89 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getQqID() {
/*  93 */     return (String)getAttr().get("qqID");
/*     */   }
/*     */ 
/*     */   public String getQqKey() {
/*  97 */     return (String)getAttr().get("qqKey");
/*     */   }
/*     */ 
/*     */   public Boolean getSinaEnable() {
/* 101 */     String enable = (String)getAttr().get("sinaEnable");
/* 102 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getSinaID() {
/* 106 */     return (String)getAttr().get("sinaID");
/*     */   }
/*     */ 
/*     */   public String getSinaKey() {
/* 110 */     return (String)getAttr().get("sinaKey");
/*     */   }
/*     */ 
/*     */   public Boolean getQqWeboEnable() {
/* 114 */     String enable = (String)getAttr().get("qqWeboEnable");
/* 115 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getQqWeboID() {
/* 119 */     return (String)getAttr().get("qqWeboID");
/*     */   }
/*     */ 
/*     */   public String getQqWeboKey() {
/* 123 */     return (String)getAttr().get("qqWeboKey");
/*     */   }
/*     */ 
/*     */   public Boolean getWeixinEnable() {
/* 127 */     String enable = (String)getAttr().get("weixinEnable");
/* 128 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getWeixinID() {
/* 132 */     return (String)getAttr().get("weixinID");
/*     */   }
/*     */ 
/*     */   public String getWeixinKey() {
/* 136 */     return (String)getAttr().get("weixinKey");
/*     */   }
/*     */ 
/*     */   public void setQqEnable(boolean enable) {
/* 140 */     getAttr().put("qqEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setQqID(String id) {
/* 144 */     getAttr().put("qqID", id);
/*     */   }
/*     */ 
/*     */   public void setQqKey(String key) {
/* 148 */     getAttr().put("qqKey", key);
/*     */   }
/*     */ 
/*     */   public void setSinaEnable(boolean enable)
/*     */   {
/* 153 */     getAttr().put("sinaEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setSinaID(String id) {
/* 157 */     getAttr().put("sinaID", id);
/*     */   }
/*     */ 
/*     */   public void setSinaKey(String key) {
/* 161 */     getAttr().put("sinaKey", key);
/*     */   }
/*     */ 
/*     */   public void setQqWeboEnable(boolean enable) {
/* 165 */     getAttr().put("qqWeboEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setQqWeboID(String id) {
/* 169 */     getAttr().put("qqWeboID", id);
/*     */   }
/*     */ 
/*     */   public void setQqWeboKey(String key) {
/* 173 */     getAttr().put("qqWeboKey", key);
/*     */   }
/*     */ 
/*     */   public void setWeixinEnable(boolean enable) {
/* 177 */     getAttr().put("weixinEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setWeixinID(String id) {
/* 181 */     getAttr().put("weixinID", id);
/*     */   }
/*     */ 
/*     */   public void setWeixinKey(String key) {
/* 185 */     getAttr().put("weixinKey", key);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.WebsiteAttr
 * JD-Core Version:    0.6.0
 */