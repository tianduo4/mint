/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseAddress;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Address extends BaseAddress
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Address()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Address(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public Address(Long id, String name)
/*    */   {
/* 37 */     super(id, 
/* 37 */       name);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 43 */     JSONObject json = new JSONObject();
/* 44 */     json.put("id", CommonUtils.parseId(getId()));
/* 45 */     json.put("parentId", getParent() != null ? CommonUtils.parseLong(getParent().getId()) : "");
/* 46 */     json.put("name", CommonUtils.parseStr(getName()));
/* 47 */     json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
/* 48 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Address
 * JD-Core Version:    0.6.0
 */