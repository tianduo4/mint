/*    */ package com.jspgou.cms.action.admin.main;
/*    */ 
/*    */ import com.jspgou.cms.AdminMap;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class UnLoadAdminAct
/*    */ {
/* 25 */   private static final Logger log = LoggerFactory.getLogger(UnLoadAdminAct.class);
/*    */ 
/* 29 */   @RequestMapping(value={"/commonAdmin/v_list.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*    */   public String unLoad(HttpServletRequest request, HttpServletResponse response, ModelMap model) { Map adminMap = AdminMap.adminmap;
/* 30 */     model.addAttribute("map1", adminMap);
/* 31 */     Set<String> keySet = adminMap.keySet();
/* 32 */     for (String username : keySet) {
/* 33 */       ((Integer)adminMap.get(username)).intValue();
/*    */     }
/*    */ 
/* 37 */     return "admin/uplocklist"; }
/*    */ 
/*    */   @RequestMapping(value={"/commonAdmin/v_unlock.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String unlock(HttpServletResponse response, String username) {
/* 42 */     AdminMap.unLoadAdmin(username);
/* 43 */     ResponseUtils.renderJson(response, "解锁成功 !");
/* 44 */     return null;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.UnLoadAdminAct
 * JD-Core Version:    0.6.0
 */