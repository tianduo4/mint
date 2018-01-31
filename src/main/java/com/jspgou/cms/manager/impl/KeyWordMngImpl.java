/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.KeyWordDao;
/*    */ import com.jspgou.cms.entity.KeyWord;
/*    */ import com.jspgou.cms.manager.KeyWordMng;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class KeyWordMngImpl
/*    */   implements KeyWordMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private KeyWordDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<KeyWord> getAllList()
/*    */   {
/* 21 */     List list = this.dao.getAllList();
/* 22 */     return list;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public KeyWord findById(Integer id) {
/* 28 */     KeyWord entity = this.dao.findById(id);
/* 29 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<KeyWord> findKeyWord(Integer count) {
/* 33 */     return this.dao.findKeyWord(count);
/*    */   }
/*    */ 
/*    */   public KeyWord save(String keyword) {
/* 37 */     List list = getKeyWordContent(keyword);
/* 38 */     KeyWord bean = null;
/* 39 */     if (list.isEmpty()) {
/* 40 */       bean = new KeyWord();
/* 41 */       bean.setKeyword(keyword);
/* 42 */       bean.setTimes(Integer.valueOf(1));
/* 43 */       this.dao.save(bean);
/*    */     } else {
/* 45 */       bean = (KeyWord)list.iterator().next();
/* 46 */       bean.setTimes(Integer.valueOf(bean.getTimes().intValue() + 1));
/*    */     }
/* 48 */     return bean;
/*    */   }
/*    */ 
/*    */   public List<KeyWord> getKeyWordContent(String keyWord)
/*    */   {
/* 53 */     return this.dao.getKeyWordContent(keyWord);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.KeyWordMngImpl
 * JD-Core Version:    0.6.0
 */