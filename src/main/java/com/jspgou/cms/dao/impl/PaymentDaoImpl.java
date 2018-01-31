/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PaymentDao;
/*    */ import com.jspgou.cms.entity.Payment;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class PaymentDaoImpl extends HibernateBaseDao<Payment, Long>
/*    */   implements PaymentDao
/*    */ {
/*    */   public List<Payment> getListForCart(Long webId, boolean cacheable)
/*    */   {
/* 22 */     String hql = "from Payment bean where bean.website.id=:webId and bean.disabled=false order by bean.priority";
/* 23 */     return getSession().createQuery(hql).setParameter("webId", webId)
/* 24 */       .setCacheable(cacheable).list();
/*    */   }
/*    */ 
/*    */   public List<Payment> getList(Long webId, boolean all)
/*    */   {
/* 30 */     Finder f = 
/* 31 */       Finder.create("from Payment bean where bean.website.id=:webId");
/* 32 */     f.setParam("webId", webId);
/* 33 */     if (!all)
/* 34 */       f.append(" bean.disabled=false order by bean.priority");
/*    */     else {
/* 36 */       f.append(" order by bean.disabled,bean.priority");
/*    */     }
/* 38 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<Payment> getByCode(String code, Long webId)
/*    */   {
/* 44 */     String hql = "from Payment bean where bean.website.id=? and bean.code=?";
/* 45 */     return find(hql, new Object[] { webId, code });
/*    */   }
/*    */ 
/*    */   public Payment findById(Long id)
/*    */   {
/* 50 */     Payment entity = (Payment)get(id);
/* 51 */     return entity;
/*    */   }
/*    */ 
/*    */   public Payment save(Payment bean)
/*    */   {
/* 56 */     getSession().save(bean);
/* 57 */     return bean;
/*    */   }
/*    */ 
/*    */   public Payment deleteById(Long id)
/*    */   {
/* 62 */     Payment entity = (Payment)super.get(id);
/* 63 */     if (entity != null) {
/* 64 */       getSession().delete(entity);
/*    */     }
/* 66 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Payment> getEntityClass()
/*    */   {
/* 71 */     return Payment.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.PaymentDaoImpl
 * JD-Core Version:    0.6.0
 */