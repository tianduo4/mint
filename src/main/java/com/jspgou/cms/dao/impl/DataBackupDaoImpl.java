/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.DataBackupDao;
/*    */ import com.jspgou.cms.entity.DataBackup;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class DataBackupDaoImpl extends HibernateBaseDao<DataBackup, Integer>
/*    */   implements DataBackupDao
/*    */ {
/*    */   protected Class<DataBackup> getEntityClass()
/*    */   {
/* 20 */     return DataBackup.class;
/*    */   }
/*    */ 
/*    */   public DataBackup getDataBackup()
/*    */   {
/* 26 */     String hql = " from DataBackup";
/* 27 */     List list = getSession().createQuery(hql).list();
/* 28 */     if (list.size() == 0) {
/* 29 */       return null;
/*    */     }
/* 31 */     return (DataBackup)list.get(0);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.DataBackupDaoImpl
 * JD-Core Version:    0.6.0
 */