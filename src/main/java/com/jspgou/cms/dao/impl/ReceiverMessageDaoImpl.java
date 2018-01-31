/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ReceiverMessageDao;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ReceiverMessageDaoImpl extends HibernateBaseDao<ReceiverMessage, Long>
/*     */   implements ReceiverMessageDao
/*     */ {
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  19 */     Criteria crit = createCriteria(new Criterion[0]);
/*  20 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/*  21 */     return page;
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long sendMemberId, int pageNo, Integer box, int pageSize) {
/*  25 */     String hql = " select msg from ReceiverMessage msg where 1=1 ";
/*  26 */     Finder finder = Finder.create(hql);
/*  27 */     if (sendMemberId != null) {
/*  28 */       finder.append(" and msg.msgReceiverUser.id=:sendMemberId").setParam(
/*  29 */         "sendMemberId", sendMemberId);
/*     */     }
/*  31 */     if (box != null) {
/*  32 */       finder.append(" and msg.msgBox =:box").setParam("box", box);
/*     */     }
/*  34 */     finder.append(" order by msg.id desc");
/*  35 */     return find(finder, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable, int pageNo, int pageSize)
/*     */   {
/*  43 */     String hql = " select msg from ReceiverMessage msg where 1=1 ";
/*  44 */     Finder finder = Finder.create(hql);
/*     */ 
/*  46 */     if ((sendMemberId != null) && (receiverMemberId != null)) {
/*  47 */       finder.append(" and ((msg.msgReceiverUser.id=:receiverMemberId and msg.msgBox =:box) or (msg.msgSendUser.id=:sendMemberId  and msg.msgBox =:box) )")
/*  48 */         .setParam("sendMemberId", sendMemberId).setParam(
/*  49 */         "receiverMemberId", receiverMemberId).setParam("box", 
/*  50 */         box);
/*     */     } else {
/*  52 */       if (sendMemberId != null) {
/*  53 */         finder.append(" and msg.msgSendUser.id=:sendMemberId").setParam(
/*  54 */           "sendMemberId", sendMemberId);
/*     */       }
/*  56 */       if (receiverMemberId != null) {
/*  57 */         finder.append(" and msg.msgReceiverUser.id=:receiverMemberId")
/*  58 */           .setParam("receiverMemberId", receiverMemberId);
/*     */       }
/*  60 */       if (box != null) {
/*  61 */         finder.append(" and msg.msgBox =:box").setParam("box", box);
/*     */       }
/*     */     }
/*  64 */     if (StringUtils.isNotBlank(title)) {
/*  65 */       finder.append(" and msg.msgTitle like:title").setParam("title", 
/*  66 */         "%" + title + "%");
/*     */     }
/*  68 */     if (sendBeginTime != null) {
/*  69 */       finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
/*  70 */         "sendBeginTime", sendBeginTime);
/*     */     }
/*  72 */     if (sendEndTime != null) {
/*  73 */       finder.append(" and msg.sendTime <=:sendEndTime").setParam(
/*  74 */         "sendEndTime", sendEndTime);
/*     */     }
/*  76 */     if (status != null) {
/*  77 */       if (status.booleanValue())
/*  78 */         finder.append(" and msg.msgStatus =true");
/*     */       else {
/*  80 */         finder.append(" and msg.msgStatus =false");
/*     */       }
/*     */     }
/*  83 */     finder.append(" order by msg.id desc");
/*     */ 
/*  85 */     return find(finder, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<ReceiverMessage> getList(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable)
/*     */   {
/*  92 */     String hql = " select msg from ReceiverMessage msg where 1=1  ";
/*  93 */     Finder finder = Finder.create(hql);
/*     */ 
/*  95 */     if ((sendMemberId != null) && (receiverMemberId != null)) {
/*  96 */       finder
/*  97 */         .append(
/*  98 */         " and ((msg.msgReceiverUser.id=:receiverMemberId and msg.msgBox =:box) or (msg.msgSendUser.id=:sendMemberId  and msg.msgBox =:box) )")
/*  99 */         .setParam("sendMemberId", sendMemberId).setParam(
/* 100 */         "receiverMemberId", receiverMemberId).setParam("box", 
/* 101 */         box);
/*     */     } else {
/* 103 */       if (sendMemberId != null) {
/* 104 */         finder.append(" and msg.msgSendUser.id=:sendMemberId").setParam(
/* 105 */           "sendMemberId", sendMemberId);
/*     */       }
/* 107 */       if (receiverMemberId != null) {
/* 108 */         finder.append(" and msg.msgReceiverUser.id=:receiverMemberId")
/* 109 */           .setParam("receiverMemberId", receiverMemberId);
/*     */       }
/* 111 */       if (box != null) {
/* 112 */         finder.append(" and msg.msgBox =:box").setParam("box", box);
/*     */       }
/*     */     }
/* 115 */     if (StringUtils.isNotBlank(title)) {
/* 116 */       finder.append(" and msg.msgTitle like:title").setParam("title", 
/* 117 */         "%" + title + "%");
/*     */     }
/* 119 */     if (sendBeginTime != null) {
/* 120 */       finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
/* 121 */         "sendBeginTime", sendBeginTime);
/*     */     }
/* 123 */     if (sendEndTime != null) {
/* 124 */       finder.append(" and msg.sendTime <=:sendEndTime").setParam(
/* 125 */         "sendEndTime", sendEndTime);
/*     */     }
/* 127 */     if (status != null) {
/* 128 */       if (status.booleanValue())
/* 129 */         finder.append(" and msg.msgStatus =true");
/*     */       else {
/* 131 */         finder.append(" and msg.msgStatus =false");
/*     */       }
/*     */     }
/* 134 */     finder.append(" order by msg.id desc");
/* 135 */     return find(finder);
/*     */   }
/*     */ 
/*     */   public Pagination getUnreadPage(Long sendMemberId, int pageNo, int pageSize) {
/* 139 */     String hql = " select msg from ReceiverMessage msg where 1=1 ";
/* 140 */     Finder finder = Finder.create(hql);
/* 141 */     if (sendMemberId != null) {
/* 142 */       finder.append(" and msg.msgReceiverUser.id=:sendMemberId").setParam(
/* 143 */         "sendMemberId", sendMemberId);
/*     */     }
/*     */ 
/* 146 */     finder.append(" and msg.msgStatus=:unread").setParam("unread", Boolean.valueOf(false));
/* 147 */     finder.append(" order by msg.id desc");
/* 148 */     return find(finder, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public ReceiverMessage findById(Long id) {
/* 152 */     ReceiverMessage entity = (ReceiverMessage)get(id);
/* 153 */     return entity;
/*     */   }
/*     */ 
/*     */   public ReceiverMessage save(ReceiverMessage bean) {
/* 157 */     getSession().save(bean);
/* 158 */     return bean;
/*     */   }
/*     */ 
/*     */   public ReceiverMessage deleteById(Long id) {
/* 162 */     ReceiverMessage entity = (ReceiverMessage)super.get(id);
/* 163 */     if (entity != null) {
/* 164 */       getSession().delete(entity);
/*     */     }
/* 166 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<ReceiverMessage> getEntityClass()
/*     */   {
/* 171 */     return ReceiverMessage.class;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ReceiverMessageDaoImpl
 * JD-Core Version:    0.6.0
 */