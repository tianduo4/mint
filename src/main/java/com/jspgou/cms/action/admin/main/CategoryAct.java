/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.cms.manager.ProductTypePropertyMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.ArrayUtils;
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
/*     */ public class CategoryAct
/*     */   implements ServletContextAware
/*     */ {
/*  45 */   private static final Logger log = LoggerFactory.getLogger(CategoryAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypePropertyMng productTypePropertyMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng standardTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng manager;
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*  49 */   @RequestMapping({"/category/v_left.do"})
/*     */   public String left() { return "category/left"; }
/*     */ 
/*     */   @RequestMapping({"/category/v_tree.do"})
/*     */   public String tree(String root, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  55 */     Website web = SiteUtils.getWeb(request);
/*  56 */     log.debug("tree path={}", root);
/*     */     boolean isRoot;
/*     */     boolean isRoot;
/*  59 */     if ((StringUtils.isBlank(root)) || ("source".equals(root)))
/*  60 */       isRoot = true;
/*     */     else {
/*  62 */       isRoot = false;
/*     */     }
/*  64 */     model.addAttribute("isRoot", Boolean.valueOf(isRoot));
/*  65 */     WebErrors errors = validateTree(root, request);
/*  66 */     if (errors.hasErrors()) {
/*  67 */       log.error((String)errors.getErrors().get(0));
/*  68 */       ResponseUtils.renderJson(response, "[]");
/*  69 */       return null;
/*     */     }
/*     */     List list;
/*     */     List list;
/*  72 */     if (isRoot) {
/*  73 */       list = this.manager.getTopList(web.getId());
/*     */     } else {
/*  75 */       Integer rootId = Integer.valueOf(root);
/*  76 */       list = this.manager.getChildList(web.getId(), rootId);
/*     */     }
/*  78 */     model.addAttribute("list", list);
/*  79 */     response.setHeader("Cache-Control", "no-cache");
/*  80 */     response.setContentType("text/json;charset=UTF-8");
/*  81 */     return "category/tree";
/*     */   }
/*     */   @RequestMapping({"/category/v_list.do"})
/*     */   public String list(Integer root, HttpServletRequest request, ModelMap model) {
/*  86 */     Website web = SiteUtils.getWeb(request);
/*     */     List list;
/*     */     List list;
/*  88 */     if (root == null)
/*  89 */       list = this.manager.getTopList(web.getId());
/*     */     else {
/*  91 */       list = this.manager.getChildList(web.getId(), root);
/*     */     }
/*  93 */     List typeList = this.productTypeMng.getList(web.getId());
/*  94 */     model.addAttribute("typeList", typeList);
/*  95 */     model.addAttribute("root", root);
/*  96 */     model.addAttribute("list", list);
/*  97 */     return "category/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/v_add.do"})
/*     */   public String add(Integer root, Long typeId, HttpServletRequest request, ModelMap model) {
/* 103 */     Website web = SiteUtils.getWeb(request);
/* 104 */     Category parent = null;
/*     */ 
/* 107 */     ProductType type = this.productTypeMng.findById(typeId);
/*     */ 
/* 109 */     List itemList = this.productTypePropertyMng.getList(typeId, true);
/*     */     List brandList;
/*     */     List brandList;
/* 110 */     if (root != null) {
/* 111 */       parent = this.manager.findById(root);
/* 112 */       model.addAttribute("parent", parent);
/* 113 */       model.addAttribute("root", root);
/* 114 */       brandList = new ArrayList(parent.getBrands());
/*     */     } else {
/* 116 */       brandList = this.brandMng.getList();
/*     */     }
/* 118 */     model.addAttribute("brandList", brandList);
/*     */ 
/* 120 */     String ctgTplDirRel = type.getCtgTplDirRel();
/* 121 */     String realDir = this.servletContext.getRealPath(ctgTplDirRel);
/* 122 */     String relPath = ctgTplDirRel.substring(web.getTemplateRel(request).length());
/*     */ 
/* 124 */     String txtTplDirRel = type.getTxtTplDirRel();
/* 125 */     String txtrealDir = this.servletContext.getRealPath(txtTplDirRel);
/* 126 */     String txtrelPath = txtTplDirRel.substring(web.getTemplateRel(request).length());
/*     */ 
/* 128 */     String[] channelTpls = type.getChannelTpls(realDir, relPath);
/*     */ 
/* 130 */     String[] contentTpls = type.getContentTpls(txtrealDir, txtrelPath);
/*     */ 
/* 132 */     List parentList = this.manager.getListForParent(
/* 133 */       SiteUtils.getWebId(request), null);
/*     */ 
/* 136 */     List brandList1 = this.brandMng.getAllList();
/* 137 */     model.addAttribute("brandList1", brandList1);
/*     */ 
/* 139 */     List standardTypeList = this.standardTypeMng.getList();
/* 140 */     model.addAttribute("standardTypeList", standardTypeList);
/* 141 */     model.addAttribute("channelTpls", channelTpls);
/* 142 */     model.addAttribute("contentTpls", contentTpls);
/* 143 */     model.addAttribute("parentList", parentList);
/* 144 */     model.addAttribute("type", type);
/* 145 */     model.addAttribute("itemList", itemList);
/* 146 */     return "category/add";
/*     */   }
/*     */   @RequestMapping({"/category/v_edit.do"})
/*     */   public String edit(Integer id, Integer root, HttpServletRequest request, ModelMap model) {
/* 151 */     WebErrors errors = validateEdit(id, request);
/* 152 */     if (errors.hasErrors()) {
/* 153 */       return errors.showErrorPage(model);
/*     */     }
/* 155 */     if (root != null) {
/* 156 */       model.addAttribute("root", root);
/*     */     }
/* 158 */     Website web = SiteUtils.getWeb(request);
/* 159 */     Category category = this.manager.findById(id);
/* 160 */     List parentList = this.manager.getListForParent(SiteUtils.getWebId(request), id);
/* 161 */     List typeList = this.productTypeMng.getList(web.getId());
/*     */ 
/* 163 */     ProductType type = category.getType();
/*     */ 
/* 166 */     List brandList1 = this.brandMng.getAllList();
/* 167 */     List itemList = this.productTypePropertyMng.getList(type.getId(), true);
/*     */     List brandList;
/*     */     List brandList;
/* 169 */     if (category.getParent() != null) {
/* 170 */       Set set = new HashSet();
/* 171 */       set = category.getParent().getBrands();
/*     */       List brandList;
/* 173 */       if ((set != null) && (set.size() != 0)) {
/* 174 */         brandList = new ArrayList(set);
/*     */       }
/*     */       else
/* 177 */         brandList = this.brandMng.getList();
/*     */     }
/*     */     else {
/* 180 */       brandList = this.brandMng.getList();
/*     */     }
/* 182 */     model.addAttribute("brandList1", brandList1);
/* 183 */     model.addAttribute("brandList", brandList);
/*     */ 
/* 185 */     String ctgTplDirRel = type.getCtgTplDirRel();
/* 186 */     String realDir = this.servletContext.getRealPath(ctgTplDirRel);
/* 187 */     String relPath = ctgTplDirRel.substring(web.getTemplateRel(request).length());
/*     */ 
/* 189 */     String txtTplDirRel = type.getTxtTplDirRel();
/* 190 */     String txtrealDir = this.servletContext.getRealPath(txtTplDirRel);
/* 191 */     String txtrelPath = txtTplDirRel.substring(web.getTemplateRel(request).length());
/*     */ 
/* 193 */     String[] channelTpls = type.getChannelTpls(realDir, relPath);
/*     */ 
/* 195 */     String[] contentTpls = type.getContentTpls(txtrealDir, txtrelPath);
/* 196 */     String tpl = category.getTplChannel();
/* 197 */     if ((!StringUtils.isBlank(tpl)) && (!ArrayUtils.contains(channelTpls, tpl))) {
/* 198 */       channelTpls = (String[])ArrayUtils.add(channelTpls, 0, tpl);
/*     */     }
/* 200 */     tpl = category.getTplContent();
/* 201 */     if ((!StringUtils.isBlank(tpl)) && (!ArrayUtils.contains(contentTpls, tpl))) {
/* 202 */       contentTpls = (String[])ArrayUtils.add(contentTpls, 0, tpl);
/*     */     }
/* 204 */     List standardTypeList = this.standardTypeMng.getList();
/* 205 */     Long[] standardTypeIds = StandardType.fetchIds(category.getStandardType());
/* 206 */     model.addAttribute("standardTypeList", standardTypeList);
/* 207 */     model.addAttribute("channelTpls", channelTpls);
/* 208 */     model.addAttribute("contentTpls", contentTpls);
/* 209 */     model.addAttribute("parentList", parentList);
/* 210 */     model.addAttribute("typeList", typeList);
/* 211 */     model.addAttribute("category", category);
/* 212 */     model.addAttribute("standardTypeIds", standardTypeIds);
/* 213 */     model.addAttribute("itemList", itemList);
/* 214 */     return "category/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/o_save.do"})
/*     */   public String save(Category bean, Long root, Integer parentId, Long typeId, Long[] brandIds, Long[] standardTypeIds, HttpServletRequest request, ModelMap model) {
/* 220 */     WebErrors errors = validateSave(bean, request);
/* 221 */     if (errors.hasErrors()) {
/* 222 */       return errors.showErrorPage(model);
/*     */     }
/* 224 */     bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
/* 225 */     bean = this.manager.save(bean, parentId, typeId, brandIds, standardTypeIds);
/* 226 */     log.info("save Category. id={}", bean.getId());
/* 227 */     model.addAttribute("root", root);
/* 228 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/o_update.do"})
/*     */   public String update(Category bean, Integer root, Integer parentId, Long[] brandIds, Long[] standardTypeIds, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 234 */     WebErrors errors = validateUpdate(bean.getId(), request);
/* 235 */     if (errors.hasErrors()) {
/* 236 */       return errors.showErrorPage(model);
/*     */     }
/* 238 */     Map attr = RequestUtils.getRequestMap(request, "attr_");
/* 239 */     bean = this.manager.update(bean, parentId, null, brandIds, attr, standardTypeIds);
/* 240 */     log.info("update Category. id={}.", bean.getId());
/* 241 */     return list(root, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/o_delete.do"})
/*     */   public String delete(Integer[] ids, Integer root, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 247 */     WebErrors errors = validateDelete(ids, request);
/* 248 */     if (errors.hasErrors()) {
/* 249 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 253 */       Category[] beans = this.manager.deleteByIds(ids);
/* 254 */       for (Category bean : beans)
/* 255 */         log.info("delete Category. id={}", bean.getId());
/*     */     }
/*     */     catch (Exception e) {
/* 258 */       errors.addErrorString(this.productMng.getTipFile("Please.delete.the.goods.and.other.related.data.from.the.modified.classification"));
/* 259 */       return errors.showErrorPage(model);
/*     */     }
/*     */     Category[] beans;
/* 261 */     return list(root, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/v_checkPath.do"})
/*     */   public String checkPath(String path, HttpServletRequest request, HttpServletResponse response) {
/* 267 */     if ((StringUtils.isBlank(path)) || 
/* 268 */       (!this.manager.checkPath(SiteUtils.getWebId(request), path)))
/* 269 */       ResponseUtils.renderJson(response, "false");
/*     */     else {
/* 271 */       ResponseUtils.renderJson(response, "true");
/*     */     }
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/o_priority.do"})
/*     */   public String priority(Integer[] wids, Integer root, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 279 */     WebErrors errors = validatePriority(wids, priority, request);
/* 280 */     if (errors.hasErrors()) {
/* 281 */       return errors.showErrorPage(model);
/*     */     }
/* 283 */     this.manager.updatePriority(wids, priority);
/* 284 */     model.addAttribute("message", "global.success");
/* 285 */     return list(root, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateTree(String path, HttpServletRequest request) {
/* 289 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 293 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Category bean, HttpServletRequest request) {
/* 297 */     WebErrors errors = WebErrors.create(request);
/* 298 */     bean.setWebsite(SiteUtils.getWeb(request));
/* 299 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Integer id, HttpServletRequest request) {
/* 303 */     WebErrors errors = WebErrors.create(request);
/* 304 */     errors.ifNull(id, "id");
/* 305 */     vldExist(id, errors);
/* 306 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
/* 310 */     WebErrors errors = WebErrors.create(request);
/* 311 */     errors.ifNull(id, "id");
/* 312 */     vldExist(id, errors);
/* 313 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
/* 317 */     WebErrors errors = WebErrors.create(request);
/* 318 */     errors.ifEmpty(ids, "ids");
/* 319 */     for (Integer id : ids) {
/* 320 */       vldExist(id, errors);
/*     */     }
/* 322 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Integer[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 327 */     WebErrors errors = WebErrors.create(request);
/* 328 */     if (errors.ifEmpty(wids, "wids")) {
/* 329 */       return errors;
/*     */     }
/* 331 */     if (errors.ifEmpty(priority, "priority")) {
/* 332 */       return errors;
/*     */     }
/* 334 */     if (wids.length != priority.length) {
/* 335 */       errors.addErrorString("wids length not equals priority length");
/* 336 */       return errors;
/*     */     }
/* 338 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 339 */       vldExist(wids[i], errors);
/* 340 */       if (priority[i] == null) {
/* 341 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 344 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Integer id, WebErrors errors) {
/* 348 */     if (errors.hasErrors()) {
/* 349 */       return;
/*     */     }
/* 351 */     Category entity = this.manager.findById(id);
/* 352 */     errors.ifNotExist(entity, Category.class, id);
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 371 */     this.servletContext = servletContext;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.CategoryAct
 * JD-Core Version:    0.6.0
 */