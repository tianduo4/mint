/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.WebserviceDao;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.Webservice;
/*     */ import com.jspgou.cms.entity.WebserviceParam;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @org.springframework.stereotype.Service
/*     */ @Transactional
/*     */ public class WebserviceMngImpl
/*     */   implements WebserviceMng
/*     */ {
/*     */   private WebserviceDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  29 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  30 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Webservice> getList(String type) {
/*  36 */     return this.dao.getList(type);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public boolean hashWebservice(String type)
/*     */   {
/*  43 */     return getList(type).size() > 0;
/*     */   }
/*     */ 
/*     */   public String callWebService(Webservice webservice, Map<String, String> paramsValues)
/*     */   {
/*  51 */     String endpoint = webservice.getAddress();
/*  52 */     org.apache.axis.client.Service service = new org.apache.axis.client.Service();
/*     */ 
/*  54 */     String res = null;
/*     */     try {
/*  56 */       Call call = (Call)service.createCall();
/*  57 */       call.setTargetEndpointAddress(endpoint);
/*  58 */       call.setOperationName(new QName(webservice.getTargetNamespace(), webservice.getOperate()));
/*  59 */       List params = webservice.getParams();
/*  60 */       Object[] values = new Object[params.size()];
/*  61 */       for (int i = 0; i < params.size(); i++) {
/*  62 */         WebserviceParam p = (WebserviceParam)params.get(i);
/*  63 */         String defaultValue = p.getDefaultValue();
/*  64 */         String pValue = (String)paramsValues.get(p.getParamName());
/*  65 */         if (StringUtils.isBlank(pValue))
/*  66 */           values[i] = defaultValue;
/*     */         else {
/*  68 */           values[i] = pValue;
/*     */         }
/*     */       }
/*  71 */       res = (String)call.invoke(values);
/*     */     } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*     */     }
/*  75 */     return res;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Webservice findById(Integer id) {
/*  81 */     Webservice entity = this.dao.findById(id);
/*  82 */     return entity;
/*     */   }
/*     */ 
/*     */   public Webservice save(Webservice bean, String[] paramName, String[] defaultValue)
/*     */   {
/*  88 */     bean = this.dao.save(bean);
/*     */ 
/*  90 */     if ((paramName != null) && (paramName.length > 0)) {
/*  91 */       int i = 0; for (int len = paramName.length; i < len; i++) {
/*  92 */         if (!StringUtils.isBlank(paramName[i])) {
/*  93 */           bean.addToParams(paramName[i], defaultValue[i]);
/*     */         }
/*     */       }
/*     */     }
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   public Webservice update(Webservice bean, String[] paramName, String[] defaultValue)
/*     */   {
/* 103 */     Updater updater = new Updater(bean);
/* 104 */     Webservice entity = this.dao.updateByUpdater(updater);
/* 105 */     entity.getParams().clear();
/* 106 */     if ((paramName != null) && (paramName.length > 0)) {
/* 107 */       int i = 0; for (int len = paramName.length; i < len; i++) {
/* 108 */         if (!StringUtils.isBlank(paramName[i])) {
/* 109 */           entity.addToParams(paramName[i], defaultValue[i]);
/*     */         }
/*     */       }
/*     */     }
/* 113 */     return entity;
/*     */   }
/*     */ 
/*     */   public Webservice deleteById(Integer id)
/*     */   {
/* 118 */     Webservice bean = this.dao.deleteById(id);
/* 119 */     return bean;
/*     */   }
/*     */ 
/*     */   public Webservice[] deleteByIds(Integer[] ids)
/*     */   {
/* 124 */     Webservice[] beans = new Webservice[ids.length];
/* 125 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 126 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 128 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(WebserviceDao dao)
/*     */   {
/* 135 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void callWebService(String operate, Map<String, String> params)
/*     */   {
/* 140 */     List<Webservice> list = getList(operate);
/* 141 */     for (Webservice s : list)
/* 142 */       callWebService(s, params);
/*     */   }
/*     */ 
/*     */   public void callWebService(String admin, String username, String password, String email, ShopMember shopmember, String operate)
/*     */   {
/* 150 */     if (hashWebservice(operate)) {
/* 151 */       Map paramsValues = new HashMap();
/* 152 */       paramsValues.put("username", username);
/* 153 */       paramsValues.put("password", password);
/* 154 */       paramsValues.put("admin", admin);
/* 155 */       if (StringUtils.isNotBlank(email)) {
/* 156 */         paramsValues.put("email", email);
/*     */       }
/* 158 */       if (shopmember != null) {
/* 159 */         if (StringUtils.isNotBlank(shopmember.getRealName())) {
/* 160 */           paramsValues.put("realname", shopmember.getRealName());
/*     */         }
/* 162 */         if (shopmember.getGender() != null) {
/* 163 */           paramsValues.put("sex", shopmember.getGender().toString());
/*     */         }
/* 165 */         if (StringUtils.isNotBlank(shopmember.getMobile())) {
/* 166 */           paramsValues.put("tel", shopmember.getMobile());
/*     */         }
/*     */       }
/* 169 */       callWebService(operate, paramsValues);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.WebserviceMngImpl
 * JD-Core Version:    0.6.0
 */