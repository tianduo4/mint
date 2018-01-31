/*    */ package com.jspgou.plug.store.entity;
/*    */ 
/*    */ import com.jspgou.plug.store.entity.base.BasePlugStoreConfig;
/*    */ 
/*    */ public class PlugStoreConfig extends BasePlugStoreConfig
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public PlugStoreConfig()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig(Integer id)
/*    */   {
/* 19 */     super(id);
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig(Integer id, String serverUrl, String passwod)
/*    */   {
/* 33 */     super(id, 
/* 32 */       serverUrl, 
/* 33 */       passwod);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.store.entity.PlugStoreConfig
 * JD-Core Version:    0.6.0
 */