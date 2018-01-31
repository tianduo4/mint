/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopChannelContentDao;
/*    */ import com.jspgou.cms.entity.ShopChannel;
/*    */ import com.jspgou.cms.entity.ShopChannelContent;
/*    */ import com.jspgou.cms.manager.ShopChannelContentMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopChannelContentMngImpl
/*    */   implements ShopChannelContentMng
/*    */ {
/*    */   private ShopChannelContentDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 24 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 25 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopChannelContent findById(Long id) {
/* 31 */     ShopChannelContent entity = this.dao.findById(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent save(String content, ShopChannel channel)
/*    */   {
/* 37 */     ShopChannelContent bean = new ShopChannelContent();
/* 38 */     bean.setContent(content);
/* 39 */     bean.setChannel(channel);
/* 40 */     this.dao.save(bean);
/* 41 */     channel.setChannelContent(bean);
/* 42 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent update(ShopChannelContent bean)
/*    */   {
/* 47 */     Updater updater = new Updater(
/* 48 */       bean);
/* 49 */     ShopChannelContent entity = this.dao.updateByUpdater(updater);
/* 50 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent deleteById(Long id)
/*    */   {
/* 55 */     ShopChannelContent bean = this.dao.deleteById(id);
/* 56 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopChannelContent[] deleteByIds(Long[] ids)
/*    */   {
/* 61 */     ShopChannelContent[] beans = new ShopChannelContent[ids.length];
/* 62 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 63 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 65 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopChannelContentDao dao)
/*    */   {
/* 72 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopChannelContentMngImpl
 * JD-Core Version:    0.6.0
 */