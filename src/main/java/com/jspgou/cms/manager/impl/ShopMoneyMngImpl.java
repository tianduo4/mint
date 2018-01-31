/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopMoneyDao;
/*    */ import com.jspgou.cms.entity.ShopMoney;
/*    */ import com.jspgou.cms.manager.ShopMoneyMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Date;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopMoneyMngImpl
/*    */   implements ShopMoneyMng
/*    */ {
/*    */   private ShopMoneyDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 25 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 26 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPage(Long memberId, Boolean status, Date startTime, Date endTime, Integer pageSize, Integer pageNo)
/*    */   {
/* 32 */     Pagination page = this.dao.getPage(memberId, status, 
/* 33 */       startTime, endTime, pageNo, pageSize);
/* 34 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopMoney findById(Long id) {
/* 40 */     ShopMoney entity = this.dao.findById(id);
/* 41 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMoney save(ShopMoney bean)
/*    */   {
/* 46 */     this.dao.save(bean);
/* 47 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMoney update(ShopMoney bean)
/*    */   {
/* 52 */     Updater updater = new Updater(bean);
/* 53 */     ShopMoney entity = this.dao.updateByUpdater(updater);
/* 54 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMoney deleteById(Long id)
/*    */   {
/* 59 */     ShopMoney bean = this.dao.deleteById(id);
/* 60 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMoney[] deleteByIds(Long[] ids)
/*    */   {
/* 65 */     ShopMoney[] beans = new ShopMoney[ids.length];
/* 66 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 67 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 69 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopMoneyDao dao)
/*    */   {
/* 76 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 81 */     this.dao.deleteByMemberId(memberId);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopMoneyMngImpl
 * JD-Core Version:    0.6.0
 */