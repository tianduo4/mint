/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopArticleDao;
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.List;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ShopArticleDaoImpl extends HibernateBaseDao<ShopArticle, Long>
/*     */   implements ShopArticleDao
/*     */ {
/*     */   public Pagination getPage(Integer channelId, Long webId, int pageNo, int pageSize)
/*     */   {
/*  23 */     Finder f = Finder.create("select bean from ShopArticle bean ");
/*  24 */     if (channelId != null) {
/*  25 */       f.append(" inner join bean.channel channel,ShopChannel parent");
/*  26 */       f.append(" where channel.lft between parent.lft and parent.rgt");
/*  27 */       f.append(" and parent.id=:parentId");
/*  28 */       f.setParam("parentId", channelId);
/*  29 */       f.append(" and bean.website.id=:webId");
/*  30 */       f.setParam("webId", webId);
/*     */     } else {
/*  32 */       f.append(" where bean.website.id=:webId");
/*  33 */       f.setParam("webId", webId);
/*     */     }
/*  35 */     f.append(" order by bean.publishTime desc");
/*  36 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByChannel(Long channelId, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/*  42 */     Finder f = Finder.create("from ShopArticle bean");
/*  43 */     f.append(" where bean.channel.id=:channelId");
/*  44 */     f.append(" order by bean.publishTime desc");
/*  45 */     f.setParam("channelId", channelId);
/*  46 */     f.setCacheable(cacheable);
/*  47 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByWebsite(Long webId, int pageNo, int pageSize, boolean cacheable)
/*     */   {
/*  53 */     Finder f = Finder.create("from ShopArticle bean");
/*  54 */     f.append(" where bean.website.id=:webId");
/*  55 */     f.append(" order by bean.publishTime desc");
/*  56 */     f.setParam("webId", webId);
/*  57 */     f.setCacheable(cacheable);
/*  58 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<ShopArticle> getListByChannel(Integer channelId, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/*  65 */     Finder f = Finder.create("from ShopArticle bean");
/*  66 */     f.append(" where bean.channel.id=:channelId");
/*  67 */     f.setParam("channelId", channelId);
/*  68 */     f.setCacheable(cacheable);
/*  69 */     f.setFirstResult(firstResult);
/*  70 */     f.setMaxResults(maxResults);
/*  71 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<ShopArticle> getListByWebsite(Long webId, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/*  78 */     Finder f = Finder.create("from ShopArticle bean");
/*  79 */     f.append(" where bean.website.id=:webId");
/*  80 */     f.setParam("webId", webId);
/*  81 */     f.setCacheable(cacheable);
/*  82 */     f.setFirstResult(firstResult);
/*  83 */     f.setMaxResults(maxResults);
/*  84 */     return find(f);
/*     */   }
/*     */ 
/*     */   public ShopArticle findById(Long id)
/*     */   {
/*  89 */     ShopArticle entity = (ShopArticle)get(id);
/*  90 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopArticle save(ShopArticle bean)
/*     */   {
/*  95 */     getSession().save(bean);
/*  96 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopArticle deleteById(Long id)
/*     */   {
/* 101 */     ShopArticle entity = (ShopArticle)super.get(id);
/* 102 */     if (entity != null) {
/* 103 */       getSession().delete(entity);
/*     */     }
/* 105 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<ShopArticle> getEntityClass()
/*     */   {
/* 110 */     return ShopArticle.class;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopArticleDaoImpl
 * JD-Core Version:    0.6.0
 */