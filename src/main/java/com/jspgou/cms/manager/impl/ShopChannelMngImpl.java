/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ShopChannelDao;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.entity.ShopChannelContent;
/*     */ import com.jspgou.cms.manager.ShopChannelContentMng;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ShopChannelMngImpl
/*     */   implements ShopChannelMng
/*     */ {
/*     */   private ShopChannelContentMng shopChannelContentMng;
/*     */   private ShopChannelDao dao;
/*     */ 
/*     */   public List<ShopChannel> getTopList(Long webId)
/*     */   {
/*  34 */     return this.dao.getTopList(webId, false, null);
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getChildList(Long wegId, Integer parentId)
/*     */   {
/*  47 */     return this.dao.getChildList(wegId, parentId);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<ShopChannel> getList(Long webId) {
/*  53 */     List list = this.dao.getTopList(webId, false, null);
/*  54 */     List allList = new ArrayList();
/*  55 */     addChildToList(allList, list);
/*  56 */     return allList;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<ShopChannel> getArticleList(Long webId) {
/*  62 */     return this.dao.getList(webId, Integer.valueOf(2));
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<ShopChannel> getAloneChannelList(Long webId)
/*     */   {
/*  70 */     return this.dao.getList(webId, Integer.valueOf(1));
/*     */   }
/*     */ 
/*     */   public List<ShopChannel> getList(Long webId, Long idBegin, Long idEnd)
/*     */   {
/*  75 */     return this.dao.getList(webId, Integer.valueOf(2), idBegin, idEnd);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<ShopChannel> getListForParent(Long webId, Integer currId)
/*     */   {
/*  82 */     List allList = getList(webId);
/*  83 */     List childList = this.dao.getListForChild(webId, currId);
/*  84 */     allList.removeAll(childList);
/*  85 */     return allList;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<ShopChannel> getListForParentNoSort(Long webId, Long currId) {
/*  91 */     return this.dao.getListForParent(webId, currId);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<ShopChannel> getTopListForTag(Long webId, Integer count) {
/*  97 */     return this.dao.getTopList(webId, true, count);
/*     */   }
/*     */ 
/*     */   private void addChildToList(List<ShopChannel> to, Collection<ShopChannel> from)
/*     */   {
/* 103 */     for (ShopChannel chnl : from) {
/* 104 */       to.add(chnl);
/* 105 */       Collection child = chnl.getChild();
/* 106 */       if ((child != null) && (child.size() > 0))
/* 107 */         addChildToList(to, child);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ShopChannel getByPath(Long webId, String path)
/*     */   {
/* 114 */     return this.dao.getByPath(webId, path);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public ShopChannel findById(Integer id) {
/* 120 */     ShopChannel entity = this.dao.findById(id);
/* 121 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopChannel save(ShopChannel bean, Integer parentId, String content)
/*     */   {
/* 126 */     ShopChannel parent = null;
/* 127 */     if (parentId != null) {
/* 128 */       parent = findById(parentId);
/* 129 */       bean.setParent(parent);
/*     */     }
/*     */ 
/* 132 */     this.dao.save(bean);
/* 133 */     if (content != null) {
/* 134 */       this.shopChannelContentMng.save(content, bean);
/*     */     }
/* 136 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopChannel update(ShopChannel bean, Integer parentId, String content)
/*     */   {
/* 141 */     ShopChannel entity = findById(bean.getId());
/* 142 */     ShopChannelContent c = entity.getChannelContent();
/* 143 */     if (c != null)
/* 144 */       c.setContent(content);
/*     */     else {
/* 146 */       this.shopChannelContentMng.save(content, entity);
/*     */     }
/* 148 */     if (parentId != null)
/* 149 */       entity.setParent(findById(parentId));
/*     */     else {
/* 151 */       entity.setParent(null);
/*     */     }
/* 153 */     Updater updater = new Updater(bean);
/* 154 */     entity = this.dao.updateByUpdater(updater);
/* 155 */     return entity;
/*     */   }
/*     */ 
/*     */   public ShopChannel deleteById(Integer id)
/*     */   {
/* 160 */     ShopChannel bean = this.dao.deleteById(id);
/* 161 */     return bean;
/*     */   }
/*     */ 
/*     */   public ShopChannel[] deleteByIds(Integer[] ids)
/*     */   {
/* 166 */     ShopChannel[] beans = new ShopChannel[ids.length];
/* 167 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 168 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 170 */     return beans;
/*     */   }
/*     */ 
/*     */   public ShopChannel[] updatePriority(Integer[] ids, Integer[] priority)
/*     */   {
/* 184 */     ShopChannel[] beans = new ShopChannel[ids.length];
/* 185 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 186 */       beans[i] = findById(ids[i]);
/* 187 */       beans[i].setPriority(priority[i]);
/*     */     }
/* 189 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(ShopChannelDao dao)
/*     */   {
/* 197 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setShopChannelContentMng(ShopChannelContentMng shopChannelContentMng) {
/* 203 */     this.shopChannelContentMng = shopChannelContentMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopChannelMngImpl
 * JD-Core Version:    0.6.0
 */