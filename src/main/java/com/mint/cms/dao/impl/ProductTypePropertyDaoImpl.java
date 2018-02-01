package com.mint.cms.dao.impl;

import com.mint.cms.dao.ProductTypePropertyDao;
import com.mint.cms.entity.ProductType;
import com.mint.cms.entity.ProductTypeProperty;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTypePropertyDaoImpl extends HibernateBaseDao<ProductTypeProperty, Long>
        implements ProductTypePropertyDao {
    public ProductTypeProperty deleteById(Long id) {
        ProductTypeProperty entity = (ProductTypeProperty) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public ProductTypeProperty findById(Long id) {
        ProductTypeProperty entity = (ProductTypeProperty) get(id);
        return entity;
    }

    public Pagination getList(int pageNo, int pageSize, Long typeid, Boolean isCategory, String searchType, String searchContent) {
        Finder f = Finder.create("from ProductTypeProperty bean where 1=1 ");
        if (typeid != null) {
            f.append(" and bean.propertyType.id = :typeid").setParam("typeid", typeid);
        }
        if (searchType != null) {
            if ("1".equals(searchType)) {
                f.append(" and bean.propertyType.name like :searchContent ");
            } else if ("2".equals(searchType)) {
                f.append(" and bean.propertyName like :searchContent ");
            }
            f.setParam("searchContent", "%" + searchContent + "%");
        }
        f.append(" and bean.category=:isCategory").setParam("isCategory", isCategory);
        f.append(" order by bean.propertyType.id,bean.sort");
        return find(f, pageNo, pageSize);
    }

    public List<ProductTypeProperty> getList(Long typeId, Boolean isCategory) {
        Finder f = Finder.create("from ProductTypeProperty bean where 1=1 ");
        if (typeId != null) {
            f.append(" and bean.propertyType.id = :typeId").setParam("typeId", typeId);
        }
        f.append(" and bean.category=:isCategory").setParam("isCategory", isCategory);
        f.append(" order by bean.sort asc");
        return find(f);
    }

    public ProductTypeProperty save(ProductTypeProperty bean) {
        getSession().save(bean);
        return bean;
    }

    protected Class<ProductTypeProperty> getEntityClass() {
        return ProductTypeProperty.class;
    }

    public List<ProductTypeProperty> findBySearch(String searchType, String searchContent) {
        String hql = "from ProductTypeProperty bean where 1 = 1 ";

        if ("1".equals(searchType)) {
            hql = hql + " and bean.propertyType.name like :searchContent ";
        } else if ("2".equals(searchType)) {
            hql = hql + " and bean.propertyName like :searchContent ";
        }
        searchContent = "%" + searchContent + "%";
        return getSession().createQuery(hql).setString("searchContent", searchContent).setCacheable(false).list();
    }

    public List<ProductTypeProperty> findListByTypeId(Long typeId) {
        String hql = "from ProductTypeProperty bean where bean.propertyType.id = :typeId ";
        return getSession().createQuery(hql).setLong("typeId", typeId.longValue()).setCacheable(false).list();
    }

    public List<ProductTypeProperty> getList(Long typeId, boolean isCategory) {
        Finder f = Finder.create("from ProductTypeProperty bean where 1=1");
        f.append(" and bean.propertyType.id=:typeId").setParam("typeId", typeId);
        f.append(" and bean.category=:isCategory").setParam("isCategory", Boolean.valueOf(isCategory));
        f.append(" order by bean.sort asc");
        return find(f);
    }

    public List<ProductTypeProperty> getItems(ProductType model, boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles) {
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

