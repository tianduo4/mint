/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseProductType;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class ProductType extends BaseProductType
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public String getTplDirRel()
/*     */   {
/*  34 */     return "/" + "category";
/*     */   }
/*     */ 
/*     */   public String getCtgTplDirRel()
/*     */   {
/*  43 */     return "/" + "category";
/*     */   }
/*     */ 
/*     */   public String getTxtTplDirRel()
/*     */   {
/*  52 */     return "/" + "product";
/*     */   }
/*     */ 
/*     */   public String[] getChannelTpls(String realDir, String relPath)
/*     */   {
/*  64 */     return getTpls(realDir, relPath, getChannelPrefix());
/*     */   }
/*     */ 
/*     */   public String[] getContentTpls(String realDir, String relPath)
/*     */   {
/*  75 */     return getTpls(realDir, relPath, getContentPrefix());
/*     */   }
/*     */ 
/*     */   public static String[] getTpls(String realDir, String relPath, String prefix)
/*     */   {
/*  91 */     File dir = new File(realDir);
/*  92 */     File[] files = dir.listFiles(new FilenameFilter(prefix)
/*     */     {
/*     */       public boolean accept(File dir, String name)
/*     */       {
/*  96 */         name = name.substring(0, name.indexOf("."));
/*  97 */         return name.endsWith(ProductType.this);
/*     */       }
/*     */     });
/* 100 */     int len = files.length;
/* 101 */     String[] paths = new String[len];
/* 102 */     for (int i = 0; i < len; i++) {
/* 103 */       paths[i] = (relPath + "/" + files[i].getName());
/*     */     }
/* 105 */     return paths;
/*     */   }
/*     */ 
/*     */   public void addToexendeds(Exended exended)
/*     */   {
/* 112 */     Set set = getExendeds();
/* 113 */     if (set == null) {
/* 114 */       set = new HashSet();
/* 115 */       setExendeds(set);
/*     */     }
/* 117 */     set.add(exended);
/* 118 */     exended.addToProductTypes(this);
/*     */   }
/*     */ 
/*     */   public void removeFromExendeds(Exended exended)
/*     */   {
/* 123 */     Set set = getExendeds();
/* 124 */     if (set != null)
/* 125 */       set.remove(exended);
/*     */   }
/*     */ 
/*     */   public ProductType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ProductType(Long id)
/*     */   {
/* 139 */     super(id);
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson(Long root) throws JSONException {
/* 143 */     JSONObject json = new JSONObject();
/* 144 */     json.put("id", CommonUtils.parseId(getId()));
/* 145 */     json.put("name", CommonUtils.parseStr(getName()));
/* 146 */     json.put("channelPrefix", CommonUtils.parseStr(getChannelPrefix()));
/* 147 */     json.put("contentPrefix", CommonUtils.parseStr(getContentPrefix()));
/* 148 */     json.put("listImgHeight", CommonUtils.parseInteger(getListImgHeight()));
/* 149 */     json.put("listImgWidth", CommonUtils.parseInteger(getListImgWidth()));
/* 150 */     json.put("minImgHeight", CommonUtils.parseInteger(getMinImgHeight()));
/* 151 */     json.put("minImgWidth", CommonUtils.parseInteger(getMinImgWidth()));
/* 152 */     json.put("detailImgHeight", CommonUtils.parseInteger(getDetailImgHeight()));
/* 153 */     json.put("detailImgWidth", CommonUtils.parseInteger(getDetailImgWidth()));
/* 154 */     if (root != null) {
/* 155 */       json.put("root", root);
/*     */     }
/* 157 */     return json;
/*     */   }
/*     */ 
/*     */   public ProductType(Long id, Website website, String name, String path, String channelPrefix, String contentPrefix, Boolean refBrand, Integer detailImgWidth, Integer detailImgHeight, Integer listImgWidth, Integer listImgHeight, Integer minImgWidth, Integer minImgHeight)
/*     */   {
/* 191 */     super(id, 
/* 180 */       website, 
/* 181 */       name, 
/* 183 */       channelPrefix, 
/* 184 */       contentPrefix, 
/* 185 */       refBrand, 
/* 186 */       detailImgWidth, 
/* 187 */       detailImgHeight, 
/* 188 */       listImgWidth, 
/* 189 */       listImgHeight, 
/* 190 */       minImgWidth, 
/* 191 */       minImgHeight);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ProductType
 * JD-Core Version:    0.6.0
 */