package com.mint.cms.manager.impl;

import com.mint.cms.dao.ProductTagDao;
import com.mint.cms.entity.ProductTag;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ProductTagMng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class ProductTagMngImpl
        implements ProductTagMng {
    private ProductMng productMng;
    private ProductTagDao dao;

    @Transactional(readOnly = true)
    public List<ProductTag> getList(Long webId) {
        List list = this.dao.getList(webId);
        return list;
    }

    @Transactional(readOnly = true)
    public ProductTag findById(Long id) {
        ProductTag entity = this.dao.findById(id);
        return entity;
    }

    public ProductTag save(ProductTag bean) {
        bean.init();
        this.dao.save(bean);
        return bean;
    }

    public ProductTag[] updateTagName(Long[] ids, String[] tagNames) {
        Assert.notEmpty(ids);
        Assert.notEmpty(tagNames);
        if (ids.length != tagNames.length) {
            throw new IllegalArgumentException(
                    "ids length not equals tagNames length");
        }
        ProductTag[] tags = new ProductTag[ids.length];

        int i = 0;
        for (int len = ids.length; i < len; i++) {
            ProductTag tag = findById(ids[i]);
            tag.setName(tagNames[i]);
            tags[i] = tag;
        }
        return tags;
    }

    public ProductTag[] deleteByIds(Long[] ids) {
        ProductTag[] beans = new ProductTag[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
        }
        this.productMng.deleteTagAssociation(beans);
        i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = this.dao.deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setProductMng(ProductMng productMng) {
        this.productMng = productMng;
    }

    @Autowired
    public void setDao(ProductTagDao dao) {
        this.dao = dao;
    }
}

