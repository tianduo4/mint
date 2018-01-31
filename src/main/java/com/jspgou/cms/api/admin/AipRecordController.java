/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ApiRecord;
/*     */ import com.jspgou.cms.manager.ApiRecordMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class AipRecordController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiRecordMng manager;
/*     */ 
/*     */   @RequestMapping({"/apiRecord/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  40 */     String body = "\"\"";
/*  41 */     String message = "\"success\"";
/*  42 */     int code = 200;
/*     */     try
/*     */     {
/*  45 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  47 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  48 */       if (errors.hasErrors()) {
/*  49 */         code = 202;
/*  50 */         message = "\"param error\"";
/*     */       } else {
/*  52 */         Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), pageSize.intValue());
/*  53 */         List apiRecord = pagination.getList();
/*  54 */         JSONArray jsons = new JSONArray();
/*  55 */         for (ApiRecord api : apiRecord) {
/*  56 */           jsons.add(api.converToJson());
/*     */         }
/*  58 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*  62 */       code = 100;
/*  63 */       message = "\"system exception\"";
/*     */     }
/*  65 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  66 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/apiRecord/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  78 */     String body = "\"\"";
/*  79 */     String message = "\"success\"";
/*  80 */     int code = 200;
/*     */     try
/*     */     {
/*  84 */       if (!StringUtils.isNotBlank(ids)) {
/*  85 */         code = 202;
/*  86 */         message = "\"param error\"";
/*     */       } else {
/*  88 */         String[] str = ids.split(",");
/*  89 */         Long[] id = new Long[str.length];
/*  90 */         for (int i = 0; i < str.length; i++) {
/*  91 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*  93 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/*  96 */       ExceptionUtil.convertException(response, request, e);
/*  97 */       return;
/*     */     }
/*  99 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 100 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.AipRecordController
 * JD-Core Version:    0.6.0
 */