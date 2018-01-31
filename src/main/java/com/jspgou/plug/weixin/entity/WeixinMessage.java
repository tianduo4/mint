/*    */ package com.jspgou.plug.weixin.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.plug.weixin.entity.base.BaseWeixinMessage;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class WeixinMessage extends BaseWeixinMessage
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int CONTENT_ONLY = 2;
/*    */   public static final int CONTENT_WITH_KEY = 1;
/*    */   public static final int CONTENT_WITH_IMG = 0;
/*    */ 
/*    */   public Boolean getWelcome()
/*    */   {
/* 16 */     return super.isWelcome();
/*    */   }
/*    */ 
/*    */   public WeixinMessage()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WeixinMessage(Integer id)
/*    */   {
/* 28 */     super(id);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 32 */     JSONObject json = new JSONObject();
/*    */ 
/* 34 */     json.put("id", CommonUtils.parseId(getId()));
/* 35 */     json.put("number", CommonUtils.parseStr(getNumber()));
/* 36 */     json.put("title", CommonUtils.parseStr(getTitle()));
/* 37 */     json.put("path", CommonUtils.parseStr(getPath()));
/* 38 */     json.put("url", CommonUtils.parseStr(getUrl()));
/* 39 */     json.put("content", CommonUtils.parseStr(getContent()));
/* 40 */     json.put("welcome", CommonUtils.parseBoolean(getWelcome()));
/* 41 */     json.put("type", Integer.valueOf(CommonUtils.parseInteger(getType())));
/*    */ 
/* 43 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.entity.WeixinMessage
 * JD-Core Version:    0.6.0
 */