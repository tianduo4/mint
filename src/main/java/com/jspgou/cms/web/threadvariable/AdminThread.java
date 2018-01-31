/*    */ package com.jspgou.cms.web.threadvariable;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopAdmin;
/*    */ 
/*    */ public class AdminThread
/*    */ {
/*  9 */   private static ThreadLocal<ShopAdmin> instance = new ThreadLocal();
/*    */ 
/*    */   public static ShopAdmin get() {
/* 12 */     return (ShopAdmin)instance.get();
/*    */   }
/*    */ 
/*    */   public static void set(ShopAdmin group) {
/* 16 */     instance.set(group);
/*    */   }
/*    */ 
/*    */   public static void remove() {
/* 20 */     instance.remove();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.threadvariable.AdminThread
 * JD-Core Version:    0.6.0
 */