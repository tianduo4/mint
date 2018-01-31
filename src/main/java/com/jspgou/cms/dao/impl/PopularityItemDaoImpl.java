/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PopularityItemDao;
/*    */ import com.jspgou.cms.entity.PopularityItem;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class PopularityItemDaoImpl extends HibernateBaseDao<PopularityItem, Long>
/*    */   implements PopularityItemDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 19 */     Criteria crit = createCriteria(new Criterion[0]);
/* 20 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 21 */     return page;
/*    */   }
/*    */ 
/*    */   public List<PopularityItem> getlist(Long cartId, Long popularityGroupId)
/*    */   {
/* 26 */     Finder f = Finder.create("select bean from PopularityItem bean where 1=1 ");
/* 27 */     if (cartId != null) {
/* 28 */       f.append(" and bean.cart.id=:cartId");
/* 29 */       f.setParam("cartId", cartId);
/*    */     }
/* 31 */     if (popularityGroupId != null) {
/* 32 */       f.append(" and bean.popularityGroup.id=:popularityGroupId");
/* 33 */       f.setParam("popularityGroupId", popularityGroupId);
/*    */     }
/*    */ 
/* 36 */     return find(f);
/*    */   }
/*    */ 
/*    */   public PopularityItem findById(Long cartId, Long popularityId)
/*    */   {
/* 41 */     String hql = "from PopularityItem bean where bean.cart.id=? and bean.popularityGroup.id=?";
/* 42 */     Query query = getSession().createQuery(hql);
/* 43 */     query.setParameter(0, cartId).setParameter(1, popularityId);
/*    */ 
/* 45 */     query.setMaxResults(1);
/* 46 */     return (PopularityItem)query.setCacheable(true).uniqueResult();
/*    */   }
/*    */ 
/*    */   public PopularityItem findById(Long id)
/*    */   {
/* 51 */     PopularityItem entity = (PopularityItem)get(id);
/* 52 */     return entity;
/*    */   }
/*    */ 
/*    */   public PopularityItem save(PopularityItem bean)
/*    */   {
/* 57 */     getSession().save(bean);
/* 58 */     return bean;
/*    */   }
/*    */ 
/*    */   public PopularityItem deleteById(Long id)
/*    */   {
/* 63 */     PopularityItem entity = (PopularityItem)super.get(id);
/* 64 */     if (entity != null) {
/* 65 */       getSession().delete(entity);
/*    */     }
/* 67 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<PopularityItem> getEntityClass()
/*    */   {
/* 72 */     return PopularityItem.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.PopularityItemDaoImpl
 * JD-Core Version:    0.6.0
 */