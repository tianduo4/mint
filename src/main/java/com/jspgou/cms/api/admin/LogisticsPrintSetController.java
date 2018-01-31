/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.LogisticsText;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.upload.FileRepository;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class LogisticsPrintSetController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private FileRepository fileRepository;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @RequestMapping({"/logistics/v_courierAdd"})
/*     */   public String courierAdd(Long id, String appId, HttpServletRequest request, ModelMap model)
/*     */   {
/*  42 */     WebErrors errors = validateEdit(id, request);
/*  43 */     if (errors.hasErrors()) {
/*  44 */       return errors.showErrorPage(model);
/*     */     }
/*  46 */     model.addAttribute("logistics", this.manager.findById(id));
/*  47 */     model.addAttribute("base", request.getContextPath());
/*  48 */     model.addAttribute("appId", appId);
/*  49 */     return "logistics/courierAdd";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/common/o_upload_image"})
/*     */   public String execute(String fileName, String appId, Integer uploadNum, Integer zoomWidth, Integer zoomHeight, @RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, ModelMap model)
/*     */     throws IOException
/*     */   {
/*  57 */     Website site = this.websiteMng.findById(Long.valueOf(1L));
/*     */     try {
/*  59 */       WebErrors errors = validate("image", file, request);
/*  60 */       if (!errors.hasErrors())
/*     */       {
/*  62 */         errors = ApiValidate.validateRequiredParams(errors, new Object[] { file });
/*  63 */         if (!errors.hasErrors()) {
/*  64 */           String origName = file.getOriginalFilename();
/*  65 */           String ext = FilenameUtils.getExtension(origName).toLowerCase(
/*  66 */             Locale.ENGLISH);
/*  67 */           String filepath = "";
/*  68 */           filepath = this.fileRepository.upload(site, ext, file);
/*  69 */           if (StringUtils.isEmpty(filepath)) {
/*  70 */             errors.addErrorCode(String.valueOf(400));
/*  71 */             errors.addErrorString("上传错误");
/*     */           } else {
/*  73 */             model.addAttribute("uploadPath", site.getUploadResUrlBuff() + filepath);
/*  74 */             model.addAttribute("uploadNum", uploadNum);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IllegalStateException e) {
/*  79 */       model.addAttribute("error", e.getMessage());
/*     */     } catch (IOException e) {
/*  81 */       model.addAttribute("error", e.getMessage());
/*     */     }
/*  83 */     model.addAttribute("appId", appId);
/*  84 */     model.addAttribute("base", site.getUploadResUrlBuff());
/*  85 */     return "/common/iframe_upload";
/*     */   }
/*     */ 
/*     */   private WebErrors validate(String type, MultipartFile file, HttpServletRequest request)
/*     */   {
/*  90 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/*  92 */     if ((file == null) || (type == null)) {
/*  93 */       errors.addErrorCode(String.valueOf(202));
/*  94 */       errors.addErrorString("参数错误");
/*  95 */       return errors;
/*     */     }
/*     */ 
/*  98 */     if ((!type.equals("image")) && (!type.equals("attach")) && (!type.equals("video"))) {
/*  99 */       errors.addErrorCode(String.valueOf(401));
/* 100 */       errors.addErrorString("上传类型文件暂不支持");
/* 101 */       return errors;
/*     */     }
/* 103 */     String filename = file.getOriginalFilename();
/* 104 */     String ext = FilenameUtils.getExtension(filename);
/*     */ 
/* 106 */     if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
/* 107 */       errors.addErrorCode(String.valueOf(402));
/* 108 */       errors.addErrorString("文件名含非法字符，如\\ /等等");
/* 109 */       return errors;
/*     */     }
/* 111 */     if (type.equals("image"))
/*     */     {
/* 113 */       if (!ImageUtils.isValidImageExt(ext)) {
/* 114 */         errors.addErrorCode(String.valueOf(403));
/* 115 */         errors.addErrorString("图片文件后缀不支持，暂时只支持(jpg, jpeg,gif, png, bmp )");
/* 116 */         return errors;
/*     */       }
/*     */     }
/* 119 */     return errors;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_courierSave"})
/*     */   public String courierSave(String courier, String appId, String imgUrl, Long logisticsId, Integer evenSpacing, HttpServletRequest request, ModelMap model)
/*     */   {
/* 127 */     WebErrors errors = validateEditCourier(courier, request);
/* 128 */     if (errors.hasErrors()) {
/* 129 */       return errors.showErrorPage(model);
/*     */     }
/* 131 */     Logistics bean = this.manager.findById(logisticsId);
/* 132 */     JSONObject json = JSONObject.fromObject(courier);
/* 133 */     JSONObject undefined = json.getJSONObject("undefined");
/* 134 */     JSONArray results = undefined.getJSONArray("elements");
/* 135 */     bean.setFnt(Double.valueOf(((String)results.getJSONObject(0).get("top")).replace("px", "")));
/* 136 */     bean.setFnz(Double.valueOf(((String)results.getJSONObject(0).get("left")).replace("px", "")));
/* 137 */     bean.setFnw(Double.valueOf(((String)results.getJSONObject(0).get("width")).replace("px", "")));
/* 138 */     bean.setFnh(Double.valueOf(((String)results.getJSONObject(0).get("height")).replace("px", "")));
/* 139 */     bean.setFnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 140 */     bean.setFat(Double.valueOf(((String)results.getJSONObject(1).get("top")).replace("px", "")));
/* 141 */     bean.setFaz(Double.valueOf(((String)results.getJSONObject(1).get("left")).replace("px", "")));
/* 142 */     bean.setFaw(Double.valueOf(((String)results.getJSONObject(1).get("width")).replace("px", "")));
/* 143 */     bean.setFah(Double.valueOf(((String)results.getJSONObject(1).get("height")).replace("px", "")));
/* 144 */     bean.setFawh((String)results.getJSONObject(0).get("fontWeight"));
/* 145 */     bean.setFpt(Double.valueOf(((String)results.getJSONObject(2).get("top")).replace("px", "")));
/* 146 */     bean.setFpz(Double.valueOf(((String)results.getJSONObject(2).get("left")).replace("px", "")));
/* 147 */     bean.setFpw(Double.valueOf(((String)results.getJSONObject(2).get("width")).replace("px", "")));
/* 148 */     bean.setFph(Double.valueOf(((String)results.getJSONObject(2).get("height")).replace("px", "")));
/* 149 */     bean.setFpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 150 */     bean.setSnt(Double.valueOf(((String)results.getJSONObject(3).get("top")).replace("px", "")));
/* 151 */     bean.setSnz(Double.valueOf(((String)results.getJSONObject(3).get("left")).replace("px", "")));
/* 152 */     bean.setSnw(Double.valueOf(((String)results.getJSONObject(3).get("width")).replace("px", "")));
/* 153 */     bean.setSnh(Double.valueOf(((String)results.getJSONObject(3).get("height")).replace("px", "")));
/* 154 */     bean.setSnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 155 */     bean.setSat(Double.valueOf(((String)results.getJSONObject(4).get("top")).replace("px", "")));
/* 156 */     bean.setSaz(Double.valueOf(((String)results.getJSONObject(4).get("left")).replace("px", "")));
/* 157 */     bean.setSaw(Double.valueOf(((String)results.getJSONObject(4).get("width")).replace("px", "")));
/* 158 */     bean.setSah(Double.valueOf(((String)results.getJSONObject(4).get("height")).replace("px", "")));
/* 159 */     bean.setSawh((String)results.getJSONObject(0).get("fontWeight"));
/* 160 */     bean.setSpt(Double.valueOf(((String)results.getJSONObject(5).get("top")).replace("px", "")));
/* 161 */     bean.setSpz(Double.valueOf(((String)results.getJSONObject(5).get("left")).replace("px", "")));
/* 162 */     bean.setSpw(Double.valueOf(((String)results.getJSONObject(5).get("width")).replace("px", "")));
/* 163 */     bean.setSph(Double.valueOf(((String)results.getJSONObject(5).get("height")).replace("px", "")));
/* 164 */     bean.setSpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 165 */     bean.setEvenSpacing(evenSpacing);
/* 166 */     if (StringUtils.isNotBlank(imgUrl))
/* 167 */       bean.setImgUrl(imgUrl);
/*     */     else {
/* 169 */       bean.setImgUrl("res/common/img/kd/ems.jpg");
/*     */     }
/* 171 */     bean.setCourier(Boolean.valueOf(true));
/* 172 */     this.manager.update(bean, bean.getLogisticsText().getText());
/* 173 */     model.addAttribute("logistics", bean);
/* 174 */     return courierEdit(logisticsId, appId, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/v_courierEdit"})
/*     */   public String courierEdit(Long id, String appId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 181 */     WebErrors errors = validateEdit(id, request);
/* 182 */     if (errors.hasErrors()) {
/* 183 */       return errors.showErrorPage(model);
/*     */     }
/* 185 */     model.addAttribute("logistics", this.manager.findById(id));
/* 186 */     model.addAttribute("base", request.getContextPath());
/* 187 */     model.addAttribute("appId", appId);
/* 188 */     return "logistics/courierEdit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_courierEdit"})
/*     */   public String courierEdit(String courier, String appId, String imgUrl, Long logisticsId, Integer evenSpacing, HttpServletRequest request, ModelMap model)
/*     */   {
/* 195 */     WebErrors errors = validateEditCourier(courier, request);
/* 196 */     if (errors.hasErrors()) {
/* 197 */       return errors.showErrorPage(model);
/*     */     }
/* 199 */     Logistics bean = this.manager.findById(logisticsId);
/* 200 */     JSONObject json = JSONObject.fromObject(courier);
/* 201 */     JSONObject undefined = json.getJSONObject("undefined");
/* 202 */     JSONArray results = undefined.getJSONArray("elements");
/* 203 */     bean.setFnt(Double.valueOf(((String)results.getJSONObject(0).get("top")).replace("px", "")));
/* 204 */     bean.setFnz(Double.valueOf(((String)results.getJSONObject(0).get("left")).replace("px", "")));
/* 205 */     bean.setFnw(Double.valueOf(((String)results.getJSONObject(0).get("width")).replace("px", "")));
/* 206 */     bean.setFnh(Double.valueOf(((String)results.getJSONObject(0).get("height")).replace("px", "")));
/* 207 */     bean.setFnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 208 */     bean.setFat(Double.valueOf(((String)results.getJSONObject(1).get("top")).replace("px", "")));
/* 209 */     bean.setFaz(Double.valueOf(((String)results.getJSONObject(1).get("left")).replace("px", "")));
/* 210 */     bean.setFaw(Double.valueOf(((String)results.getJSONObject(1).get("width")).replace("px", "")));
/* 211 */     bean.setFah(Double.valueOf(((String)results.getJSONObject(1).get("height")).replace("px", "")));
/* 212 */     bean.setFawh((String)results.getJSONObject(0).get("fontWeight"));
/* 213 */     bean.setFpt(Double.valueOf(((String)results.getJSONObject(2).get("top")).replace("px", "")));
/* 214 */     bean.setFpz(Double.valueOf(((String)results.getJSONObject(2).get("left")).replace("px", "")));
/* 215 */     bean.setFpw(Double.valueOf(((String)results.getJSONObject(2).get("width")).replace("px", "")));
/* 216 */     bean.setFph(Double.valueOf(((String)results.getJSONObject(2).get("height")).replace("px", "")));
/* 217 */     bean.setFpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 218 */     bean.setSnt(Double.valueOf(((String)results.getJSONObject(3).get("top")).replace("px", "")));
/* 219 */     bean.setSnz(Double.valueOf(((String)results.getJSONObject(3).get("left")).replace("px", "")));
/* 220 */     bean.setSnw(Double.valueOf(((String)results.getJSONObject(3).get("width")).replace("px", "")));
/* 221 */     bean.setSnh(Double.valueOf(((String)results.getJSONObject(3).get("height")).replace("px", "")));
/* 222 */     bean.setSnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 223 */     bean.setSat(Double.valueOf(((String)results.getJSONObject(4).get("top")).replace("px", "")));
/* 224 */     bean.setSaz(Double.valueOf(((String)results.getJSONObject(4).get("left")).replace("px", "")));
/* 225 */     bean.setSaw(Double.valueOf(((String)results.getJSONObject(4).get("width")).replace("px", "")));
/* 226 */     bean.setSah(Double.valueOf(((String)results.getJSONObject(4).get("height")).replace("px", "")));
/* 227 */     bean.setSawh((String)results.getJSONObject(0).get("fontWeight"));
/* 228 */     bean.setSpt(Double.valueOf(((String)results.getJSONObject(5).get("top")).replace("px", "")));
/* 229 */     bean.setSpz(Double.valueOf(((String)results.getJSONObject(5).get("left")).replace("px", "")));
/* 230 */     bean.setSpw(Double.valueOf(((String)results.getJSONObject(5).get("width")).replace("px", "")));
/* 231 */     bean.setSph(Double.valueOf(((String)results.getJSONObject(5).get("height")).replace("px", "")));
/* 232 */     bean.setSpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 233 */     bean.setEvenSpacing(evenSpacing);
/* 234 */     bean.setImgUrl(imgUrl);
/* 235 */     this.manager.update(bean, bean.getLogisticsText().getText());
/* 236 */     model.addAttribute("logistics", bean);
/* 237 */     return courierEdit(logisticsId, appId, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request)
/*     */   {
/* 242 */     WebErrors errors = WebErrors.create(request);
/* 243 */     errors.ifNull(id, "id");
/* 244 */     vldExist(id, errors);
/* 245 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEditCourier(String courier, HttpServletRequest request) {
/* 249 */     WebErrors errors = WebErrors.create(request);
/* 250 */     if (StringUtils.isEmpty(courier)) {
/* 251 */       errors.addErrorString("请先保存模板再提交");
/* 252 */       return errors;
/*     */     }
/* 254 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 258 */     Logistics entity = this.manager.findById(id);
/* 259 */     return errors.ifNotExist(entity, Logistics.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.LogisticsPrintSetController
 * JD-Core Version:    0.6.0
 */