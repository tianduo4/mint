/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PosterDao;
/*    */ import com.jspgou.cms.entity.Poster;
/*    */ import com.jspgou.cms.manager.PosterMng;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class PosterMngImpl
/*    */   implements PosterMng
/*    */ {
/*    */   private PosterDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Poster findById(Integer id)
/*    */   {
/* 23 */     Poster entity = this.dao.findById(id);
/* 24 */     return entity;
/*    */   }
/*    */ 
/*    */   public Poster saveOrUpdate(Poster bean)
/*    */   {
/* 29 */     this.dao.saveOrUpdate(bean);
/* 30 */     return bean;
/*    */   }
/*    */ 
/*    */   public List<Poster> getPage()
/*    */   {
/* 36 */     return this.dao.getPage();
/*    */   }
/*    */ 
/*    */   public Poster update(Poster Poster)
/*    */   {
/* 41 */     return this.dao.update(Poster);
/*    */   }
/*    */ 
/*    */   public void deleteByIds(Integer[] ids)
/*    */   {
/* 46 */     for (Integer id : ids)
/* 47 */       deleteById(id);
/*    */   }
/*    */ 
/*    */   public Poster deleteById(Integer id)
/*    */   {
/* 53 */     Poster bean = this.dao.deleteById(id);
/* 54 */     return bean;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(PosterDao dao)
/*    */   {
/* 61 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public void updateByApi(Integer[] ids, String[] picUrls, String[] interUrls)
/*    */   {
/* 66 */     for (int i = 0; i < ids.length; i++)
/* 67 */       if (ids[i].intValue() == 0) {
/* 68 */         Poster poster = new Poster();
/* 69 */         poster.setPicUrl(picUrls[i]);
/* 70 */         poster.setInterUrl(interUrls[i]);
/* 71 */         poster.setTime(new Date());
/* 72 */         this.dao.saveOrUpdate(poster);
/*    */       } else {
/* 74 */         Poster poster = this.dao.findById(ids[i]);
/* 75 */         poster.setPicUrl(picUrls[i]);
/* 76 */         poster.setInterUrl(interUrls[i]);
/* 77 */         poster.setTime(new Date());
/* 78 */         this.dao.update(poster);
/*    */       }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.PosterMngImpl
 * JD-Core Version:    0.6.0
 */