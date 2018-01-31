/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseShopMemberGroup;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ShopMemberGroup extends BaseShopMemberGroup
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopMemberGroup()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup(Long id, Website website, String name, Integer score, Integer discount)
/*    */   {
/* 43 */     super(id, 
/* 40 */       website, 
/* 41 */       name, 
/* 42 */       score, 
/* 43 */       discount);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 49 */     JSONObject json = new JSONObject();
/* 50 */     json.put("id", CommonUtils.parseId(getId()));
/* 51 */     json.put("name", CommonUtils.parseStr(getName()));
/* 52 */     json.put("score", Integer.valueOf(CommonUtils.parseInteger(getScore())));
/* 53 */     json.put("discount", Integer.valueOf(CommonUtils.parseInteger(getDiscount())));
/* 54 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopMemberGroup
 * JD-Core Version:    0.6.0
 */