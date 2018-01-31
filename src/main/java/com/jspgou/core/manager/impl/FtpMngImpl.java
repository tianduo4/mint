/*    */ package com.jspgou.core.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.core.dao.FtpDao;
/*    */ import com.jspgou.core.entity.Ftp;
/*    */ import com.jspgou.core.manager.FtpMng;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class FtpMngImpl
/*    */   implements FtpMng
/*    */ {
/*    */   private FtpDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<Ftp> getList()
/*    */   {
/* 19 */     return this.dao.getList();
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public Ftp findById(Integer id) {
/* 24 */     Ftp entity = this.dao.findById(id);
/* 25 */     return entity;
/*    */   }
/*    */ 
/*    */   public Ftp save(Ftp bean) {
/* 29 */     this.dao.save(bean);
/* 30 */     return bean;
/*    */   }
/*    */ 
/*    */   public Ftp update(Ftp bean) {
/* 34 */     Updater updater = new Updater(bean);
/* 35 */     Ftp entity = this.dao.updateByUpdater(updater);
/* 36 */     return entity;
/*    */   }
/*    */ 
/*    */   public Ftp deleteById(Integer id) {
/* 40 */     Ftp bean = this.dao.deleteById(id);
/* 41 */     return bean;
/*    */   }
/*    */ 
/*    */   public Ftp[] deleteByIds(Integer[] ids) {
/* 45 */     Ftp[] beans = new Ftp[ids.length];
/* 46 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 47 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 49 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(FtpDao dao)
/*    */   {
/* 56 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.FtpMngImpl
 * JD-Core Version:    0.6.0
 */