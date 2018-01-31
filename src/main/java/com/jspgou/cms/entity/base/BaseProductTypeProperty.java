/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ProductTypeProperty;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseProductTypeProperty
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ProductTypeProperty";
/*  24 */   public static String PROP_COLS = "cols";
/*  25 */   public static String PROP_SORT = "sort";
/*  26 */   public static String PROP_ROWS = "rows";
/*  27 */   public static String PROP_FIELD = "field";
/*  28 */   public static String PROP_DATA_TYPE = "dataType";
/*  29 */   public static String PROP_IS_NOT_NULL = "isNotNull";
/*  30 */   public static String PROP_PROPERTY_TYPE = "propertyType";
/*  31 */   public static String PROP_CUSTOM = "custom";
/*  32 */   public static String PROP_PROPERTY_NAME = "propertyName";
/*  33 */   public static String PROP_IS_START = "isStart";
/*  34 */   public static String PROP_SINGLE = "single";
/*  35 */   public static String PROP_OPT_VALUE = "optValue";
/*  36 */   public static String PROP_CATEGORY = "category";
/*  37 */   public static String PROP_DEF_VALUE = "defValue";
/*  38 */   public static String PROP_ID = "id";
/*     */ 
/*  88 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String propertyName;
/*     */   private String field;
/*     */   private Integer isStart;
/*     */   private Integer isNotNull;
/*     */   private Integer sort;
/*     */   private String defValue;
/*     */   private String optValue;
/*     */   private String rows;
/*     */   private String cols;
/*     */   private Integer dataType;
/*     */   private Boolean single;
/*     */   private Boolean category;
/*     */   private Boolean custom;
/*     */   private ProductType propertyType;
/*     */ 
/*     */   public BaseProductTypeProperty()
/*     */   {
/*  43 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductTypeProperty(Long id)
/*     */   {
/*  50 */     setId(id);
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductTypeProperty(Long id, ProductType propertyType, String propertyName, String field, Integer isStart, Integer isNotNull, Integer sort, Integer dataType, Boolean single, Boolean category, Boolean custom)
/*     */   {
/*  70 */     setId(id);
/*  71 */     setPropertyType(propertyType);
/*  72 */     setPropertyName(propertyName);
/*  73 */     setField(field);
/*  74 */     setIsStart(isStart);
/*  75 */     setIsNotNull(isNotNull);
/*  76 */     setSort(sort);
/*  77 */     setDataType(dataType);
/*  78 */     setSingle(single);
/*  79 */     setCategory(category);
/*  80 */     setCustom(custom);
/*  81 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 120 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 128 */     this.id = id;
/* 129 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getPropertyName()
/*     */   {
/* 139 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(String propertyName)
/*     */   {
/* 147 */     this.propertyName = propertyName;
/*     */   }
/*     */ 
/*     */   public String getField()
/*     */   {
/* 155 */     return this.field;
/*     */   }
/*     */ 
/*     */   public void setField(String field)
/*     */   {
/* 163 */     this.field = field;
/*     */   }
/*     */ 
/*     */   public Integer getIsStart()
/*     */   {
/* 171 */     return this.isStart;
/*     */   }
/*     */ 
/*     */   public void setIsStart(Integer isStart)
/*     */   {
/* 179 */     this.isStart = isStart;
/*     */   }
/*     */ 
/*     */   public Integer getIsNotNull()
/*     */   {
/* 187 */     return this.isNotNull;
/*     */   }
/*     */ 
/*     */   public void setIsNotNull(Integer isNotNull)
/*     */   {
/* 195 */     this.isNotNull = isNotNull;
/*     */   }
/*     */ 
/*     */   public Integer getSort()
/*     */   {
/* 203 */     return this.sort;
/*     */   }
/*     */ 
/*     */   public void setSort(Integer sort)
/*     */   {
/* 211 */     this.sort = sort;
/*     */   }
/*     */ 
/*     */   public String getDefValue()
/*     */   {
/* 219 */     return this.defValue;
/*     */   }
/*     */ 
/*     */   public void setDefValue(String defValue)
/*     */   {
/* 227 */     this.defValue = defValue;
/*     */   }
/*     */ 
/*     */   public String getOptValue()
/*     */   {
/* 235 */     return this.optValue;
/*     */   }
/*     */ 
/*     */   public void setOptValue(String optValue)
/*     */   {
/* 243 */     this.optValue = optValue;
/*     */   }
/*     */ 
/*     */   public String getRows()
/*     */   {
/* 251 */     return this.rows;
/*     */   }
/*     */ 
/*     */   public void setRows(String rows)
/*     */   {
/* 259 */     this.rows = rows;
/*     */   }
/*     */ 
/*     */   public String getCols()
/*     */   {
/* 267 */     return this.cols;
/*     */   }
/*     */ 
/*     */   public void setCols(String cols)
/*     */   {
/* 275 */     this.cols = cols;
/*     */   }
/*     */ 
/*     */   public Integer getDataType()
/*     */   {
/* 283 */     return this.dataType;
/*     */   }
/*     */ 
/*     */   public void setDataType(Integer dataType)
/*     */   {
/* 291 */     this.dataType = dataType;
/*     */   }
/*     */ 
/*     */   public Boolean getSingle()
/*     */   {
/* 299 */     return this.single;
/*     */   }
/*     */ 
/*     */   public void setSingle(Boolean single)
/*     */   {
/* 307 */     this.single = single;
/*     */   }
/*     */ 
/*     */   public Boolean getCategory()
/*     */   {
/* 315 */     return this.category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Boolean category)
/*     */   {
/* 323 */     this.category = category;
/*     */   }
/*     */ 
/*     */   public Boolean getCustom()
/*     */   {
/* 331 */     return this.custom;
/*     */   }
/*     */ 
/*     */   public void setCustom(Boolean custom)
/*     */   {
/* 339 */     this.custom = custom;
/*     */   }
/*     */ 
/*     */   public ProductType getPropertyType()
/*     */   {
/* 347 */     return this.propertyType;
/*     */   }
/*     */ 
/*     */   public void setPropertyType(ProductType propertyType)
/*     */   {
/* 355 */     this.propertyType = propertyType;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 362 */     if (obj == null) return false;
/* 363 */     if (!(obj instanceof ProductTypeProperty)) return false;
/*     */ 
/* 365 */     ProductTypeProperty productTypeProperty = (ProductTypeProperty)obj;
/* 366 */     if ((getId() == null) || (productTypeProperty.getId() == null)) return false;
/* 367 */     return getId().equals(productTypeProperty.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 373 */     if (-2147483648 == this.hashCode) {
/* 374 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 376 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 377 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 380 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 386 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductTypeProperty
 * JD-Core Version:    0.6.0
 */