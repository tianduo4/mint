/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.DataBackup;
/*     */ import com.jspgou.cms.manager.DataBackupMng;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.ConfigAttr;
/*     */ import com.jspgou.core.entity.EmailSender;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.MessageTemplate;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.FtpMng;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ConfigAct
/*     */ {
/*  48 */   private final Logger log = LoggerFactory.getLogger(ConfigAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private DataBackupMng dataBackupMng;
/*     */ 
/*     */   @Autowired
/*     */   private FtpMng ftpMng;
/*     */ 
/*  52 */   @RequestMapping({"/config/v_global_edit.do"})
/*     */   public String globalEdit(HttpServletRequest request, ModelMap model) { model.addAttribute("global", SiteUtils.getWeb(request).getGlobal());
/*  53 */     return "config/global_edit"; }
/*     */ 
/*     */   @RequestMapping({"/config/o_global_update.do"})
/*     */   public String globalUpdate(Global global, HttpServletRequest request, ModelMap model)
/*     */   {
/*  59 */     global.setId(SiteUtils.getWeb(request).getGlobal().getId());
/*  60 */     this.globalMng.update(global);
/*  61 */     this.log.info("update Global success.");
/*  62 */     model.addAttribute("message", "global.success");
/*  63 */     return globalEdit(request, model);
/*     */   }
/*     */   @RequestMapping({"/config/v_basic_edit.do"})
/*     */   public String basicEdit(HttpServletRequest request, ModelMap model) {
/*  68 */     Website website = SiteUtils.getWeb(request);
/*  69 */     List ftpList = this.ftpMng.getList();
/*     */ 
/*  72 */     int tplPathLength = website.getTplPath().length();
/*  73 */     model.addAttribute("ftpList", ftpList);
/*  74 */     model.addAttribute("website", website);
/*  75 */     return "config/basic_edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/o_basic_update.do"})
/*     */   public String basicUpdate(Website bean, Integer uploadFtpId, Integer syncPageFtpId, HttpServletRequest request, ModelMap model) {
/*  81 */     WebErrors errors = validateBaseUpdate(bean, request);
/*  82 */     if (errors.hasErrors()) {
/*  83 */       return errors.showErrorPage(model);
/*     */     }
/*  85 */     Website site = SiteUtils.getWeb(request);
/*  86 */     bean.setId(site.getId());
/*  87 */     bean = this.websiteMng.update1(bean, uploadFtpId, syncPageFtpId);
/*  88 */     this.log.info("update website success. id={}", site.getId());
/*  89 */     model.addAttribute("message", "global.success");
/*  90 */     return basicEdit(request, model);
/*     */   }
/*     */   @RequestMapping({"/config/v_sso_edit.do"})
/*     */   public String ssoAuthenticateEdit(HttpServletRequest request, ModelMap model) {
/*  95 */     Website website = this.websiteMng.get();
/*  96 */     model.addAttribute("ssoMap", website.getSsoAttr());
/*  97 */     model.addAttribute("configAttr", website.getWebsiteAttr());
/*  98 */     return "config/sso_edit";
/*     */   }
/*     */   @RequestMapping({"/config/o_sso_update.do"})
/*     */   public String ssoAuthenticateUpdate(HttpServletRequest request, ModelMap model) {
/* 103 */     Map ssoMap = RequestUtils.getRequestMap(request, "attr_");
/* 104 */     this.websiteMng.updateSsoAttr(ssoMap);
/* 105 */     model.addAttribute("message", "global.success");
/* 106 */     this.log.info("update attrs of Config.");
/* 107 */     return ssoAuthenticateEdit(request, model);
/*     */   }
/*     */   @RequestMapping({"/config/v_shop_edit.do"})
/*     */   public String shopEdit(HttpServletRequest request, ModelMap model) {
/* 112 */     model.addAttribute("config", SiteUtils.getWeb(request));
/* 113 */     return "config/basic_edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/o_shop_update.do"})
/*     */   public String shopUpdate(Website website, HttpServletRequest request, ModelMap model) {
/* 119 */     website.setId(SiteUtils.getWebId(request));
/* 120 */     this.websiteMng.update(website);
/* 121 */     this.log.info("update website success. id={}", website.getId());
/* 122 */     model.addAttribute("message", "global.success");
/* 123 */     return basicEdit(request, model);
/*     */   }
/*     */   @RequestMapping({"/config/v_email_edit.do"})
/*     */   public String emailEdit(HttpServletRequest request, ModelMap model) {
/* 128 */     Website web = SiteUtils.getWeb(request);
/* 129 */     model.addAttribute("emailSender", web.getEmailSender());
/* 130 */     model.addAttribute("messageMap", web.getMessages());
/* 131 */     return "config/email_edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/o_email_update.do"})
/*     */   public String emailUpdate(EmailSender emailSender, String resetPasswordSubject, String resetPasswordText, String activeTitle, String activeTxt, HttpServletRequest request, ModelMap model)
/*     */   {
/* 139 */     Website web = SiteUtils.getWeb(request);
/* 140 */     WebErrors errors = validateEmail(request);
/* 141 */     if (errors.hasErrors()) {
/* 142 */       return errors.showErrorPage(model);
/*     */     }
/* 144 */     web.setEmailSender(emailSender);
/* 145 */     Map messages = web.getMessages();
/* 146 */     MessageTemplate resetPassword = new MessageTemplate();
/* 147 */     resetPassword.setSubject(resetPasswordSubject);
/* 148 */     resetPassword.setText(resetPasswordText);
/* 149 */     resetPassword.setActiveTitle(activeTitle);
/* 150 */     resetPassword.setActiveTxt(activeTxt);
/* 151 */     messages.put("resetPassword", resetPassword);
/* 152 */     this.websiteMng.update(web);
/* 153 */     this.log.info("update EmailSender success.");
/* 154 */     return emailEdit(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/o_email_send_test.do"})
/*     */   public String sendEmailTest(EmailSender email, String to, MessageTemplate tpl, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 161 */     Website web = SiteUtils.getWeb(request);
/*     */     try {
/* 163 */       String base = new String(web.getUrlBuff(true));
/* 164 */       this.userMng.senderEmail(Long.valueOf(0L), "Test_Username", base, to, "Test_ResetKey", 
/* 165 */         "Test_ResetPassword", email, tpl);
/* 166 */       ResponseUtils.renderJson(response, new JSONObject().put("success", 
/* 167 */         true).toString());
/*     */     } catch (Exception e) {
/* 169 */       JSONObject json = new JSONObject();
/* 170 */       json.put("success", false);
/* 171 */       json.put("message", e.getMessage());
/* 172 */       ResponseUtils.renderJson(response, json.toString());
/*     */     }
/* 174 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/v_api_edit.do"})
/*     */   public String apiEdit(HttpServletRequest request, ModelMap model) {
/* 180 */     model.addAttribute("configAttr", this.globalMng.findIdkey().getConfigAttr());
/* 181 */     return "config/attr_edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/o_aip_update.do"})
/*     */   public String apiUpdate(Boolean qqEnable, Boolean sinaEnable, Boolean weixinEnable, ConfigAttr configattr, HttpServletRequest request, ModelMap model) {
/* 187 */     configattr.setQqEnable(qqEnable.booleanValue());
/* 188 */     configattr.setSinaEnable(sinaEnable.booleanValue());
/* 189 */     configattr.setWeixinEnable(weixinEnable.booleanValue());
/* 190 */     this.globalMng.updateConfigAttr(configattr);
/* 191 */     model.addAttribute("message", "global.success");
/* 192 */     this.log.info("update attrs of Config.");
/* 193 */     return apiEdit(request, model);
/*     */   }
/*     */   @RequestMapping({"/config/v_dataBackup.do"})
/*     */   public String v_dataBackup(HttpServletRequest request, ModelMap model) {
/* 198 */     DataBackup dataBackup = this.dataBackupMng.getDataBackup();
/* 199 */     model.addAttribute("dataBackup", dataBackup);
/* 200 */     return "config/dataBackup";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/o_dataBackup.do"})
/*     */   public void o_dataBackup(DataBackup bean, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws UnsupportedEncodingException, IOException
/*     */   {
/* 207 */     this.dataBackupMng.update(bean);
/* 208 */     response.setContentType("application/x-download;charset=UTF-8");
/* 209 */     response.setHeader("Content-Disposition", "attachment;filename=" + bean.getDataBaseName() + new Date().getTime() + ".sql");
/* 210 */     PrintWriter out = response.getWriter();
/* 211 */     out.print(backup(bean));
/* 212 */     out.flush();
/* 213 */     out.close();
/*     */   }
/*     */ 
/*     */   private String backup(DataBackup bean)
/*     */   {
/* 218 */     String outStr = "";
/*     */     try {
/* 220 */       Runtime rt = Runtime.getRuntime();
/* 221 */       Process child = rt.exec("cmd /c mysqldump -u" + bean.getUsername() + " -p" + bean.getPassword() + 
/* 222 */         " -h" + bean.getAddress() + " " + bean.getDataBaseName());
/* 223 */       InputStream in = child.getInputStream();
/* 224 */       InputStreamReader xx = new InputStreamReader(in, "utf-8");
/*     */ 
/* 226 */       StringBuffer sb = new StringBuffer("");
/* 227 */       BufferedReader br = new BufferedReader(xx);
/*     */       String inStr;
/* 228 */       while ((inStr = br.readLine()) != null)
/*     */       {
/*     */         String inStr;
/* 229 */         sb.append(inStr + "\r\n");
/*     */       }
/* 231 */       outStr = sb.toString();
/* 232 */       child.waitFor();
/* 233 */       in.close();
/* 234 */       xx.close();
/* 235 */       br.close();
/*     */     } catch (Exception e) {
/* 237 */       e.printStackTrace();
/*     */     }
/* 239 */     return outStr;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEmail(HttpServletRequest request) {
/* 243 */     WebErrors errors = WebErrors.create(request);
/* 244 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateBaseUpdate(Website website, HttpServletRequest request)
/*     */   {
/* 249 */     WebErrors errors = WebErrors.create(request);
/* 250 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ConfigAct
 * JD-Core Version:    0.6.0
 */