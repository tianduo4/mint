/*    */ package com.jspgou.plug.weixin.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.plug.weixin.entity.base.BaseWeixinMenu;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class WeixinMenu extends BaseWeixinMenu
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public WeixinMenu()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WeixinMenu(Integer id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 29 */     JSONObject json = new JSONObject();
/* 30 */     json.put("id", CommonUtils.parseId(getId()));
/* 31 */     json.put("name", CommonUtils.parseStr(getName()));
/* 32 */     json.put("type", CommonUtils.parseStr(getType()));
/* 33 */     json.put("url", CommonUtils.parseStr(getUrl()));
/* 34 */     json.put("key", CommonUtils.parseStr(getKey()));
/* 35 */     json.put("parentId", getParent() != null ? Integer.valueOf(CommonUtils.parseInteger(getParent().getId())) : "");
/* 36 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.entity.WeixinMenu
 * JD-Core Version:    0.6.0
 */