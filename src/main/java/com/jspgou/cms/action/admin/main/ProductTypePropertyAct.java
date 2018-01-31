/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ProductTypeProperty;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.cms.manager.ProductTypePropertyMng;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class ProductTypePropertyAct
/*     */ {
/*  26 */   private static final Logger log = LoggerFactory.getLogger(ProductTypePropertyAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypePropertyMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*  31 */   @RequestMapping({"/typeProperty/v_list.do"})
/*     */   public String list(Long typeId, Boolean isCategory, HttpServletRequest request, ModelMap model) { ProductType pType = this.productTypeMng.findById(typeId);
/*  32 */     List list = this.manager.getList(typeId, isCategory);
/*  33 */     model.addAttribute("list", list);
/*  34 */     model.addAttribute("typeId", typeId);
/*  35 */     model.addAttribute("isCategory", isCategory);
/*  36 */     model.addAttribute("fieldList", getFieldList(list));
/*  37 */     model.addAttribute("pType", pType);
/*  38 */     if (isCategory.booleanValue()) {
/*  39 */       return "typeProperty/list_category";
/*     */     }
/*  41 */     return "typeProperty/list_product";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/v_add.do"})
/*     */   public String add(Long typeId, Boolean isCategory, HttpServletRequest request, ModelMap model)
/*     */   {
/*  48 */     ProductTypeProperty property = this.manager.findById(typeId);
/*  49 */     model.addAttribute("property", property);
/*  50 */     model.addAttribute("typeId", typeId);
/*  51 */     model.addAttribute("isCategory", isCategory);
/*  52 */     return "typeProperty/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/v_edit.do"})
/*     */   public String delete(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  58 */     ProductTypeProperty property = this.manager.findById(id);
/*  59 */     model.addAttribute("property", property);
/*  60 */     model.addAttribute("typeId", property.getPropertyType().getId());
/*  61 */     model.addAttribute("isCategory", property.getCategory());
/*  62 */     return "typeProperty/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/o_save_list.do"})
/*     */   public String saveList(Long typeId, Boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles, HttpServletRequest request, ModelMap model)
/*     */   {
/*  70 */     ProductType pType = this.productTypeMng.findById(typeId);
/*  71 */     List itemList = getItems(pType, isCategory.booleanValue(), fields, propertyNames, 
/*  72 */       dataTypes, sorts, singles);
/*  73 */     this.manager.saveList(itemList);
/*  74 */     log.info("save CmsModelItem count={}", Integer.valueOf(itemList.size()));
/*  75 */     model.addAttribute("typeId", typeId);
/*  76 */     model.addAttribute("isCategory", isCategory);
/*  77 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/o_save.do"})
/*     */   public String save(ProductTypeProperty bean, Long typeId, HttpServletRequest request, ModelMap model) {
/*  83 */     ProductType type = new ProductType();
/*  84 */     type.setId(typeId);
/*  85 */     bean.setPropertyType(type);
/*  86 */     bean.setSingle(Boolean.valueOf(true));
/*  87 */     bean.setCustom(Boolean.valueOf(true));
/*  88 */     this.manager.save(bean);
/*  89 */     model.addAttribute("typeId", typeId);
/*  90 */     model.addAttribute("isCategory", bean.getCategory());
/*  91 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/o_update.do"})
/*     */   public String update(ProductTypeProperty bean, Long typeId, boolean category, HttpServletRequest request, ModelMap model) {
/*  97 */     this.manager.update(bean);
/*  98 */     model.addAttribute("typeId", typeId);
/*  99 */     model.addAttribute("isCategory", Boolean.valueOf(category));
/* 100 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/o_priority.do"})
/*     */   public String priority(Long[] wids, Integer[] sort, String[] propertyName, Boolean[] single, Long typeId, Boolean isCategory, HttpServletRequest request, ModelMap model)
/*     */   {
/* 107 */     if ((wids != null) && (wids.length > 0)) {
/* 108 */       this.manager.updatePriority(wids, sort, propertyName, single);
/*     */     }
/* 110 */     model.addAttribute("message", "global.success");
/* 111 */     return list(typeId, isCategory, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer typeId, Boolean isCategory, HttpServletRequest request, ModelMap model) {
/* 117 */     ProductTypeProperty[] beans = this.manager.deleteByIds(ids);
/* 118 */     for (ProductTypeProperty bean : beans) {
/* 119 */       log.info("delete CmsModelItem id={}", bean.getId());
/*     */     }
/* 121 */     model.addAttribute("typeId", typeId);
/* 122 */     model.addAttribute("isCategory", isCategory);
/* 123 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   private List<String> getFieldList(List<ProductTypeProperty> items) {
/* 127 */     List list = new ArrayList(items.size());
/* 128 */     for (ProductTypeProperty item : items) {
/* 129 */       list.add(item.getField());
/*     */     }
/* 131 */     return list;
/*     */   }
/*     */ 
/*     */   private List<ProductTypeProperty> getItems(ProductType model, boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles)
/*     */   {
/* 137 */     List list = new ArrayList();
/*     */ 
/* 139 */     int i = 0; for (int len = fields.length; i < len; i++) {
/* 140 */       if (!StringUtils.isBlank(fields[i])) {
/* 141 */         ProductTypeProperty item = new ProductTypeProperty();
/* 142 */         item.setCustom(Boolean.valueOf(false));
/* 143 */         item.setPropertyType(model);
/* 144 */         item.setCategory(Boolean.valueOf(isCategory));
/*     */ 
/* 146 */         item.setField(fields[i]);
/* 147 */         item.setPropertyName(propertyNames[i]);
/* 148 */         item.setSort(sorts[i]);
/* 149 */         item.setDataType(dataTypes[i]);
/* 150 */         item.setSingle(singles[i]);
/* 151 */         list.add(item);
/*     */       }
/*     */     }
/* 154 */     return list;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ProductTypePropertyAct
 * JD-Core Version:    0.6.0
 */