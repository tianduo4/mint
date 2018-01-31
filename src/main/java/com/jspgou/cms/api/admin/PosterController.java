/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.Poster;
/*     */ import com.jspgou.cms.manager.PosterMng;
/*     */ import com.jspgou.cms.web.SignValidate;
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
/*     */ public class PosterController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PosterMng posterMng;
/*     */ 
/*     */   @RequestMapping({"/poster/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  35 */     String body = "\"\"";
/*  36 */     String message = "\"success\"";
/*  37 */     int code = 200;
/*     */     try {
/*  39 */       JSONArray jsons = new JSONArray();
/*  40 */       List<Poster> lists = this.posterMng.getPage();
/*  41 */       for (Poster poster : lists) {
/*  42 */         jsons.add(poster.converToJson());
/*     */       }
/*  44 */       body = jsons.toString();
/*     */     } catch (Exception e) {
/*  46 */       e.printStackTrace();
/*  47 */       code = 100;
/*  48 */       message = "\"system exception\"";
/*     */     }
/*  50 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  51 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/poster/update"})
/*     */   public void update(String ids, String picUrls, String interUrls, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  66 */     String body = "\"\"";
/*  67 */     String message = "\"success\"";
/*  68 */     int code = 200;
/*     */     try {
/*  70 */       WebErrors errors = WebErrors.create(request);
/*  71 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { picUrls, ids, interUrls });
/*     */ 
/*  74 */       if (errors.hasErrors()) {
/*  75 */         code = 202;
/*  76 */         message = "\"param error\"";
/*     */       } else {
/*  78 */         String[] idstrs = ids.split(",");
/*  79 */         Integer[] id = new Integer[idstrs.length];
/*  80 */         for (int i = 0; i < idstrs.length; i++) {
/*  81 */           if ((StringUtils.isEmpty(idstrs[i])) || ("0".equals(idstrs[i])))
/*  82 */             id[i] = Integer.valueOf(0);
/*     */           else {
/*  84 */             id[i] = Integer.valueOf(Integer.parseInt(idstrs[i]));
/*     */           }
/*     */         }
/*  87 */         String[] picUrl = picUrls.split(",");
/*  88 */         String[] interUrl = interUrls.split(",");
/*  89 */         if ((idstrs != null) && (picUrl != null) && (interUrl != null) && (id.length == picUrl.length) && (id.length == interUrl.length))
/*     */         {
/*  91 */           this.posterMng.updateByApi(id, picUrl, interUrl);
/*     */         } else {
/*  93 */           code = 202;
/*  94 */           message = "\"param error\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/*  98 */       e.printStackTrace();
/*  99 */       code = 100;
/* 100 */       message = "\"system exception\"";
/*     */     }
/* 102 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 103 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.PosterController
 * JD-Core Version:    0.6.0
 */