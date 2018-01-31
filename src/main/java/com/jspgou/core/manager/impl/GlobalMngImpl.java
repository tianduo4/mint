/*    */ package com.jspgou.core.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.security.encoder.PwdEncoder;
/*    */ import com.jspgou.core.dao.GlobalDao;
/*    */ import com.jspgou.core.entity.ConfigAttr;
/*    */ import com.jspgou.core.entity.Global;
/*    */ import com.jspgou.core.manager.GlobalMng;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class GlobalMngImpl
/*    */   implements GlobalMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PwdEncoder pwdEncoder;
/*    */   private GlobalDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Global get()
/*    */   {
/* 25 */     Global entity = this.dao.findById(Long.valueOf(1L));
/* 26 */     return entity;
/*    */   }
/*    */ 
/*    */   public Global findById(Long id)
/*    */   {
/* 32 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public Global update(Global bean)
/*    */   {
/* 37 */     return this.dao.update(bean);
/*    */   }
/*    */ 
/*    */   public void updateConfigAttr(ConfigAttr configAttr) {
/* 41 */     findIdkey().getAttr().putAll(configAttr.getAttr());
/*    */   }
/*    */ 
/*    */   public Global findIdkey()
/*    */   {
/* 46 */     return this.dao.findIdkey();
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(GlobalDao dao)
/*    */   {
/* 53 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Global updateGlobalPwd(Long id, String password)
/*    */   {
/* 59 */     Global entity = findById(id);
/* 60 */     if (!StringUtils.isBlank(password)) {
/* 61 */       entity.setPassword(this.pwdEncoder.encodePassword(password));
/*    */     }
/* 63 */     return entity;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.GlobalMngImpl
 * JD-Core Version:    0.6.0
 */