/*    */ package com.jspgou.core.security;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.lang.builder.EqualsBuilder;
/*    */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*    */ 
/*    */ public class CmsShiroUser
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public Integer id;
/*    */   public String username;
/*    */ 
/*    */   public CmsShiroUser(Integer id, String username)
/*    */   {
/* 18 */     this.id = id;
/* 19 */     this.username = username;
/*    */   }
/*    */ 
/*    */   public Integer getId() {
/* 23 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 27 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getUsername() {
/* 31 */     return this.username;
/*    */   }
/*    */ 
/*    */   public void setUsername(String username) {
/* 35 */     this.username = username;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 40 */     return this.username;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 45 */     return HashCodeBuilder.reflectionHashCode(this, 
/* 46 */       new String[] { this.username });
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 51 */     return EqualsBuilder.reflectionEquals(this, obj, 
/* 52 */       new String[] { this.username });
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.CmsShiroUser
 * JD-Core Version:    0.6.0
 */