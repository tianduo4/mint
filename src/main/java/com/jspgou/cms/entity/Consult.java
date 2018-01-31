/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseConsult;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Consult extends BaseConsult
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Consult()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Consult(Long id)
/*    */   {
/* 26 */     super(id);
/*    */   }
/*    */ 
/*    */   public Consult(Long id, ShopMember member, Product product)
/*    */   {
/* 40 */     super(id, 
/* 39 */       member, 
/* 40 */       product);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 44 */     JSONObject json = new JSONObject();
/* 45 */     json.put("id", CommonUtils.parseId(getId()));
/* 46 */     json.put("consult", CommonUtils.parseStr(getConsult()));
/* 47 */     json.put("adminReplay", CommonUtils.parseStr(getAdminReplay()));
/* 48 */     json.put("time", CommonUtils.parseDate(getTime(), DateUtils.COMMON_FORMAT_STR));
/* 49 */     json.put("userName", getMember() != null ? CommonUtils.parseStr(getMember().getUsername()) : "");
/* 50 */     json.put("productName", getProduct() != null ? CommonUtils.parseStr(getProduct().getName()) : "");
/*    */ 
/* 52 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Consult
 * JD-Core Version:    0.6.0
 */