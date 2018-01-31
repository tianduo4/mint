/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseWebserviceCallRecord;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import java.util.Date;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class WebserviceCallRecord extends BaseWebserviceCallRecord
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public WebserviceCallRecord()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord(Integer id)
/*    */   {
/* 23 */     super(id);
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord(Integer id, WebserviceAuth auth, String serviceCode, Date recordTime)
/*    */   {
/* 39 */     super(id, 
/* 37 */       auth, 
/* 38 */       serviceCode, 
/* 39 */       recordTime);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 43 */     JSONObject json = new JSONObject();
/* 44 */     json.put("id", CommonUtils.parseId(getId()));
/* 45 */     json.put("serviceCode", CommonUtils.parseStr(getServiceCode()));
/* 46 */     json.put("recordTime", CommonUtils.parseDate(getRecordTime(), DateUtils.COMMON_FORMAT_STR));
/* 47 */     json.put("username", getAuth() != null ? CommonUtils.parseStr(getAuth().getUsername()) : "");
/* 48 */     json.put("system", getAuth() != null ? CommonUtils.parseStr(getAuth().getSystem()) : "");
/* 49 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.WebserviceCallRecord
 * JD-Core Version:    0.6.0
 */