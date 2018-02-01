package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ProductDao;
import com.jspgou.cms.dao.ProductFashionDao;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.Collect;
import com.jspgou.cms.entity.Consult;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExt;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.ProductStandard;
import com.jspgou.cms.entity.ProductTag;
import com.jspgou.cms.entity.ProductText;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.entity.base.BaseProduct;
import com.jspgou.cms.manager.BrandMng;
import com.jspgou.cms.manager.CartItemMng;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.cms.manager.CollectMng;
import com.jspgou.cms.manager.ConsultMng;
import com.jspgou.cms.manager.ProductExtMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ProductStandardMng;
import com.jspgou.cms.manager.ProductTagMng;
import com.jspgou.cms.manager.ProductTextMng;
import com.jspgou.cms.manager.RelatedgoodsMng;
import com.jspgou.cms.manager.ShopConfigMng;
import com.jspgou.cms.manager.StandardMng;
import com.jspgou.common.file.FileNameUtils;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.image.ImageScale;
import com.jspgou.common.image.ImageUtils;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.springmvc.RealPathResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductMngImpl
        implements ProductMng {

    @Autowired
    private ProductFashionDao fashDao;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private CollectMng collectMng;

    @Autowired
    private ConsultMng consultMng;

    @Autowired
    private CartItemMng cartItemMng;

    @Autowired
    private RealPathResolver realPathResolver;

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private CategoryMng categoryMng;

    @Autowired
    private ProductTagMng productTagMng;

    @Autowired
    private ProductTextMng productTextMng;

    @Autowired
    private ShopConfigMng shopConfigMng;

    @Autowired
    private BrandMng brandMng;

    @Autowired
    private ImageScale imageScale;

    @Autowired
    private ProductExtMng productExtMng;

    @Autowired
    private ProductStandardMng productStandardMng;

    @Autowired
    private RelatedgoodsMng relatedgoodsMng;

    @Autowired
    private ProductDao dao;

    @Autowired
    private StandardMng standardMng;

    @Autowired
    private ProductFashionMng fashMng;

    public List<Product> getList(Long typeId, Long brandId, String productName) {
        return this.dao.getList(typeId, brandId, productName, true);
    }

    public void resetSaleTop() {
        List<Product> list = getList(null, null, null);
        for (Product product : list) {
            product.setSaleCount(Integer.valueOf(0));
            update(product);
        }
    }

    public void resetProfitTop() {
        List<Product> list = getList(null, null, null);
        for (Product product : list) {
            product.setLiRun(Double.valueOf(0.0D));
            update(product);
        }
    }

    public void updateViewCount(Product product) {
        product.setViewCount(Long.valueOf(product.getViewCount().longValue() + 1L));
    }

    @Transactional(readOnly = true)
    public Pagination getPage(Long webId, Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, int pageNo, int pageSize) {
        Pagination page;
        if (ctgId != null)
            page = this.dao.getPageByCategory(ctgId, productName, brandName, status,
                    isRecommend, isSpecial, isHotsale, isNewProduct, typeId,
                    startSalePrice, endSalePrice, startStock, endStock, pageNo, pageSize, false);
        else {
            page = this.dao.getPageByWebsite(webId, productName, brandName, status,
                    isRecommend, isSpecial, isHotsale, isNewProduct, typeId,
                    startSalePrice, endSalePrice, startStock, endStock, pageNo,
                    pageSize, false);
        }
        return page;
    }

    public Pagination getPage(int orderBy, int pageNo, int pageSize) {
        return this.dao.getPage(orderBy, pageNo, pageSize, true);
    }

    public Pagination getPage1(Long typeId, int orderBy, int pageNo, int pageSize) {
        return this.dao.getPage1(typeId, orderBy, pageNo, pageSize, true);
    }

    public List<Product> findAll() {
        return this.dao.findAll();
    }

    @Transactional(readOnly = true)
    public Pagination getPageForTag(Long webId, Integer ctgId, Long tagId, Boolean isRecommend, Boolean isSpecial, int pageNo, int pageSize) {
        Pagination page;
        if (tagId != null) {
            page = this.dao.getPageByTag(tagId, ctgId, isRecommend, isSpecial,
                    pageNo, pageSize, true);
        } else {
            if (ctgId != null)
                page = this.dao.getPageByCategory(ctgId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial,
                        null, null, null, null, null, null, null, pageNo, pageSize, true);
            else {
                page = this.dao.getPageByWebsite(webId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial,
                        null, null, null, null, null, null, null, pageNo, pageSize, true);
            }
        }
        return page;
    }

    public Pagination getPageByStockWarning(Long webId, Integer count, Boolean status, int pageNo, int pageSize) {
        return this.dao.getPageByStockWarning(webId, count, status, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public Pagination getPageForTagChannel(String brandId, Long webId, Integer ctgId, Long tagId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, int pageNo, int pageSize) {
        Pagination page;
        if (tagId != null) {
            page = this.dao.getPageByTag(tagId, ctgId, isRecommend, isSpecial, pageNo, pageSize, true);
        } else {
            if (ctgId != null)
                page = this.dao.getPageByCategoryChannel(brandId, ctgId, isRecommend, names, values, isSpecial, orderBy, startPrice, endPrice, pageNo, pageSize, true);
            else {
                page = this.dao.getPageByWebsite(webId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial, null, null, null, null, null, null, null, pageNo, pageSize, true);
            }
        }
        return page;
    }

    public List<Product> getListForTag(Long webId, Integer ctgId, Long tagId, Boolean isRecommend, Boolean isSpecial, Boolean isHostSale, Boolean isNewProduct, int firstResult, int maxResults) {
        List list;
        if (tagId != null) {
            list = this.dao.getListByTag(tagId, ctgId, isRecommend, isSpecial, firstResult, maxResults, true);
        } else {
            if (ctgId != null)
                list = this.dao.getListByCategory(ctgId, isRecommend, isSpecial, firstResult, maxResults, true);
            else {
                list = this.dao.getListByWebsite1(webId, isRecommend, isSpecial, isHostSale, isNewProduct, firstResult, maxResults, true);
            }
        }
        return list;
    }

    public List<Product> getIsRecommend(Boolean isRecommend, int count) {
        return this.dao.getIsRecommend(isRecommend, count);
    }

    public Integer[] getProductByTag(Long webId) {
        return this.dao.getProductByTag(webId);
    }

    public int deleteTagAssociation(ProductTag[] tags) {
        if (ArrayUtils.isEmpty(tags)) {
            return 0;
        }
        return this.dao.deleteTagAssociation(tags);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Product entity = this.dao.findById(id);
        return entity;
    }

    public Product save(Product bean, ProductExt ext, Long webId, Integer categoryId, Long brandId, Long[] tagIds, String[] keywords, String[] names, String[] values, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, MultipartFile file) {
        ProductText text = bean.getProductText();
        if (text != null) {
            text.setProduct(bean);
        }
        Category category = this.categoryMng.findById(categoryId);
        if (brandId != null) {
            bean.setBrand(this.brandMng.findById(brandId));
        }
        Website web = this.websiteMng.findById(webId);
        bean.setWebsite(web);
        bean.setConfig(this.shopConfigMng.findById(webId));
        bean.setCategory(category);
        bean.setType(category.getType());
        if (!ArrayUtils.isEmpty(tagIds)) {
            for (Long tagId : tagIds) {
                bean.addToTags(this.productTagMng.findById(tagId));
            }
        }
        if (!ArrayUtils.isEmpty(keywords)) {
            bean.setKeywords(Arrays.asList(keywords));
        }
        bean.init();
        this.dao.save(bean);

        if ((names != null) && (names.length > 0)) {
            int i = 0;
            for (int len = names.length; i < len; i++) {
                if (!StringUtils.isBlank(names[i])) {
                    bean.addToExendeds(names[i], values[i]);
                }
            }
        }

        if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
            int i = 0;
            for (int len = fashionSwitchPic.length; i < len; i++) {
                if (!StringUtils.isBlank(fashionSwitchPic[i])) {
                    bean.addToPictures(fashionSwitchPic[i], fashionBigPic[i], fashionAmpPic[i]);
                }
            }
        }
        String uploadPath = this.realPathResolver.get(web.getUploadRel());
        saveImage(bean, ext, category.getType(), file, uploadPath);
        this.productExtMng.save(ext, bean);
        return bean;
    }

    public Product save1(Product bean, ProductExt ext, Long webId, Integer categoryId, Long brandId, String[] keywords, String[] names, String[] values, String[] fashionSwitchPic) {
        ProductText text = bean.getProductText();
        if (text != null) {
            text.setProduct(bean);
        }
        Category category = this.categoryMng.findById(categoryId);
        if (brandId != null) {
            bean.setBrand(this.brandMng.findById(brandId));
        }
        Website web = this.websiteMng.findById(webId);
        bean.setWebsite(web);
        bean.setConfig(this.shopConfigMng.findById(webId));
        bean.setCategory(category);
        bean.setType(category.getType());
        bean.init();

        if (!ArrayUtils.isEmpty(keywords)) {
            bean.setKeywords(Arrays.asList(keywords));
        }

        this.dao.save(bean);

        if ((names != null) && (names.length > 0)) {
            int i = 0;
            for (int len = names.length; i < len; i++) {
                if (!StringUtils.isBlank(names[i])) {
                    bean.addToExendeds(names[i], values[i]);
                }
            }
        }

        if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
            int i = 0;
            for (int len = fashionSwitchPic.length; i < len; i++) {
                if (!StringUtils.isBlank(fashionSwitchPic[i])) {
                    bean.addToPictures(fashionSwitchPic[i], fashionSwitchPic[i], fashionSwitchPic[i]);
                    if (i == 0) {
                        ext.setCoverImg(fashionSwitchPic[0]);
                    }
                }
            }

        }

        this.productExtMng.save(ext, bean);
        return bean;
    }

    public Product updateByUpdater(Product bean) {
        Updater updater = new Updater(bean);
        Product entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Product update(Product bean, ProductExt ext, Integer ctgId, Long brandId, Long[] tagIds, String[] keywords, String[] names, String[] values, Map<String, String> attr, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, MultipartFile file) {
        Product entity = findById(bean.getId());
        ProductText text = bean.getProductText();

        if (text != null) {
            ProductText ptext = entity.getProductText();
            if (ptext != null) {
                ptext.setText(text.getText());
                ptext.setText1(text.getText1());
                ptext.setText2(text.getText2());
            } else {
                text.setId(bean.getId());
                text.setProduct(entity);
                entity.setProductText(text);
                this.productTextMng.save(text);
            }
        }

        entity.removeAllTags();

        Category category = this.categoryMng.findById(ctgId);
        entity.setCategory(category);
        entity.setType(category.getType());
        if (entity.getStatus() == null) {
            entity.setStatus(Integer.valueOf(Product.DEL_STATUS));
        }

        if (brandId != null)
            entity.setBrand(this.brandMng.findById(brandId));
        else {
            entity.setBrand(null);
        }

        if (!ArrayUtils.isEmpty(tagIds)) {
            for (Long tagId : tagIds) {
                entity.addToTags(this.productTagMng.findById(tagId));
            }
        }

        if (keywords != null) {
            List kw = entity.getKeywords();
            kw.clear();
            kw.addAll(Arrays.asList(keywords));
        } else {
            entity.getKeywords().clear();
        }

        Updater updater = new Updater(bean);
        updater.exclude(BaseProduct.PROP_WEBSITE);
        updater.exclude(BaseProduct.PROP_PRODUCT_TEXT);
        entity = this.dao.updateByUpdater(updater);

        if (attr != null) {
            Map attrOrig = entity.getAttr();
            attrOrig.clear();
            attrOrig.putAll(attr);
        }

        entity.getExendeds().clear();
        if ((names != null) && (names.length > 0)) {
            int i = 0;
            for (int len = names.length; i < len; i++) {
                if (!StringUtils.isBlank(names[i])) {
                    entity.addToExendeds(names[i], values[i]);
                }
            }
        }

        entity.getPictures().clear();
        if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
            int i = 0;
            for (int len = fashionSwitchPic.length; i < len; i++) {
                if (!StringUtils.isBlank(fashionSwitchPic[i])) {
                    entity.addToPictures(fashionSwitchPic[i], fashionBigPic[i], fashionAmpPic[i]);
                }
            }
        }
        String uploadPath = this.realPathResolver.get(entity.getWebsite().getUploadRel());
        saveImage(entity, ext, category.getType(), file, uploadPath);
        this.productExtMng.saveOrUpdate(ext, entity);
        return entity;
    }

    public Product update1(Product bean, ProductExt ext, Integer ctgId, Long brandId, String[] keywords, String[] names, String[] values, Map<String, String> attr, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic) {
        Product entity = findById(bean.getId());
        ProductText text = bean.getProductText();

        if (text != null) {
            ProductText ptext = entity.getProductText();
            if (ptext != null) {
                ptext.setText(text.getText());
                ptext.setText1(text.getText1());
                ptext.setText2(text.getText2());
            } else {
                text.setId(bean.getId());
                text.setProduct(entity);
                entity.setProductText(text);
                this.productTextMng.save(text);
            }
        }

        entity.removeAllTags();

        Category category = this.categoryMng.findById(ctgId);
        entity.setCategory(category);
        entity.setType(category.getType());
        if (entity.getStatus() == null) {
            entity.setStatus(Integer.valueOf(0));
        }

        if (brandId != null)
            entity.setBrand(this.brandMng.findById(brandId));
        else {
            entity.setBrand(null);
        }

        if (!ArrayUtils.isEmpty(keywords)) {
            bean.setKeywords(Arrays.asList(keywords));
        }

        Updater updater = new Updater(bean);
        updater.exclude(BaseProduct.PROP_WEBSITE);
        updater.exclude(BaseProduct.PROP_PRODUCT_TEXT);
        entity = this.dao.updateByUpdater(updater);

        if (attr != null) {
            Map attrOrig = entity.getAttr();
            attrOrig.clear();
            attrOrig.putAll(attr);
        }

        entity.getExendeds().clear();
        if ((names != null) && (names.length > 0)) {
            int i = 0;
            for (int len = names.length; i < len; i++) {
                if (!StringUtils.isBlank(names[i])) {
                    entity.addToExendeds(names[i], values[i]);
                }
            }

        }

        entity.getPictures().clear();
        if ((fashionSwitchPic != null) && (fashionSwitchPic.length > 0)) {
            int i = 0;
            for (int len = fashionSwitchPic.length; i < len; i++) {
                if (!StringUtils.isBlank(fashionSwitchPic[i])) {
                    entity.addToPictures(fashionSwitchPic[i], fashionBigPic[i], fashionAmpPic[i]);
                    if (i == 0) {
                        ext.setCoverImg(fashionSwitchPic[0]);
                    }
                }
            }

        }

        this.productExtMng.saveOrUpdate(ext, entity);
        return entity;
    }

    public Product update(Product bean) {
        Updater updater = new Updater(bean);
        Product entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Product[] deleteByUpIds(Long[] ids) {
        Product[] beans = new Product[ids.length];

        int i = 0;
        for (int len = ids.length; i < len; i++) {
            Product product = this.dao.findById(ids[i]);
            product.setStatus(Integer.valueOf(Product.DEL_STATUS));
            update(product);
            beans[i] = product;
        }
        return beans;
    }

    public Product[] deleteByIds(Long[] ids) {
        Product[] beans = new Product[ids.length];
        int i = 0;
        List<Collect> clist;
        List<Consult> consultList;
        for (int len = ids.length; i < len; i++) {
            this.cartItemMng.deleteByProductId(ids[i]);
            clist = this.collectMng.findByProductId(ids[i]);
            for (Collect collect : clist) {
                this.collectMng.deleteById(collect.getId());
            }
            consultList = this.consultMng.findByProductId(ids[i]);
            for (Consult consult : consultList) {
                this.consultMng.deleteById(consult.getId());
            }
            List<ProductStandard> psList = this.productStandardMng.findByProductId(ids[i]);
            for (ProductStandard ps : psList) {
                this.productStandardMng.deleteById(ps.getId());
            }

            if (this.relatedgoodsMng.findByIdProductId(ids[i]) != null) {
                this.relatedgoodsMng.deleteProduct(ids[i]);
            }
            Product product = findById(ids[i]);
            product.getTags().clear();
            product.getFashions().clear();
            product.getKeywords().clear();
            product.getPopularityGroups().clear();
            beans[i] = this.dao.deleteById(ids[i]);
        }
        for (Product p : beans) {
            p.removeAllTags();
        }
        return beans;
    }

    private boolean saveImage(Product product, ProductExt bean, ProductType type, MultipartFile file, String uploadPath) {
        if ((file == null) || (file.isEmpty())) {
            return false;
        }

        deleteImage(product, uploadPath);

        String ext = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!ImageUtils.isImage(ext)) {
            return false;
        }

        String dateDir = FileNameUtils.genPathName();

        File root = new File(uploadPath, dateDir);

        String relPath = "/" + dateDir + "/";
        if (!root.exists()) {
            root.mkdirs();
        }

        String name = FileNameUtils.genFileName();

        File tempFile = new File(root, name);
        try {
            file.transferTo(tempFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            String detailName = name + Product.DETAIL_SUFFIX + "." + ext;
            File detailFile = new File(root, detailName);
            this.imageScale.resizeFix(tempFile, detailFile,
                    type.getDetailImgWidth().intValue(), type.getDetailImgHeight().intValue());
            bean.setDetailImg(relPath + detailName);

            String listName = name + Product.LIST_SUFFIX + "." + ext;
            File listFile = new File(root, listName);
            this.imageScale.resizeFix(tempFile, listFile, type.getListImgWidth().intValue(),
                    type.getListImgHeight().intValue());
            bean.setListImg(relPath + listName);

            String minName = name + Product.MIN_SUFFIX + "." + ext;
            File minFile = new File(root, minName);
            this.imageScale.resizeFix(tempFile, minFile, type.getMinImgWidth().intValue(), type
                    .getMinImgHeight().intValue());
            bean.setMinImg(relPath + minName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tempFile.delete();
        return true;
    }

    public void deleteImage(Product entity, String uploadPath) {
        String detail = entity.getDetailImg();
        if (!StringUtils.isBlank(detail)) {
            new File(uploadPath + detail).delete();
        }
        String list = entity.getListImg();
        if (!StringUtils.isBlank(list)) {
            new File(uploadPath + list).delete();
        }
        String min = entity.getMinImg();
        if (!StringUtils.isBlank(min))
            new File(uploadPath + min).delete();
    }

    public Integer getStoreByProductPattern(Long id, Long fashId) {
        ProductFashion bean = this.fashDao.getPfashion(id, fashId);
        return bean.getStockCount();
    }

    public List<Product> getHistoryProduct(HashSet<Long> set, Integer count) {
        return this.dao.getHistoryProduct(set, count);
    }

    public Integer getTotalStore(Long productId) {
        Product bean = this.dao.findById(productId);
        Set<ProductFashion> set = bean.getFashions();
        Integer store = Integer.valueOf(0);
        if (set != null) {
            for (ProductFashion f : set) {
                store = Integer.valueOf(store.intValue() + f.getStockCount().intValue());
            }
        }

        return store;
    }

    public String getTipFile(String key) {
        String TipFile = "/WEB-INF/languages/jspgou_admin/messages_zh_CN.properties";
        String TipFileText = null;
        try {
            InputStream in = new FileInputStream(this.realPathResolver.get(TipFile));
            Properties p = new Properties();
            p.load(in);
            TipFileText = p.getProperty(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TipFileText;
    }

    public Product saveByApi(Product bean, ProductExt ext, Long webId, Integer categoryId, Long brandId, String[] keywords, String[] names, String[] values, String[] fashionSwitchPics, Long[] pictures, String[] colorImgs, Long[] characters, String[] natures, Boolean[] isDefault, Integer[] stockCount, Double[] salePrice, Double[] marketPrice, Double[] costPrice)
            throws Exception {
        bean = save1(bean, ext, webId, categoryId, brandId, keywords, names, values, fashionSwitchPics);

        if (pictures != null) {
            for (int i = 0; i < pictures.length; i++) {
                ProductStandard ps = new ProductStandard();
                ps.setImgPath(colorImgs[i]);
                ps.setType(this.standardMng.findById(pictures[i]).getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(pictures[i]));
                ps.setDataType(true);
                this.productStandardMng.save(ps);
            }
        }

        if (characters != null) {
            for (int i = 0; i < characters.length; i++) {
                if ((pictures != null) && (pictures.length > 0) && (Arrays.asList(pictures).contains(characters[i]))) {
                    continue;
                }
                ProductStandard ps = new ProductStandard();
                ps.setType(this.standardMng.findById(characters[i])
                        .getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(characters[i]));
                ps.setDataType(false);
                this.productStandardMng.save(ps);
            }
        }
        saveProductFash(bean, natures, isDefault, stockCount,
                salePrice, marketPrice, costPrice);
        return bean;
    }

    public Product updateByApi(Product bean, ProductExt ext, Long brandId, String[] keywords, String[] names, String[] values, Map<String, String> attr, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, Integer categoryId, String[] fashionSwitchPics, Long[] pictures, String[] colorImgs, Long[] characters, Long[] fashionIds, String[] natures, Boolean[] isDefault, Integer[] stockCount, Double[] salePrice, Double[] marketPrice, Double[] costPrice)
            throws Exception {
        bean = update1(bean, ext, categoryId, brandId, keywords, names, values, attr, fashionSwitchPics,
                fashionSwitchPics, fashionSwitchPics);
        List pcList = this.productStandardMng
                .findByProductId(bean.getId());
        for (int j = 0; j < pcList.size(); j++) {
            this.productStandardMng.deleteById(((ProductStandard) pcList.get(j)).getId());
        }
        if (pictures != null) {
            for (int i = 0; i < pictures.length; i++) {
                ProductStandard ps = new ProductStandard();
                ps.setImgPath(colorImgs[i]);
                ps.setType(this.standardMng.findById(pictures[i]).getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(pictures[i]));
                ps.setDataType(true);
                this.productStandardMng.save(ps);
            }
        }
        if (characters != null) {
            for (int i = 0; i < characters.length; i++) {
                if ((pictures != null) && (pictures.length > 0) && (Arrays.asList(pictures).contains(characters[i]))) {
                    continue;
                }
                ProductStandard ps = new ProductStandard();
                ps.setType(this.standardMng.findById(characters[i])
                        .getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(characters[i]));
                ps.setDataType(false);
                this.productStandardMng.save(ps);
            }
        }
        if (bean.getCategory().getColorsize().booleanValue()) {
            Set<ProductFashion> pfList = bean
                    .getFashions();

            if (fashionIds != null)
                for (ProductFashion ps : pfList) {
                    if (!Arrays.asList(fashionIds).contains(
                            ps.getId())) {
                        this.fashMng.deleteById(ps.getId());
                        this.cartItemMng.deleteByProductFactionId(ps
                                .getId());
                    }
                }
            else {
                for (ProductFashion ps : pfList) {
                    this.fashMng.deleteById(ps.getId());
                    this.cartItemMng.deleteByProductFactionId(ps.getId());
                }
            }
            updateProductFash(bean, fashionIds, natures, isDefault,
                    stockCount, salePrice, marketPrice, costPrice);
        }
        return bean;
    }

    private void saveProductFash(Product bean, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices) {
        if (nature != null)
            for (int i = 0; i < nature.length; i++) {
                ProductFashion pfash = new ProductFashion();
                pfash.setCreateTime(new Date());
                pfash.setIsDefault(isDefaults[i]);
                pfash.setStockCount(stockCounts[i]);
                pfash.setMarketPrice(marketPrices[i]);
                pfash.setSalePrice(salePrices[i]);
                pfash.setCostPrice(costPrices[i]);
                pfash.setProductId(bean);
                pfash.setNature(nature[i]);
                String[] arr = nature[i].split("_");
                ProductFashion fashion = this.productFashionMng.save(pfash, arr);
                this.productFashionMng.addStandard(fashion, arr);
                if (isDefaults[i].booleanValue()) {
                    bean.setCostPrice(costPrices[i]);
                    bean.setMarketPrice(marketPrices[i]);
                    bean.setSalePrice(salePrices[i]);
                    update(bean);
                }
            }
    }

    private void updateProductFash(Product bean, Long[] fashId, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices) {
        if (nature != null)
            for (int i = 0; i < nature.length; i++) {
                if ((fashId != null) && (i < fashId.length) && (fashId[i].longValue() > 0L)) {
                    ProductFashion pfash = this.productFashionMng.findById(fashId[i]);
                    pfash.setCreateTime(new Date());
                    pfash.setIsDefault(isDefaults[i]);
                    pfash.setStockCount(stockCounts[i]);
                    pfash.setMarketPrice(marketPrices[i]);
                    pfash.setSalePrice(salePrices[i]);
                    pfash.setCostPrice(costPrices[i]);
                    pfash.setProductId(bean);
                    pfash.setNature(nature[i]);
                    String[] arr = nature[i].split("_");
                    this.productFashionMng.updateStandard(pfash, arr);

                    if (isDefaults[i].booleanValue()) {
                        bean.setCostPrice(costPrices[i]);
                        bean.setMarketPrice(marketPrices[i]);
                        bean.setSalePrice(salePrices[i]);
                        update(bean);
                    }
                } else {
                    ProductFashion pfash = new ProductFashion();
                    pfash.setCreateTime(new Date());
                    pfash.setIsDefault(isDefaults[i]);
                    pfash.setStockCount(stockCounts[i]);
                    pfash.setMarketPrice(marketPrices[i]);
                    pfash.setSalePrice(salePrices[i]);
                    pfash.setCostPrice(costPrices[i]);
                    pfash.setProductId(bean);
                    pfash.setNature(nature[i]);
                    String[] arr = nature[i].split("_");
                    ProductFashion fashion = this.productFashionMng.save(pfash, arr);
                    this.productFashionMng.addStandard(fashion, arr);
                    if (isDefaults[i].booleanValue()) {
                        bean.setCostPrice(costPrices[i]);
                        bean.setMarketPrice(marketPrices[i]);
                        bean.setSalePrice(salePrices[i]);
                        update(bean);
                    }
                }
            }
    }

    public Long getProductCount(Long webId) {
        return this.dao.getProductCount(webId);
    }

    public Long getOverStock(Long webId) {
        return this.dao.getOverStock(webId);
    }

    public Pagination getPageOverStockList(Long webId, int pageNo, int pageSize) {
        return this.dao.getPageOverStockList(webId, pageNo, pageSize);
    }
}

