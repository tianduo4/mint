/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CustomerServiceDao;
/*    */ import com.jspgou.cms.entity.CustomerService;
/*    */ import com.jspgou.cms.manager.CustomerServiceMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class CustomerServiceMngImpl
/*    */   implements CustomerServiceMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private CustomerServiceDao dao;
/*    */ 
/*    */   public CustomerService findById(Long id)
/*    */   {
/* 24 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public Pagination getPagination(Boolean disable, int pageNo, int pageSize)
/*    */   {
/* 29 */     return this.dao.getPagination(disable, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<CustomerService> getList()
/*    */   {
/* 34 */     return this.dao.getList(Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   public CustomerService update(CustomerService bean)
/*    */   {
/* 39 */     Updater updater = new Updater(bean);
/* 40 */     CustomerService entity = this.dao.updateByUpdater(updater);
/* 41 */     return entity;
/*    */   }
/*    */ 
/*    */   public CustomerService save(CustomerService bean)
/*    */   {
/* 46 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public CustomerService[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 51 */     CustomerService[] beans = new CustomerService[ids.length];
/* 52 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 53 */       beans[i] = findById(ids[i]);
/* 54 */       beans[i].setPriority(priority[i]);
/*    */     }
/* 56 */     return beans;
/*    */   }
/*    */ 
/*    */   public CustomerService[] deleteByIds(Long[] ids)
/*    */   {
/* 61 */     CustomerService[] beans = new CustomerService[ids.length];
/* 62 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 63 */       beans[i] = this.dao.deleteById(ids[i]);
/*    */     }
/* 65 */     return beans;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CustomerServiceMngImpl
 * JD-Core Version:    0.6.0
 */