/*    */ package com.jspgou.common.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MyFileUtils
/*    */ {
/*    */   public static List<File> getFiles(File folder)
/*    */   {
/* 10 */     List files = new ArrayList();
/* 11 */     iterateFolder(folder, files);
/* 12 */     return files;
/*    */   }
/*    */ 
/*    */   private static void iterateFolder(File folder, List<File> files) {
/* 16 */     File[] flist = folder.listFiles();
/* 17 */     files.add(folder);
/* 18 */     if ((flist == null) || (flist.length == 0))
/* 19 */       files.add(folder);
/*    */     else
/* 21 */       for (File f : flist)
/* 22 */         if (f.isDirectory())
/* 23 */           iterateFolder(f, files);
/*    */         else
/* 25 */           files.add(f);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.MyFileUtils
 * JD-Core Version:    0.6.0
 */