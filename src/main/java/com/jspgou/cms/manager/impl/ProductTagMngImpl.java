/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductTagDao;
/*    */ import com.jspgou.cms.entity.ProductTag;
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.cms.manager.ProductTagMng;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ import org.springframework.util.Assert;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ProductTagMngImpl
/*    */   implements ProductTagMng
/*    */ {
/*    */   private ProductMng productMng;
/*    */   private ProductTagDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<ProductTag> getList(Long webId)
/*    */   {
/* 24 */     List list = this.dao.getList(webId);
/* 25 */     return list;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ProductTag findById(Long id) {
/* 31 */     ProductTag entity = this.dao.findById(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductTag save(ProductTag bean)
/*    */   {
/* 37 */     bean.init();
/* 38 */     this.dao.save(bean);
/* 39 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductTag[] updateTagName(Long[] ids, String[] tagNames)
/*    */   {
/* 44 */     Assert.notEmpty(ids);
/* 45 */     Assert.notEmpty(tagNames);
/* 46 */     if (ids.length != tagNames.length) {
/* 47 */       throw new IllegalArgumentException(
/* 48 */         "ids length not equals tagNames length");
/*    */     }
/* 50 */     ProductTag[] tags = new ProductTag[ids.length];
/*    */ 
/* 52 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 53 */       ProductTag tag = findById(ids[i]);
/* 54 */       tag.setName(tagNames[i]);
/* 55 */       tags[i] = tag;
/*    */     }
/* 57 */     return tags;
/*    */   }
/*    */ 
/*    */   public ProductTag[] deleteByIds(Long[] ids)
/*    */   {
/* 62 */     ProductTag[] beans = new ProductTag[ids.length];
/* 63 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 64 */       beans[i] = findById(ids[i]);
/*    */     }
/* 66 */     this.productMng.deleteTagAssociation(beans);
/* 67 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 68 */       beans[i] = this.dao.deleteById(ids[i]);
/*    */     }
/* 70 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setProductMng(ProductMng productMng)
/*    */   {
/* 77 */     this.productMng = productMng;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ProductTagDao dao)
/*    */   {
/* 84 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductTagMngImpl
 * JD-Core Version:    0.6.0
 */