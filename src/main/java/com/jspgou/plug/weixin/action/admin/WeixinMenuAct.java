/*     */ package com.jspgou.plug.weixin.action.admin;
/*     */ 
/*     */ import com.jspgou.cms.service.WeixinSvc;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMenu;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMenuMng;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.shiro.authz.annotation.RequiresPermissions;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class WeixinMenuAct
/*     */ {
/*  30 */   private static final Logger log = LoggerFactory.getLogger(WeixinMenuAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMenuMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinSvc weixinSvc;
/*     */ 
/*  35 */   @RequiresPermissions({"weixinMenu:v_list"})
/*     */   @RequestMapping({"/weixinMenu/v_list.do"})
/*     */   public String list(Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) { Website site = SiteUtils.getWeb(request);
/*  36 */     Pagination p = this.manager.getPage(site.getId(), parentId, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  37 */     if (parentId != null) {
/*  38 */       WeixinMenu entity = this.manager.findById(parentId);
/*  39 */       if (entity.getParent() != null) {
/*  40 */         model.addAttribute("parentListId", entity.getParent().getId());
/*     */       }
/*     */     }
/*  43 */     model.addAttribute("pagination", p);
/*  44 */     model.addAttribute("parentId", parentId);
/*  45 */     model.addAttribute("pageNo", pageNo);
/*  46 */     return "weixinMenu/list"; } 
/*     */   @RequiresPermissions({"weixinMenu:v_add"})
/*     */   @RequestMapping({"/weixinMenu/v_add.do"})
/*     */   public String add(Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  52 */     model.addAttribute("parentId", parentId);
/*  53 */     model.addAttribute("pageNo", pageNo);
/*  54 */     return "weixinMenu/add";
/*     */   }
/*  60 */   @RequiresPermissions({"weixinMenu:v_edit"})
/*     */   @RequestMapping({"/weixinMenu/v_edit.do"})
/*     */   public String edit(Integer id, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) { WeixinMenu entity = this.manager.findById(id);
/*  61 */     model.addAttribute("parentId", parentId);
/*  62 */     model.addAttribute("pageNo", pageNo);
/*  63 */     model.addAttribute("menu", entity);
/*  64 */     return "weixinMenu/edit"; } 
/*     */   @RequiresPermissions({"weixinMenu:o_save"})
/*     */   @RequestMapping({"/weixinMenu/o_save.do"})
/*     */   public String save(WeixinMenu bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  70 */     Website site = SiteUtils.getWeb(request);
/*  71 */     bean.setSite(site);
/*  72 */     if (parentId != null) {
/*  73 */       bean.setParent(this.manager.findById(parentId));
/*     */     }
/*  75 */     this.manager.save(bean);
/*  76 */     return list(parentId, pageNo, request, model);
/*     */   }
/*  82 */   @RequiresPermissions({"weixinMenu:o_update"})
/*     */   @RequestMapping({"/weixinMenu/o_update.do"})
/*     */   public String update(WeixinMenu bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) { this.manager.update(bean);
/*  83 */     return list(parentId, pageNo, request, model); }
/*     */ 
/*     */   @RequiresPermissions({"weixinMenu:o_menu"})
/*     */   @RequestMapping({"/weixinMenu/o_menu.do"})
/*     */   public String menu(WeixinMenu bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  90 */     Website site = SiteUtils.getWeb(request);
/*  91 */     List menus = this.manager.getList(site.getId(), Integer.valueOf(100));
/*  92 */     this.weixinSvc.createMenu(getMenuJsonString(menus));
/*  93 */     return list(parentId, pageNo, request, model);
/*     */   }
/*  99 */   @RequiresPermissions({"weixinMenu:o_delete"})
/*     */   @RequestMapping({"/weixinMenu/o_delete.do"})
/*     */   public String delete(Integer[] ids, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) { WebErrors errors = validateDelete(ids, request);
/* 100 */     if (errors.hasErrors()) {
/* 101 */       return errors.showErrorPage(model);
/*     */     }
/* 103 */     WeixinMenu[] beans = this.manager.deleteByIds(ids);
/* 104 */     for (WeixinMenu bean : beans) {
/* 105 */       log.info("delete Brief id={}", bean.getId());
/*     */     }
/* 107 */     return list(parentId, pageNo, request, model); }
/*     */ 
/*     */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request)
/*     */   {
/* 111 */     WebErrors errors = WebErrors.create(request);
/* 112 */     if (errors.ifEmpty(ids, "ids")) {
/* 113 */       return errors;
/*     */     }
/* 115 */     for (Integer id : ids) {
/* 116 */       vldExist(id, errors);
/*     */     }
/* 118 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Integer id, WebErrors errors) {
/* 122 */     if (errors.ifNull(id, "id")) {
/* 123 */       return true;
/*     */     }
/* 125 */     WeixinMenu entity = this.manager.findById(id);
/*     */ 
/* 127 */     return errors.ifNotExist(entity, WeixinMenu.class, id);
/*     */   }
/*     */ 
/*     */   public String getMenuJsonString(List<WeixinMenu> menus)
/*     */   {
/* 134 */     String strJson = "{\"button\":[";
/*     */ 
/* 137 */     for (int i = 0; i < menus.size(); i++) {
/* 138 */       strJson = strJson + "{\t";
/* 139 */       WeixinMenu menu = (WeixinMenu)menus.get(i);
/* 140 */       if (menu.getChild().size() > 0) {
/* 141 */         strJson = strJson + 
/* 142 */           "\"name\":\"" + menu.getName() + "\"," + 
/* 143 */           "\"sub_button\":[";
/* 144 */         Set sets = menu.getChild();
/* 145 */         Iterator iter = sets.iterator();
/* 146 */         int no = 1;
/* 147 */         while (iter.hasNext()) {
/* 148 */           if (no > 5) {
/*     */             break;
/*     */           }
/* 151 */           if (no == 1)
/* 152 */             strJson = strJson + "{";
/*     */           else {
/* 154 */             strJson = strJson + ",{";
/*     */           }
/* 156 */           WeixinMenu child = (WeixinMenu)iter.next();
/* 157 */           if (child.getType().equals("click"))
/* 158 */             strJson = strJson + 
/* 159 */               "\"type\":\"click\"," + 
/* 160 */               "\"name\":\"" + child.getName() + "\"," + 
/* 161 */               "\"key\":\"" + child.getKey() + "\"}";
/*     */           else {
/* 163 */             strJson = strJson + 
/* 164 */               "\"type\":\"view\"," + 
/* 165 */               "\"name\":\"" + child.getName() + "\"," + 
/* 166 */               "\"url\":\"" + child.getUrl() + "\"}";
/*     */           }
/* 168 */           no++;
/*     */         }
/*     */ 
/* 171 */         strJson = strJson + "]";
/* 172 */       } else if (menu.getType().equals("click")) {
/* 173 */         strJson = strJson + 
/* 174 */           "\"type\":\"click\"," + 
/* 175 */           "\"name\":\"" + menu.getName() + "\"," + 
/* 176 */           "\"key\":\"" + menu.getKey() + "\"";
/*     */       } else {
/* 178 */         strJson = strJson + 
/* 179 */           "\"type\":\"view\"," + 
/* 180 */           "\"name\":\"" + menu.getName() + "\"," + 
/* 181 */           "\"url\":\"" + menu.getUrl() + "\"";
/*     */       }
/* 183 */       if (i == menus.size() - 1)
/* 184 */         strJson = strJson + "}";
/*     */       else {
/* 186 */         strJson = strJson + "},";
/*     */       }
/*     */     }
/* 189 */     strJson = strJson + "]}";
/* 190 */     return strJson;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.action.admin.WeixinMenuAct
 * JD-Core Version:    0.6.0
 */