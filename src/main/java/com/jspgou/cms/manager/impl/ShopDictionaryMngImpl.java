/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopDictionaryDao;
/*    */ import com.jspgou.cms.entity.ShopDictionary;
/*    */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopDictionaryMngImpl
/*    */   implements ShopDictionaryMng
/*    */ {
/*    */   private ShopDictionaryDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 25 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 26 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPage(String name, Long typeId, int pageNo, int pageSize)
/*    */   {
/* 31 */     Pagination page = this.dao.getPage(name, typeId, pageNo, pageSize);
/* 32 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopDictionary findById(Long id) {
/* 38 */     ShopDictionary entity = this.dao.findById(id);
/* 39 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<ShopDictionary> getListByType(Long typeId)
/*    */   {
/* 44 */     return this.dao.getListByType(typeId);
/*    */   }
/*    */ 
/*    */   public ShopDictionary save(ShopDictionary bean)
/*    */   {
/* 50 */     this.dao.save(bean);
/* 51 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopDictionary update(ShopDictionary bean)
/*    */   {
/* 56 */     Updater updater = new Updater(bean);
/* 57 */     ShopDictionary entity = this.dao.updateByUpdater(updater);
/* 58 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopDictionary deleteById(Long id)
/*    */   {
/* 63 */     ShopDictionary bean = this.dao.deleteById(id);
/* 64 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopDictionary[] deleteByIds(Long[] ids)
/*    */   {
/* 69 */     ShopDictionary[] beans = new ShopDictionary[ids.length];
/* 70 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 71 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 73 */     return beans;
/*    */   }
/*    */ 
/*    */   public ShopDictionary[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 78 */     int len = ids.length;
/* 79 */     ShopDictionary[] beans = new ShopDictionary[len];
/* 80 */     for (int i = 0; i < len; i++) {
/* 81 */       beans[i] = findById(ids[i]);
/* 82 */       beans[i].setPriority(priority[i]);
/*    */     }
/* 84 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopDictionaryDao dao)
/*    */   {
/* 91 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopDictionaryMngImpl
 * JD-Core Version:    0.6.0
 */