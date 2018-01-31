/*    */ package com.jspgou.cms;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AdminMap
/*    */ {
/* 13 */   public static Map<String, Integer> adminmap = new HashMap();
/*    */ 
/* 15 */   public static Integer getAdminMapVal(String username) { return (Integer)adminmap.get(username); }
/*    */ 
/*    */   public static void addAdminMapVal(String username)
/*    */   {
/* 19 */     if (adminmap.get(username) == null)
/* 20 */       adminmap.put(username, Integer.valueOf(1));
/*    */     else
/* 22 */       adminmap.put(username, Integer.valueOf(((Integer)adminmap.get(username)).intValue() + 1));
/*    */   }
/*    */ 
/*    */   public static void unLoadAdmin(String username)
/*    */   {
/* 27 */     adminmap.put(username, Integer.valueOf(0));
/* 28 */     adminmap.remove(username);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.AdminMap
 * JD-Core Version:    0.6.0
 */