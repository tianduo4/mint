/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ShopMemberAct
/*     */ {
/*  41 */   private static final Logger log = LoggerFactory.getLogger(ShopMemberAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberGroupMng shopMemberGroupMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceMng webserviceMng;
/*     */ 
/*  46 */   @RequestMapping({"/member/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), 
/*  47 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  48 */     model.addAttribute("pagination", pagination);
/*  49 */     return "member/list"; }
/*     */ 
/*     */   @RequestMapping({"/member/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  54 */     Website web = SiteUtils.getWeb(request);
/*  55 */     List groupList = this.shopMemberGroupMng.getList(
/*  56 */       SiteUtils.getWebId(request));
/*     */ 
/*  58 */     List userDegreeList = this.shopDictionaryMng.getListByType(Long.valueOf(1L));
/*     */ 
/*  60 */     List familyMembersList = this.shopDictionaryMng.getListByType(Long.valueOf(2L));
/*     */ 
/*  62 */     List incomeDescList = this.shopDictionaryMng.getListByType(Long.valueOf(3L));
/*     */ 
/*  64 */     List workSeniorityList = this.shopDictionaryMng.getListByType(Long.valueOf(4L));
/*     */ 
/*  66 */     List degreeList = this.shopDictionaryMng.getListByType(Long.valueOf(5L));
/*     */ 
/*  68 */     model.addAttribute("groupList", groupList);
/*  69 */     model.addAttribute("userDegreeList", userDegreeList);
/*  70 */     model.addAttribute("familyMembersList", familyMembersList);
/*  71 */     model.addAttribute("incomeDescList", incomeDescList);
/*  72 */     model.addAttribute("workSeniorityList", workSeniorityList);
/*  73 */     model.addAttribute("degreeList", degreeList);
/*  74 */     return "member/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/o_save.do"})
/*     */   public String save(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String username, String password, String email, Boolean disabled, HttpServletRequest request, ModelMap model)
/*     */   {
/*  82 */     WebErrors errors = validateSave(bean, username, email, request);
/*  83 */     if (errors.hasErrors()) {
/*  84 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  87 */     bean = this.manager.register(username, password, email, Boolean.valueOf(true), null, request
/*  88 */       .getRemoteAddr(), disabled, SiteUtils.getWebId(request), 
/*  89 */       groupId, userDegreeId, degreeId, incomeDescId, workSeniorityId, 
/*  90 */       familyMembersId, bean);
/*     */ 
/*  92 */     this.webserviceMng.callWebService("false", username, password, email, bean, "addUser");
/*  93 */     log.info("save ShopMember, id={}", bean.getId());
/*  94 */     return "redirect:v_list.do";
/*     */   }
/*     */   @RequestMapping({"/member/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/*  99 */     Website web = SiteUtils.getWeb(request);
/* 100 */     WebErrors errors = validateEdit(id, request);
/* 101 */     if (errors.hasErrors()) {
/* 102 */       return errors.showErrorPage(model);
/*     */     }
/* 104 */     List groupList = this.shopMemberGroupMng.getList(
/* 105 */       SiteUtils.getWebId(request));
/*     */ 
/* 108 */     List userDegreeList = this.shopDictionaryMng.getListByType(Long.valueOf(1L));
/*     */ 
/* 110 */     List familyMembersList = this.shopDictionaryMng.getListByType(Long.valueOf(2L));
/*     */ 
/* 112 */     List incomeDescList = this.shopDictionaryMng.getListByType(Long.valueOf(3L));
/*     */ 
/* 114 */     List workSeniorityList = this.shopDictionaryMng.getListByType(Long.valueOf(4L));
/*     */ 
/* 116 */     List degreeList = this.shopDictionaryMng.getListByType(Long.valueOf(5L));
/*     */ 
/* 118 */     ShopMember member = this.manager.findById(id);
/* 119 */     model.addAttribute("groupList", groupList);
/* 120 */     model.addAttribute("member", member);
/* 121 */     model.addAttribute("groupList", groupList);
/* 122 */     model.addAttribute("userDegreeList", userDegreeList);
/* 123 */     model.addAttribute("familyMembersList", familyMembersList);
/* 124 */     model.addAttribute("incomeDescList", incomeDescList);
/* 125 */     model.addAttribute("workSeniorityList", workSeniorityList);
/* 126 */     model.addAttribute("degreeList", degreeList);
/* 127 */     return "member/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/o_update.do"})
/*     */   public String update(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String password, String email, Boolean disabled, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 135 */     WebErrors errors = validateUpdate(bean.getId(), email, request);
/* 136 */     if (errors.hasErrors()) {
/* 137 */       return errors.showErrorPage(model);
/*     */     }
/* 139 */     bean = this.manager.update(bean, groupId, userDegreeId, 
/* 140 */       degreeId, incomeDescId, workSeniorityId, familyMembersId, 
/* 141 */       password, email, disabled);
/*     */ 
/* 143 */     this.webserviceMng.callWebService("false", bean.getUsername(), password, email, bean, "updateUser");
/* 144 */     log.info("update ShopMember, id={}.", bean.getId());
/* 145 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 151 */     WebErrors errors = validateDelete(ids, request);
/* 152 */     if (errors.hasErrors()) {
/* 153 */       return errors.showErrorPage(model);
/*     */     }
/* 155 */     ShopMember[] beans = this.manager.deleteByIds(ids);
/* 156 */     for (ShopMember bean : beans)
/*     */     {
/* 158 */       Map paramsValues = new HashMap();
/* 159 */       paramsValues.put("username", bean.getUsername());
/* 160 */       paramsValues.put("admin", "true");
/* 161 */       this.webserviceMng.callWebService("deleteUser", paramsValues);
/* 162 */       log.info("delete ShopMember, id={}", bean.getId());
/*     */     }
/* 164 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopMember bean, String username, String email, HttpServletRequest request)
/*     */   {
/* 169 */     WebErrors errors = WebErrors.create(request);
/* 170 */     Website web = SiteUtils.getWeb(request);
/* 171 */     bean.setWebsite(web);
/* 172 */     if (vldUsername(username, errors)) {
/* 173 */       return errors;
/*     */     }
/* 175 */     if (vldEmail(email, errors)) {
/* 176 */       return errors;
/*     */     }
/* 178 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 182 */     WebErrors errors = WebErrors.create(request);
/* 183 */     Website web = SiteUtils.getWeb(request);
/* 184 */     if (vldExist(id, web.getId(), errors)) {
/* 185 */       return errors;
/*     */     }
/* 187 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, String email, HttpServletRequest request)
/*     */   {
/* 192 */     WebErrors errors = WebErrors.create(request);
/* 193 */     Website web = SiteUtils.getWeb(request);
/* 194 */     if (vldEmailUpdate(id, email, errors)) {
/* 195 */       return errors;
/*     */     }
/* 197 */     if (vldExist(id, web.getId(), errors)) {
/* 198 */       return errors;
/*     */     }
/* 200 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 204 */     WebErrors errors = WebErrors.create(request);
/* 205 */     Website web = SiteUtils.getWeb(request);
/* 206 */     errors.ifEmpty(ids, "ids");
/* 207 */     for (Long id : ids) {
/* 208 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 210 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 214 */     if (errors.hasErrors()) {
/* 215 */       return true;
/*     */     }
/* 217 */     if (errors.ifNull(id, "id")) {
/* 218 */       return true;
/*     */     }
/* 220 */     ShopMember entity = this.manager.findById(id);
/* 221 */     if (errors.ifNotExist(entity, ShopMember.class, id)) {
/* 222 */       return true;
/*     */     }
/* 224 */     if (!entity.getWebsite().getId().equals(webId)) {
/* 225 */       errors.notInWeb(ShopMember.class, id);
/* 226 */       return true;
/*     */     }
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean vldEmailUpdate(Long id, String email, WebErrors errors) {
/* 232 */     if (!StringUtils.isBlank(email)) {
/* 233 */       ShopMember member = this.manager.findById(id);
/* 234 */       if ((member.getEmail() != null) && (!member.getEmail().equals(email)) && 
/* 235 */         (vldEmail(email, errors))) {
/* 236 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean vldEmail(String email, WebErrors errors) {
/* 244 */     if (errors.ifNotEmail(email, "email", 100)) {
/* 245 */       return true;
/*     */     }
/* 247 */     if (this.userMng.emailExist(email)) {
/* 248 */       errors.addErrorCode("error.emailExist");
/* 249 */       return true;
/*     */     }
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean vldUsername(String username, WebErrors errors) {
/* 255 */     if (errors.ifNotUsername(username, "username", 3, 100)) {
/* 256 */       return true;
/*     */     }
/* 258 */     if (this.userMng.usernameExist(username)) {
/* 259 */       errors.addErrorCode("error.usernameExist");
/* 260 */       return true;
/*     */     }
/* 262 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopMemberAct
 * JD-Core Version:    0.6.0
 */