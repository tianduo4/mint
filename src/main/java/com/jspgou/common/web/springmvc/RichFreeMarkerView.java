/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
/*    */ 
/*    */ public class RichFreeMarkerView extends FreeMarkerView
/*    */ {
/*    */   public static final String CONTEXT_PATH = "base";
/*    */   public static final String SSO_ENABLE = "ssoEnable";
/*    */   public static final String USER = "user";
/*    */ 
/*    */   protected void exposeHelpers(Map model, HttpServletRequest request)
/*    */     throws Exception
/*    */   {
/* 43 */     super.exposeHelpers(model, request);
/* 44 */     model.put("base", request.getContextPath());
/* 45 */     Website web = SiteUtils.getWeb(request);
/* 46 */     model.put("ssoEnable", web.getSsoEnable());
/* 47 */     model.put("user", AdminThread.get());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.RichFreeMarkerView
 * JD-Core Version:    0.6.0
 */