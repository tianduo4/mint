/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseAdvertise;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Advertise extends BaseAdvertise
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void init()
/*    */   {
/* 20 */     if (getClickCount() == null) {
/* 21 */       setClickCount(Integer.valueOf(0));
/*    */     }
/* 23 */     if (getDisplayCount() == null) {
/* 24 */       setDisplayCount(Integer.valueOf(0));
/*    */     }
/* 26 */     if (getEnabled() == null) {
/* 27 */       setEnabled(Boolean.valueOf(true));
/*    */     }
/* 29 */     if (getWeight() == null)
/* 30 */       setWeight(Integer.valueOf(1));
/*    */   }
/*    */ 
/*    */   public Advertise()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Advertise(Integer id)
/*    */   {
/* 44 */     super(id);
/*    */   }
/*    */ 
/*    */   public Advertise(Integer id, Adspace adspace)
/*    */   {
/* 56 */     super(id, 
/* 56 */       adspace);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson()
/*    */     throws JSONException
/*    */   {
/* 62 */     JSONObject json = new JSONObject();
/* 63 */     json.put("id", CommonUtils.parseId(getId()));
/* 64 */     json.put("name", CommonUtils.parseStr(getName()));
/* 65 */     json.put("weight", CommonUtils.parseInteger(getWeight()));
/* 66 */     json.put("displayCount", CommonUtils.parseInteger(getDisplayCount()));
/* 67 */     json.put("clickCount", CommonUtils.parseInteger(getClickCount()));
/* 68 */     json.put("startTime", CommonUtils.parseDate(getStartTime(), DateUtils.COMMON_FORMAT_SHORT));
/* 69 */     json.put("endTime", CommonUtils.parseDate(getEndTime(), DateUtils.COMMON_FORMAT_SHORT));
/* 70 */     json.put("enabled", CommonUtils.parseBoolean(getEnabled()));
/* 71 */     json.put("adspaceId", getAdspace() != null ? Integer.valueOf(CommonUtils.parseInteger(getAdspace().getId())) : "");
/* 72 */     if (getAttr() != null) {
/* 73 */       for (Map.Entry entry : getAttr().entrySet())
/* 74 */         json.put("attr_" + (String)entry.getKey(), entry.getValue());
/*    */     }
/*    */     else {
/* 77 */       json.put("attr_image_link", "");
/* 78 */       json.put("attr_image_url", "");
/*    */     }
/* 80 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Advertise
 * JD-Core Version:    0.6.0
 */