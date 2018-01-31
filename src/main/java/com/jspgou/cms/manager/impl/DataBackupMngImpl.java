/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.DataBackupDao;
/*    */ import com.jspgou.cms.entity.DataBackup;
/*    */ import com.jspgou.cms.manager.DataBackupMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class DataBackupMngImpl
/*    */   implements DataBackupMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private DataBackupDao dao;
/*    */ 
/*    */   public DataBackup getDataBackup()
/*    */   {
/* 22 */     return this.dao.getDataBackup();
/*    */   }
/*    */ 
/*    */   public DataBackup update(DataBackup bean)
/*    */   {
/* 27 */     Updater updater = new Updater(bean);
/* 28 */     DataBackup entity = this.dao.updateByUpdater(updater);
/* 29 */     return entity;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.DataBackupMngImpl
 * JD-Core Version:    0.6.0
 */