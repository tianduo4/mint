/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.GiftExchangeDao;
/*    */ import com.jspgou.cms.entity.GiftExchange;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.SQLQuery;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class GiftExchangeDaoImpl extends HibernateBaseDao<GiftExchange, Long>
/*    */   implements GiftExchangeDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Criteria crit = createCriteria(new Criterion[0]);
/* 19 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public List<GiftExchange> getlist(Long memberId)
/*    */   {
/* 25 */     Finder f = Finder.create("from GiftExchange bean where bean.member.id=:memberId");
/* 26 */     f.setParam("memberId", memberId);
/* 27 */     return find(f);
/*    */   }
/*    */ 
/*    */   public GiftExchange findById(Long id)
/*    */   {
/* 32 */     GiftExchange entity = (GiftExchange)get(id);
/* 33 */     return entity;
/*    */   }
/*    */ 
/*    */   public GiftExchange save(GiftExchange bean)
/*    */   {
/* 38 */     getSession().save(bean);
/* 39 */     return bean;
/*    */   }
/*    */ 
/*    */   public GiftExchange deleteById(Long id)
/*    */   {
/* 44 */     GiftExchange entity = (GiftExchange)super.get(id);
/* 45 */     if (entity != null) {
/* 46 */       getSession().delete(entity);
/*    */     }
/* 48 */     return entity;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId) {
/* 52 */     String sql = "delete from jc_shop_gift_exchange where member_id=" + memberId;
/* 53 */     getSession().createSQLQuery(sql).executeUpdate();
/*    */   }
/*    */ 
/*    */   protected Class<GiftExchange> getEntityClass()
/*    */   {
/* 58 */     return GiftExchange.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.GiftExchangeDaoImpl
 * JD-Core Version:    0.6.0
 */