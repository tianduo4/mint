/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopChannelDao;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import java.util.List;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ShopChannelDaoImpl extends HibernateBaseDao<ShopChannel, Integer>
/*     */   implements ShopChannelDao
/*     */ {
/*     */   public List<ShopChannel> getList(Long webId)
/*     */   {
/*  22 */     String hql = "from ShopChannel bean where bean.website.id=?";
/*  23 */     return find(hql, new Object[] { webId });
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getList(Long webId, Integer type)
/*     */   {
/*  29 */     String hql = "from ShopChannel bean where bean.website.id=? and bean.type=? order by bean.priority";
/*  30 */     return find(hql, new Object[] { webId, type });
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getList(Long webId, Integer type, Long idBegin, Long idEnd)
/*     */   {
/*  36 */     List channList = getSession().createQuery("from ShopChannel bean where bean.website.id=:webId and bean.type=:type and bean.id >=:idBegin and bean.id <=:idEnd")
/*  38 */       .setParameter("webId", webId).setParameter("type", type)
/*  39 */       .setParameter("idBegin", idBegin).setParameter("idEnd", idEnd).list();
/*  40 */     return channList;
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getListForParent(Long webId, Long currId)
/*     */   {
/*  46 */     Finder f = 
/*  47 */       Finder.create("select node from ShopChannel node,ShopChannel exclude");
/*  48 */     f.append(" where node.lft<exclude.lft and node.rgt>exclude.rgt");
/*  49 */     f.append(" and exclude.id=:currId and node.website.id=:webId");
/*  50 */     f.setParam("webId", webId);
/*  51 */     f.setParam("currId", currId);
/*  52 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getListForChild(Long webId, Integer parentId)
/*     */   {
/*  58 */     Finder f = 
/*  59 */       Finder.create("select node from ShopChannel node, ShopChannel parent");
/*  60 */     f.append(" where node.lft>=parent.lft and node.lft<=parent.rgt");
/*  61 */     f.append(" and parent.id=:parentId and node.website.id=:webId");
/*  62 */     f.setParam("webId", webId);
/*  63 */     f.setParam("parentId", parentId);
/*  64 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getTopList(Long webId, boolean cacheable, Integer count)
/*     */   {
/*  70 */     String hql = "from ShopChannel bean where bean.website.id=? and bean.parent.id is null order by bean.priority";
/*     */ 
/*  72 */     if (count != null) {
/*  73 */       return getSession().createQuery(hql).setParameter(0, webId).setCacheable(cacheable)
/*  74 */         .setFirstResult(0).setMaxResults(count.intValue()).list();
/*     */     }
/*  76 */     return getSession().createQuery(hql).setParameter(0, webId)
/*  77 */       .setCacheable(cacheable).list();
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getChildList(Long webId, Integer parentId)
/*     */   {
/*  94 */     Finder f = Finder.create("from ShopChannel bean");
/*  95 */     f.append(" where bean.parent.id=:parentId");
/*  96 */     f.setParam("parentId", parentId);
/*  97 */     f.append(" order by bean.priority asc,bean.id asc");
/*  98 */     return find(f);
/*     */   }
/*     */ 
/*     */   public ShopChannel getByPath(Long webId, String path)
/*     */   {
/* 103 */     String hql = "from ShopChannel bean where bean.website.id=? and bean.path=?";
/* 104 */     return (ShopChannel)findUnique(hql, new Object[] { webId, path });
/*     */   }
/*     */ 
/*     */   public ShopChannel findById(Integer id)
/*     */   {
/* 109 */     ShopChannel entity = (ShopChannel)get(id);
/* 110 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopChannel save(ShopChannel bean)
/*     */   {
/* 115 */     getSession().save(bean);
/* 116 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopChannel deleteById(Integer id)
/*     */   {
/* 121 */     ShopChannel entity = (ShopChannel)super.get(id);
/* 122 */     if (entity != null) {
/* 123 */       getSession().delete(entity);
/*     */     }
/* 125 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<ShopChannel> getEntityClass()
/*     */   {
/* 130 */     return ShopChannel.class;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShopChannelDaoImpl
 * JD-Core Version:    0.6.0
 */