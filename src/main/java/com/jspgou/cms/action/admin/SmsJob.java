/*    */ package com.jspgou.cms.action.admin;
/*    */ 
/*    */ import com.jspgou.cms.manager.OrderMng;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class SmsJob
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private OrderMng manager;
/*    */ 
/*    */   public void execute()
/*    */   {
/* 12 */     this.manager.abolishOrder();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.SmsJob
 * JD-Core Version:    0.6.0
 */