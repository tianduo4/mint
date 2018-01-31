/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CartItemDao;
/*    */ import com.jspgou.cms.entity.CartItem;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class CartItemDaoImpl extends HibernateBaseDao<CartItem, Long>
/*    */   implements CartItemDao
/*    */ {
/*    */   public CartItem findById(Long id)
/*    */   {
/* 21 */     CartItem entity = (CartItem)get(id);
/* 22 */     return entity;
/*    */   }
/*    */ 
/*    */   public CartItem deleteById(Long id)
/*    */   {
/* 27 */     CartItem entity = (CartItem)super.get(id);
/* 28 */     if (entity != null) {
/* 29 */       getSession().delete(entity);
/*    */     }
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<CartItem> getlist(Long cartId, Long popularityGroupId)
/*    */   {
/* 36 */     Finder f = Finder.create("select bean from CartItem bean");
/* 37 */     f.append(" where bean.cart.id=:cartId and bean.popularityGroup.id=:popularityGroupId");
/* 38 */     f.setParam("cartId", cartId).setParam("popularityGroupId", popularityGroupId);
/* 39 */     f.append(" order by bean.id desc");
/* 40 */     return find(f);
/*    */   }
/*    */ 
/*    */   public int deleteByProductId(Long productId)
/*    */   {
/* 45 */     String hql = " delete CartItem bean where bean.product.id=:productId";
/* 46 */     return getSession().createQuery(hql).setParameter("productId", productId).executeUpdate();
/*    */   }
/*    */ 
/*    */   public int deleteByProductFactionId(Long productFacId)
/*    */   {
/* 51 */     String hql = " delete CartItem bean where bean.productFash.id=:productFash";
/* 52 */     return getSession().createQuery(hql).setParameter("productFash", productFacId).executeUpdate();
/*    */   }
/*    */ 
/*    */   protected Class<CartItem> getEntityClass()
/*    */   {
/* 57 */     return CartItem.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.CartItemDaoImpl
 * JD-Core Version:    0.6.0
 */