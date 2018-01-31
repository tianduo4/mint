/*    */ package com.jspgou.core.entity;
/*    */ 
/*    */ import com.jspgou.core.entity.base.BaseUser;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class User extends BaseUser
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void init()
/*    */   {
/* 16 */     if (StringUtils.isBlank(getEmail())) {
/* 17 */       setEmail(null);
/*    */     }
/* 19 */     if (getCreateTime() == null) {
/* 20 */       setCreateTime(new Date());
/*    */     }
/* 22 */     setLoginCount(Long.valueOf(0L));
/*    */   }
/*    */ 
/*    */   public User()
/*    */   {
/*    */   }
/*    */ 
/*    */   public User(Long id)
/*    */   {
/* 33 */     super(id);
/*    */   }
/*    */ 
/*    */   public User(Long id, String username, String password, Date createTime, Long long2, Integer errCount)
/*    */   {
/* 50 */     super(id, 
/* 46 */       username, 
/* 47 */       password, 
/* 48 */       createTime, 
/* 49 */       long2, 
/* 50 */       errCount);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.User
 * JD-Core Version:    0.6.0
 */