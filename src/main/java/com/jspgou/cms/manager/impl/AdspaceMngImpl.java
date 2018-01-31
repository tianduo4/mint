/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.AdspaceDao;
/*    */ import com.jspgou.cms.entity.Adspace;
/*    */ import com.jspgou.cms.manager.AdspaceMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class AdspaceMngImpl
/*    */   implements AdspaceMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private AdspaceDao dao;
/*    */ 
/*    */   public Adspace deleteById(Integer id)
/*    */   {
/* 23 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public Adspace[] deleteByIds(Integer[] ids)
/*    */   {
/* 28 */     Adspace[] beans = new Adspace[ids.length];
/* 29 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 30 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 32 */     return beans;
/*    */   }
/*    */ 
/*    */   public Adspace findById(Integer id)
/*    */   {
/* 38 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public List<Adspace> getList()
/*    */   {
/* 43 */     return this.dao.getList();
/*    */   }
/*    */ 
/*    */   public Adspace save(Adspace bean)
/*    */   {
/* 48 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public Adspace updateByAdspacenumb(Integer AdspaceId, Integer AdspaceNumb, Integer shopMemberId)
/*    */   {
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   public Adspace updateByUpdater(Adspace bean)
/*    */   {
/* 58 */     Updater updater = new Updater(bean);
/* 59 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.AdspaceMngImpl
 * JD-Core Version:    0.6.0
 */