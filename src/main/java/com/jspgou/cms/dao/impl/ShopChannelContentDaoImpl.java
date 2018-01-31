/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopChannelContentDao;
/*    */ import com.jspgou.cms.entity.ShopChannelContent;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopChannelContentDaoImpl extends HibernateBaseDao<ShopChannelContent, Long>
/*    */   implements ShopChannelContentDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 19 */     Criteria crit = createCriteria(new Criterion[0]);
/* 20 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 21 */     return page;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent findById(Long id)
/*    */   {
/* 26 */     ShopChannelContent entity = (ShopChannelContent)get(id);
/* 27 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent save(ShopChannelContent bean)
/*    */   {
/* 32 */     getSession().save(bean);
/* 33 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent deleteById(Long id)
/*    */   {
/* 38 */     ShopChannelContent entity = (ShopChannelContent)super.get(id);
/* 39 */     if (entity != null) {
/* 40 */       getSession().delete(entity);
/*    */     }
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopChannelContent> getEntityClass()
/*    */   {
/* 47 */     return ShopChannelContent.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopChannelContentDaoImpl
 * JD-Core Version:    0.6.0
 */