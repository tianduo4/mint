/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.core.dao.RoleDao;
/*    */ import com.jspgou.core.entity.Role;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class RoleDaoImpl extends HibernateBaseDao<Role, Integer>
/*    */   implements RoleDao
/*    */ {
/*    */   public List<Role> getList()
/*    */   {
/* 20 */     String hql = "from Role bean order by bean.priority asc";
/* 21 */     return find(hql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public Role findById(Integer id)
/*    */   {
/* 26 */     Role entity = (Role)get(id);
/* 27 */     return entity;
/*    */   }
/*    */ 
/*    */   public Role save(Role bean)
/*    */   {
/* 32 */     getSession().save(bean);
/* 33 */     return bean;
/*    */   }
/*    */ 
/*    */   public Role deleteById(Integer id)
/*    */   {
/* 38 */     Role entity = (Role)super.get(id);
/* 39 */     if (entity != null) {
/* 40 */       getSession().delete(entity);
/*    */     }
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Role> getEntityClass()
/*    */   {
/* 47 */     return Role.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.RoleDaoImpl
 * JD-Core Version:    0.6.0
 */