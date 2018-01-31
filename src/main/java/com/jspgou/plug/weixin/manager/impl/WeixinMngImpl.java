/*    */ package com.jspgou.plug.weixin.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.weixin.dao.WeixinDao;
/*    */ import com.jspgou.plug.weixin.entity.Weixin;
/*    */ import com.jspgou.plug.weixin.manager.WeixinMng;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class WeixinMngImpl
/*    */   implements WeixinMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WeixinDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(Integer siteId, int pageNo, int pageSize)
/*    */   {
/* 19 */     return this.dao.getPage(siteId, pageNo, pageSize);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public Weixin findById(Integer id) {
/* 24 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Weixin findBy() {
/* 30 */     return this.dao.findBy();
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Weixin find(Long siteId) {
/* 36 */     return this.dao.find(siteId);
/*    */   }
/*    */ 
/*    */   public Weixin save(Weixin bean) {
/* 40 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public Weixin update(Weixin bean) {
/* 44 */     Updater updater = new Updater(bean);
/* 45 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ 
/*    */   public Weixin deleteById(Integer id) {
/* 49 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public Weixin[] delete(Integer[] ids) {
/* 53 */     Weixin[] beans = new Weixin[ids.length];
/* 54 */     for (int i = 0; i < ids.length; i++) {
/* 55 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 57 */     return beans;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.manager.impl.WeixinMngImpl
 * JD-Core Version:    0.6.0
 */