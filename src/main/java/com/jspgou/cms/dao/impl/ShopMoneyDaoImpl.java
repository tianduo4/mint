/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopMoneyDao;
/*    */ import com.jspgou.cms.entity.ShopMoney;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Date;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.SQLQuery;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopMoneyDaoImpl extends HibernateBaseDao<ShopMoney, Long>
/*    */   implements ShopMoneyDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Criteria crit = createCriteria(new Criterion[0]);
/* 23 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPage(Long memberId, Boolean status, Date startTime, Date endTime, Integer pageSize, Integer pageNo)
/*    */   {
/* 30 */     Finder f = Finder.create("select bean from ShopMoney bean where 1=1 ");
/* 31 */     if (memberId != null) {
/* 32 */       f.append(" and bean.member.id=:memberId").setParam("memberId", memberId);
/*    */     }
/* 34 */     if (status != null) {
/* 35 */       f.append(" and bean.status=:status").setParam("status", status);
/*    */     }
/* 37 */     if (startTime != null) {
/* 38 */       f.append(" and bean.createTime>:startTime");
/* 39 */       f.setParam("startTime", startTime);
/*    */     }
/* 41 */     if (endTime != null) {
/* 42 */       f.append(" and bean.createTime<:endTime");
/* 43 */       f.setParam("endTime", endTime);
/*    */     }
/* 45 */     f.append(" order by bean.id desc");
/* 46 */     return find(f, pageNo.intValue(), pageSize.intValue());
/*    */   }
/*    */ 
/*    */   public ShopMoney findById(Long id)
/*    */   {
/* 51 */     ShopMoney entity = (ShopMoney)get(id);
/* 52 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMoney save(ShopMoney bean)
/*    */   {
/* 57 */     getSession().save(bean);
/* 58 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMoney deleteById(Long id)
/*    */   {
/* 63 */     ShopMoney entity = (ShopMoney)super.get(id);
/* 64 */     if (entity != null) {
/* 65 */       getSession().delete(entity);
/*    */     }
/* 67 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopMoney> getEntityClass()
/*    */   {
/* 72 */     return ShopMoney.class;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 78 */     String sql = "delete from jc_shop_money where member_id=" + memberId;
/* 79 */     getSession().createSQLQuery(sql).executeUpdate();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopMoneyDaoImpl
 * JD-Core Version:    0.6.0
 */