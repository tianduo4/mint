/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopArticleDao;
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopArticleContent;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.manager.ShopArticleContentMng;
/*     */ import com.jspgou.cms.manager.ShopArticleMng;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ShopArticleMngImpl
/*     */   implements ShopArticleMng
/*     */ {
/*     */   private ShopArticleContentMng shopArticleContentMng;
/*     */   private ShopChannelMng shopChannelMng;
/*     */   private ShopArticleDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Integer channelId, Long webId, int pageNo, int pageSize)
/*     */   {
/*  30 */     Pagination page = this.dao.getPage(channelId, webId, pageNo, pageSize);
/*  31 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPageForTag(Long webId, Long channelId, int pageNo, int pageSize)
/*     */   {
/*     */     Pagination page;
/*     */     Pagination page;
/*  39 */     if (channelId != null)
/*  40 */       page = this.dao.getPageByChannel(channelId, pageNo, pageSize, true);
/*     */     else {
/*  42 */       page = this.dao.getPageByWebsite(webId, pageNo, pageSize, true);
/*     */     }
/*  44 */     return page;
/*     */   }
/*     */ 
/*     */   public List<ShopArticle> getListForTag(Long webId, Integer channelId, int firstResult, int maxResults)
/*     */   {
/*     */     List list;
/*     */     List list;
/*  51 */     if (channelId != null)
/*  52 */       list = this.dao.getListByChannel(channelId, firstResult, maxResults, 
/*  53 */         true);
/*     */     else {
/*  55 */       list = this.dao.getListByWebsite(webId, firstResult, maxResults, true);
/*     */     }
/*  57 */     return list;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public ShopArticle findById(Long id) {
/*  63 */     ShopArticle entity = this.dao.findById(id);
/*  64 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopArticle save(ShopArticle bean, Integer channelId, String content)
/*     */   {
/*  69 */     ShopChannel channel = this.shopChannelMng.findById(channelId);
/*  70 */     bean.setChannel(channel);
/*  71 */     bean.init();
/*  72 */     this.dao.save(bean);
/*  73 */     if (content != null) {
/*  74 */       this.shopArticleContentMng.save(content, bean);
/*     */     }
/*  76 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopArticle update(ShopArticle bean, Integer channelId, String content)
/*     */   {
/*  81 */     ShopArticle entity = findById(bean.getId());
/*  82 */     ShopArticleContent c = entity.getArticleContent();
/*  83 */     if (c != null)
/*  84 */       c.setContent(content);
/*     */     else {
/*  86 */       this.shopArticleContentMng.save(content, entity);
/*     */     }
/*  88 */     if (channelId != null) {
/*  89 */       entity.setChannel(this.shopChannelMng.findById(channelId));
/*     */     }
/*  91 */     Updater updater = new Updater(bean);
/*  92 */     entity = this.dao.updateByUpdater(updater);
/*  93 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopArticle deleteById(Long id)
/*     */   {
/*  98 */     ShopArticle bean = this.dao.deleteById(id);
/*  99 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopArticle[] deleteByIds(Long[] ids)
/*     */   {
/* 104 */     ShopArticle[] beans = new ShopArticle[ids.length];
/* 105 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 106 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 108 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(ShopArticleDao dao)
/*     */   {
/* 117 */     this.dao = dao;
/*     */   }
/*     */   @Autowired
/*     */   public void setShopChannelMng(ShopChannelMng shopChannelMng) {
/* 122 */     this.shopChannelMng = shopChannelMng;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setShopArticleContentMng(ShopArticleContentMng shopArticleContentMng) {
/* 128 */     this.shopArticleContentMng = shopArticleContentMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopArticleMngImpl
 * JD-Core Version:    0.6.0
 */