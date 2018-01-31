/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopArticleMng;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ 
/*     */ @Controller
/*     */ public class ShopChannelAct
/*     */   implements ServletContextAware
/*     */ {
/*  35 */   private static final Logger log = LoggerFactory.getLogger(ShopChannelAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopChannelMng manager;
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @Autowired
/*     */   private ShopArticleMng shopArticleMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopChannelMng shopChannelMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*  39 */   @RequestMapping({"/channel/v_left.do"})
/*     */   public String left() { return "channel/left"; }
/*     */ 
/*     */   @RequestMapping({"/channel/v_tree.do"})
/*     */   public String tree(String root, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  45 */     Website web = SiteUtils.getWeb(request);
/*  46 */     log.debug("tree path={}", root);
/*     */     boolean isRoot;
/*     */     boolean isRoot;
/*  49 */     if ((StringUtils.isBlank(root)) || ("source".equals(root)))
/*  50 */       isRoot = true;
/*     */     else {
/*  52 */       isRoot = false;
/*     */     }
/*  54 */     model.addAttribute("isRoot", Boolean.valueOf(isRoot));
/*  55 */     WebErrors errors = validateTree(root, request);
/*  56 */     if (errors.hasErrors()) {
/*  57 */       log.error((String)errors.getErrors().get(0));
/*  58 */       ResponseUtils.renderJson(response, "[]");
/*  59 */       return null;
/*     */     }
/*     */     List list;
/*     */     List list;
/*  62 */     if (isRoot) {
/*  63 */       list = this.manager.getTopList(web.getId());
/*     */     } else {
/*  65 */       Integer rootId = Integer.valueOf(root);
/*  66 */       list = this.manager.getChildList(web.getId(), rootId);
/*     */     }
/*  68 */     model.addAttribute("list", list);
/*  69 */     response.setHeader("Cache-Control", "no-cache");
/*  70 */     response.setContentType("text/json;charset=UTF-8");
/*  71 */     return "channel/tree";
/*     */   }
/*     */   @RequestMapping({"/channel/v_list.do"})
/*     */   public String list(Integer root, HttpServletRequest request, ModelMap model) {
/*  76 */     Website web = SiteUtils.getWeb(request);
/*     */     List list;
/*     */     List list;
/*  78 */     if (root == null)
/*  79 */       list = this.manager.getTopList(web.getId());
/*     */     else {
/*  81 */       list = this.manager.getChildList(web.getId(), root);
/*     */     }
/*  83 */     model.addAttribute("root", root);
/*  84 */     model.addAttribute("list", list);
/*  85 */     return "channel/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/v_add.do"})
/*     */   public String add(Integer root, Integer type, HttpServletRequest request, ModelMap model) {
/*  91 */     Website web = SiteUtils.getWeb(request);
/*  92 */     ShopChannel parent = null;
/*  93 */     if (root != null) {
/*  94 */       parent = this.manager.findById(root);
/*  95 */       model.addAttribute("parent", parent);
/*  96 */       model.addAttribute("root", root);
/*     */     }
/*     */ 
/*  99 */     String tplChannelDirRel = ShopChannel.getChannelTplDirRel(web);
/* 100 */     String realChannelDir = this.servletContext.getRealPath(tplChannelDirRel);
/* 101 */     String relChannelPath = tplChannelDirRel.substring(web.getTemplateRel(request).length());
/* 102 */     String[] channelTpls = ShopChannel.getChannelTpls(type, realChannelDir, relChannelPath);
/*     */ 
/* 104 */     String tplContentDirRel = ShopChannel.getContentTplDirRel(web);
/* 105 */     String realContentDir = this.servletContext.getRealPath(tplContentDirRel);
/* 106 */     String relContentPath = tplContentDirRel.substring(web.getTemplateRel(request).length());
/* 107 */     String[] contentTpls = ShopChannel.getContentTpls(type, realContentDir, relContentPath);
/*     */ 
/* 109 */     model.addAttribute("channelTpls", channelTpls);
/* 110 */     model.addAttribute("contentTpls", contentTpls);
/* 111 */     model.addAttribute("type", type);
/* 112 */     return "channel/add";
/*     */   }
/*     */   @RequestMapping({"/channel/v_edit.do"})
/*     */   public String edit(Integer id, HttpServletRequest request, ModelMap model) {
/* 117 */     Website web = SiteUtils.getWeb(request);
/* 118 */     WebErrors errors = validateEdit(id, request);
/* 119 */     if (errors.hasErrors()) {
/* 120 */       return errors.showErrorPage(model);
/*     */     }
/* 122 */     ShopChannel shopChannel = this.manager.findById(id);
/* 123 */     Integer type = shopChannel.getType();
/*     */ 
/* 125 */     String tplChannelDirRel = ShopChannel.getChannelTplDirRel(web);
/* 126 */     String realChannelDir = this.servletContext.getRealPath(tplChannelDirRel);
/* 127 */     String relChannelPath = tplChannelDirRel.substring(web.getTemplateRel(request).length());
/* 128 */     String[] channelTpls = ShopChannel.getChannelTpls(type, realChannelDir, relChannelPath);
/*     */ 
/* 130 */     String tplContentDirRel = ShopChannel.getContentTplDirRel(web);
/* 131 */     String realContentDir = this.servletContext.getRealPath(tplContentDirRel);
/* 132 */     String relContentPath = tplContentDirRel.substring(web.getTemplateRel(request).length());
/* 133 */     String[] contentTpls = ShopChannel.getContentTpls(type, realContentDir, relContentPath);
/* 134 */     model.addAttribute("channelTpls", channelTpls);
/* 135 */     model.addAttribute("contentTpls", contentTpls);
/*     */ 
/* 137 */     List parentList = this.manager.getListForParent(web.getId(), 
/* 138 */       shopChannel.getId());
/* 139 */     model.addAttribute("parentList", parentList);
/* 140 */     model.addAttribute("shopChannel", shopChannel);
/* 141 */     return "channel/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/o_save.do"})
/*     */   public String save(Integer root, ShopChannel bean, String content, HttpServletRequest request, ModelMap model) {
/* 147 */     WebErrors errors = validateSave(bean, root, request);
/* 148 */     if (errors.hasErrors()) {
/* 149 */       return errors.showErrorPage(model);
/*     */     }
/* 151 */     bean = this.manager.save(bean, root, content);
/* 152 */     log.info("save ShopChannel id={}", bean.getId());
/* 153 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/o_update.do"})
/*     */   public String update(ShopChannel bean, Integer parentId, String content, HttpServletRequest request, ModelMap model) {
/* 159 */     WebErrors errors = validateUpdate(bean.getId(), request);
/* 160 */     if (errors.hasErrors()) {
/* 161 */       return errors.showErrorPage(model);
/*     */     }
/* 163 */     bean = this.manager.update(bean, parentId, content);
/* 164 */     log.info("update ShopChannel id={}.", bean.getId());
/* 165 */     return "redirect:v_list.do";
/*     */   }
/*     */   @RequestMapping({"/channel/o_delete.do"})
/*     */   public String delete(Integer[] ids, HttpServletRequest request, ModelMap model) {
/* 170 */     WebErrors errors = validateDelete(ids, request);
/* 171 */     if (errors.hasErrors()) {
/* 172 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 176 */       ShopChannel[] beans = this.manager.deleteByIds(ids);
/* 177 */       for (ShopChannel bean : beans)
/* 178 */         log.info("delete ShopChannel id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/* 181 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.corresponding.article.under.the.change.column"));
/* 182 */       return errors.showErrorPage(model);
/*     */     }
/*     */     ShopChannel[] beans;
/* 184 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/o_priority.do"})
/*     */   public String priority(Integer[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 190 */     WebErrors errors = validatePriority(wids, priority, request);
/* 191 */     if (errors.hasErrors()) {
/* 192 */       return errors.showErrorPage(model);
/*     */     }
/* 194 */     this.manager.updatePriority(wids, priority);
/* 195 */     model.addAttribute("message", "global.success");
/* 196 */     return list(null, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Integer[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 201 */     WebErrors errors = WebErrors.create(request);
/* 202 */     if (errors.ifEmpty(wids, "wids")) {
/* 203 */       return errors;
/*     */     }
/* 205 */     if (errors.ifEmpty(priority, "priority")) {
/* 206 */       return errors;
/*     */     }
/* 208 */     if (wids.length != priority.length) {
/* 209 */       errors.addErrorString("wids length not equals priority length");
/* 210 */       return errors;
/*     */     }
/* 212 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 213 */       vldExist(wids[i], errors);
/* 214 */       if (priority[i] == null) {
/* 215 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 218 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Integer id, WebErrors errors) {
/* 222 */     if (errors.hasErrors()) {
/* 223 */       return;
/*     */     }
/* 225 */     ShopChannel entity = this.manager.findById(id);
/* 226 */     errors.ifNotExist(entity, ShopChannel.class, id);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopChannel bean, Integer parentId, HttpServletRequest request)
/*     */   {
/* 231 */     WebErrors errors = WebErrors.create(request);
/* 232 */     Website web = SiteUtils.getWeb(request);
/* 233 */     bean.setWebsite(web);
/* 234 */     if ((parentId != null) && 
/* 235 */       (vldExist(parentId, web.getId(), errors))) {
/* 236 */       return errors;
/*     */     }
/*     */ 
/* 239 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Integer id, HttpServletRequest request) {
/* 243 */     WebErrors errors = WebErrors.create(request);
/* 244 */     Website web = SiteUtils.getWeb(request);
/* 245 */     if (vldExist(id, web.getId(), errors)) {
/* 246 */       return errors;
/*     */     }
/* 248 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
/* 252 */     WebErrors errors = WebErrors.create(request);
/* 253 */     Website web = SiteUtils.getWeb(request);
/* 254 */     if (vldExist(id, web.getId(), errors)) {
/* 255 */       return errors;
/*     */     }
/* 257 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 261 */     WebErrors errors = WebErrors.create(request);
/* 262 */     Website web = SiteUtils.getWeb(request);
/* 263 */     errors.ifEmpty(ids, "ids");
/* 264 */     for (Integer id : ids) {
/* 265 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 267 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Integer id, Long webId, WebErrors errors) {
/* 271 */     if (errors.ifNull(id, "id")) {
/* 272 */       return true;
/*     */     }
/* 274 */     ShopChannel entity = this.manager.findById(id);
/* 275 */     if (errors.ifNotExist(entity, ShopChannel.class, id)) {
/* 276 */       return true;
/*     */     }
/* 278 */     if (!entity.getWebsite().getId().equals(webId)) {
/* 279 */       errors.notInWeb(ShopChannel.class, id);
/* 280 */       return true;
/*     */     }
/* 282 */     return false;
/*     */   }
/*     */ 
/*     */   private WebErrors validateTree(String path, HttpServletRequest request) {
/* 286 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 290 */     return errors;
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 305 */     this.servletContext = servletContext;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopChannelAct
 * JD-Core Version:    0.6.0
 */