/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopShipmentsDao;
/*    */ import com.jspgou.cms.entity.ShopShipments;
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
/*    */ public class ShopShipmentsDaoImpl extends HibernateBaseDao<ShopShipments, Long>
/*    */   implements ShopShipmentsDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 17 */     Criteria crit = createCriteria(new Criterion[0]);
/* 18 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/*    */ 
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public List<ShopShipments> getList(Boolean isDefault) {
/* 24 */     Finder f = Finder.create("from ShopShipments bean where 1=1");
/* 25 */     if (isDefault != null) {
/* 26 */       f.append(" and bean.isDefault=:isDefault");
/* 27 */       f.setParam("isDefault", isDefault);
/*    */     }
/* 29 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ShopShipments findById(Long id) {
/* 33 */     ShopShipments entity = (ShopShipments)get(id);
/* 34 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopShipments save(ShopShipments bean) {
/* 38 */     getSession().save(bean);
/* 39 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopShipments deleteById(Long id) {
/* 43 */     ShopShipments entity = (ShopShipments)super.get(id);
/* 44 */     if (entity != null) {
/* 45 */       getSession().delete(entity);
/*    */     }
/* 47 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopShipments> getEntityClass()
/*    */   {
/* 52 */     return ShopShipments.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopShipmentsDaoImpl
 * JD-Core Version:    0.6.0
 */