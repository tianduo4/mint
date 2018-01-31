/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.LogisticsTextDao;
/*    */ import com.jspgou.cms.entity.LogisticsText;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LogisticsTextDaoImpl extends HibernateBaseDao<LogisticsText, Long>
/*    */   implements LogisticsTextDao
/*    */ {
/*    */   public LogisticsText save(LogisticsText bean)
/*    */   {
/* 18 */     getSession().save(bean);
/* 19 */     return bean;
/*    */   }
/*    */ 
/*    */   protected Class<LogisticsText> getEntityClass()
/*    */   {
/* 24 */     return LogisticsText.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.LogisticsTextDaoImpl
 * JD-Core Version:    0.6.0
 */