/*    */ package com.jspgou.plug.weixin.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.weixin.dao.WeixinMenuDao;
/*    */ import com.jspgou.plug.weixin.entity.WeixinMenu;
/*    */ import com.jspgou.plug.weixin.manager.WeixinMenuMng;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class WeixinMenuMngImpl
/*    */   implements WeixinMenuMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WeixinMenuDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(Long siteId, Integer parentId, int pageNo, int pageSize)
/*    */   {
/* 21 */     return this.dao.getPage(siteId, parentId, pageNo, pageSize);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public List<WeixinMenu> getList(Long siteId, Integer count) {
/* 26 */     return this.dao.getList(siteId, count);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public WeixinMenu findById(Integer id) {
/* 31 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public WeixinMenu save(WeixinMenu bean) {
/* 35 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public WeixinMenu update(WeixinMenu bean) {
/* 39 */     Updater updater = new Updater(bean);
/* 40 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ 
/*    */   public WeixinMenu deleteById(Integer id) {
/* 44 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public WeixinMenu[] deleteByIds(Integer[] ids) {
/* 48 */     WeixinMenu[] beans = new WeixinMenu[ids.length];
/* 49 */     for (int i = 0; i < ids.length; i++) {
/* 50 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 52 */     return beans;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.manager.impl.WeixinMenuMngImpl
 * JD-Core Version:    0.6.0
 */