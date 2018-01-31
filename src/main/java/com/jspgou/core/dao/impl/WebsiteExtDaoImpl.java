/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.core.dao.WebsiteExtDao;
/*    */ import com.jspgou.core.entity.WebsiteExt;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class WebsiteExtDaoImpl extends HibernateBaseDao<WebsiteExt, String>
/*    */   implements WebsiteExtDao
/*    */ {
/*    */   public List<WebsiteExt> getList()
/*    */   {
/* 19 */     String hql = "from WebsiteExt";
/* 20 */     return find(hql, new Object[0]);
/*    */   }
/*    */ 
/*    */   protected Class<WebsiteExt> getEntityClass()
/*    */   {
/* 25 */     return WebsiteExt.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.WebsiteExtDaoImpl
 * JD-Core Version:    0.6.0
 */