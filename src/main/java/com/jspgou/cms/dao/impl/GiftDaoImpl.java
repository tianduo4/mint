/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.GiftDao;
/*    */ import com.jspgou.cms.entity.Gift;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class GiftDaoImpl extends HibernateBaseDao<Gift, Long>
/*    */   implements GiftDao
/*    */ {
/*    */   public Pagination getPageGift(int pageNo, int pageSize)
/*    */   {
/* 20 */     Finder f = Finder.create("from Gift bean");
/* 21 */     f.append(" order by bean.id desc");
/* 22 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Gift findById(Long id)
/*    */   {
/* 28 */     Gift entity = (Gift)get(id);
/* 29 */     return entity;
/*    */   }
/*    */ 
/*    */   public Gift save(Gift bean)
/*    */   {
/* 34 */     getSession().save(bean);
/* 35 */     return bean;
/*    */   }
/*    */ 
/*    */   public Gift deleteById(Long id)
/*    */   {
/* 40 */     Gift entity = (Gift)super.get(id);
/* 41 */     if (entity != null) {
/* 42 */       getSession().delete(entity);
/*    */     }
/* 44 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Gift> getEntityClass()
/*    */   {
/* 49 */     return Gift.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.GiftDaoImpl
 * JD-Core Version:    0.6.0
 */