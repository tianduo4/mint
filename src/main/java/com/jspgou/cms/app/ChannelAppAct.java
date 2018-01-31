/*    */ package com.jspgou.cms.app;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopChannel;
/*    */ import com.jspgou.cms.manager.ShopChannelMng;
/*    */ import com.jspgou.cms.web.SiteUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class ChannelAppAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private ShopChannelMng channelMng;
/*    */ 
/*    */   @RequestMapping({"/api/channelList.jspx"})
/*    */   public void channelList(HttpServletRequest request, HttpServletResponse response)
/*    */     throws JSONException
/*    */   {
/* 40 */     Website web = SiteUtils.getWeb(request);
/* 41 */     Map map = new HashMap();
/* 42 */     String parentId = request.getParameter("parentId");
/* 43 */     String count = request.getParameter("count");
/*    */ 
/* 45 */     List list = null;
/* 46 */     String result = "00";
/* 47 */     if (StringUtils.isNotBlank(parentId)) {
/* 48 */       ShopChannel channel = this.channelMng.findById(Integer.valueOf(Integer.parseInt(parentId)));
/* 49 */       if (channel != null)
/* 50 */         list = new ArrayList(channel.getChild());
/*    */       else {
/* 52 */         list = new ArrayList();
/*    */       }
/*    */     }
/* 55 */     else if (StringUtils.isNotBlank(count)) {
/* 56 */       list = this.channelMng.getTopListForTag(web.getId(), Integer.valueOf(Integer.parseInt(count)));
/*    */     }
/*    */ 
/* 59 */     if (list != null) {
/* 60 */       if (list.size() > 0) {
/* 61 */         result = "01";
/* 62 */         map.put("pd", getShopChannelJson(list));
/*    */       } else {
/* 64 */         result = "02";
/*    */       }
/*    */     }
/* 67 */     else result = "02";
/*    */ 
/* 69 */     map.put("result", result);
/*    */   }
/*    */ 
/*    */   private String getShopChannelJson(List<ShopChannel> beanList) throws JSONException
/*    */   {
/* 74 */     JSONObject o = new JSONObject();
/* 75 */     JSONArray arr = new JSONArray();
/* 76 */     for (ShopChannel channel : beanList) {
/* 77 */       o.put("id", channel.getId());
/* 78 */       o.put("name", channel.getName());
/* 79 */       arr.put(o);
/*    */     }
/*    */ 
/* 82 */     return arr.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.ChannelAppAct
 * JD-Core Version:    0.6.0
 */