/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseApiInfo;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ApiInfo extends BaseApiInfo
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ApiInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ApiInfo(Long id)
/*    */   {
/* 23 */     super(id);
/*    */   }
/*    */ 
/*    */   public ApiInfo(Long id, String apiName, String apiUrl, String apiCode, boolean disabled, Integer limitCallDay, Integer callTotalCount, Integer callMonthCount, Integer callWeekCount, Integer callDayCount)
/*    */   {
/* 51 */     super(id, 
/* 43 */       apiName, 
/* 44 */       apiUrl, 
/* 45 */       apiCode, 
/* 46 */       Boolean.valueOf(disabled), 
/* 47 */       limitCallDay, 
/* 48 */       callTotalCount, 
/* 49 */       callMonthCount, 
/* 50 */       callWeekCount, 
/* 51 */       callDayCount);
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 57 */     if (getCallTotalCount() == null) {
/* 58 */       setCallTotalCount(Integer.valueOf(0));
/*    */     }
/* 60 */     if (getCallMonthCount() == null) {
/* 61 */       setCallMonthCount(Integer.valueOf(0));
/*    */     }
/* 63 */     if (getCallWeekCount() == null) {
/* 64 */       setCallWeekCount(Integer.valueOf(0));
/*    */     }
/* 66 */     if (getCallDayCount() == null)
/* 67 */       setCallDayCount(Integer.valueOf(0));
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 73 */     JSONObject json = new JSONObject();
/* 74 */     json.put("id", CommonUtils.parseId(getId()));
/* 75 */     json.put("apiName", CommonUtils.parseStr(getApiName()));
/* 76 */     json.put("apiUrl", CommonUtils.parseStr(getApiUrl()));
/* 77 */     json.put("apiCode", CommonUtils.parseStr(getApiCode()));
/* 78 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/* 79 */     json.put("limitCallDay", Integer.valueOf(CommonUtils.parseInteger(getLimitCallDay())));
/* 80 */     json.put("callTotalCount", Integer.valueOf(CommonUtils.parseInteger(getCallTotalCount())));
/* 81 */     json.put("callMonthCount", Integer.valueOf(CommonUtils.parseInteger(getCallMonthCount())));
/* 82 */     json.put("callWeekCount", Integer.valueOf(CommonUtils.parseInteger(getCallWeekCount())));
/* 83 */     json.put("callDayCount", Integer.valueOf(CommonUtils.parseInteger(getCallDayCount())));
/* 84 */     json.put("lastCallTime", CommonUtils.parseDate(getLastCallTime(), DateUtils.COMMON_FORMAT_STR));
/*    */ 
/* 86 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ApiInfo
 * JD-Core Version:    0.6.0
 */