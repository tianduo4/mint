/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.RelatedgoodsDao;
/*    */ import com.jspgou.cms.entity.Relatedgoods;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class RelatedgoodsDaoImpl extends HibernateBaseDao<Relatedgoods, Long>
/*    */   implements RelatedgoodsDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Criteria crit = createCriteria(new Criterion[0]);
/* 23 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */ 
/*    */   public List<Relatedgoods> findByIdProductId(Long productId)
/*    */   {
/* 29 */     Finder f = Finder.create("from Relatedgoods bean where 1=1");
/* 30 */     if (productId != null) {
/* 31 */       f.append(" and bean.productId=:productId");
/* 32 */       f.setParam("productId", productId);
/*    */     }
/* 34 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Relatedgoods findById(Long id)
/*    */   {
/* 39 */     Relatedgoods entity = (Relatedgoods)get(id);
/* 40 */     return entity;
/*    */   }
/*    */ 
/*    */   public Relatedgoods save(Relatedgoods bean)
/*    */   {
/* 45 */     getSession().save(bean);
/* 46 */     return bean;
/*    */   }
/*    */ 
/*    */   public Relatedgoods deleteById(Long id)
/*    */   {
/* 51 */     Relatedgoods entity = (Relatedgoods)super.get(id);
/* 52 */     if (entity != null) {
/* 53 */       getSession().delete(entity);
/*    */     }
/* 55 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Relatedgoods> getEntityClass()
/*    */   {
/* 60 */     return Relatedgoods.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.RelatedgoodsDaoImpl
 * JD-Core Version:    0.6.0
 */