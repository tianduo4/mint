/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseApiRecord;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import java.util.Date;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ApiRecord extends BaseApiRecord
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ApiRecord()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ApiRecord(Long id)
/*    */   {
/* 29 */     super(id);
/*    */   }
/*    */ 
/*    */   public ApiRecord(Long id, ApiAccount apiAccount, Date apiCallTime, Long callTimeStamp, String sign)
/*    */   {
/* 47 */     super(id, 
/* 44 */       apiAccount, 
/* 45 */       apiCallTime, 
/* 46 */       callTimeStamp, 
/* 47 */       sign);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 51 */     JSONObject json = new JSONObject();
/* 52 */     json.put("id", CommonUtils.parseId(getId()));
/* 53 */     json.put("apiIp", CommonUtils.parseStr(getApiIp()));
/* 54 */     json.put("apiCallTime", CommonUtils.parseDate(getApiCallTime(), DateUtils.COMMON_FORMAT_STR));
/* 55 */     json.put("sign", CommonUtils.parseStr(getSign()));
/* 56 */     json.put("appId", getApiAccount() != null ? CommonUtils.parseStr(getApiAccount().getAppId()) : "");
/* 57 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ApiRecord
 * JD-Core Version:    0.6.0
 */