/*    */ package com.jspgou.cms.web.threadvariable;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopMemberGroup;
/*    */ 
/*    */ public class GroupThread
/*    */ {
/*  9 */   private static ThreadLocal<ShopMemberGroup> instance = new ThreadLocal();
/*    */ 
/*    */   public static ShopMemberGroup get() {
/* 12 */     return (ShopMemberGroup)instance.get();
/*    */   }
/*    */ 
/*    */   public static void set(ShopMemberGroup group) {
/* 16 */     instance.set(group);
/*    */   }
/*    */ 
/*    */   public static void remove() {
/* 20 */     instance.remove();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.threadvariable.GroupThread
 * JD-Core Version:    0.6.0
 */