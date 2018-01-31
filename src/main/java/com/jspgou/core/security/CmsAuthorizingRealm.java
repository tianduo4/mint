/*    */ package com.jspgou.core.security;
/*    */ 
/*    */ import com.jspgou.core.entity.User;
/*    */ import com.jspgou.core.manager.UserMng;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.shiro.authc.AuthenticationException;
/*    */ import org.apache.shiro.authc.AuthenticationInfo;
/*    */ import org.apache.shiro.authc.AuthenticationToken;
/*    */ import org.apache.shiro.authc.SimpleAuthenticationInfo;
/*    */ import org.apache.shiro.authc.UsernamePasswordToken;
/*    */ import org.apache.shiro.authz.AuthorizationInfo;
/*    */ import org.apache.shiro.authz.SimpleAuthorizationInfo;
/*    */ import org.apache.shiro.realm.AuthorizingRealm;
/*    */ import org.apache.shiro.subject.PrincipalCollection;
/*    */ import org.apache.shiro.subject.SimplePrincipalCollection;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class CmsAuthorizingRealm extends AuthorizingRealm
/*    */ {
/*    */   protected UserMng cmsUserMng;
/*    */ 
/*    */   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
/*    */     throws AuthenticationException
/*    */   {
/* 32 */     UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
/* 33 */     User user = this.cmsUserMng.getByUsername(token.getUsername());
/* 34 */     if (user != null) {
/* 35 */       return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */   protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
/*    */   {
/* 46 */     SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
/* 47 */     Set allPerms = new HashSet();
/* 48 */     allPerms.clear();
/* 49 */     allPerms.add("*");
/* 50 */     auth.setStringPermissions(allPerms);
/* 51 */     return auth;
/*    */   }
/*    */ 
/*    */   public void removeUserAuthorizationInfoCache(String username) {
/* 55 */     SimplePrincipalCollection pc = new SimplePrincipalCollection();
/* 56 */     pc.add(username, super.getName());
/* 57 */     super.clearCachedAuthorizationInfo(pc);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setUserMng(UserMng cmsUserMng)
/*    */   {
/* 64 */     this.cmsUserMng = cmsUserMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.CmsAuthorizingRealm
 * JD-Core Version:    0.6.0
 */