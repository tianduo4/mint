/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.core.entity.Role;
/*     */ import com.jspgou.core.manager.LogMng;
/*     */ import com.jspgou.core.manager.RoleMng;
/*     */ import com.jspgou.core.security.CmsAuthorizingRealm;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class RoleAct
/*     */ {
/*  29 */   private static final Logger log = LoggerFactory.getLogger(RoleAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private RoleMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private LogMng cmsLogMng;
/*     */ 
/*     */   @Autowired
/*     */   private CmsAuthorizingRealm authorizingRealm;
/*     */ 
/*  33 */   @RequestMapping({"/role/v_list.do"})
/*     */   public String list(HttpServletRequest request, ModelMap model) { List list = this.manager.getList();
/*  34 */     model.addAttribute("list", list);
/*  35 */     return "role/list"; }
/*     */ 
/*     */   @RequestMapping({"/role/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  40 */     return "role/add";
/*     */   }
/*     */   @RequestMapping({"/role/v_edit.do"})
/*     */   public String edit(Integer id, HttpServletRequest request, ModelMap model) {
/*  45 */     model.addAttribute("role", this.manager.findById(id));
/*  46 */     return "role/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/role/o_save.do"})
/*     */   public String save(Role bean, String[] perms, HttpServletRequest request, ModelMap model) {
/*  52 */     bean = this.manager.save(bean, splitPerms(perms));
/*  53 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/role/o_update.do"})
/*     */   public String update(Role bean, String[] perms, boolean all, HttpServletRequest request, ModelMap model) {
/*  59 */     bean = this.manager.update(bean, splitPerms(perms));
/*     */ 
/*  77 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/role/o_delete.do"})
/*     */   public String delete(Integer[] ids, HttpServletRequest request, ModelMap model) {
/*  83 */     Role[] beans = this.manager.deleteByIds(ids);
/*  84 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   private Set<String> splitPerms(String[] perms) {
/*  88 */     Set set = new HashSet();
/*  89 */     if (perms != null) {
/*  90 */       for (String perm : perms) {
/*  91 */         for (String p : StringUtils.split(perm, ',')) {
/*  92 */           if (!StringUtils.isBlank(p)) {
/*  93 */             set.add(p);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  98 */     return set;
/*     */   }
/*     */ 
/*     */   private boolean hasChangePermission(boolean all, String[] perms, Role bean) {
/* 102 */     Role role = this.manager.findById(bean.getId());
/*     */ 
/* 104 */     return !bean.getPerms().toArray().equals(perms);
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Integer id, HttpServletRequest request)
/*     */   {
/* 110 */     WebErrors errors = WebErrors.create(request);
/* 111 */     if (vldExist(id, errors)) {
/* 112 */       return errors;
/*     */     }
/* 114 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Integer id, WebErrors errors) {
/* 118 */     if (errors.ifNull(id, "id")) {
/* 119 */       return true;
/*     */     }
/* 121 */     Role entity = this.manager.findById(id);
/*     */ 
/* 123 */     return errors.ifNotExist(entity, Role.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.RoleAct
 * JD-Core Version:    0.6.0
 */