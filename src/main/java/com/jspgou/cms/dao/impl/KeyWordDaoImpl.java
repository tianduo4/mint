/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.KeyWordDao;
/*    */ import com.jspgou.cms.entity.KeyWord;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class KeyWordDaoImpl extends HibernateBaseDao<KeyWord, Integer>
/*    */   implements KeyWordDao
/*    */ {
/*    */   public KeyWord findById(Integer id)
/*    */   {
/* 22 */     KeyWord entity = (KeyWord)get(id);
/* 23 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<KeyWord> findKeyWord(Integer count)
/*    */   {
/* 29 */     List list = getSession().createQuery("from KeyWord bean order by bean.times desc")
/* 30 */       .setMaxResults(count.intValue()).list();
/* 31 */     return list;
/*    */   }
/*    */ 
/*    */   public List<KeyWord> getKeyWordContent(String keyWord)
/*    */   {
/* 37 */     List keyContent = getSession().createQuery("from KeyWord bean where bean.keyword=:keyword ")
/* 38 */       .setParameter("keyword", keyWord).list();
/* 39 */     return keyContent;
/*    */   }
/*    */ 
/*    */   public KeyWord save(KeyWord bean)
/*    */   {
/* 44 */     getSession().save(bean);
/* 45 */     return bean;
/*    */   }
/*    */ 
/*    */   public KeyWord deleteById(Integer id)
/*    */   {
/* 50 */     KeyWord entity = (KeyWord)super.get(id);
/* 51 */     if (entity != null) {
/* 52 */       getSession().delete(entity);
/*    */     }
/* 54 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<KeyWord> getEntityClass()
/*    */   {
/* 59 */     return KeyWord.class;
/*    */   }
/*    */ 
/*    */   public List<KeyWord> getAllList()
/*    */   {
/* 66 */     return null;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.KeyWordDaoImpl
 * JD-Core Version:    0.6.0
 */