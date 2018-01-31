/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseCustomerService;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class CustomerService extends BaseCustomerService
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public CustomerService()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CustomerService(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public CustomerService(Long id, String name, String type, String content, Integer priority, Boolean disable)
/*    */   {
/* 45 */     super(id, 
/* 41 */       name, 
/* 42 */       type, 
/* 43 */       content, 
/* 44 */       priority, 
/* 45 */       disable);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 51 */     JSONObject json = new JSONObject();
/* 52 */     json.put("id", CommonUtils.parseId(getId()));
/* 53 */     json.put("name", CommonUtils.parseStr(getName()));
/* 54 */     json.put("type", CommonUtils.parseStr(getType()));
/* 55 */     json.put("content", CommonUtils.parseStr(getContent()));
/* 56 */     json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
/* 57 */     json.put("disable", CommonUtils.parseBoolean(getDisable()));
/* 58 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.CustomerService
 * JD-Core Version:    0.6.0
 */