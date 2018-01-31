/*    */ package com.jspgou.core.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.core.dao.RoleDao;
/*    */ import com.jspgou.core.entity.Role;
/*    */ import com.jspgou.core.manager.RoleMng;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class RoleMngImpl
/*    */   implements RoleMng
/*    */ {
/*    */   private RoleDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<Role> getList()
/*    */   {
/* 24 */     return this.dao.getList();
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Role findById(Integer id) {
/* 30 */     Role entity = this.dao.findById(id);
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   public Role save(Role bean, Set<String> perms)
/*    */   {
/* 36 */     bean.setPerms(perms);
/* 37 */     this.dao.save(bean);
/* 38 */     return bean;
/*    */   }
/*    */ 
/*    */   public Role update(Role bean, Set<String> perms)
/*    */   {
/* 43 */     Updater updater = new Updater(bean);
/* 44 */     bean = this.dao.updateByUpdater(updater);
/* 45 */     bean.getPerms().clear();
/* 46 */     bean.setPerms(perms);
/* 47 */     return bean;
/*    */   }
/*    */ 
/*    */   public Role deleteById(Integer id)
/*    */   {
/* 52 */     Role bean = this.dao.deleteById(id);
/* 53 */     return bean;
/*    */   }
/*    */ 
/*    */   public Role[] deleteByIds(Integer[] ids)
/*    */   {
/* 58 */     Role[] beans = new Role[ids.length];
/* 59 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 60 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 62 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(RoleDao dao)
/*    */   {
/* 69 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.RoleMngImpl
 * JD-Core Version:    0.6.0
 */