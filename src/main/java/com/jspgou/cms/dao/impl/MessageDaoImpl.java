/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.MessageDao;
/*    */ import com.jspgou.cms.entity.Message;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class MessageDaoImpl extends HibernateBaseDao<Message, Long>
/*    */   implements MessageDao
/*    */ {
/*    */   public Pagination getPage(Long sendMemberId, int pageNo, int pageSize)
/*    */   {
/* 23 */     String hql = " select msg from Message msg where 1=1 ";
/* 24 */     Finder finder = Finder.create(hql);
/* 25 */     if (sendMemberId != null) {
/* 26 */       finder.append(" and msg.msgSendUser.id=:sendMemberId").setParam(
/* 27 */         "sendMemberId", sendMemberId);
/*    */     }
/* 29 */     finder.append(" order by msg.id desc");
/* 30 */     return find(finder, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Message findById(Long id) {
/* 34 */     Message entity = (Message)get(id);
/* 35 */     return entity;
/*    */   }
/*    */ 
/*    */   public Message save(Message bean) {
/* 39 */     getSession().save(bean);
/* 40 */     return bean;
/*    */   }
/*    */ 
/*    */   public Message deleteById(Long id) {
/* 44 */     Message entity = (Message)super.get(id);
/* 45 */     if (entity != null) {
/* 46 */       getSession().delete(entity);
/*    */     }
/* 48 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Message> getEntityClass()
/*    */   {
/* 53 */     return Message.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.MessageDaoImpl
 * JD-Core Version:    0.6.0
 */