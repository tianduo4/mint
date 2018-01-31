/*    */ package com.jspgou.core.tpl;
/*    */ 
/*    */ public class ParentDirIsFileExceptioin extends RuntimeException
/*    */ {
/*    */   private String parentDir;
/*    */ 
/*    */   public ParentDirIsFileExceptioin(String parentDir)
/*    */   {
/* 21 */     this.parentDir = parentDir;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 26 */     return "parent directory is a file: " + this.parentDir;
/*    */   }
/*    */ 
/*    */   public String getParentDir()
/*    */   {
/* 35 */     return this.parentDir;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.tpl.ParentDirIsFileExceptioin
 * JD-Core Version:    0.6.0
 */