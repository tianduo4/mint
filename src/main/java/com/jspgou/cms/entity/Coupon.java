/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseCoupon;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Date;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Coupon extends BaseCoupon
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Coupon()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Coupon(Long id)
/*    */   {
/* 26 */     super(id);
/*    */   }
/*    */ 
/*    */   public Coupon(Long id, Website website, String couponName, Date couponTime, Date couponEndTime, String couponPicture, BigDecimal couponPrice, BigDecimal leastPrice, Boolean isusing, Integer couponCount)
/*    */   {
/* 54 */     super(id, 
/* 46 */       website, 
/* 47 */       couponName, 
/* 48 */       couponTime, 
/* 49 */       couponEndTime, 
/* 50 */       couponPicture, 
/* 51 */       couponPrice, 
/* 52 */       leastPrice, 
/* 53 */       isusing, 
/* 54 */       couponCount);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 60 */     JSONObject json = new JSONObject();
/* 61 */     json.put("id", CommonUtils.parseId(getId()));
/* 62 */     json.put("couponName", CommonUtils.parseStr(getCouponName()));
/* 63 */     json.put("couponTime", CommonUtils.parseDate(getCouponTime(), DateUtils.COMMON_FORMAT_STR));
/* 64 */     json.put("couponEndTime", CommonUtils.parseDate(getCouponEndTime(), DateUtils.COMMON_FORMAT_STR));
/* 65 */     json.put("couponPicture", CommonUtils.parseStr(getCouponPicture()));
/* 66 */     json.put("isusing", CommonUtils.parseBoolean(getIsusing()));
/* 67 */     json.put("couponPrice", CommonUtils.parseBigDecimal(getCouponPrice()));
/* 68 */     json.put("leastPrice", CommonUtils.parseBigDecimal(getLeastPrice()));
/* 69 */     json.put("couponCount", Integer.valueOf(CommonUtils.parseInteger(getCouponCount())));
/* 70 */     json.put("categoryId", getCategory() != null ? Integer.valueOf(CommonUtils.parseInteger(getCategory().getId())) : "");
/* 71 */     json.put("categoryName", getCategory() != null ? CommonUtils.parseStr(getCategory().getName()) : "全品类");
/* 72 */     json.put("comments", CommonUtils.parseStr(getComments()));
/* 73 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Coupon
 * JD-Core Version:    0.6.0
 */