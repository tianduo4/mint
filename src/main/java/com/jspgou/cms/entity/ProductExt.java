package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseProductExt;

import java.util.ArrayList;
import java.util.List;

public class ProductExt extends BaseProductExt {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getWeight() == null) {
            setWeight(Integer.valueOf(0));
        }
        if (getUnit() == null)
            setUnit("");
    }

    public ProductExt() {
    }

    public ProductExt(Long id) {
        super(id);
    }

    public ProductExt(Long id, Integer weight, String unit) {
        super(id,
                weight,
                unit);
    }

    public List<String> getImgs() {
        String imgs = getProductImgs();
        List t = new ArrayList();
        if ((imgs != null) && (!imgs.equals(""))) {
            String[] c = imgs.split("@@");
            for (int i = 0; i < c.length; i++) {
                if (c[i].indexOf("/") != -1) {
                    t.add(c[i]);
                }
            }
        }

        return t;
    }

    public List<String> getImgsDesc() {
        String imgs = getProductImgDesc();
        List t = new ArrayList();
        String[] c = imgs.split("@@");
        for (int i = 0; i < c.length; i++) {
            t.add(c[i]);
        }

        return t;
    }
}

