/*    */ package com.jspgou.core.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.core.entity.base.BaseGlobal;
/*    */ import java.util.Map;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Global extends BaseGlobal
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Global()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Global(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public ConfigAttr getConfigAttr()
/*    */   {
/* 31 */     ConfigAttr configAttr = new ConfigAttr(getAttr());
/* 32 */     return configAttr;
/*    */   }
/*    */ 
/*    */   public void setConfigAttr(ConfigAttr configAttr) {
/* 36 */     getAttr().putAll(configAttr.getAttr());
/*    */   }
/*    */ 
/*    */   public Boolean getQqEnable() {
/* 40 */     ConfigAttr configAttr = getConfigAttr();
/* 41 */     return configAttr.getQqEnable();
/*    */   }
/*    */ 
/*    */   public Boolean getSinaEnable() {
/* 45 */     ConfigAttr configAttr = getConfigAttr();
/* 46 */     return configAttr.getSinaEnable();
/*    */   }
/*    */ 
/*    */   public Boolean getQqWeboEnable() {
/* 50 */     ConfigAttr configAttr = getConfigAttr();
/* 51 */     return configAttr.getQqWeboEnable();
/*    */   }
/*    */ 
/*    */   public String getQqID() {
/* 55 */     ConfigAttr configAttr = getConfigAttr();
/* 56 */     return configAttr.getQqID();
/*    */   }
/*    */ 
/*    */   public String getSinaID() {
/* 60 */     ConfigAttr configAttr = getConfigAttr();
/* 61 */     return configAttr.getSinaID();
/*    */   }
/*    */ 
/*    */   public String getQqWeboID() {
/* 65 */     ConfigAttr configAttr = getConfigAttr();
/* 66 */     return configAttr.getQqWeboID();
/*    */   }
/*    */ 
/*    */   public Boolean getWeixinEnable() {
/* 70 */     ConfigAttr configAttr = getConfigAttr();
/* 71 */     return configAttr.getWeixinEnable();
/*    */   }
/*    */ 
/*    */   public String getWeixinID() {
/* 75 */     ConfigAttr configAttr = getConfigAttr();
/* 76 */     return configAttr.getWeixinID();
/*    */   }
/*    */ 
/*    */   public String getWeixinKey() {
/* 80 */     ConfigAttr configAttr = getConfigAttr();
/* 81 */     return configAttr.getWeixinKey();
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson() throws JSONException
/*    */   {
/* 86 */     JSONObject json = new JSONObject();
/* 87 */     json.put("id", CommonUtils.parseId(getId()));
/* 88 */     json.put("contextPath", CommonUtils.parseStr(getContextPath()));
/* 89 */     json.put("port", CommonUtils.parseInteger(getPort()));
/* 90 */     json.put("treaty", CommonUtils.parseStr(getTreaty()));
/* 91 */     json.put("activeScore", CommonUtils.parseInteger(getActiveScore()));
/* 92 */     json.put("stockWarning", CommonUtils.parseInteger(getStockWarning()));
/* 93 */     json.put("defImg", CommonUtils.parseStr(getDefImg()));
/* 94 */     json.put("password", CommonUtils.parseStr(getPassword()));
/* 95 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Global
 * JD-Core Version:    0.6.0
 */