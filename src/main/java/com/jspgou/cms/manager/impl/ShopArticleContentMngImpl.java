/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopArticleContentDao;
/*    */ import com.jspgou.cms.entity.ShopArticle;
/*    */ import com.jspgou.cms.entity.ShopArticleContent;
/*    */ import com.jspgou.cms.manager.ShopArticleContentMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopArticleContentMngImpl
/*    */   implements ShopArticleContentMng
/*    */ {
/*    */   private ShopArticleContentDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 24 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 25 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopArticleContent findById(Long id) {
/* 31 */     ShopArticleContent entity = this.dao.findById(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent save(String content, ShopArticle article)
/*    */   {
/* 37 */     ShopArticleContent bean = new ShopArticleContent();
/* 38 */     bean.setContent(content);
/* 39 */     bean.setArticle(article);
/* 40 */     this.dao.save(bean);
/* 41 */     article.setArticleContent(bean);
/* 42 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent update(ShopArticleContent bean)
/*    */   {
/* 47 */     Updater updater = new Updater(
/* 48 */       bean);
/* 49 */     ShopArticleContent entity = this.dao.updateByUpdater(updater);
/* 50 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent deleteById(Long id)
/*    */   {
/* 55 */     ShopArticleContent bean = this.dao.deleteById(id);
/* 56 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopArticleContent[] deleteByIds(Long[] ids)
/*    */   {
/* 61 */     ShopArticleContent[] beans = new ShopArticleContent[ids.length];
/* 62 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 63 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 65 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopArticleContentDao dao)
/*    */   {
/* 72 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopArticleContentMngImpl
 * JD-Core Version:    0.6.0
 */