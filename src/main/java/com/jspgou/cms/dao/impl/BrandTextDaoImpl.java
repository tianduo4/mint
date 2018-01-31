/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.BrandTextDao;
/*    */ import com.jspgou.cms.entity.BrandText;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class BrandTextDaoImpl extends HibernateBaseDao<BrandText, Long>
/*    */   implements BrandTextDao
/*    */ {
/*    */   public BrandText save(BrandText bean)
/*    */   {
/* 18 */     getSession().save(bean);
/* 19 */     return bean;
/*    */   }
/*    */ 
/*    */   protected Class<BrandText> getEntityClass()
/*    */   {
/* 24 */     return BrandText.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.BrandTextDaoImpl
 * JD-Core Version:    0.6.0
 */