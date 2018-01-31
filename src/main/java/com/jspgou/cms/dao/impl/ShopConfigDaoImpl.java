/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopConfigDao;
/*    */ import com.jspgou.cms.entity.ShopConfig;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopConfigDaoImpl extends HibernateBaseDao<ShopConfig, Long>
/*    */   implements ShopConfigDao
/*    */ {
/*    */   public ShopConfig findById(Long id)
/*    */   {
/* 18 */     ShopConfig entity = (ShopConfig)get(id);
/* 19 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopConfig save(ShopConfig bean)
/*    */   {
/* 24 */     getSession().save(bean);
/* 25 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopConfig deleteById(Long id)
/*    */   {
/* 30 */     ShopConfig entity = (ShopConfig)super.get(id);
/* 31 */     if (entity != null) {
/* 32 */       getSession().delete(entity);
/*    */     }
/* 34 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopConfig> getEntityClass()
/*    */   {
/* 39 */     return ShopConfig.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopConfigDaoImpl
 * JD-Core Version:    0.6.0
 */