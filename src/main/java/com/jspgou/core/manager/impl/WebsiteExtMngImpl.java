/*    */ package com.jspgou.core.manager.impl;
/*    */ 
/*    */ import com.jspgou.core.dao.WebsiteExtDao;
/*    */ import com.jspgou.core.entity.WebsiteExt;
/*    */ import com.jspgou.core.entity.WebsiteExt.ConfigLogin;
/*    */ import com.jspgou.core.manager.WebsiteExtMng;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class WebsiteExtMngImpl
/*    */   implements WebsiteExtMng
/*    */ {
/*    */   private WebsiteExtDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Map<String, String> getMap()
/*    */   {
/* 24 */     List list = this.dao.getList();
/* 25 */     Map map = new HashMap(list.size());
/* 26 */     for (WebsiteExt websiteExt : list) {
/* 27 */       map.put(websiteExt.getId(), websiteExt.getValue());
/*    */     }
/* 29 */     return map;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public WebsiteExt.ConfigLogin getConfigLogin() {
/* 35 */     return WebsiteExt.ConfigLogin.create(getMap());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(WebsiteExtDao dao)
/*    */   {
/* 42 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.WebsiteExtMngImpl
 * JD-Core Version:    0.6.0
 */