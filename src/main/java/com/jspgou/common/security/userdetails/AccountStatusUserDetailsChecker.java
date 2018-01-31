/*    */ package com.jspgou.common.security.userdetails;
/*    */ 
/*    */ import com.jspgou.common.security.AccountExpiredException;
/*    */ import com.jspgou.common.security.AccountStatusException;
/*    */ import com.jspgou.common.security.CredentialsExpiredException;
/*    */ import com.jspgou.common.security.DisabledException;
/*    */ import com.jspgou.common.security.LockedException;
/*    */ 
/*    */ public class AccountStatusUserDetailsChecker
/*    */   implements UserDetailsChecker
/*    */ {
/*    */   public void check(UserDetails user)
/*    */     throws AccountStatusException
/*    */   {
/* 19 */     if (!user.isAccountNonLocked()) {
/* 20 */       throw new LockedException();
/*    */     }
/*    */ 
/* 23 */     if (!user.isEnabled()) {
/* 24 */       throw new DisabledException("User is disabled", user);
/*    */     }
/*    */ 
/* 27 */     if (!user.isAccountNonExpired()) {
/* 28 */       throw new AccountExpiredException("User account has expired", user);
/*    */     }
/*    */ 
/* 31 */     if (!user.isCredentialsNonExpired())
/* 32 */       throw new CredentialsExpiredException(
/* 33 */         "User credentials have expired", user);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.userdetails.AccountStatusUserDetailsChecker
 * JD-Core Version:    0.6.0
 */