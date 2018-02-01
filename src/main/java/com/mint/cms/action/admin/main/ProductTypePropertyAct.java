package com.mint.cms.action.admin.main;

import com.mint.cms.entity.ProductType;
import com.mint.cms.entity.ProductTypeProperty;
import com.mint.cms.manager.ProductTypeMng;
import com.mint.cms.manager.ProductTypePropertyMng;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductTypePropertyAct {
    private static final Logger log = LoggerFactory.getLogger(ProductTypePropertyAct.class);

    @Autowired
    private ProductTypePropertyMng manager;

    @Autowired
    private ProductTypeMng productTypeMng;

    @RequestMapping({"/typeProperty/v_list.do"})
    public String list(Long typeId, Boolean isCategory, HttpServletRequest request, ModelMap model) {
        ProductType pType = this.productTypeMng.findById(typeId);
        List list = this.manager.getList(typeId, isCategory);
        model.addAttribute("list", list);
        model.addAttribute("typeId", typeId);
        model.addAttribute("isCategory", isCategory);
        model.addAttribute("fieldList", getFieldList(list));
        model.addAttribute("pType", pType);
        if (isCategory.booleanValue()) {
            return "typeProperty/list_category";
        }
        return "typeProperty/list_product";
    }

    @RequestMapping({"/typeProperty/v_add.do"})
    public String add(Long typeId, Boolean isCategory, HttpServletRequest request, ModelMap model) {
        ProductTypeProperty property = this.manager.findById(typeId);
        model.addAttribute("property", property);
        model.addAttribute("typeId", typeId);
        model.addAttribute("isCategory", isCategory);
        return "typeProperty/add";
    }

    @RequestMapping({"/typeProperty/v_edit.do"})
    public String delete(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        ProductTypeProperty property = this.manager.findById(id);
        model.addAttribute("property", property);
        model.addAttribute("typeId", property.getPropertyType().getId());
        model.addAttribute("isCategory", property.getCategory());
        return "typeProperty/edit";
    }

    @RequestMapping({"/typeProperty/o_save_list.do"})
    public String saveList(Long typeId, Boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles, HttpServletRequest request, ModelMap model) {
        ProductType pType = this.productTypeMng.findById(typeId);
        List itemList = getItems(pType, isCategory.booleanValue(), fields, propertyNames,
                dataTypes, sorts, singles);
        this.manager.saveList(itemList);
        log.info("save CmsModelItem count={}", Integer.valueOf(itemList.size()));
        model.addAttribute("typeId", typeId);
        model.addAttribute("isCategory", isCategory);
        return "redirect:v_list.do";
    }

    @RequestMapping({"/typeProperty/o_save.do"})
    public String save(ProductTypeProperty bean, Long typeId, HttpServletRequest request, ModelMap model) {
        ProductType type = new ProductType();
        type.setId(typeId);
        bean.setPropertyType(type);
        bean.setSingle(Boolean.valueOf(true));
        bean.setCustom(Boolean.valueOf(true));
        this.manager.save(bean);
        model.addAttribute("typeId", typeId);
        model.addAttribute("isCategory", bean.getCategory());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/typeProperty/o_update.do"})
    public String update(ProductTypeProperty bean, Long typeId, boolean category, HttpServletRequest request, ModelMap model) {
        this.manager.update(bean);
        model.addAttribute("typeId", typeId);
        model.addAttribute("isCategory", Boolean.valueOf(category));
        return "redirect:v_list.do";
    }

    @RequestMapping({"/typeProperty/o_priority.do"})
    public String priority(Long[] wids, Integer[] sort, String[] propertyName, Boolean[] single, Long typeId, Boolean isCategory, HttpServletRequest request, ModelMap model) {
        if ((wids != null) && (wids.length > 0)) {
            this.manager.updatePriority(wids, sort, propertyName, single);
        }
        model.addAttribute("message", "global.success");
        return list(typeId, isCategory, request, model);
    }

    @RequestMapping({"/typeProperty/o_delete.do"})
    public String delete(Long[] ids, Integer typeId, Boolean isCategory, HttpServletRequest request, ModelMap model) {
        ProductTypeProperty[] beans = this.manager.deleteByIds(ids);
        for (ProductTypeProperty bean : beans) {
            log.info("delete CmsModelItem id={}", bean.getId());
        }
        model.addAttribute("typeId", typeId);
        model.addAttribute("isCategory", isCategory);
        return "redirect:v_list.do";
    }

    private List<String> getFieldList(List<ProductTypeProperty> items) {
        List list = new ArrayList(items.size());
        for (ProductTypeProperty item : items) {
            list.add(item.getField());
        }
        return list;
    }

    private List<ProductTypeProperty> getItems(ProductType model, boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles) {
        List list = new ArrayList();

        int i = 0;
        for (int len = fields.length; i < len; i++) {
            if (!StringUtils.isBlank(fields[i])) {
                ProductTypeProperty item = new ProductTypeProperty();
                item.setCustom(Boolean.valueOf(false));
                item.setPropertyType(model);
                item.setCategory(Boolean.valueOf(isCategory));

                item.setField(fields[i]);
                item.setPropertyName(propertyNames[i]);
                item.setSort(sorts[i]);
                item.setDataType(dataTypes[i]);
                item.setSingle(singles[i]);
                list.add(item);
            }
        }
        return list;
    }
}

