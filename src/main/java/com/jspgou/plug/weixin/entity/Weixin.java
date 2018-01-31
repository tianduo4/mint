/*    */ package com.jspgou.plug.weixin.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.plug.weixin.entity.base.BaseWeixin;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Weixin extends BaseWeixin
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Weixin()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Weixin(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 26 */     JSONObject json = new JSONObject();
/* 27 */     json.put("id", CommonUtils.parseId(getId()));
/* 28 */     json.put("welcome", CommonUtils.parseStr(getWelcome()));
/* 29 */     json.put("pic", CommonUtils.parseStr(getPic()));
/* 30 */     json.put("appKey", CommonUtils.parseStr(getAppKey()));
/* 31 */     json.put("appSecret", CommonUtils.parseStr(getAppSecret()));
/* 32 */     json.put("token", CommonUtils.parseStr(getToken()));
/* 33 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.entity.Weixin
 * JD-Core Version:    0.6.0
 */