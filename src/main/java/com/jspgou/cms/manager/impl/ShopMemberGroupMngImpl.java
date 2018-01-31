/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopMemberGroupDao;
/*    */ import com.jspgou.cms.entity.ShopMemberGroup;
/*    */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopMemberGroupMngImpl
/*    */   implements ShopMemberGroupMng
/*    */ {
/*    */   private ShopMemberGroupDao dao;
/*    */ 
/*    */   public ShopMemberGroup findGroupByScore(Long webId, int score)
/*    */   {
/* 26 */     List groupList = this.dao.getList(webId, true);
/* 27 */     int size = groupList.size();
/* 28 */     if (size < 1)
/* 29 */       throw new IllegalStateException(
/* 30 */         "ShopMmeberGroup not found in website id=" + webId);
/* 31 */     if (size == 1) {
/* 32 */       return (ShopMemberGroup)groupList.get(0);
/*    */     }
/* 34 */     ShopMemberGroup group = (ShopMemberGroup)groupList.get(0);
/*    */ 
/* 36 */     for (int i = size - 1; i > 0; i--) {
/* 37 */       ShopMemberGroup temp = (ShopMemberGroup)groupList.get(i);
/* 38 */       if (score > temp.getScore().intValue()) {
/* 39 */         group = temp;
/* 40 */         break;
/*    */       }
/*    */     }
/* 43 */     return group;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<ShopMemberGroup> getList(Long webId) {
/* 49 */     return this.dao.getList(webId, false);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<ShopMemberGroup> getList()
/*    */   {
/* 56 */     return this.dao.getList();
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopMemberGroup findById(Long id) {
/* 62 */     ShopMemberGroup entity = this.dao.findById(id);
/* 63 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup save(ShopMemberGroup bean)
/*    */   {
/* 68 */     this.dao.save(bean);
/* 69 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup update(ShopMemberGroup bean)
/*    */   {
/* 74 */     Updater updater = new Updater(bean);
/* 75 */     ShopMemberGroup entity = this.dao.updateByUpdater(updater);
/* 76 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup deleteById(Long id)
/*    */   {
/* 81 */     ShopMemberGroup bean = this.dao.deleteById(id);
/* 82 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopMemberGroup[] deleteByIds(Long[] ids)
/*    */   {
/* 87 */     ShopMemberGroup[] beans = new ShopMemberGroup[ids.length];
/* 88 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 89 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 91 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopMemberGroupDao dao)
/*    */   {
/* 98 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopMemberGroupMngImpl
 * JD-Core Version:    0.6.0
 */