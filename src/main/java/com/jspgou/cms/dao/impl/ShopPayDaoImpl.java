/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopPayDao;
/*    */ import com.jspgou.cms.entity.ShopPay;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopPayDaoImpl extends HibernateBaseDao<ShopPay, Integer>
/*    */   implements ShopPayDao
/*    */ {
/*    */   public Pagination getPageShopPay(int pageNo, int pageSize)
/*    */   {
/* 20 */     Finder f = Finder.create("from ShopPay bean");
/* 21 */     f.append(" order by bean.id desc");
/* 22 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public ShopPay findById(Integer id)
/*    */   {
/* 28 */     ShopPay entity = (ShopPay)get(id);
/* 29 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopPay save(ShopPay bean)
/*    */   {
/* 34 */     getSession().save(bean);
/* 35 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopPay deleteById(Integer id)
/*    */   {
/* 40 */     ShopPay entity = (ShopPay)super.get(id);
/* 41 */     if (entity != null) {
/* 42 */       getSession().delete(entity);
/*    */     }
/* 44 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopPay> getEntityClass()
/*    */   {
/* 49 */     return ShopPay.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopPayDaoImpl
 * JD-Core Version:    0.6.0
 */