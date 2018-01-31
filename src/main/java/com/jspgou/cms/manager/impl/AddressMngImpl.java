/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.AddressDao;
/*    */ import com.jspgou.cms.entity.Address;
/*    */ import com.jspgou.cms.manager.AddressMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class AddressMngImpl
/*    */   implements AddressMng
/*    */ {
/*    */   private AddressDao dao;
/*    */ 
/*    */   public List<Address> getListById(Long parentId)
/*    */   {
/* 23 */     return this.dao.getListById(parentId);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize) {
/* 29 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 30 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPageByParentId(Long parentId, int pageNo, int pageSize)
/*    */   {
/* 35 */     Pagination page = this.dao.getPageByParentId(parentId, pageNo, pageSize);
/* 36 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Address findById(Long id) {
/* 42 */     Address entity = this.dao.findById(id);
/* 43 */     return entity;
/*    */   }
/*    */ 
/*    */   public Address save(Address bean)
/*    */   {
/* 48 */     this.dao.save(bean);
/* 49 */     return bean;
/*    */   }
/*    */ 
/*    */   public Address update(Address bean)
/*    */   {
/* 54 */     Updater updater = new Updater(bean);
/* 55 */     Address entity = this.dao.updateByUpdater(updater);
/* 56 */     return entity;
/*    */   }
/*    */ 
/*    */   public Address deleteById(Long id)
/*    */   {
/* 61 */     Address bean = this.dao.deleteById(id);
/* 62 */     return bean;
/*    */   }
/*    */ 
/*    */   public Address[] deleteByIds(Long[] ids)
/*    */   {
/* 67 */     Address[] beans = new Address[ids.length];
/* 68 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 69 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 71 */     return beans;
/*    */   }
/*    */ 
/*    */   public Address[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 76 */     Address[] beans = new Address[ids.length];
/* 77 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 78 */       beans[i] = findById(ids[i]);
/* 79 */       beans[i].setPriority(priority[i]);
/*    */     }
/* 81 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(AddressDao dao)
/*    */   {
/* 88 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.AddressMngImpl
 * JD-Core Version:    0.6.0
 */