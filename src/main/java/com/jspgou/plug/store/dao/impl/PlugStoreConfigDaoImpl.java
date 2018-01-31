/*    */ package com.jspgou.plug.store.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.store.dao.PlugStoreConfigDao;
/*    */ import com.jspgou.plug.store.entity.PlugStoreConfig;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class PlugStoreConfigDaoImpl extends HibernateBaseDao<PlugStoreConfig, Integer>
/*    */   implements PlugStoreConfigDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 16 */     Criteria crit = createCriteria(new Criterion[0]);
/* 17 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 18 */     return page;
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig findById(Integer id)
/*    */   {
/* 24 */     return (PlugStoreConfig)get(id);
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig save(PlugStoreConfig bean) {
/* 28 */     getSession().save(bean);
/* 29 */     return bean;
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig deleteById(Integer id) {
/* 33 */     PlugStoreConfig entity = (PlugStoreConfig)super.get(id);
/* 34 */     if (entity != null) {
/* 35 */       getSession().delete(entity);
/*    */     }
/* 37 */     return entity;
/*    */   }
/*    */ 
/*    */   public Class<PlugStoreConfig> getEntityClass()
/*    */   {
/* 42 */     return PlugStoreConfig.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.store.dao.impl.PlugStoreConfigDaoImpl
 * JD-Core Version:    0.6.0
 */