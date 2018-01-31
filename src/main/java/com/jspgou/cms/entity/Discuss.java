/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseDiscuss;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Discuss extends BaseDiscuss
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Discuss()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Discuss(Long id)
/*    */   {
/* 26 */     super(id);
/*    */   }
/*    */ 
/*    */   public Discuss(Long id, ShopMember member, Product product, String content)
/*    */   {
/* 42 */     super(id, 
/* 40 */       member, 
/* 41 */       product, 
/* 42 */       content);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 46 */     JSONObject json = new JSONObject();
/* 47 */     json.put("id", CommonUtils.parseId(getId()));
/* 48 */     json.put("content", CommonUtils.parseStr(getContent()));
/* 49 */     json.put("time", CommonUtils.parseDate(getTime(), DateUtils.COMMON_FORMAT_STR));
/* 50 */     json.put("replay", CommonUtils.parseStr(getReplay()));
/* 51 */     json.put("discussType", CommonUtils.parseStr(getDiscussType()));
/* 52 */     json.put("userName", getMember() != null ? CommonUtils.parseStr(getMember().getUsername()) : "");
/* 53 */     json.put("productName", getProduct() != null ? CommonUtils.parseStr(getProduct().getName()) : "");
/* 54 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Discuss
 * JD-Core Version:    0.6.0
 */