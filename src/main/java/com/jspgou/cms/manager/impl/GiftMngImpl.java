/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.GiftDao;
/*    */ import com.jspgou.cms.dao.ShopMemberDao;
/*    */ import com.jspgou.cms.entity.Gift;
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.GiftMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class GiftMngImpl
/*    */   implements GiftMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private GiftDao dao;
/*    */ 
/*    */   @Autowired
/*    */   private ShopMemberDao memberDao;
/*    */ 
/*    */   public Gift deleteById(Long id)
/*    */   {
/* 24 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public Gift[] deleteByIds(Long[] ids)
/*    */   {
/* 29 */     Gift[] beans = new Gift[ids.length];
/* 30 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 31 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 33 */     return beans;
/*    */   }
/*    */ 
/*    */   public Gift findById(Long id)
/*    */   {
/* 39 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public Pagination getPageGift(int pageNo, int pageSize)
/*    */   {
/* 44 */     return this.dao.getPageGift(pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Gift save(Gift bean)
/*    */   {
/* 49 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public Gift updateByGiftnumb(Long giftId, Integer giftNumb, Long shopMemberId)
/*    */   {
/* 54 */     Gift gift = this.dao.findById(giftId);
/* 55 */     ShopMember smber = this.memberDao.findById(shopMemberId);
/* 56 */     Integer stock = gift.getGiftStock();
/* 57 */     Integer totalScore = Integer.valueOf(gift.getGiftScore().intValue() * giftNumb.intValue());
/* 58 */     if (stock.intValue() < giftNumb.intValue())
/* 59 */       return null;
/* 60 */     if (totalScore.intValue() > smber.getScore().intValue()) {
/* 61 */       return null;
/*    */     }
/* 63 */     gift.setGiftStock(Integer.valueOf(stock.intValue() - giftNumb.intValue()));
/* 64 */     smber.setScore(Integer.valueOf(smber.getScore().intValue() - totalScore.intValue()));
/*    */ 
/* 66 */     return gift;
/*    */   }
/*    */ 
/*    */   public Gift updateByUpdater(Gift bean)
/*    */   {
/* 71 */     Updater updater = new Updater(bean);
/* 72 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.GiftMngImpl
 * JD-Core Version:    0.6.0
 */