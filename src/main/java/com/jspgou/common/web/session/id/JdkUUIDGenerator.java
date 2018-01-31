/*    */ package com.jspgou.common.web.session.id;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class JdkUUIDGenerator
/*    */   implements SessionIdGenerator
/*    */ {
/*    */   public String get()
/*    */   {
/* 13 */     return StringUtils.remove(UUID.randomUUID().toString(), '-');
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.session.id.JdkUUIDGenerator
 * JD-Core Version:    0.6.0
 */