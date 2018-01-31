/*    */ package com.jspgou.plug.weixin.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.plug.weixin.dao.WeixinMenuDao;
/*    */ import com.jspgou.plug.weixin.entity.WeixinMenu;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class WeixinMenuDaoImpl extends HibernateBaseDao<WeixinMenu, Integer>
/*    */   implements WeixinMenuDao
/*    */ {
/*    */   public Pagination getPage(Long siteId, Integer parentId, int pageNo, int pageSize)
/*    */   {
/* 14 */     Finder f = Finder.create("select bean from WeixinMenu bean where 1=1");
/* 15 */     if (parentId != null) {
/* 16 */       f.append(" and bean.parent.id=:parentId");
/* 17 */       f.setParam("parentId", parentId);
/*    */     } else {
/* 19 */       f.append(" and bean.parent is null");
/*    */     }
/* 21 */     f.append(" and bean.site.id=:siteId");
/* 22 */     f.setParam("siteId", siteId);
/*    */ 
/* 24 */     f.setCacheable(true);
/* 25 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<WeixinMenu> getList(Long siteId, Integer count) {
/* 29 */     Finder f = Finder.create("select bean from WeixinMenu bean where 1=1");
/* 30 */     f.append(" and bean.parent is null");
/* 31 */     if (siteId != null) {
/* 32 */       f.append(" and bean.site.id=:siteId");
/* 33 */       f.setParam("siteId", siteId);
/*    */     }
/* 35 */     f.append(" order by bean.id desc ");
/* 36 */     f.setCacheable(true);
/* 37 */     f.setMaxResults(count.intValue());
/* 38 */     return find(f);
/*    */   }
/*    */ 
/*    */   public WeixinMenu findById(Integer id) {
/* 42 */     return (WeixinMenu)get(id);
/*    */   }
/*    */ 
/*    */   public WeixinMenu save(WeixinMenu bean) {
/* 46 */     getSession().save(bean);
/* 47 */     return bean;
/*    */   }
/*    */ 
/*    */   public WeixinMenu deleteById(Integer id) {
/* 51 */     WeixinMenu entity = (WeixinMenu)get(id);
/* 52 */     if (entity != null) {
/* 53 */       getSession().delete(entity);
/* 54 */       return entity;
/*    */     }
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   protected Class<WeixinMenu> getEntityClass()
/*    */   {
/* 61 */     return WeixinMenu.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.dao.impl.WeixinMenuDaoImpl
 * JD-Core Version:    0.6.0
 */