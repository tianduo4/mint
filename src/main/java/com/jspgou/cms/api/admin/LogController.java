/*    */ package com.jspgou.cms.api.admin;
/*    */ 
/*    */ import com.jspgou.cms.api.ApiResponse;
/*    */ import com.jspgou.cms.api.ApiValidate;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.page.SimplePage;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import com.jspgou.core.entity.Log;
/*    */ import com.jspgou.core.manager.LogMng;
/*    */ import com.jspgou.core.web.WebErrors;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import net.sf.json.JSONArray;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class LogController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private LogMng logMng;
/*    */ 
/*    */   @RequestMapping({"/log/list"})
/*    */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 41 */     String body = "\"\"";
/* 42 */     String message = "\"success\"";
/* 43 */     int code = 200;
/*    */     try {
/* 45 */       WebErrors errors = WebErrors.create(request);
/*    */ 
/* 47 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/* 48 */       if (errors.hasErrors()) {
/* 49 */         code = 202;
/* 50 */         message = "\"param error\"";
/*    */       } else {
/* 52 */         Pagination pagination = this.logMng.getPage(SimplePage.cpn(pageNo), pageSize.intValue());
/* 53 */         List logs = pagination.getList();
/* 54 */         JSONArray jsons = new JSONArray();
/* 55 */         for (Log log : logs) {
/* 56 */           jsons.add(log.converToJson());
/*    */         }
/* 58 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*    */       }
/*    */     } catch (Exception e) {
/* 61 */       e.printStackTrace();
/* 62 */       code = 100;
/* 63 */       message = "\"system exception\"";
/*    */     }
/* 65 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 66 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.LogController
 * JD-Core Version:    0.6.0
 */