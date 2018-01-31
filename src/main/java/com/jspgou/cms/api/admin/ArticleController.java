/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.manager.ShopArticleMng;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ArticleController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopArticleMng shopArticleMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopChannelMng shopChannelMng;
/*     */ 
/*     */   @RequestMapping({"/article/list"})
/*     */   public void list(Integer cid, Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  46 */     String body = "\"\"";
/*  47 */     String message = "\"success\"";
/*  48 */     int code = 200;
/*     */     try {
/*  50 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  52 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  53 */       if (errors.hasErrors()) {
/*  54 */         code = 202;
/*  55 */         message = "\"param error\"";
/*     */       } else {
/*  57 */         Pagination pagination = this.shopArticleMng.getPage(cid, CmsThreadVariable.getSite().getId(), pageNo.intValue(), pageSize.intValue());
/*  58 */         List articles = pagination.getList();
/*  59 */         JSONArray jsons = new JSONArray();
/*  60 */         for (ShopArticle article : articles) {
/*  61 */           jsons.add(article.converToJson());
/*     */         }
/*  63 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  66 */       e.printStackTrace();
/*  67 */       code = 100;
/*  68 */       message = "\"system exception\"";
/*     */     }
/*  70 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  71 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/channelList"})
/*     */   public void getParentChannel(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  82 */     String body = "\"\"";
/*  83 */     String message = "\"success\"";
/*  84 */     int code = 200;
/*     */     try {
/*  86 */       List list = this.shopChannelMng.getArticleList(CmsThreadVariable.getSite().getId());
/*  87 */       JSONArray jsons = new JSONArray();
/*  88 */       for (ShopChannel channel : list) {
/*  89 */         jsons.add(channel.converToTreeJson());
/*     */       }
/*  91 */       body = jsons.toString();
/*     */     } catch (Exception e) {
/*  93 */       e.printStackTrace();
/*  94 */       code = 100;
/*  95 */       message = "\"system exception\"";
/*     */     }
/*  97 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  98 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/article/save"})
/*     */   public void save(ShopArticle article, Integer channelId, String content, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 114 */     String body = "\"\"";
/* 115 */     String message = "\"success\"";
/* 116 */     int code = 200;
/*     */     try {
/* 118 */       WebErrors errors = WebErrors.create(request);
/* 119 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { channelId, article.getTitle(), article.getDisabled() });
/*     */ 
/* 121 */       if (errors.hasErrors()) {
/* 122 */         code = 202;
/* 123 */         message = "\"param error\"";
/*     */       } else {
/* 125 */         article.setWebsite(CmsThreadVariable.getSite());
/* 126 */         this.shopArticleMng.save(article, channelId, content);
/*     */       }
/*     */     } catch (Exception e) {
/* 129 */       e.printStackTrace();
/* 130 */       code = 100;
/* 131 */       message = "\"system exception\"";
/*     */     }
/* 133 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 134 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 145 */     String body = "\"\"";
/* 146 */     String message = "\"success\"";
/* 147 */     int code = 200;
/*     */     try {
/* 149 */       WebErrors errors = WebErrors.create(request);
/* 150 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 152 */       if (errors.hasErrors()) {
/* 153 */         code = 202;
/* 154 */         message = "\"param error\"";
/*     */       } else {
/* 156 */         ShopArticle article = new ShopArticle();
/* 157 */         if ((id != null) && (id.longValue() != 0L)) {
/* 158 */           article = this.shopArticleMng.findById(id);
/*     */         }
/* 160 */         if (article != null) {
/* 161 */           body = article.converToJson().toString();
/*     */         } else {
/* 163 */           code = 206;
/* 164 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 168 */       e.printStackTrace();
/* 169 */       code = 100;
/* 170 */       message = "\"system exception\"";
/*     */     }
/* 172 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 173 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/article/update"})
/*     */   public void update(ShopArticle article, Integer channelId, String content, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 186 */     String body = "\"\"";
/* 187 */     String message = "\"success\"";
/* 188 */     int code = 200;
/*     */     try {
/* 190 */       WebErrors errors = WebErrors.create(request);
/* 191 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { channelId, article.getTitle(), article.getDisabled() });
/*     */ 
/* 193 */       if (errors.hasErrors()) {
/* 194 */         code = 202;
/* 195 */         message = "\"param error\"";
/*     */       } else {
/* 197 */         this.shopArticleMng.update(article, channelId, content);
/*     */       }
/*     */     } catch (Exception e) {
/* 200 */       e.printStackTrace();
/* 201 */       code = 100;
/* 202 */       message = "\"system exception\"";
/*     */     }
/* 204 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 205 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/article/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 217 */     String body = "\"\"";
/* 218 */     String message = "\"success\"";
/* 219 */     int code = 200;
/*     */     try
/*     */     {
/* 222 */       if (!StringUtils.isNotBlank(ids)) {
/* 223 */         code = 202;
/* 224 */         message = "\"param error\"";
/*     */       } else {
/* 226 */         String[] str = ids.split(",");
/* 227 */         Long[] id = new Long[str.length];
/* 228 */         for (int i = 0; i < str.length; i++) {
/* 229 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 231 */         this.shopArticleMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 234 */       ExceptionUtil.convertException(response, request, e);
/* 235 */       return;
/*     */     }
/* 237 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 238 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ArticleController
 * JD-Core Version:    0.6.0
 */