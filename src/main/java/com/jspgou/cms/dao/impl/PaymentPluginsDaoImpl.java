/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PaymentPluginsDao;
/*    */ import com.jspgou.cms.entity.PaymentPlugins;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class PaymentPluginsDaoImpl extends HibernateBaseDao<PaymentPlugins, Long>
/*    */   implements PaymentPluginsDao
/*    */ {
/*    */   public List<PaymentPlugins> getList()
/*    */   {
/* 24 */     Finder f = Finder.create("from PaymentPlugins bean where 1=1");
/* 25 */     f.append(" order by bean.priority");
/* 26 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<PaymentPlugins> getList1(String platform)
/*    */   {
/* 31 */     Finder f = Finder.create("from PaymentPlugins bean where 1=1");
/* 32 */     if (StringUtils.isNotBlank(platform)) {
/* 33 */       f.append(" and bean.platform=:platform");
/* 34 */       f.setParam("platform", platform);
/*    */     }
/* 36 */     f.append(" and bean.disabled=false");
/*    */ 
/* 38 */     return find(f);
/*    */   }
/*    */ 
/*    */   public PaymentPlugins findById(Long id)
/*    */   {
/* 47 */     PaymentPlugins entity = (PaymentPlugins)get(id);
/* 48 */     return entity;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins findByCode(String code)
/*    */   {
/* 53 */     return (PaymentPlugins)findUniqueByProperty("code", code);
/*    */   }
/*    */ 
/*    */   public PaymentPlugins save(PaymentPlugins bean)
/*    */   {
/* 58 */     getSession().save(bean);
/* 59 */     return bean;
/*    */   }
/*    */ 
/*    */   public PaymentPlugins deleteById(Long id)
/*    */   {
/* 64 */     PaymentPlugins entity = (PaymentPlugins)super.get(id);
/* 65 */     if (entity != null) {
/* 66 */       getSession().delete(entity);
/*    */     }
/* 68 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<PaymentPlugins> getEntityClass()
/*    */   {
/* 73 */     return PaymentPlugins.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.PaymentPluginsDaoImpl
 * JD-Core Version:    0.6.0
 */