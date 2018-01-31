/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopDictionaryTypeDao;
/*    */ import com.jspgou.cms.entity.ShopDictionaryType;
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
/*    */ public class ShopDictionaryTypeDaoImpl extends HibernateBaseDao<ShopDictionaryType, Long>
/*    */   implements ShopDictionaryTypeDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 23 */     Criteria crit = createCriteria(new Criterion[0]);
/* 24 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 25 */     return page;
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType findById(Long id)
/*    */   {
/* 30 */     ShopDictionaryType entity = (ShopDictionaryType)get(id);
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<ShopDictionaryType> findAll()
/*    */   {
/* 37 */     Finder f = Finder.create("from ShopDictionaryType bean ");
/* 38 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType save(ShopDictionaryType bean)
/*    */   {
/* 43 */     getSession().save(bean);
/* 44 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType deleteById(Long id)
/*    */   {
/* 49 */     ShopDictionaryType entity = (ShopDictionaryType)super.get(id);
/* 50 */     if (entity != null) {
/* 51 */       getSession().delete(entity);
/*    */     }
/* 53 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopDictionaryType> getEntityClass()
/*    */   {
/* 58 */     return ShopDictionaryType.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopDictionaryTypeDaoImpl
 * JD-Core Version:    0.6.0
 */