/*    */ package com.jspgou.plug.weixin.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.weixin.dao.WeixinMessageDao;
/*    */ import com.jspgou.plug.weixin.entity.WeixinMessage;
/*    */ import com.jspgou.plug.weixin.manager.WeixinMessageMng;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class WeixinMessageMngImpl
/*    */   implements WeixinMessageMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WeixinMessageDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(Long siteId, int pageNo, int pageSize)
/*    */   {
/* 21 */     return this.dao.getPage(siteId, pageNo, pageSize);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public List<WeixinMessage> getList(Long siteId) {
/* 26 */     return this.dao.getList(siteId);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public WeixinMessage getWelcome(Long siteId) {
/* 31 */     return this.dao.getWelcome(siteId);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public WeixinMessage findByNumber(String number, Long siteId) {
/* 36 */     return this.dao.findByNumber(number, siteId);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public WeixinMessage findById(Integer id) {
/* 41 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public WeixinMessage save(WeixinMessage bean)
/*    */   {
/* 46 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public WeixinMessage update(WeixinMessage bean)
/*    */   {
/* 51 */     Updater updater = new Updater(bean);
/* 52 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ 
/*    */   public WeixinMessage deleteById(Integer id)
/*    */   {
/* 57 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public WeixinMessage[] deleteByIds(Integer[] ids)
/*    */   {
/* 63 */     WeixinMessage[] beans = new WeixinMessage[ids.length];
/* 64 */     for (int i = 0; i < ids.length; i++) {
/* 65 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 67 */     return beans;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.manager.impl.WeixinMessageMngImpl
 * JD-Core Version:    0.6.0
 */