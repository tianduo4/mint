/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ConsultDao;
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ConsultDaoImpl extends HibernateBaseDao<Consult, Long>
/*     */   implements ConsultDao
/*     */ {
/*     */   public Consult findById(Long id)
/*     */   {
/*  25 */     Consult entity = (Consult)get(id);
/*  26 */     return entity;
/*     */   }
/*     */ 
/*     */   public Consult saveOrUpdate(Consult bean)
/*     */   {
/*  31 */     getSession().saveOrUpdate(bean);
/*  32 */     return bean;
/*     */   }
/*     */ 
/*     */   public List<Consult> findByProductId(Long productId)
/*     */   {
/*  38 */     Finder f = Finder.create("from Consult bean where bean.product.id=:id").setParam("id", productId);
/*  39 */     return find(f);
/*     */   }
/*     */ 
/*     */   public Consult getSameConsult(Long memberId)
/*     */   {
/*  45 */     Iterator it = getSession().createQuery("from Consult bean where bean.member.id=:id order by bean.id desc")
/*  46 */       .setParameter("id", memberId).setMaxResults(1).iterate();
/*  47 */     if (it.hasNext()) {
/*  48 */       return (Consult)it.next();
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long productId, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, boolean cache)
/*     */   {
/*  57 */     Finder f = Finder.create("from Consult bean where 1=1 ");
/*  58 */     if (productId != null) {
/*  59 */       f.append(" and bean.product.id=:id");
/*  60 */       f.setParam("id", productId);
/*     */     }
/*  62 */     if (!StringUtils.isBlank(userName)) {
/*  63 */       f.append(" and bean.member.member.user.username like:userName");
/*  64 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/*  66 */     if (!StringUtils.isBlank(productName)) {
/*  67 */       f.append(" and bean.product.name like:productName");
/*  68 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/*  70 */     if (startTime != null) {
/*  71 */       f.append(" and bean.time>:startTime");
/*  72 */       f.setParam("startTime", startTime);
/*     */     }
/*  74 */     if (endTime != null) {
/*  75 */       f.append(" and bean.time<:endTime");
/*  76 */       f.setParam("endTime", endTime);
/*     */     }
/*  78 */     f.append(" order by bean.id desc");
/*  79 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getVisiblePage(String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize)
/*     */   {
/*  87 */     Finder f = Finder.create("from Consult bean where 1=1 ");
/*  88 */     if (!StringUtils.isBlank(userName)) {
/*  89 */       f.append(" and bean.member.member.user.username like:userName");
/*  90 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/*  92 */     if (!StringUtils.isBlank(productName)) {
/*  93 */       f.append(" and bean.product.name like:productName");
/*  94 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/*  96 */     if (startTime != null) {
/*  97 */       f.append(" and bean.time>:startTime");
/*  98 */       f.setParam("startTime", startTime);
/*     */     }
/* 100 */     if (endTime != null) {
/* 101 */       f.append(" and bean.time<:endTime");
/* 102 */       f.setParam("endTime", endTime);
/*     */     }
/* 104 */     f.append(" order by bean.id desc");
/* 105 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache)
/*     */   {
/* 111 */     Finder f = Finder.create("from Consult bean");
/* 112 */     if (memberId != null) {
/* 113 */       f.append(" where bean.member.id=:id");
/* 114 */       f.setParam("id", memberId);
/*     */     }
/* 116 */     f.append(" order by bean.id desc");
/* 117 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Consult update(Consult bean)
/*     */   {
/* 122 */     getSession().update(bean);
/* 123 */     return bean;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 128 */     String sql = "delete from jc_shop_consult where member_id = " + memberId;
/* 129 */     getSession().createSQLQuery(sql).executeUpdate();
/*     */   }
/*     */ 
/*     */   public Consult deleteById(Long id)
/*     */   {
/* 134 */     Consult entity = (Consult)super.get(id);
/* 135 */     if (entity != null) {
/* 136 */       getSession().delete(entity);
/*     */     }
/* 138 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<Consult> getEntityClass()
/*     */   {
/* 143 */     return Consult.class;
/*     */   }
/*     */ 
/*     */   public Long getProductConsult()
/*     */   {
/* 149 */     Finder finder = Finder.create("select count(1) from Consult bean  where bean.adminReplay is null ");
/* 150 */     List list = find(finder);
/* 151 */     return (Long)list.get(0);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ConsultDaoImpl
 * JD-Core Version:    0.6.0
 */