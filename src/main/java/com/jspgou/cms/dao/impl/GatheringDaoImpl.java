/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.GatheringDao;
/*    */ import com.jspgou.cms.entity.Gathering;
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
/*    */ public class GatheringDaoImpl extends HibernateBaseDao<Gathering, Long>
/*    */   implements GatheringDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Criteria crit = createCriteria(new Criterion[0]);
/* 23 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */ 
/*    */   public List<Gathering> getlist(Long orderId)
/*    */   {
/* 29 */     Finder f = Finder.create("from Gathering bean where bean.indent.id=:id");
/* 30 */     f.setParam("id", orderId);
/* 31 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Gathering findById(Long id)
/*    */   {
/* 36 */     Gathering entity = (Gathering)get(id);
/* 37 */     return entity;
/*    */   }
/*    */ 
/*    */   public Gathering save(Gathering bean)
/*    */   {
/* 42 */     getSession().save(bean);
/* 43 */     return bean;
/*    */   }
/*    */ 
/*    */   public Gathering deleteById(Long id)
/*    */   {
/* 48 */     Gathering entity = (Gathering)super.get(id);
/* 49 */     if (entity != null) {
/* 50 */       getSession().delete(entity);
/*    */     }
/* 52 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Gathering> getEntityClass()
/*    */   {
/* 57 */     return Gathering.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.GatheringDaoImpl
 * JD-Core Version:    0.6.0
 */