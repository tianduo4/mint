/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopScoreDao;
/*    */ import com.jspgou.cms.entity.ShopScore;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.SQLQuery;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShopScoreDaoImpl extends HibernateBaseDao<ShopScore, Long>
/*    */   implements ShopScoreDao
/*    */ {
/*    */   public Pagination getPage(Long memberId, Boolean status, Boolean useStatus, Date startTime, Date endTime, Integer pageSize, Integer pageNo)
/*    */   {
/* 24 */     Finder f = Finder.create("select bean from ShopScore bean where 1=1 ");
/* 25 */     if (memberId != null) {
/* 26 */       f.append(" and bean.member.id=:memberId").setParam("memberId", memberId);
/*    */     }
/* 28 */     if (status != null) {
/* 29 */       f.append(" and bean.status=:status").setParam("status", status);
/*    */     }
/* 31 */     if (useStatus != null) {
/* 32 */       f.append(" and bean.useStatus=:useStatus").setParam("useStatus", useStatus);
/*    */     }
/* 34 */     if (startTime != null) {
/* 35 */       f.append(" and bean.scoreTime>:startTime");
/* 36 */       f.setParam("startTime", startTime);
/*    */     }
/* 38 */     if (endTime != null) {
/* 39 */       f.append(" and bean.scoreTime<:endTime");
/* 40 */       f.setParam("endTime", endTime);
/*    */     }
/* 42 */     f.append(" order by bean.id desc");
/* 43 */     return find(f, pageNo.intValue(), pageSize.intValue());
/*    */   }
/*    */ 
/*    */   public List<ShopScore> getlist(String code)
/*    */   {
/* 48 */     Finder f = Finder.create("select bean from ShopScore bean where 1=1 ");
/* 49 */     if (!StringUtils.isBlank(code)) {
/* 50 */       f.append(" and bean.code=:code").setParam("code", code);
/*    */     }
/* 52 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ShopScore findById(Long id)
/*    */   {
/* 57 */     ShopScore entity = (ShopScore)get(id);
/* 58 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopScore save(ShopScore bean)
/*    */   {
/* 63 */     getSession().save(bean);
/* 64 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopScore deleteById(Long id)
/*    */   {
/* 69 */     ShopScore entity = (ShopScore)super.get(id);
/* 70 */     if (entity != null) {
/* 71 */       getSession().delete(entity);
/*    */     }
/* 73 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ShopScore> getEntityClass()
/*    */   {
/* 78 */     return ShopScore.class;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 83 */     String sql = "delete from jc_shop_score where member_id = " + memberId;
/* 84 */     getSession().createSQLQuery(sql).executeUpdate();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopScoreDaoImpl
 * JD-Core Version:    0.6.0
 */