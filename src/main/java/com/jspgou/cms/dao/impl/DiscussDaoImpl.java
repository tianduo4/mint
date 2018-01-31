/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.DiscussDao;
/*     */ import com.jspgou.cms.entity.Discuss;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class DiscussDaoImpl extends HibernateBaseDao<Discuss, Long>
/*     */   implements DiscussDao
/*     */ {
/*     */   public Discuss findById(Long id)
/*     */   {
/*  24 */     Discuss entity = (Discuss)get(id);
/*  25 */     return entity;
/*     */   }
/*     */ 
/*     */   public Discuss saveOrUpdate(Discuss bean)
/*     */   {
/*  31 */     getSession().saveOrUpdate(bean);
/*  32 */     return bean;
/*     */   }
/*     */ 
/*     */   public Discuss update(Discuss bean)
/*     */   {
/*  37 */     getSession().update(bean);
/*  38 */     return bean;
/*     */   }
/*     */ 
/*     */   public Pagination getPageByProduct(Long memberId, Long productId, String discussType, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, boolean cache)
/*     */   {
/*  44 */     Finder f = Finder.create("from Discuss bean where 1=1 ");
/*     */ 
/*  46 */     if (memberId != null) {
/*  47 */       f.append(" and bean.member.id=:memberId");
/*  48 */       f.setParam("memberId", memberId);
/*     */     }
/*  50 */     if (productId != null) {
/*  51 */       f.append(" and bean.product.id=:id");
/*  52 */       f.setParam("id", productId);
/*     */     }
/*     */ 
/*  55 */     if (discussType != null) {
/*  56 */       f.append(" and bean.discussType=:discussType");
/*  57 */       f.setParam("discussType", discussType);
/*     */     }
/*  59 */     if (!StringUtils.isBlank(userName)) {
/*  60 */       f.append(" and bean.member.member.user.username like:userName");
/*  61 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/*  63 */     if (!StringUtils.isBlank(productName)) {
/*  64 */       f.append(" and bean.product.name like:productName");
/*  65 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/*  67 */     if (startTime != null) {
/*  68 */       f.append(" and bean.time>:startTime");
/*  69 */       f.setParam("startTime", startTime);
/*     */     }
/*  71 */     if (endTime != null) {
/*  72 */       f.append(" and bean.time<:endTime");
/*  73 */       f.setParam("endTime", endTime);
/*     */     }
/*  75 */     f.append(" order by bean.id desc");
/*  76 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache)
/*     */   {
/*  81 */     Finder f = Finder.create("from Discuss bean");
/*  82 */     if (memberId != null) {
/*  83 */       f.append(" where bean.member.id=:id");
/*  84 */       f.setParam("id", memberId);
/*     */     }
/*  86 */     f.append(" order by bean.id desc");
/*  87 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Discuss> findByType(Long productId, String discussType)
/*     */   {
/*  92 */     Finder f = Finder.create("from Discuss bean where 1=1");
/*  93 */     if (productId != null) {
/*  94 */       f.append(" and bean.product.id=:productId");
/*  95 */       f.setParam("productId", productId);
/*     */     }
/*  97 */     if (discussType != null) {
/*  98 */       f.append(" and bean.discussType=:discussType");
/*  99 */       f.setParam("discussType", discussType);
/*     */     }
/* 101 */     return find(f);
/*     */   }
/*     */ 
/*     */   public Discuss deleteById(Long id) {
/* 105 */     Discuss entity = (Discuss)super.get(id);
/* 106 */     if (entity != null) {
/* 107 */       getSession().delete(entity);
/*     */     }
/* 109 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<Discuss> getEntityClass()
/*     */   {
/* 114 */     return Discuss.class;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 120 */     String sql = "delete from jc_shop_discuss where member_id=" + memberId;
/* 121 */     getSession().createSQLQuery(sql).executeUpdate();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.DiscussDaoImpl
 * JD-Core Version:    0.6.0
 */