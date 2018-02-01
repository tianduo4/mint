package com.mint.cms.manager.impl;

import com.mint.cms.dao.CouponDao;
import com.mint.cms.entity.Coupon;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.manager.CouponMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponMngImpl
        implements CouponMng {

    @Autowired
    private RealPathResolver realPathResolver;

    @Autowired
    private CategoryMng categoryMng;

    @Autowired
    private CouponDao dao;

    @Autowired
    private WebsiteMng siteMng;

    public Pagination getPage(int pageNo, int pageSize, Integer categoryId) {
        return this.dao.getPage(pageNo, pageSize, categoryId);
    }

    @Transactional(readOnly = true)
    public Pagination getPageByUsing(int pageNo, int pageSize) {
        return this.dao.getPageByUsing(new Date(), pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public List<Coupon> getList() {
        List list = this.dao.getList();
        return list;
    }

    @Transactional(readOnly = true)
    public Coupon findById(Long id) {
        Coupon entity = this.dao.findById(id);
        return entity;
    }

    public Coupon save(Coupon bean, Long webId) {
        Website site = this.siteMng.findById(webId);
        bean.setWebsite(site);
        String couponname = null;
        String comments = null;
        try {
            couponname = new String(bean.getCouponName().getBytes("ISO-8859-1"), "UTF-8");
            comments = new String(bean.getComments().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bean.setCouponName(couponname);
        bean.setComments(comments);
        Coupon entity = this.dao.save(bean);
        return entity;
    }

    public Coupon save(Coupon bean, Website site, Integer categoryId) {
        bean.setWebsite(site);
        if ((categoryId != null) && (categoryId.intValue() != 0)) {
            bean.setCategory(this.categoryMng.findById(categoryId));
        }
        Coupon entity = this.dao.save(bean);
        return entity;
    }

    public Coupon update(Coupon bean) {
        Updater updater = new Updater(bean);
        Coupon entity = this.dao.updateByUpdater(updater);

        return entity;
    }

    public Coupon deleteById(Long id, String url) {
        Coupon entity = findById(id);
        String path = entity.getCouponPicture();
        String path1 = this.realPathResolver.get(path).replace("\\", File.separator).replace("/", File.separator).replace(url.replace("\\", File.separator).replace("/", File.separator) + url.replace("\\", File.separator).replace("/", File.separator), url.replace("\\", File.separator).replace("/", File.separator));
        File f = new File(path1);
        if (f != null) {
            f.delete();
        }
        entity = this.dao.deleteById(id);
        return entity;
    }

    public Coupon[] deleteByIds(Long[] ids, String url) {
        Coupon[] beans = new Coupon[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i], url);
        }
        return beans;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

