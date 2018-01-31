/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.MemberCouponDao;
/*    */ import com.jspgou.cms.entity.MemberCoupon;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class MemberCouponDaoImpl extends HibernateBaseDao<MemberCoupon, Long>
/*    */   implements MemberCouponDao
/*    */ {
/*    */   public MemberCoupon findByCoupon(Long memberId, Long couponId)
/*    */   {
/* 25 */     String hql = "from MemberCoupon bean where bean.member.id=? and bean.coupon.id=?";
/* 26 */     Query query = getSession().createQuery(hql);
/* 27 */     query.setParameter(0, memberId).setParameter(1, couponId);
/*    */ 
/* 29 */     query.setMaxResults(1);
/* 30 */     return (MemberCoupon)query.setCacheable(true).uniqueResult();
/*    */   }
/*    */ 
/*    */   public List<MemberCoupon> getList(Long memberId, Date newTime, BigDecimal price)
/*    */   {
/* 36 */     Finder f = Finder.create("select bean from MemberCoupon bean where bean.isuse=false");
/* 37 */     if (memberId != null) {
/* 38 */       f.append(" and bean.member.id=:id");
/* 39 */       f.setParam("id", memberId);
/*    */     }
/* 41 */     if (newTime != null) {
/* 42 */       f.append(" and bean.coupon.isusing=true");
/* 43 */       f.append(" and bean.coupon.couponEndTime>:newTime");
/* 44 */       f.append(" and bean.coupon.couponTime<:newTime");
/* 45 */       f.setParam("newTime", newTime);
/*    */     }
/* 47 */     if (price != null) {
/* 48 */       f.append(" and bean.coupon.leastPrice<=:price");
/* 49 */       f.setParam("price", price);
/*    */     }
/* 51 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<MemberCoupon> getList(Long memberId)
/*    */   {
/* 57 */     String hql = "from MemberCoupon bean where bean.member.id=:id";
/* 58 */     return getSession().createQuery(hql).setParameter("id", memberId).list();
/*    */   }
/*    */ 
/*    */   public MemberCoupon findById(Long id)
/*    */   {
/* 64 */     MemberCoupon entity = (MemberCoupon)get(id);
/* 65 */     return entity;
/*    */   }
/*    */ 
/*    */   public MemberCoupon update(MemberCoupon bean)
/*    */   {
/* 70 */     getSession().update(bean);
/* 71 */     return bean;
/*    */   }
/*    */ 
/*    */   public MemberCoupon save(MemberCoupon bean)
/*    */   {
/* 76 */     getSession().save(bean);
/* 77 */     return bean;
/*    */   }
/*    */ 
/*    */   public MemberCoupon deleteById(Long id)
/*    */   {
/* 82 */     MemberCoupon entity = (MemberCoupon)super.get(id);
/* 83 */     if (entity != null) {
/* 84 */       getSession().delete(entity);
/*    */     }
/* 86 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<MemberCoupon> getEntityClass()
/*    */   {
/* 91 */     return MemberCoupon.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.MemberCouponDaoImpl
 * JD-Core Version:    0.6.0
 */