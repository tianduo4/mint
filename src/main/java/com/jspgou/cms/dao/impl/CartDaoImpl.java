/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CartDao;
/*    */ import com.jspgou.cms.entity.Cart;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class CartDaoImpl extends HibernateBaseDao<Cart, Long>
/*    */   implements CartDao
/*    */ {
/*    */   public Cart findById(Long id)
/*    */   {
/* 18 */     Cart entity = (Cart)get(id);
/* 19 */     return entity;
/*    */   }
/*    */ 
/*    */   public Cart saveOrUpdate(Cart bean)
/*    */   {
/* 24 */     getSession().saveOrUpdate(bean);
/* 25 */     return bean;
/*    */   }
/*    */ 
/*    */   public Cart update(Cart bean)
/*    */   {
/* 30 */     getSession().update(bean);
/* 31 */     return bean;
/*    */   }
/*    */ 
/*    */   public Cart deleteById(Long id)
/*    */   {
/* 36 */     Cart entity = (Cart)super.get(id);
/* 37 */     if (entity != null) {
/* 38 */       getSession().delete(entity);
/*    */     }
/* 40 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Cart> getEntityClass()
/*    */   {
/* 45 */     return Cart.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.CartDaoImpl
 * JD-Core Version:    0.6.0
 */