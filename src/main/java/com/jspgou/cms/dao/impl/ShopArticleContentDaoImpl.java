/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopArticleContentDao;
/*    */ import com.jspgou.cms.entity.ShopArticleContent;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopArticleContentDaoImpl extends HibernateBaseDao<ShopArticleContent, Long>
/*    */   implements ShopArticleContentDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 20 */     Criteria crit = createCriteria(new Criterion[0]);
/* 21 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 22 */     return page;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent findById(Long id)
/*    */   {
/* 27 */     ShopArticleContent entity = (ShopArticleContent)get(id);
/* 28 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent save(ShopArticleContent bean)
/*    */   {
/* 33 */     getSession().save(bean);
/* 34 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent deleteById(Long id)
/*    */   {
/* 39 */     ShopArticleContent entity = (ShopArticleContent)super.get(id);
/* 40 */     if (entity != null) {
/* 41 */       getSession().delete(entity);
/*    */     }
/* 43 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopArticleContent> getEntityClass()
/*    */   {
/* 48 */     return ShopArticleContent.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopArticleContentDaoImpl
 * JD-Core Version:    0.6.0
 */