/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopDictionaryTypeDao;
/*    */ import com.jspgou.cms.entity.ShopDictionaryType;
/*    */ import com.jspgou.cms.manager.ShopDictionaryTypeMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopDictionaryTypeMngImpl
/*    */   implements ShopDictionaryTypeMng
/*    */ {
/*    */   private ShopDictionaryTypeDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 25 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 26 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopDictionaryType findById(Long id) {
/* 32 */     ShopDictionaryType entity = this.dao.findById(id);
/* 33 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<ShopDictionaryType> findAll()
/*    */   {
/* 38 */     return this.dao.findAll();
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType save(ShopDictionaryType bean)
/*    */   {
/* 43 */     this.dao.save(bean);
/* 44 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType update(ShopDictionaryType bean)
/*    */   {
/* 49 */     Updater updater = new Updater(bean);
/* 50 */     ShopDictionaryType entity = this.dao.updateByUpdater(updater);
/* 51 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType deleteById(Long id)
/*    */   {
/* 56 */     ShopDictionaryType bean = this.dao.deleteById(id);
/* 57 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopDictionaryType[] deleteByIds(Long[] ids)
/*    */   {
/* 62 */     ShopDictionaryType[] beans = new ShopDictionaryType[ids.length];
/* 63 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 64 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 66 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopDictionaryTypeDao dao)
/*    */   {
/* 73 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopDictionaryTypeMngImpl
 * JD-Core Version:    0.6.0
 */