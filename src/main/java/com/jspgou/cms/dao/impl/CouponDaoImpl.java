/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CouponDao;
/*    */ import com.jspgou.cms.entity.Coupon;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.SQLQuery;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class CouponDaoImpl extends HibernateBaseDao<Coupon, Long>
/*    */   implements CouponDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize, Integer categoryId)
/*    */   {
/* 24 */     Finder f = Finder.create("from Coupon bean");
/* 25 */     if (categoryId != null) {
/* 26 */       f.append(" where bean.category.id = :categoryId ");
/* 27 */       f.setParam("categoryId", categoryId);
/*    */     }
/* 29 */     f.append(" order by bean.id desc");
/* 30 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Pagination getPageByUsing(Date newTime, int pageNo, int pageSize)
/*    */   {
/* 35 */     Finder f = Finder.create("from Coupon bean where bean.isusing=true");
/* 36 */     if (newTime != null) {
/* 37 */       f.append(" and bean.couponEndTime>:newTime");
/* 38 */       f.setParam("newTime", newTime);
/*    */     }
/* 40 */     f.append(" order by bean.id asc");
/* 41 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<Coupon> getList()
/*    */   {
/* 47 */     String hql = "from Coupon bean where 1=1";
/* 48 */     return getSession().createQuery(hql).list();
/*    */   }
/*    */ 
/*    */   public Coupon findById(Long id)
/*    */   {
/* 53 */     Coupon entity = (Coupon)get(id);
/* 54 */     return entity;
/*    */   }
/*    */ 
/*    */   public Coupon save(Coupon bean)
/*    */   {
/* 59 */     getSession().save(bean);
/* 60 */     return bean;
/*    */   }
/*    */ 
/*    */   public Coupon deleteById(Long id)
/*    */   {
/* 65 */     Coupon entity = (Coupon)super.get(id);
/* 66 */     if (entity != null) {
/* 67 */       getSession().delete(entity);
/*    */     }
/* 69 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Coupon> getEntityClass()
/*    */   {
/* 74 */     return Coupon.class;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 79 */     String sql = "delete from jc_shop_member_coupon where member_id = " + memberId;
/* 80 */     getSession().createSQLQuery(sql).executeUpdate();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.CouponDaoImpl
 * JD-Core Version:    0.6.0
 */