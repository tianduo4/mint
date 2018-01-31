/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.AdminMng;
/*     */ import com.jspgou.core.manager.RoleMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ public class ShopAdminAct
/*     */ {
/*  51 */   private static final Logger log = LoggerFactory.getLogger(ShopAdminAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   protected RoleMng roleMng;
/*     */ 
/*     */   @Autowired
/*     */   protected AdminMng adminMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceMng webserviceMng;
/*     */ 
/*     */   @Autowired
/*     */   private PwdEncoder pwdEncoder;
/*     */ 
/*  56 */   @RequestMapping({"/admin/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  57 */     model.addAttribute(pagination);
/*  58 */     return "admin/list"; }
/*     */ 
/*     */   @RequestMapping({"/admin/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  63 */     List roleList = this.roleMng.getList();
/*  64 */     model.addAttribute("roleList", roleList);
/*  65 */     return "admin/add";
/*     */   }
/*     */   @RequestMapping({"/admin/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  70 */     WebErrors errors = validateEdit(id, request);
/*  71 */     if (errors.hasErrors()) {
/*  72 */       return errors.showErrorPage(model);
/*     */     }
/*  74 */     List roleList = this.roleMng.getList();
/*  75 */     model.addAttribute("roleList", roleList);
/*  76 */     model.addAttribute("admin", this.manager.findById(id));
/*  77 */     model.addAttribute("roleIds", this.manager.findById(id).getAdmin().getRoleIds());
/*  78 */     return "admin/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/o_save.do"})
/*     */   public String save(ShopAdmin bean, String username, String password, Boolean viewonlyAdmin, String email, Boolean disabled, Integer[] roleIds, HttpServletRequest request, ModelMap model)
/*     */   {
/*  85 */     WebErrors errors = validateSave(bean, request);
/*  86 */     if (errors.hasErrors()) {
/*  87 */       return errors.showErrorPage(model);
/*     */     }
/*  89 */     Website web = SiteUtils.getWeb(request);
/*  90 */     bean = this.manager.register(username, password, viewonlyAdmin, email, request.getRemoteAddr(), disabled.booleanValue(), web.getId(), bean);
/*     */ 
/*  92 */     this.webserviceMng.callWebService("true", username, password, email, null, "addUser");
/*  93 */     this.adminMng.addRole(bean.getAdmin(), roleIds);
/*  94 */     log.info("save ShopAdmin id={}", bean.getId());
/*  95 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/o_update.do"})
/*     */   public String update(ShopAdmin bean, String password, Boolean viewonlyAdmin, String email, Boolean disabled, Integer[] roleIds, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 102 */     WebErrors errors = validateUpdate(bean.getId(), request);
/* 103 */     if (errors.hasErrors()) {
/* 104 */       return errors.showErrorPage(model);
/*     */     }
/* 106 */     bean = this.manager.update(bean, password, disabled, email, viewonlyAdmin);
/*     */ 
/* 108 */     ShopMember member = new ShopMember();
/* 109 */     member.setRealName(bean.getFirstname());
/* 110 */     this.webserviceMng.callWebService("true", bean.getUsername(), password, email, member, "updateUser");
/* 111 */     this.adminMng.updateRole(bean.getAdmin(), roleIds);
/* 112 */     log.info("update ShopAdmin id={}.", bean.getId());
/* 113 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 119 */     WebErrors errors = validateDelete(ids, request);
/* 120 */     if (errors.hasErrors()) {
/* 121 */       return errors.showErrorPage(model);
/*     */     }
/* 123 */     ShopAdmin[] beans = this.manager.deleteByIds(ids);
/* 124 */     for (ShopAdmin bean : beans)
/*     */     {
/* 126 */       Map paramsValues = new HashMap();
/* 127 */       paramsValues.put("username", bean.getUsername());
/* 128 */       paramsValues.put("admin", "true");
/* 129 */       this.webserviceMng.callWebService("deleteUser", paramsValues);
/* 130 */       log.info("delete ShopAdmin id={}", bean.getId());
/*     */     }
/* 132 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/v_check_username.do"})
/*     */   public String checkUsername(String username, HttpServletRequest request, HttpServletResponse response) {
/* 138 */     if ((StringUtils.isBlank(username)) || (this.userMng.usernameExist(username)))
/* 139 */       ResponseUtils.renderJson(response, "false");
/*     */     else {
/* 141 */       ResponseUtils.renderJson(response, "true");
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/v_check_email.do"})
/*     */   public String checkEmail(String email, HttpServletRequest request, HttpServletResponse response) {
/* 149 */     if ((StringUtils.isBlank(email)) || (this.userMng.emailExist(email)))
/* 150 */       ResponseUtils.renderJson(response, "false");
/*     */     else {
/* 152 */       ResponseUtils.renderJson(response, "true");
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/v_editPwd.do"})
/*     */   public String editPwd(HttpServletRequest request, ModelMap model)
/*     */   {
/* 161 */     ShopAdmin admin = AdminThread.get();
/* 162 */     Long id = admin.getId();
/* 163 */     model.addAttribute("admin", this.manager.findById(id));
/* 164 */     return "admin/password";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/ajax_update.do"})
/*     */   public void ajaxupdate(String pwd, ShopAdmin bean, String password, Boolean disabled, Integer pageNo, Long[] roleIds, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws JSONException
/*     */   {
/* 171 */     ShopAdmin admin = AdminThread.get();
/* 172 */     User user = admin.getAdmin().getUser();
/* 173 */     JSONObject json = new JSONObject();
/* 174 */     if (!this.pwdEncoder.isPasswordValid(user.getPassword(), pwd)) {
/* 175 */       json.put("success", false);
/* 176 */       json.put("status", 1);
/* 177 */       ResponseUtils.renderJson(response, json.toString());
/* 178 */       return;
/*     */     }
/* 180 */     bean = this.manager.update(bean, password, disabled, null, null);
/* 181 */     log.info("update ShopAdmin id={}.", bean.getId());
/* 182 */     json.put("success", true);
/* 183 */     json.put("status", 0);
/* 184 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */   @RequestMapping({"/admin/success.do"})
/*     */   public String success(HttpServletRequest request, ModelMap model) {
/* 189 */     return "admin/success";
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopAdmin bean, HttpServletRequest request)
/*     */   {
/* 194 */     WebErrors errors = WebErrors.create(request);
/* 195 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 199 */     WebErrors errors = WebErrors.create(request);
/* 200 */     errors.ifNull(id, "id");
/* 201 */     vldExist(id, errors);
/* 202 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request)
/*     */   {
/* 207 */     WebErrors errors = WebErrors.create(request);
/* 208 */     if (vldExist(id, errors)) {
/* 209 */       return errors;
/*     */     }
/* 211 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 215 */     WebErrors errors = WebErrors.create(request);
/* 216 */     errors.ifEmpty(ids, "ids");
/* 217 */     for (Long id : ids) {
/* 218 */       vldExist(id, errors);
/*     */     }
/* 220 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 224 */     ShopAdmin entity = this.manager.findById(id);
/* 225 */     return errors.ifNotExist(entity, ShopAdmin.class, id);
/*     */   }
/*     */ 
/*     */   private Set<String> handleperms(String[] perms) {
/* 229 */     Set permSet = new HashSet();
/*     */ 
/* 231 */     for (String perm : perms) {
/* 232 */       String[] arr = perm.split("@");
/* 233 */       int i = 0; for (int len = arr.length; i < len; i++) {
/* 234 */         permSet.add(arr[i]);
/*     */       }
/*     */     }
/* 237 */     return permSet;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopAdminAct
 * JD-Core Version:    0.6.0
 */