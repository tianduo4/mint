/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseShopMemberAddress;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ShopMemberAddress extends BaseShopMemberAddress
/*    */   implements AddressInterface
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public String getMobile()
/*    */   {
/* 16 */     String mobile = "";
/* 17 */     if (StringUtils.isNotBlank(getPhone())) {
/* 18 */       if (StringUtils.isNotBlank(getAreaCode())) {
/* 19 */         mobile = getAreaCode() + "-";
/*    */       }
/* 21 */       mobile = mobile + getPhone();
/* 22 */       if (StringUtils.isNotBlank(getExtNumber())) {
/* 23 */         mobile = mobile + "-" + getExtNumber();
/*    */       }
/* 25 */       return mobile;
/*    */     }
/* 27 */     return mobile;
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress(Long id)
/*    */   {
/* 40 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress(Long id, ShopMember member, String province, String city, String country, Long provinceId, Long cityId, Long countryId, String username, String detailaddress, boolean isDefault)
/*    */   {
/* 70 */     super(id, 
/* 61 */       member, 
/* 62 */       province, 
/* 63 */       city, 
/* 64 */       country, 
/* 65 */       provinceId, 
/* 66 */       cityId, 
/* 67 */       countryId, 
/* 68 */       username, 
/* 69 */       detailaddress, 
/* 70 */       isDefault);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopMemberAddress
 * JD-Core Version:    0.6.0
 */