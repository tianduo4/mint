/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.manager.ResourceMng;
/*     */ import com.jspgou.common.file.FileWrap;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.TemplateMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ import org.springframework.web.multipart.MultipartHttpServletRequest;
/*     */ 
/*     */ @Controller
/*     */ public class ResourceAct
/*     */   implements ServletContextAware
/*     */ {
/*  45 */   private static final Logger log = LoggerFactory.getLogger(ResourceAct.class);
/*     */   private static final String REL_PATH = "relPath";
/*     */   private String root;
/*     */ 
/*     */   @Autowired
/*     */   private TemplateMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ResourceMng resourceMng;
/*     */   private ServletContext servletContext;
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @RequestMapping({"/resource/v_left.do"})
/*     */   public String left(HttpServletRequest request, ModelMap model)
/*     */   {
/*  53 */     Website web = SiteUtils.getWeb(request);
/*  54 */     String resReal = this.servletContext.getRealPath(web.getResBaseRel());
/*  55 */     String resName = 
/*  56 */       MessageResolver.getMessage(request, "resource.resName", new Object[0]);
/*  57 */     FileWrap wrap = this.manager.getResFileWrap(resReal, resName);
/*  58 */     model.addAttribute("treeRoot", wrap);
/*  59 */     return "resource/left";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/v_tree.do"})
/*     */   public String tree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/*  65 */     Website web = SiteUtils.getWeb(request);
/*  66 */     String root = RequestUtils.getQueryParam(request, "root");
/*  67 */     log.debug("tree path={}", root);
/*     */ 
/*  69 */     if ((StringUtils.isBlank(root)) || ("source".equals(root))) {
/*  70 */       root = web.getResBaseRel();
/*  71 */       model.addAttribute("isRoot", Boolean.valueOf(true));
/*     */     } else {
/*  73 */       model.addAttribute("isRoot", Boolean.valueOf(false));
/*     */     }
/*  75 */     List resList = this.resourceMng.listFile(root, true);
/*  76 */     model.addAttribute("resList", resList);
/*  77 */     response.setHeader("Cache-Control", "no-cache");
/*  78 */     response.setContentType("text/json;charset=UTF-8");
/*  79 */     return "resource/tree";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/v_list.do"})
/*     */   public String list(HttpServletRequest request, ModelMap model) {
/*  85 */     Website web = SiteUtils.getWeb(request);
/*  86 */     String root = (String)model.get("root");
/*  87 */     if (root == null) {
/*  88 */       root = RequestUtils.getQueryParam(request, "root");
/*     */     }
/*  90 */     log.debug("list Resource root: {}", root);
/*  91 */     if (StringUtils.isBlank(root)) {
/*  92 */       root = web.getResBaseRel();
/*     */     }
/*  94 */     String rel = root.substring(web.getResBaseRel().length());
/*  95 */     if (rel.length() == 0) {
/*  96 */       rel = "/";
/*     */     }
/*  98 */     model.addAttribute("root", root);
/*  99 */     model.addAttribute("rel", rel);
/* 100 */     model.addAttribute("resBase", web.getResBaseUrl());
/* 101 */     model.addAttribute("list", this.resourceMng.listFile(root, false));
/* 102 */     return "resource/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_create_dir.do"})
/*     */   public String createDir(String root, String dirName, HttpServletRequest request, ModelMap model)
/*     */   {
/* 109 */     this.resourceMng.createDir(root, dirName);
/* 110 */     model.addAttribute("root", root);
/* 111 */     return list(request, model);
/*     */   }
/*     */   @RequestMapping({"/resource/v_rename.do"})
/*     */   public String renameInput(HttpServletRequest request, ModelMap model) {
/* 116 */     Website web = SiteUtils.getWeb(request);
/* 117 */     String root = RequestUtils.getQueryParam(request, "root");
/* 118 */     String name = RequestUtils.getQueryParam(request, "name");
/* 119 */     String origName = name.substring(web.getResBaseRel().length());
/* 120 */     model.addAttribute("origName", origName);
/* 121 */     model.addAttribute("root", root);
/* 122 */     return "resource/rename";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_rename.do"})
/*     */   public String renameUpdate(String relPath, String origName, String newName, HttpServletRequest request, ModelMap model) {
/* 128 */     Website web = SiteUtils.getWeb(request);
/* 129 */     String real = this.servletContext.getRealPath(web.getResBaseRel(relPath));
/* 130 */     File origFile = new File(real, origName);
/*     */ 
/* 133 */     File newFile = new File(real, newName);
/* 134 */     origFile.renameTo(newFile);
/* 135 */     log.info("rename resource dir {} to {}", origFile.getAbsolutePath(), 
/* 136 */       newFile.getAbsolutePath());
/* 137 */     model.addAttribute("relPath", relPath);
/* 138 */     return list(request, model);
/*     */   }
/*     */   @RequestMapping({"/resource/v_add.do"})
/*     */   public String add(String relPath, HttpServletRequest request, ModelMap model) {
/* 143 */     model.addAttribute("relPath", relPath);
/* 144 */     return "resource/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_save.do"})
/*     */   public String save(String relPath, String filename, String extension, String content, HttpServletRequest request, ModelMap model) {
/* 150 */     Website web = SiteUtils.getWeb(request);
/* 151 */     String real = this.servletContext.getRealPath(web.getResBaseRel(relPath));
/*     */     try {
/* 153 */       File file = new File(real, filename + extension);
/* 154 */       FileUtils.writeStringToFile(file, content, "UTF-8");
/* 155 */       log.info("save resource file success: {}", file.getAbsolutePath());
/*     */     } catch (IOException e) {
/* 157 */       log.error("write resource file error", e);
/*     */     }
/* 159 */     model.addAttribute("relPath", relPath);
/* 160 */     return list(request, model);
/*     */   }
/*     */   @RequestMapping({"/resource/v_edit.do"})
/*     */   public String edit(HttpServletRequest request, ModelMap model) {
/* 165 */     String root = RequestUtils.getQueryParam(request, "root");
/* 166 */     String name = RequestUtils.getQueryParam(request, "name");
/* 167 */     Website web = SiteUtils.getWeb(request);
/* 168 */     String real = this.servletContext.getRealPath(name);
/* 169 */     File file = new File(real);
/* 170 */     String filename = file.getName();
/*     */     try {
/* 172 */       String content = FileUtils.readFileToString(file, "UTF-8");
/* 173 */       model.addAttribute("content", content);
/*     */     } catch (IOException e) {
/* 175 */       log.error("read resource file error", e);
/*     */     }
/* 177 */     model.addAttribute("filename", filename);
/* 178 */     model.addAttribute("root", root);
/* 179 */     model.addAttribute("name", name);
/* 180 */     return "resource/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_update.do"})
/*     */   public String update(String name, String content, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 187 */     String real = this.realPathResolver.get(name);
/* 188 */     File file = new File(real);
/*     */     try {
/* 190 */       FileUtils.writeStringToFile(file, content, "UTF-8");
/*     */     } catch (IOException e) {
/* 192 */       log.error("write resource file error", e);
/*     */     }
/* 194 */     ResponseUtils.renderJson(response, "true");
/*     */ 
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_delete.do"})
/*     */   public String delete(String relPath, String[] names, HttpServletRequest request, ModelMap model) {
/* 202 */     String root = RequestUtils.getQueryParam(request, "root");
/* 203 */     for (String name : names) {
/* 204 */       int count = this.resourceMng.delete(new String[] { name });
/* 205 */       log.info("delete Resource {}, count {}", name, Integer.valueOf(count));
/*     */     }
/* 207 */     model.addAttribute("root", root);
/* 208 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_delete_single.do"})
/*     */   public String deleteSingle(HttpServletRequest request, ModelMap model) {
/* 214 */     String root = RequestUtils.getQueryParam(request, "root");
/* 215 */     String name = RequestUtils.getQueryParam(request, "name");
/* 216 */     int count = this.resourceMng.delete(new String[] { name });
/* 217 */     log.info("delete Resource {}, count {}", name, Integer.valueOf(count));
/* 218 */     model.addAttribute("root", root);
/* 219 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/v_upload.do"})
/*     */   public String uploadInput(String relPath, HttpServletRequest request, ModelMap model) {
/* 225 */     model.addAttribute("relPath", relPath);
/* 226 */     return "resource/upload";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/o_upload.do"})
/*     */   public String uploadSubmit(String relPath, HttpServletRequest request, ModelMap model)
/*     */   {
/* 233 */     MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/* 234 */     Map map = multipartRequest.getFileMap();
/* 235 */     MultipartFile[] files = new MultipartFile[map.size()];
/* 236 */     map.values().toArray(files);
/* 237 */     if (files.length > 0) {
/* 238 */       Website web = SiteUtils.getWeb(request);
/* 239 */       String realPath = this.servletContext.getRealPath(web
/* 240 */         .getResBaseRel(relPath));
/* 241 */       this.manager.uploadResourceFile(realPath, files);
/*     */     }
/* 243 */     model.addAttribute("relPath", relPath);
/* 244 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/resource/o_swfupload.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void swfUpload(String root, @RequestParam(value="Filedata", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IllegalStateException, IOException
/*     */   {
/* 253 */     this.resourceMng.saveFile(root, file);
/* 254 */     model.addAttribute("root", root);
/* 255 */     log.info("file upload seccess: {}, size:{}.", file
/* 256 */       .getOriginalFilename(), Long.valueOf(file.getSize()));
/* 257 */     ResponseUtils.renderText(response, "");
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setRealPathResolver(RealPathResolver realPathResolver)
/*     */   {
/* 271 */     this.realPathResolver = realPathResolver;
/* 272 */     this.root = realPathResolver.get("");
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 277 */     this.servletContext = servletContext;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ResourceAct
 * JD-Core Version:    0.6.0
 */