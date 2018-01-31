/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.StandardTypeDao;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class StandardTypeMngImpl
/*     */   implements StandardTypeMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */   private StandardTypeDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  29 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  30 */     return page;
/*     */   }
/*     */ 
/*     */   public StandardType getByField(String field)
/*     */   {
/*  35 */     return this.dao.getByField(field);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public StandardType findById(Long id) {
/*  41 */     StandardType entity = this.dao.findById(id);
/*  42 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<StandardType> getList()
/*     */   {
/*  47 */     return this.dao.getList();
/*     */   }
/*     */ 
/*     */   public List<StandardType> getList(Integer categoryId)
/*     */   {
/*  52 */     return this.dao.getList(categoryId);
/*     */   }
/*     */ 
/*     */   public StandardType save(StandardType bean)
/*     */   {
/*  57 */     bean = this.dao.save(bean);
/*  58 */     return bean;
/*     */   }
/*     */ 
/*     */   public StandardType update(StandardType bean)
/*     */   {
/*  63 */     Updater updater = new Updater(bean);
/*  64 */     StandardType entity = this.dao.updateByUpdater(updater);
/*  65 */     return entity;
/*     */   }
/*     */ 
/*     */   public StandardType deleteById(Long id)
/*     */   {
/*  70 */     StandardType bean = this.dao.deleteById(id);
/*  71 */     return bean;
/*     */   }
/*     */ 
/*     */   public StandardType[] deleteByIds(Long[] ids)
/*     */   {
/*  76 */     StandardType[] beans = new StandardType[ids.length];
/*  77 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  78 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  80 */     return beans;
/*     */   }
/*     */ 
/*     */   public StandardType[] updatePriority(Long[] wids, Integer[] priority)
/*     */   {
/*  85 */     int len = wids.length;
/*  86 */     StandardType[] beans = new StandardType[len];
/*  87 */     for (int i = 0; i < len; i++) {
/*  88 */       beans[i] = findById(wids[i]);
/*  89 */       beans[i].setPriority(priority[i]);
/*     */     }
/*  91 */     return beans;
/*     */   }
/*     */ 
/*     */   public StandardType addStandard(StandardType bean, String[] itemName, String[] itemColor, Integer[] itemPriority)
/*     */   {
/*  97 */     if (itemName != null) {
/*  98 */       int i = 0; for (int len = itemName.length; i < len; i++) {
/*  99 */         if (!StringUtils.isBlank(itemName[i])) {
/* 100 */           Standard item = new Standard();
/* 101 */           item.setName(itemName[i]);
/* 102 */           item.setColor(itemColor[i]);
/* 103 */           item.setPriority(itemPriority[i]);
/* 104 */           item.setType(bean);
/* 105 */           this.standardMng.save(item);
/*     */         }
/*     */       }
/*     */     }
/* 109 */     return bean;
/*     */   }
/*     */ 
/*     */   public StandardType updateStandard(StandardType bean, Long[] itemId, String[] itemName, String[] itemColor, Integer[] itemPriority) {
/* 113 */     Set set = bean.getStandardSet();
/* 114 */     if (itemId != null) {
/* 115 */       for (Standard s : set) {
/* 116 */         if (!Arrays.asList(itemId).contains(s.getId()))
/* 117 */           this.standardMng.deleteById(s.getId());
/*     */       }
/*     */     }
/*     */     else {
/* 121 */       for (Standard s : set) {
/* 122 */         this.standardMng.deleteById(s.getId());
/*     */       }
/*     */     }
/*     */ 
/* 126 */     if (itemName != null) {
/* 127 */       int i = 0; for (int len = itemName.length; i < len; i++) {
/* 128 */         if (!StringUtils.isBlank(itemName[i])) {
/* 129 */           if ((itemId != null) && (i < itemId.length)) {
/* 130 */             Standard item = this.standardMng.findById(itemId[i]);
/* 131 */             item.setName(itemName[i]);
/* 132 */             item.setColor(itemColor[i]);
/* 133 */             item.setPriority(itemPriority[i]);
/* 134 */             item.setType(bean);
/* 135 */             this.standardMng.update(item);
/*     */           } else {
/* 137 */             Standard item = new Standard();
/* 138 */             item.setName(itemName[i]);
/* 139 */             item.setColor(itemColor[i]);
/* 140 */             item.setPriority(itemPriority[i]);
/* 141 */             item.setType(bean);
/* 142 */             this.standardMng.save(item);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 147 */     return bean;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(StandardTypeDao dao)
/*     */   {
/* 157 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.StandardTypeMngImpl
 * JD-Core Version:    0.6.0
 */