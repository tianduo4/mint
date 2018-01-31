/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShippingDao;
/*    */ import com.jspgou.cms.entity.Shipping;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShippingDaoImpl extends HibernateBaseDao<Shipping, Long>
/*    */   implements ShippingDao
/*    */ {
/*    */   public List<Shipping> getList(Long webId, boolean all, boolean cacheable)
/*    */   {
/* 22 */     Finder f = Finder.create("from Shipping bean where bean.website.id=:webId");
/* 23 */     f.setParam("webId", webId);
/* 24 */     if (!all) {
/* 25 */       f.append(" and bean.disabled=false");
/*    */     }
/* 27 */     f.append(" order by bean.priority");
/* 28 */     f.setCacheable(cacheable);
/* 29 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Shipping findById(Long id)
/*    */   {
/* 34 */     Shipping entity = (Shipping)get(id);
/* 35 */     return entity;
/*    */   }
/*    */ 
/*    */   public Shipping save(Shipping bean)
/*    */   {
/* 40 */     getSession().save(bean);
/* 41 */     return bean;
/*    */   }
/*    */ 
/*    */   public Shipping deleteById(Long id)
/*    */   {
/* 46 */     Shipping entity = (Shipping)super.get(id);
/* 47 */     if (entity != null) {
/* 48 */       getSession().delete(entity);
/*    */     }
/* 50 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Shipping> getEntityClass()
/*    */   {
/* 55 */     return Shipping.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShippingDaoImpl
 * JD-Core Version:    0.6.0
 */