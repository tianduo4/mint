/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.manager.ShopArticleMng;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ShopArticleAct
/*     */ {
/*  36 */   private static final Logger log = LoggerFactory.getLogger(ShopArticleAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopChannelMng channelMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopArticleMng manager;
/*     */ 
/*  40 */   @RequestMapping({"/article/v_left.do"})
/*     */   public String left() { return "article/left";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/v_tree.do"})
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
/*  73 */       list = this.channelMng.getTopList(web.getId());
/*     */     } else {
/*  75 */       Integer rootId = Integer.valueOf(root);
/*  76 */       list = this.channelMng.getChildList(web.getId(), rootId);
/*     */     }
/*  78 */     model.addAttribute("list", list);
/*  79 */     response.setHeader("Cache-Control", "no-cache");
/*  80 */     response.setContentType("text/json;charset=UTF-8");
/*  81 */     return "article/tree";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/v_list.do"})
/*     */   public String list(Integer cid, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  87 */     Website web = SiteUtils.getWeb(request);
/*  88 */     Pagination pagination = this.manager.getPage(cid, web.getId(), SimplePage.cpn(pageNo), 
/*  89 */       CookieUtils.getPageSize(request));
/*  90 */     model.addAttribute("pagination", pagination);
/*  91 */     if (cid != null) {
/*  92 */       ShopChannel channel = this.channelMng.findById(cid);
/*  93 */       model.addAttribute("channel", channel);
/*     */     }
/*  95 */     model.addAttribute("cid", cid);
/*  96 */     return "article/list";
/*     */   }
/*     */   @RequestMapping({"/article/v_add.do"})
/*     */   public String add(Long cid, HttpServletRequest request, ModelMap model) {
/* 101 */     Website web = SiteUtils.getWeb(request);
/* 102 */     List channelList = this.channelMng.getArticleList(web
/* 103 */       .getId());
/* 104 */     model.addAttribute("channelList", channelList);
/* 105 */     if (cid != null) {
/* 106 */       model.addAttribute("cid", cid);
/*     */     }
/* 108 */     return "article/add";
/*     */   }
/*     */   @RequestMapping({"/article/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/* 113 */     Website web = SiteUtils.getWeb(request);
/* 114 */     WebErrors errors = validateEdit(id, request);
/* 115 */     if (errors.hasErrors()) {
/* 116 */       return errors.showErrorPage(model);
/*     */     }
/* 118 */     List articleChannelList = this.channelMng.getArticleList(web
/* 119 */       .getId());
/* 120 */     model.addAttribute("articleChannelList", articleChannelList);
/* 121 */     model.addAttribute("shopArticle", this.manager.findById(id));
/* 122 */     return "article/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/o_save.do"})
/*     */   public String save(ShopArticle bean, Integer channelId, String content, HttpServletRequest request, ModelMap model) {
/* 128 */     WebErrors errors = validateSave(bean, channelId, request);
/* 129 */     if (errors.hasErrors()) {
/* 130 */       return errors.showErrorPage(model);
/*     */     }
/* 132 */     bean = this.manager.save(bean, channelId, content);
/* 133 */     log.info("save ShopArticle id={}", bean.getId());
/* 134 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/o_update.do"})
/*     */   public String update(ShopArticle bean, Integer channelId, String content, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 140 */     WebErrors errors = validateUpdate(bean.getId(), channelId, request);
/* 141 */     if (errors.hasErrors()) {
/* 142 */       return errors.showErrorPage(model);
/*     */     }
/* 144 */     bean = this.manager.update(bean, channelId, content);
/* 145 */     log.info("update ShopArticle id={}.", bean.getId());
/* 146 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/article/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 152 */     WebErrors errors = validateDelete(ids, request);
/* 153 */     if (errors.hasErrors()) {
/* 154 */       return errors.showErrorPage(model);
/*     */     }
/* 156 */     ShopArticle[] beans = this.manager.deleteByIds(ids);
/* 157 */     for (ShopArticle bean : beans) {
/* 158 */       log.info("delete ShopArticle id={}", bean.getId());
/*     */     }
/* 160 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopArticle bean, Integer channelId, HttpServletRequest request)
/*     */   {
/* 165 */     WebErrors errors = WebErrors.create(request);
/* 166 */     Website web = SiteUtils.getWeb(request);
/* 167 */     bean.setWebsite(web);
/* 168 */     if (errors.ifNull(channelId, "channelId")) {
/* 169 */       return errors;
/*     */     }
/* 171 */     ShopChannel channel = this.channelMng.findById(channelId);
/* 172 */     if (!channel.getWebsite().getId().equals(web.getId())) {
/* 173 */       errors.notInWeb(ShopChannel.class, channelId);
/* 174 */       return errors;
/*     */     }
/* 176 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 180 */     WebErrors errors = WebErrors.create(request);
/* 181 */     Website web = SiteUtils.getWeb(request);
/* 182 */     if (vldExist(id, web.getId(), errors)) {
/* 183 */       return errors;
/*     */     }
/* 185 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, Integer channelId, HttpServletRequest request)
/*     */   {
/* 190 */     WebErrors errors = WebErrors.create(request);
/* 191 */     Website web = SiteUtils.getWeb(request);
/* 192 */     if (vldExist(id, web.getId(), errors)) {
/* 193 */       return errors;
/*     */     }
/* 195 */     ShopChannel channel = this.channelMng.findById(channelId);
/* 196 */     if (!channel.getWebsite().getId().equals(web.getId())) {
/* 197 */       errors.notInWeb(ShopChannel.class, channelId);
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
/* 214 */     if (errors.ifNull(id, "id")) {
/* 215 */       return true;
/*     */     }
/* 217 */     ShopArticle entity = this.manager.findById(id);
/* 218 */     if (errors.ifNotExist(entity, ShopArticle.class, id)) {
/* 219 */       return true;
/*     */     }
/* 221 */     if (!entity.getWebsite().getId().equals(webId)) {
/* 222 */       errors.notInWeb(ShopArticle.class, id);
/* 223 */       return true;
/*     */     }
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */   private WebErrors validateTree(String path, HttpServletRequest request) {
/* 229 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 233 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ShopArticleAct
 * JD-Core Version:    0.6.0
 */