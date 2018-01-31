/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PopularityItemDao;
/*    */ import com.jspgou.cms.entity.Cart;
/*    */ import com.jspgou.cms.entity.PopularityItem;
/*    */ import com.jspgou.cms.manager.PopularityGroupMng;
/*    */ import com.jspgou.cms.manager.PopularityItemMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class PopularityItemMngImpl
/*    */   implements PopularityItemMng
/*    */ {
/*    */   private PopularityItemDao dao;
/*    */ 
/*    */   @Autowired
/*    */   private PopularityGroupMng popularityGroupMng;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 23 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */ 
/*    */   public List<PopularityItem> getlist(Long cartId, Long popularityGroupId)
/*    */   {
/* 29 */     return this.dao.getlist(cartId, popularityGroupId);
/*    */   }
/*    */ 
/*    */   public PopularityItem findById(Long cartId, Long popularityId)
/*    */   {
/* 35 */     return this.dao.findById(cartId, popularityId);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public PopularityItem findById(Long id)
/*    */   {
/* 42 */     PopularityItem entity = this.dao.findById(id);
/* 43 */     return entity;
/*    */   }
/*    */ 
/*    */   public PopularityItem save(PopularityItem bean)
/*    */   {
/* 48 */     this.dao.save(bean);
/* 49 */     return bean;
/*    */   }
/*    */ 
/*    */   public PopularityItem update(PopularityItem bean)
/*    */   {
/* 54 */     Updater updater = new Updater(bean);
/* 55 */     PopularityItem entity = this.dao.updateByUpdater(updater);
/* 56 */     return entity;
/*    */   }
/*    */ 
/*    */   public PopularityItem deleteById(Long id)
/*    */   {
/* 61 */     PopularityItem bean = this.dao.deleteById(id);
/* 62 */     return bean;
/*    */   }
/*    */ 
/*    */   public PopularityItem[] deleteByIds(Long[] ids)
/*    */   {
/* 67 */     PopularityItem[] beans = new PopularityItem[ids.length];
/* 68 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 69 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 71 */     return beans;
/*    */   }
/*    */ 
/*    */   public void save(Cart cart, Long popularityId)
/*    */   {
/* 76 */     if (popularityId != null) {
/* 77 */       PopularityItem bean = findById(cart.getId(), popularityId);
/* 78 */       if (bean != null) {
/* 79 */         bean.setCount(Integer.valueOf(bean.getCount().intValue() + 1));
/* 80 */         update(bean);
/*    */       } else {
/* 82 */         bean = new PopularityItem();
/* 83 */         bean.setCart(cart);
/* 84 */         bean.setPopularityGroup(this.popularityGroupMng.findById(popularityId));
/* 85 */         bean.setCount(Integer.valueOf(1));
/* 86 */         save(bean);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(PopularityItemDao dao)
/*    */   {
/* 98 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.PopularityItemMngImpl
 * JD-Core Version:    0.6.0
 */