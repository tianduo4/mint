/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseDataBackup;
/*    */ 
/*    */ public class DataBackup extends BaseDataBackup
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public DataBackup()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DataBackup(Integer id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public DataBackup(Integer id, String dataBasePath, String address, String dataBaseName, String username, String password)
/*    */   {
/* 42 */     super(id, 
/* 38 */       dataBasePath, 
/* 39 */       address, 
/* 40 */       dataBaseName, 
/* 41 */       username, 
/* 42 */       password);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.DataBackup
 * JD-Core Version:    0.6.0
 */