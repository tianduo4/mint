/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopDictionaryDao;
/*    */ import com.jspgou.cms.entity.ShopDictionary;
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
/*    */ public class ShopDictionaryDaoImpl extends HibernateBaseDao<ShopDictionary, Long>
/*    */   implements ShopDictionaryDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Criteria crit = createCriteria(new Criterion[0]);
/* 23 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPage(String name, Long typeId, int pageNo, int pageSize)
/*    */   {
/* 29 */     Finder f = Finder.create("from ShopDictionary bean where 1=1");
/* 30 */     if (name != null) {
/* 31 */       f.append(" and bean.name like :name");
/* 32 */       f.setParam("name", "%" + name + "%");
/*    */     }
/* 34 */     if (typeId != null) {
/* 35 */       f.append(" and bean.shopDictionaryType.id=:typeId");
/* 36 */       f.setParam("typeId", typeId);
/*    */     }
/* 38 */     f.append(" order by bean.priority asc, bean.id asc");
/* 39 */     f.setCacheable(true);
/* 40 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public ShopDictionary findById(Long id)
/*    */   {
/* 45 */     ShopDictionary entity = (ShopDictionary)get(id);
/* 46 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<ShopDictionary> getListByType(Long typeId)
/*    */   {
/* 52 */     Finder f = Finder.create("from ShopDictionary bean where 1=1");
/* 53 */     if (typeId != null) {
/* 54 */       f.append(" and bean.shopDictionaryType.id=:typeId");
/* 55 */       f.setParam("typeId", typeId);
/*    */     }
/* 57 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ShopDictionary save(ShopDictionary bean)
/*    */   {
/* 62 */     getSession().save(bean);
/* 63 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopDictionary deleteById(Long id)
/*    */   {
/* 68 */     ShopDictionary entity = (ShopDictionary)super.get(id);
/* 69 */     if (entity != null) {
/* 70 */       getSession().delete(entity);
/*    */     }
/* 72 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopDictionary> getEntityClass()
/*    */   {
/* 77 */     return ShopDictionary.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopDictionaryDaoImpl
 * JD-Core Version:    0.6.0
 */