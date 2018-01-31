/*     */ package com.jspgou.core.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ConfigAttr
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
/*     */   public static final String FLOW_SWITCH = "flowSwitch";
/*     */   public static final String CONTENT_FRESH_MINUTE = "contentFreshMinute";
/*     */   public static final String CODE_IMG_WIDTH = "codeImgWidth";
/*     */   public static final String CODE_IMG_HEIGHT = "codeImgHeight";
/*     */ 
/*     */   public ConfigAttr()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ConfigAttr(Map<String, String> attr)
/*     */   {
/*  17 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr()
/*     */   {
/*  23 */     if (this.attr == null) {
/*  24 */       this.attr = new HashMap();
/*     */     }
/*  26 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr) {
/*  30 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public String getPictureNew()
/*     */   {
/*  56 */     return (String)getAttr().get("new_picture");
/*     */   }
/*     */ 
/*     */   public int getDayNew() {
/*  60 */     String day = (String)getAttr().get("day");
/*  61 */     if (StringUtils.isNotBlank(day)) {
/*  62 */       return Integer.parseInt(day);
/*     */     }
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */   public Boolean getSsoEnable()
/*     */   {
/*  69 */     String enable = (String)getAttr().get("ssoEnable");
/*  70 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public Boolean getFlowSwitch() {
/*  74 */     String flowSwitch = (String)getAttr().get("flowSwitch");
/*  75 */     return Boolean.valueOf(!"false".equals(flowSwitch));
/*     */   }
/*     */ 
/*     */   public void setPictureNew(String path)
/*     */   {
/*  80 */     getAttr().put("new_picture", path);
/*     */   }
/*     */ 
/*     */   public void setDayNew(Integer day) {
/*  84 */     getAttr().put("day", day.toString());
/*     */   }
/*     */ 
/*     */   public Boolean getPreview() {
/*  88 */     String preview = (String)getAttr().get("preview");
/*  89 */     return Boolean.valueOf(!"false".equals(preview));
/*     */   }
/*     */ 
/*     */   public void setPreview(boolean preview)
/*     */   {
/*  98 */     getAttr().put("preview", String.valueOf(preview));
/*     */   }
/*     */ 
/*     */   public Boolean getQqEnable() {
/* 102 */     String enable = (String)getAttr().get("qqEnable");
/* 103 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getQqID()
/*     */   {
/* 108 */     return (String)getAttr().get("qqID");
/*     */   }
/*     */ 
/*     */   public String getQqKey() {
/* 112 */     return (String)getAttr().get("qqKey");
/*     */   }
/*     */ 
/*     */   public Boolean getSinaEnable() {
/* 116 */     String enable = (String)getAttr().get("sinaEnable");
/* 117 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getSinaID() {
/* 121 */     return (String)getAttr().get("sinaID");
/*     */   }
/*     */ 
/*     */   public String getSinaKey() {
/* 125 */     return (String)getAttr().get("sinaKey");
/*     */   }
/*     */ 
/*     */   public Boolean getQqWeboEnable() {
/* 129 */     String enable = (String)getAttr().get("qqWeboEnable");
/* 130 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getQqWeboID() {
/* 134 */     return (String)getAttr().get("qqWeboID");
/*     */   }
/*     */ 
/*     */   public String getQqWeboKey() {
/* 138 */     return (String)getAttr().get("qqWeboKey");
/*     */   }
/*     */ 
/*     */   public Boolean getWeixinEnable() {
/* 142 */     String enable = (String)getAttr().get("weixinEnable");
/* 143 */     return Boolean.valueOf(!"false".equals(enable));
/*     */   }
/*     */ 
/*     */   public String getWeixinID() {
/* 147 */     return (String)getAttr().get("weixinID");
/*     */   }
/*     */ 
/*     */   public Integer getContentFreshMinute()
/*     */   {
/* 152 */     return Integer.valueOf(Integer.parseInt((String)getAttr().get("contentFreshMinute")));
/*     */   }
/*     */ 
/*     */   public String getWeixinKey() {
/* 156 */     return (String)getAttr().get("weixinKey");
/*     */   }
/*     */ 
/*     */   public void setQqEnable(boolean enable) {
/* 160 */     getAttr().put("qqEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setQqID(String id) {
/* 164 */     getAttr().put("qqID", id);
/*     */   }
/*     */ 
/*     */   public void setQqKey(String key) {
/* 168 */     getAttr().put("qqKey", key);
/*     */   }
/*     */ 
/*     */   public void setSinaEnable(boolean enable)
/*     */   {
/* 173 */     getAttr().put("sinaEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setFlowSwitch(boolean flowSwitch) {
/* 177 */     getAttr().put("flowSwitch", String.valueOf(flowSwitch));
/*     */   }
/*     */ 
/*     */   public void setContentFreshMinute(Integer minute) {
/* 181 */     getAttr().put("contentFreshMinute", String.valueOf(minute));
/*     */   }
/*     */ 
/*     */   public void setSinaID(String id) {
/* 185 */     getAttr().put("sinaID", id);
/*     */   }
/*     */ 
/*     */   public void setSinaKey(String key) {
/* 189 */     getAttr().put("sinaKey", key);
/*     */   }
/*     */ 
/*     */   public void setQqWeboEnable(boolean enable) {
/* 193 */     getAttr().put("qqWeboEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setQqWeboID(String id) {
/* 197 */     getAttr().put("qqWeboID", id);
/*     */   }
/*     */ 
/*     */   public void setQqWeboKey(String key) {
/* 201 */     getAttr().put("qqWeboKey", key);
/*     */   }
/*     */ 
/*     */   public void setWeixinEnable(boolean enable) {
/* 205 */     getAttr().put("weixinEnable", String.valueOf(enable));
/*     */   }
/*     */ 
/*     */   public void setWeixinID(String id) {
/* 209 */     getAttr().put("weixinID", id);
/*     */   }
/*     */ 
/*     */   public void setWeixinKey(String key) {
/* 213 */     getAttr().put("weixinKey", key);
/*     */   }
/*     */ 
/*     */   public int getCodeImgWidth() {
/* 217 */     String width = (String)getAttr().get("codeImgWidth");
/* 218 */     if (StringUtils.isNotBlank(width)) {
/* 219 */       return Integer.parseInt(width);
/*     */     }
/* 221 */     return 100;
/*     */   }
/*     */ 
/*     */   public void setCodeImgWidth(Integer width)
/*     */   {
/* 226 */     getAttr().put("codeImgWidth", width.toString());
/*     */   }
/*     */ 
/*     */   public int getCodeImgHeight() {
/* 230 */     String height = (String)getAttr().get("codeImgHeight");
/* 231 */     if (StringUtils.isNotBlank(height)) {
/* 232 */       return Integer.parseInt(height);
/*     */     }
/* 234 */     return 100;
/*     */   }
/*     */ 
/*     */   public void setCodeImgHeight(Integer height)
/*     */   {
/* 239 */     getAttr().put("codeImgHeight", height.toString());
/*     */   }
/*     */ 
/*     */   public JSONObject converToJson() {
/* 243 */     JSONObject json = new JSONObject();
/* 244 */     json.put("sinaKey", "");
/* 245 */     json.put("qqKey", "");
/* 246 */     json.put("weixinKey", "");
/* 247 */     json.put("weixinEnable", CommonUtils.parseBoolean(getWeixinEnable()));
/* 248 */     json.put("weixinID", CommonUtils.parseStr(getWeixinID()));
/* 249 */     json.put("sinaEnable", CommonUtils.parseBoolean(getSinaEnable()));
/* 250 */     json.put("qqEnable", CommonUtils.parseBoolean(getQqEnable()));
/* 251 */     json.put("sinaID", CommonUtils.parseStr(getSinaID()));
/* 252 */     json.put("qqID", CommonUtils.parseStr(getQqID()));
/* 253 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.ConfigAttr
 * JD-Core Version:    0.6.0
 */