/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.LogisticsTextDao;
/*    */ import com.jspgou.cms.entity.Logistics;
/*    */ import com.jspgou.cms.entity.LogisticsText;
/*    */ import com.jspgou.cms.manager.LogisticsTextMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class LogisticsTextMngImpl
/*    */   implements LogisticsTextMng
/*    */ {
/*    */   private LogisticsTextDao dao;
/*    */ 
/*    */   public LogisticsText save(Logistics logistics, String text)
/*    */   {
/* 22 */     LogisticsText bean = new LogisticsText();
/* 23 */     bean.setLogistics(logistics);
/* 24 */     bean.setText(text);
/* 25 */     this.dao.save(bean);
/* 26 */     return bean;
/*    */   }
/*    */ 
/*    */   public LogisticsText update(LogisticsText bean)
/*    */   {
/* 31 */     LogisticsText entity = this.dao.updateByUpdater(new Updater(bean));
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(LogisticsTextDao dao)
/*    */   {
/* 39 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.LogisticsTextMngImpl
 * JD-Core Version:    0.6.0
 */