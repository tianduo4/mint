/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseApiAccount;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ApiAccount extends BaseApiAccount
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ApiAccount()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ApiAccount(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public ApiAccount(Long id, String appId, String appKey, String aesKey, boolean disabled)
/*    */   {
/* 40 */     super(id, 
/* 37 */       appId, 
/* 38 */       appKey, 
/* 39 */       aesKey, 
/* 40 */       disabled);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 47 */     JSONObject json = new JSONObject();
/* 48 */     json.put("id", CommonUtils.parseId(getId()));
/* 49 */     json.put("acount", CommonUtils.parseStr(getAppId()));
/* 50 */     json.put("appKey", CommonUtils.parseStr(getAppKey()));
/* 51 */     json.put("aesKey", CommonUtils.parseStr(getAesKey()));
/* 52 */     json.put("disabled", CommonUtils.parseBoolean(Boolean.valueOf(getDisabled())));
/* 53 */     json.put("ivKey", CommonUtils.parseStr(getIvKey()));
/* 54 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ApiAccount
 * JD-Core Version:    0.6.0
 */