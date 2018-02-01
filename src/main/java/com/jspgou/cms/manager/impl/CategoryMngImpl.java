package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.BrandDao;
import com.jspgou.cms.dao.CategoryDao;
import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.StandardType;
import com.jspgou.cms.entity.base.BaseCategory;
import com.jspgou.cms.manager.BrandMng;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.cms.manager.ProductTypeMng;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class CategoryMngImpl
        implements CategoryMng {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private BrandMng brandMng;

    @Autowired
    private StandardTypeMng standardTypeMng;
    private ProductTypeMng productTypeMng;
    private CategoryDao dao;

    public List<Category> getAll(Long webId) {
        return this.dao.getAll(webId);
    }

    public Category getByPath(Long webId, String path) {
        return this.dao.getByPath(webId, path, false);
    }

    public Category getByPathForTag(Long webId, String path) {
        return this.dao.getByPath(webId, path, true);
    }

    @Transactional(readOnly = true)
    public List<Category> getListForParent(Long webId, Integer ctgId) {
        List allList = getList(webId);
        if (ctgId != null) {
            List list = this.dao.getListForChild(webId, ctgId);
            allList.removeAll(list);
        }
        return allList;
    }

    @Transactional(readOnly = true)
    public List<Category> getListForProduct(Long webId, Integer ctgId) {
        List list = new ArrayList();
        Category category = findById(ctgId);
        addAllChildToList(list, Arrays.asList(new Category[]{category}), category.getType()
                .getId(), null);
        return list;
    }

    @Transactional(readOnly = true)
    public List<Category> getTopList(Long webId) {
        return this.dao.getTopList(webId, false);
    }

    public List<Category> getChildList(Long wegId, Integer parentId) {
        return this.dao.getChildList(wegId, parentId);
    }

    public List<Category> getTopListForTag(Long webId) {
        return this.dao.getTopList(webId, true);
    }

    @Transactional(readOnly = true)
    public List<Category> getList(Long webId) {
        List list = this.dao.getTopList(webId, false);
        List allList = new ArrayList();
        addAllChildToList(allList, list, null, Integer.valueOf(1));
        return allList;
    }

    public boolean checkPath(Long webId, String path) {
        return this.dao.countPath(webId, path) <= 0;
    }

    @Transactional(readOnly = true)
    public Category findById(Integer id) {
        Category entity = this.dao.findById(id);
        return entity;
    }

    public Category save(Category bean, Integer parentId, Long typeId, Long[] brandIds, Long[] standardTypeIds) {
        Category parent = null;
        if (parentId != null) {
            parent = findById(parentId);
            bean.setParent(parent);
        }
        if (typeId != null) {
            bean.setType(this.productTypeMng.findById(typeId));
        }
        Category entity = this.dao.save(bean);
        if ((brandIds != null) && (brandIds.length > 0)) {
            for (Long brandId : brandIds)
                entity.addToBrands(this.brandMng.findById(brandId));
        } else {
            entity.setBrands(new HashSet());
        }
        if (parent != null) {
            parent.addToChild(bean);
        }
        if ((standardTypeIds != null) && (standardTypeIds.length > 0)) {
            for (Long sid : standardTypeIds) {
                entity.addToStandardTypes(this.standardTypeMng.findById(sid));
            }
        }
        return bean;
    }

    public List<Brand> getBrandByCate(Integer categoryId) {
        return this.brandDao.getListByCate(categoryId);
    }

    public Category update(Category bean, Integer parentId, Long typeId, Long[] brandIds, Map<String, String> attr, Long[] standardTypeIds) {
        Assert.notNull(bean);
        Category entity = findById(bean.getId());
        Category origParent = entity.getParent();
        Category parent = null;
        if (parentId != null) {
            parent = findById(parentId);
            bean.setParent(parent);
        } else {
            bean.setParent(null);
        }
        Updater updater = new Updater(bean);
        updater.include(BaseCategory.PROP_PARENT);
        entity = this.dao.updateByUpdater(updater);
        if (origParent != null) {
            origParent.removeFromChild(entity);
        }
        if (parent != null) {
            parent.addToChild(entity);
        }

        Set<Brand> brands = entity.getBrands();
        for (Brand brand : brands) {
            brand.removeFromCategorys(entity);
        }
        brands.clear();
        if ((brandIds != null) && (brandIds.length > 0)) {
            for (Long brandId : brandIds)
                entity.addToBrands(this.brandMng.findById(brandId));
        } else {
            entity.setBrands(new HashSet());
        }
        Set<StandardType> standardTypes = entity.getStandardType();
        for (StandardType t : standardTypes) {
            t.removeFromCategorys(entity);
        }
        standardTypes.clear();
        if ((standardTypeIds != null) && (standardTypeIds.length > 0)) {
            for (Long sid : standardTypeIds) {
                entity.addToStandardTypes(this.standardTypeMng.findById(sid));
            }
        }

        if (attr != null) {
            Map attrOrig = entity.getAttr();
            attrOrig.clear();
            attrOrig.putAll(attr);
        }

        return entity;
    }

    public Category deleteById(Integer id) {
        Category parent = findById(id).getParent();
        Category bean = this.dao.deleteById(id);
        if (parent != null) {
            parent.removeFromChild(bean);
        }
        return bean;
    }

    public Category[] deleteByIds(Integer[] ids) {
        Category[] beans = new Category[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = this.dao.deleteById(ids[i]);
        }

        for (Category bean : beans) {
            Category parent = bean.getParent();
            if (parent != null) {
                parent.removeFromChild(bean);
            }
        }
        return beans;
    }

    public Category[] updatePriority(Integer[] ids, Integer[] priority) {
        Category[] beans = new Category[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    private void addAllChildToList(List<Category> allList, Collection<Category> coll, Long typeId, Integer leval) {
        for (Category ctg : coll) {
            if (leval != null) {
                ctg.setLeval(leval.intValue());
            }

            if (typeId != null) {
                if (typeId.equals(ctg.getType().getId()))
                    allList.add(ctg);
            } else {
                allList.add(ctg);
            }
            Collection child = ctg.getChild();
            if ((child != null) && (child.size() > 0)) {
                if (leval != null) {
                    leval = Integer.valueOf(leval.intValue() + 1);
                }
                addAllChildToList(allList, child, typeId, leval);
                if (leval != null)
                    leval = Integer.valueOf(leval.intValue() - 1);
            }
        }
    }

    public List<Category> getListBypType(Long webId, Long typeId, Integer count) {
        return this.dao.getListByptype(webId, typeId, count);
    }

    @Autowired
    public void setProductTypeMng(ProductTypeMng productMng) {
        this.productTypeMng = productMng;
    }

    @Autowired
    public void setDao(CategoryDao dao) {
        this.dao = dao;
    }
}

