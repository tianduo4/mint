/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductTextDao;
/*    */ import com.jspgou.cms.entity.ProductText;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ProductTextDaoImpl extends HibernateBaseDao<ProductText, Long>
/*    */   implements ProductTextDao
/*    */ {
/*    */   public ProductText save(ProductText text)
/*    */   {
/* 18 */     getSession().save(text);
/* 19 */     return text;
/*    */   }
/*    */ 
/*    */   protected Class<ProductText> getEntityClass()
/*    */   {
/* 24 */     return ProductText.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductTextDaoImpl
 * JD-Core Version:    0.6.0
 */