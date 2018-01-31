/*    */ package com.jspgou.plug.weixin.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.weixin.dao.WeixinMessageDao;
/*    */ import com.jspgou.plug.weixin.entity.WeixinMessage;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class WeixinMessageDaoImpl extends HibernateBaseDao<WeixinMessage, Integer>
/*    */   implements WeixinMessageDao
/*    */ {
/*    */   public Pagination getPage(Long siteId, int pageNo, int pageSize)
/*    */   {
/* 14 */     Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=false").setParam("siteId", siteId);
/* 15 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<WeixinMessage> getList(Long siteId) {
/* 19 */     Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=false order by bean.number");
/* 20 */     f.setParam("siteId", siteId);
/* 21 */     return find(f);
/*    */   }
/*    */ 
/*    */   public WeixinMessage getWelcome(Long siteId) {
/* 25 */     Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=true order by bean.number");
/* 26 */     f.setParam("siteId", siteId);
/* 27 */     List lists = find(f);
/* 28 */     if ((lists != null) && (lists.size() > 0)) {
/* 29 */       return (WeixinMessage)lists.get(0);
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   public WeixinMessage findByNumber(String number, Long siteId) {
/* 35 */     Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.number=:number order by bean.id desc");
/* 36 */     f.setParam("number", number);
/* 37 */     f.setParam("siteId", siteId);
/* 38 */     List lists = find(f);
/* 39 */     if ((lists != null) && (lists.size() > 0)) {
/* 40 */       return (WeixinMessage)lists.get(0);
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   public WeixinMessage findById(Integer id) {
/* 46 */     return (WeixinMessage)get(id);
/*    */   }
/*    */ 
/*    */   public WeixinMessage save(WeixinMessage bean) {
/* 50 */     getSession().save(bean);
/* 51 */     return bean;
/*    */   }
/*    */ 
/*    */   public WeixinMessage deleteById(Integer id) {
/* 55 */     WeixinMessage entity = (WeixinMessage)get(id);
/* 56 */     if (entity != null) {
/* 57 */       getSession().delete(entity);
/* 58 */       return entity;
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   protected Class<WeixinMessage> getEntityClass()
/*    */   {
/* 65 */     return WeixinMessage.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.dao.impl.WeixinMessageDaoImpl
 * JD-Core Version:    0.6.0
 */