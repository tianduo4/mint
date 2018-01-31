/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiRecordDao;
/*    */ import com.jspgou.cms.entity.ApiInfo;
/*    */ import com.jspgou.cms.entity.ApiRecord;
/*    */ import com.jspgou.cms.manager.ApiAccountMng;
/*    */ import com.jspgou.cms.manager.ApiInfoMng;
/*    */ import com.jspgou.cms.manager.ApiRecordMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ApiRecordMngImpl
/*    */   implements ApiRecordMng
/*    */ {
/*    */   private ApiRecordDao dao;
/*    */ 
/*    */   @Autowired
/*    */   private ApiAccountMng apiAccountMng;
/*    */ 
/*    */   @Autowired
/*    */   private ApiInfoMng apiInfoMng;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 24 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 25 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ApiRecord findById(Long id) {
/* 30 */     ApiRecord entity = this.dao.findById(id);
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiRecord save(ApiRecord bean) {
/* 35 */     this.dao.save(bean);
/* 36 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiRecord update(ApiRecord bean) {
/* 40 */     Updater updater = new Updater(bean);
/* 41 */     ApiRecord entity = this.dao.updateByUpdater(updater);
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiRecord deleteById(Long id) {
/* 46 */     ApiRecord bean = this.dao.deleteById(id);
/* 47 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiRecord[] deleteByIds(Long[] ids) {
/* 51 */     ApiRecord[] beans = new ApiRecord[ids.length];
/* 52 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 53 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 55 */     return beans;
/*    */   }
/*    */ 
/*    */   public void callApiRecord(String ipAddr, String appId, String apiUrl, String sign) {
/* 59 */     ApiRecord apiRecord = new ApiRecord();
/* 60 */     apiRecord.setApiAccount(this.apiAccountMng.findByAppId(appId));
/* 61 */     if (this.apiInfoMng.findByApiUrl(apiUrl) != null) {
/* 62 */       apiRecord.setApiInfo(this.apiInfoMng.findByApiUrl(apiUrl));
/*    */     } else {
/* 64 */       ApiInfo apiInfo = new ApiInfo();
/* 65 */       apiInfo.setApiName("接口");
/* 66 */       apiInfo.setApiUrl(apiUrl);
/* 67 */       apiInfo.setApiCode("ApiCode");
/* 68 */       apiInfo.setDisabled(Boolean.valueOf(false));
/* 69 */       apiInfo.setLimitCallDay(Integer.valueOf(0));
/* 70 */       apiRecord.setApiInfo(this.apiInfoMng.save(apiInfo));
/*    */     }
/* 72 */     Date now = new Timestamp(System.currentTimeMillis());
/* 73 */     apiRecord.setApiCallTime(now);
/* 74 */     apiRecord.setApiIp(ipAddr);
/* 75 */     apiRecord.setSign(sign);
/* 76 */     apiRecord.setCallTimeStamp(Long.valueOf(System.currentTimeMillis()));
/* 77 */     save(apiRecord);
/*    */   }
/*    */ 
/*    */   public ApiRecord findBySign(String sign, String appId) {
/* 81 */     return this.dao.findBySign(sign, appId);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ApiRecordDao dao)
/*    */   {
/* 88 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ApiRecordMngImpl
 * JD-Core Version:    0.6.0
 */