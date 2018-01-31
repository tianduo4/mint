/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.core.dao.FtpDao;
/*    */ import com.jspgou.core.entity.Ftp;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class FtpDaoImpl extends HibernateBaseDao<Ftp, Integer>
/*    */   implements FtpDao
/*    */ {
/*    */   public List<Ftp> getList()
/*    */   {
/* 13 */     String hql = "from Ftp bean";
/* 14 */     return find(hql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public Ftp findById(Integer id) {
/* 18 */     Ftp entity = (Ftp)get(id);
/* 19 */     return entity;
/*    */   }
/*    */ 
/*    */   public Ftp save(Ftp bean) {
/* 23 */     getSession().save(bean);
/* 24 */     return bean;
/*    */   }
/*    */ 
/*    */   public Ftp deleteById(Integer id) {
/* 28 */     Ftp entity = (Ftp)super.get(id);
/* 29 */     if (entity != null) {
/* 30 */       getSession().delete(entity);
/*    */     }
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Ftp> getEntityClass()
/*    */   {
/* 37 */     return Ftp.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.FtpDaoImpl
 * JD-Core Version:    0.6.0
 */