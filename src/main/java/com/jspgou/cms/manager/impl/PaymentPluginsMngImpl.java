/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PaymentPluginsDao;
/*    */ import com.jspgou.cms.entity.PaymentPlugins;
/*    */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class PaymentPluginsMngImpl
/*    */   implements PaymentPluginsMng
/*    */ {
/*    */   private PaymentPluginsDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<PaymentPlugins> getList()
/*    */   {
/* 24 */     return this.dao.getList();
/*    */   }
/*    */ 
/*    */   public List<PaymentPlugins> getList1(String platform)
/*    */   {
/* 29 */     return this.dao.getList1(platform);
/*    */   }
/*    */ 
/*    */   public PaymentPlugins[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 34 */     PaymentPlugins[] beans = new PaymentPlugins[ids.length];
/* 35 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 36 */       beans[i] = findById(ids[i]);
/* 37 */       beans[i].setPriority(priority[i]);
/*    */     }
/* 39 */     return beans;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins findByCode(String code)
/*    */   {
/* 44 */     return this.dao.findByCode(code);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public PaymentPlugins findById(Long id) {
/* 50 */     PaymentPlugins entity = this.dao.findById(id);
/* 51 */     return entity;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins save(PaymentPlugins bean)
/*    */   {
/* 56 */     this.dao.save(bean);
/* 57 */     return bean;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins update(PaymentPlugins bean)
/*    */   {
/* 62 */     Updater updater = new Updater(bean);
/* 63 */     PaymentPlugins entity = this.dao.updateByUpdater(updater);
/* 64 */     return entity;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins deleteById(Long id)
/*    */   {
/* 69 */     PaymentPlugins bean = this.dao.deleteById(id);
/* 70 */     return bean;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins[] deleteByIds(Long[] ids)
/*    */   {
/* 75 */     PaymentPlugins[] beans = new PaymentPlugins[ids.length];
/* 76 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 77 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 79 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(PaymentPluginsDao dao)
/*    */   {
/* 86 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.PaymentPluginsMngImpl
 * JD-Core Version:    0.6.0
 */