/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CollectDao;
/*    */ import com.jspgou.cms.entity.Collect;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.SQLQuery;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class CollectDaoImpl extends HibernateBaseDao<Collect, Integer>
/*    */   implements CollectDao
/*    */ {
/*    */   public Pagination getList(Integer pageSize, Integer pageNo, Long memberId)
/*    */   {
/* 24 */     String hql = "from Collect bean where 1=1 and bean.member.id=:id";
/* 25 */     Finder f = Finder.create(hql).setParam("id", memberId);
/* 26 */     return find(f, pageNo.intValue(), pageSize.intValue());
/*    */   }
/*    */ 
/*    */   public Collect findById(Integer id)
/*    */   {
/* 31 */     Collect entity = (Collect)get(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<Collect> findByProductId(Long productId)
/*    */   {
/* 38 */     Finder f = Finder.create("from Collect bean where bean.product.id=:id").setParam("id", productId);
/* 39 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Collect findByProductFashionId(Long id)
/*    */   {
/* 45 */     Iterator list = getSession().createQuery("from Collect bean where bean.fashion.id=:id").setParameter("id", id).iterate();
/* 46 */     if (list.hasNext()) {
/* 47 */       return (Collect)list.next();
/*    */     }
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   public List<Collect> getList(Long memberId, int firstResult, int maxResults)
/*    */   {
/* 54 */     Finder f = Finder.create("from Collect bean");
/* 55 */     f.append(" where bean.user.id=:memberId").setParam("memberId", memberId);
/* 56 */     f.setCacheable(true);
/* 57 */     f.setFirstResult(firstResult);
/* 58 */     f.setMaxResults(maxResults);
/* 59 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<Collect> getList(Long productId, Long memberId)
/*    */   {
/* 64 */     Finder f = Finder.create("select bean from Collect bean where 1=1");
/* 65 */     f.append(" and bean.user.id=:memberId").setParam("memberId", memberId);
/* 66 */     f.append(" and bean.product.id=:productId").setParam("productId", productId);
/* 67 */     return find(f);
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memeberId)
/*    */   {
/* 72 */     String sql = "delete from jc_shop_collect  where member_id=" + memeberId;
/* 73 */     getSession().createSQLQuery(sql).executeUpdate();
/*    */   }
/*    */ 
/*    */   public Collect save(Collect bean)
/*    */   {
/* 78 */     getSession().save(bean);
/* 79 */     return bean;
/*    */   }
/*    */ 
/*    */   public Collect deleteById(Integer id)
/*    */   {
/* 84 */     Collect entity = (Collect)super.get(id);
/* 85 */     if (entity != null) {
/* 86 */       getSession().delete(entity);
/*    */     }
/* 88 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Collect> getEntityClass()
/*    */   {
/* 93 */     return Collect.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.CollectDaoImpl
 * JD-Core Version:    0.6.0
 */