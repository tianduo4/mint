/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.LogisticsText;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
/*     */ import com.jspgou.common.util.StrUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class LogisticsAct
/*     */ {
/*  29 */   private static final Logger log = LoggerFactory.getLogger(LogisticsAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng manager;
/*     */ 
/*  33 */   @RequestMapping({"/logistics/v_list.do"})
/*     */   public String list(HttpServletRequest request, ModelMap model) { List list = this.manager.getAllList();
/*  34 */     model.addAttribute("list", list);
/*  35 */     return "logistics/list"; }
/*     */ 
/*     */   @RequestMapping({"/logistics/v_add.do"})
/*     */   public String add(HttpServletRequest request, ModelMap model) {
/*  40 */     return "logistics/add";
/*     */   }
/*     */   @RequestMapping({"/logistics/v_courierAdd.do"})
/*     */   public String courierAdd(Long id, HttpServletRequest request, ModelMap model) {
/*  45 */     WebErrors errors = validateEdit(id, request);
/*  46 */     if (errors.hasErrors()) {
/*  47 */       return errors.showErrorPage(model);
/*     */     }
/*  49 */     model.addAttribute("logistics", this.manager.findById(id));
/*  50 */     return "logistics/courierAdd";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_courierSave.do"})
/*     */   public String courierSave(String courier, String imgUrl, Long logisticsId, Integer evenSpacing, HttpServletRequest request, ModelMap model) {
/*  56 */     WebErrors errors = validateEditCourier(courier, request);
/*  57 */     if (errors.hasErrors()) {
/*  58 */       return errors.showErrorPage(model);
/*     */     }
/*  60 */     Logistics bean = this.manager.findById(logisticsId);
/*  61 */     JSONObject json = JSONObject.fromObject(courier);
/*  62 */     JSONObject undefined = json.getJSONObject("undefined");
/*  63 */     JSONArray results = undefined.getJSONArray("elements");
/*  64 */     bean.setFnt(Double.valueOf(((String)results.getJSONObject(0).get("top")).replace("px", "")));
/*  65 */     bean.setFnz(Double.valueOf(((String)results.getJSONObject(0).get("left")).replace("px", "")));
/*  66 */     bean.setFnw(Double.valueOf(((String)results.getJSONObject(0).get("width")).replace("px", "")));
/*  67 */     bean.setFnh(Double.valueOf(((String)results.getJSONObject(0).get("height")).replace("px", "")));
/*  68 */     bean.setFnwh((String)results.getJSONObject(0).get("fontWeight"));
/*  69 */     bean.setFat(Double.valueOf(((String)results.getJSONObject(1).get("top")).replace("px", "")));
/*  70 */     bean.setFaz(Double.valueOf(((String)results.getJSONObject(1).get("left")).replace("px", "")));
/*  71 */     bean.setFaw(Double.valueOf(((String)results.getJSONObject(1).get("width")).replace("px", "")));
/*  72 */     bean.setFah(Double.valueOf(((String)results.getJSONObject(1).get("height")).replace("px", "")));
/*  73 */     bean.setFawh((String)results.getJSONObject(0).get("fontWeight"));
/*  74 */     bean.setFpt(Double.valueOf(((String)results.getJSONObject(2).get("top")).replace("px", "")));
/*  75 */     bean.setFpz(Double.valueOf(((String)results.getJSONObject(2).get("left")).replace("px", "")));
/*  76 */     bean.setFpw(Double.valueOf(((String)results.getJSONObject(2).get("width")).replace("px", "")));
/*  77 */     bean.setFph(Double.valueOf(((String)results.getJSONObject(2).get("height")).replace("px", "")));
/*  78 */     bean.setFpwh((String)results.getJSONObject(0).get("fontWeight"));
/*  79 */     bean.setSnt(Double.valueOf(((String)results.getJSONObject(3).get("top")).replace("px", "")));
/*  80 */     bean.setSnz(Double.valueOf(((String)results.getJSONObject(3).get("left")).replace("px", "")));
/*  81 */     bean.setSnw(Double.valueOf(((String)results.getJSONObject(3).get("width")).replace("px", "")));
/*  82 */     bean.setSnh(Double.valueOf(((String)results.getJSONObject(3).get("height")).replace("px", "")));
/*  83 */     bean.setSnwh((String)results.getJSONObject(0).get("fontWeight"));
/*  84 */     bean.setSat(Double.valueOf(((String)results.getJSONObject(4).get("top")).replace("px", "")));
/*  85 */     bean.setSaz(Double.valueOf(((String)results.getJSONObject(4).get("left")).replace("px", "")));
/*  86 */     bean.setSaw(Double.valueOf(((String)results.getJSONObject(4).get("width")).replace("px", "")));
/*  87 */     bean.setSah(Double.valueOf(((String)results.getJSONObject(4).get("height")).replace("px", "")));
/*  88 */     bean.setSawh((String)results.getJSONObject(0).get("fontWeight"));
/*  89 */     bean.setSpt(Double.valueOf(((String)results.getJSONObject(5).get("top")).replace("px", "")));
/*  90 */     bean.setSpz(Double.valueOf(((String)results.getJSONObject(5).get("left")).replace("px", "")));
/*  91 */     bean.setSpw(Double.valueOf(((String)results.getJSONObject(5).get("width")).replace("px", "")));
/*  92 */     bean.setSph(Double.valueOf(((String)results.getJSONObject(5).get("height")).replace("px", "")));
/*  93 */     bean.setSpwh((String)results.getJSONObject(0).get("fontWeight"));
/*  94 */     bean.setEvenSpacing(evenSpacing);
/*  95 */     if (StringUtils.isNotBlank(imgUrl))
/*  96 */       bean.setImgUrl(imgUrl);
/*     */     else {
/*  98 */       bean.setImgUrl("res/common/img/kd/ems.jpg");
/*     */     }
/* 100 */     bean.setCourier(Boolean.valueOf(true));
/* 101 */     this.manager.update(bean, bean.getLogisticsText().getText());
/* 102 */     model.addAttribute("logistics", bean);
/* 103 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/v_courierEdit.do"})
/*     */   public String courierEdit(Long id, HttpServletRequest request, ModelMap model)
/*     */   {
/* 110 */     WebErrors errors = validateEdit(id, request);
/* 111 */     if (errors.hasErrors()) {
/* 112 */       return errors.showErrorPage(model);
/*     */     }
/* 114 */     model.addAttribute("logistics", this.manager.findById(id));
/* 115 */     return "logistics/courierEdit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_courierEdit.do"})
/*     */   public String courierEdit(String courier, String imgUrl, Long logisticsId, Integer evenSpacing, HttpServletRequest request, ModelMap model)
/*     */   {
/* 122 */     WebErrors errors = validateEditCourier(courier, request);
/* 123 */     if (errors.hasErrors()) {
/* 124 */       return errors.showErrorPage(model);
/*     */     }
/* 126 */     Logistics bean = this.manager.findById(logisticsId);
/* 127 */     JSONObject json = JSONObject.fromObject(courier);
/* 128 */     JSONObject undefined = json.getJSONObject("undefined");
/* 129 */     JSONArray results = undefined.getJSONArray("elements");
/* 130 */     bean.setFnt(Double.valueOf(((String)results.getJSONObject(0).get("top")).replace("px", "")));
/* 131 */     bean.setFnz(Double.valueOf(((String)results.getJSONObject(0).get("left")).replace("px", "")));
/* 132 */     bean.setFnw(Double.valueOf(((String)results.getJSONObject(0).get("width")).replace("px", "")));
/* 133 */     bean.setFnh(Double.valueOf(((String)results.getJSONObject(0).get("height")).replace("px", "")));
/* 134 */     bean.setFnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 135 */     bean.setFat(Double.valueOf(((String)results.getJSONObject(1).get("top")).replace("px", "")));
/* 136 */     bean.setFaz(Double.valueOf(((String)results.getJSONObject(1).get("left")).replace("px", "")));
/* 137 */     bean.setFaw(Double.valueOf(((String)results.getJSONObject(1).get("width")).replace("px", "")));
/* 138 */     bean.setFah(Double.valueOf(((String)results.getJSONObject(1).get("height")).replace("px", "")));
/* 139 */     bean.setFawh((String)results.getJSONObject(0).get("fontWeight"));
/* 140 */     bean.setFpt(Double.valueOf(((String)results.getJSONObject(2).get("top")).replace("px", "")));
/* 141 */     bean.setFpz(Double.valueOf(((String)results.getJSONObject(2).get("left")).replace("px", "")));
/* 142 */     bean.setFpw(Double.valueOf(((String)results.getJSONObject(2).get("width")).replace("px", "")));
/* 143 */     bean.setFph(Double.valueOf(((String)results.getJSONObject(2).get("height")).replace("px", "")));
/* 144 */     bean.setFpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 145 */     bean.setSnt(Double.valueOf(((String)results.getJSONObject(3).get("top")).replace("px", "")));
/* 146 */     bean.setSnz(Double.valueOf(((String)results.getJSONObject(3).get("left")).replace("px", "")));
/* 147 */     bean.setSnw(Double.valueOf(((String)results.getJSONObject(3).get("width")).replace("px", "")));
/* 148 */     bean.setSnh(Double.valueOf(((String)results.getJSONObject(3).get("height")).replace("px", "")));
/* 149 */     bean.setSnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 150 */     bean.setSat(Double.valueOf(((String)results.getJSONObject(4).get("top")).replace("px", "")));
/* 151 */     bean.setSaz(Double.valueOf(((String)results.getJSONObject(4).get("left")).replace("px", "")));
/* 152 */     bean.setSaw(Double.valueOf(((String)results.getJSONObject(4).get("width")).replace("px", "")));
/* 153 */     bean.setSah(Double.valueOf(((String)results.getJSONObject(4).get("height")).replace("px", "")));
/* 154 */     bean.setSawh((String)results.getJSONObject(0).get("fontWeight"));
/* 155 */     bean.setSpt(Double.valueOf(((String)results.getJSONObject(5).get("top")).replace("px", "")));
/* 156 */     bean.setSpz(Double.valueOf(((String)results.getJSONObject(5).get("left")).replace("px", "")));
/* 157 */     bean.setSpw(Double.valueOf(((String)results.getJSONObject(5).get("width")).replace("px", "")));
/* 158 */     bean.setSph(Double.valueOf(((String)results.getJSONObject(5).get("height")).replace("px", "")));
/* 159 */     bean.setSpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 160 */     bean.setEvenSpacing(evenSpacing);
/* 161 */     bean.setImgUrl(imgUrl);
/* 162 */     this.manager.update(bean, bean.getLogisticsText().getText());
/* 163 */     model.addAttribute("logistics", bean);
/* 164 */     return list(request, model);
/*     */   }
/*     */   @RequestMapping({"/logistics/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/* 169 */     WebErrors errors = validateEdit(id, request);
/* 170 */     if (errors.hasErrors()) {
/* 171 */       return errors.showErrorPage(model);
/*     */     }
/* 173 */     model.addAttribute("logistics", this.manager.findById(id));
/* 174 */     return "logistics/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_save.do"})
/*     */   public String save(Logistics bean, String text, Long[] typeIds, HttpServletRequest request, ModelMap model) {
/* 180 */     WebErrors errors = validateSave(bean, request);
/* 181 */     if (errors.hasErrors()) {
/* 182 */       return errors.showErrorPage(model);
/*     */     }
/* 184 */     bean.setCourier(Boolean.valueOf(false));
/* 185 */     bean = this.manager.save(bean, text);
/* 186 */     log.info("save brand. id={}", bean.getId());
/* 187 */     return "redirect:v_list.do";
/*     */   }
/*     */   @RequestMapping({"/logistics/o_update.do"})
/*     */   public String update(Logistics bean, String text, HttpServletRequest request, ModelMap model) {
/* 192 */     WebErrors errors = validateUpdate(bean, request);
/* 193 */     if (errors.hasErrors()) {
/* 194 */       return errors.showErrorPage(model);
/*     */     }
/* 196 */     bean = this.manager.update(bean, text);
/* 197 */     log.info("update brand. id={}.", bean.getId());
/* 198 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_delete.do"})
/*     */   public String delete(Long[] ids, HttpServletRequest request, ModelMap model) {
/* 204 */     WebErrors errors = validateDelete(ids, request);
/* 205 */     if (errors.hasErrors()) {
/* 206 */       return errors.showErrorPage(model);
/*     */     }
/* 208 */     Logistics[] beans = this.manager.deleteByIds(ids);
/* 209 */     for (Logistics bean : beans) {
/* 210 */       log.info("delete brand. id={}", bean.getId());
/*     */     }
/* 212 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] priority, HttpServletRequest request, ModelMap model) {
/* 218 */     WebErrors errors = validatePriority(wids, priority, request);
/* 219 */     if (errors.hasErrors()) {
/* 220 */       return errors.showErrorPage(model);
/*     */     }
/* 222 */     this.manager.updatePriority(wids, priority);
/* 223 */     model.addAttribute("message", "global.success");
/* 224 */     return list(request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Logistics bean, HttpServletRequest request)
/*     */   {
/* 229 */     WebErrors errors = WebErrors.create(request);
/* 230 */     bean.setWebUrl(StrUtils.handelUrl(bean.getWebUrl()));
/* 231 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 235 */     WebErrors errors = WebErrors.create(request);
/* 236 */     errors.ifNull(id, "id");
/* 237 */     vldExist(id, errors);
/* 238 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Logistics bean, HttpServletRequest request) {
/* 242 */     WebErrors errors = WebErrors.create(request);
/* 243 */     Long id = bean.getId();
/* 244 */     bean.setWebUrl(StrUtils.handelUrl(bean.getWebUrl()));
/* 245 */     errors.ifNull(id, "id");
/* 246 */     vldExist(id, errors);
/* 247 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 251 */     WebErrors errors = WebErrors.create(request);
/* 252 */     errors.ifEmpty(ids, "ids");
/* 253 */     for (Long id : ids) {
/* 254 */       vldExist(id, errors);
/*     */     }
/* 256 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePriority(Long[] wids, Integer[] priority, HttpServletRequest request)
/*     */   {
/* 261 */     WebErrors errors = WebErrors.create(request);
/* 262 */     if (errors.ifEmpty(wids, "wids")) {
/* 263 */       return errors;
/*     */     }
/* 265 */     if (errors.ifEmpty(priority, "priority")) {
/* 266 */       return errors;
/*     */     }
/* 268 */     if (wids.length != priority.length) {
/* 269 */       errors.addErrorString("wids length not equals priority length");
/* 270 */       return errors;
/*     */     }
/* 272 */     int i = 0; for (int len = wids.length; i < len; i++) {
/* 273 */       vldExist(wids[i], errors);
/* 274 */       if (priority[i] == null) {
/* 275 */         priority[i] = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 278 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEditCourier(String courier, HttpServletRequest request) {
/* 282 */     WebErrors errors = WebErrors.create(request);
/* 283 */     if (StringUtils.isEmpty(courier)) {
/* 284 */       errors.addErrorString("请先保存模板再提交");
/* 285 */       return errors;
/*     */     }
/* 287 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, WebErrors errors) {
/* 291 */     Logistics entity = this.manager.findById(id);
/* 292 */     return errors.ifNotExist(entity, Logistics.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.LogisticsAct
 * JD-Core Version:    0.6.0
 */